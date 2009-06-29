import Interpreter.*;
import viz.*;
import java.io.*;

public class ByValue
{
	public static void main(String[] args)
	{
		System.out.println("Starting");
		Global.InterpreterType = InterpreterTypes.BY_VALUE;
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new FileReader("Samples/shell.src"));
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		VizParser parser = new VizParser(br);
		try
		{
			ASTProgram program = (ASTProgram)parser.program();

			RandomizingVisitor2<ByValVariable> rv = new RandomizingVisitor2<ByValVariable>(ByValVariable.class);
				
			program.jjtAccept(rv, null);
		
			System.out.println("Successfully Parsed");
			System.out.println("________________\n");
			
			program.buildCode();
			
			XAALConnector xc = new XAALConnector(program.getPseudocode(), "foo");
		
			QuestionFactory questionFactory = new QuestionFactory();
			
			InterpretVisitor iv = new InterpretVisitor();
			iv.setXAALConnector(xc);
			iv.setQuestionFactory(questionFactory);
			program.jjtAccept(iv, null);
			xc.draw("by_value.xaal");
			System.out.println("Visualization file by_value.xaal created");

		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}
