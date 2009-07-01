import Interpreter.*;
import viz.*;
import java.io.*;

public class CopyRestore
{
	public static void main(String[] args)
	{
		System.out.println("Starting");
		Global.InterpreterType = InterpreterTypes.BY_COPY_RESTORE;
		BufferedReader br = null;
		String name = "Samples/shell.src";
		try
		{
			br = new BufferedReader(new FileReader(name));
		}
		catch (Exception e)
		{
			System.out.println("Eh?");
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
			
			XAALConnector xc = new XAALConnector(program.getPseudocode(), "foo");
		
			QuestionFactory questionFactory = new QuestionFactory();
			
			CopyRestoreInterpretVisitor iv = new CopyRestoreInterpretVisitor();
			iv.setXAALConnector(xc);
			iv.setQuestionFactory(questionFactory);
			program.jjtAccept(iv, null);
			String fileName = "by_copy_restore.xaal";
			
			xc.draw(args[0]);
			System.out.println("Visualization file created");

		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}
