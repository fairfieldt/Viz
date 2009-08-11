import Interpreter.*;
import viz.*;
import java.io.*;

public class EricByReference
{
	public static void main(String[] args)
	{
		System.out.println("Starting");
		Global.InterpreterType = InterpreterTypes.BY_REFERENCE;
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new FileReader("C:\\Users\\Eric\\newws\\viz\\src\\Samples\\shell.src"));
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		VizParser parser = new VizParser(br);
		try
		{
			ASTProgram program = (ASTProgram)parser.program();

			RandomizingVisitor2<ByRefVariable> rv = new RandomizingVisitor2<ByRefVariable>(ByRefVariable.class);
				
			program.jjtAccept(rv, null);
		
			System.out.println("Successfully Parsed");
			System.out.println("________________\n");
			
			program.buildCode();
			
			XAALConnector xc = new XAALConnector(program.getPseudocode(), "Call by Reference");
		
			QuestionFactory questionFactory = new QuestionFactory();
			
			ByRefInterpretVisitor iv = new ByRefInterpretVisitor();
			iv.setXAALConnector(xc);
			iv.setQuestionFactory(questionFactory);
			program.jjtAccept(iv, null);
			xc.draw(args[0]);
			System.out.println("Visualization file created");

		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}
