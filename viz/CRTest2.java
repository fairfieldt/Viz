package viz;
import Interpreter.*;
import java.io.*;

public class CRTest2
{
	public static void main(String[] args)
	{
		System.out.println("Starting");
		Global.InterpreterType = InterpreterTypes.BY_COPY_RESTORE;
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new FileReader(args[0]));
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
				
			//program.jjtAccept(rv, null);
		
			System.out.println("Successfully Parsed");
			System.out.println("________________\n");
			
			program.buildCode();
			System.out.println("Built code");
			
			//program.dump("");
			XAALConnector xc = new XAALConnector(program.getPseudocode(), "foo");
		
			for (String s : program.getPseudocode())
			{
				System.out.println(s);
			}
			System.out.println("\n\n Testing Interpret Visitor");
			
			QuestionFactory questionFactory = new QuestionFactory();
			
			CopyRestoreInterpretVisitor iv = new CopyRestoreInterpretVisitor();
			iv.setXAALConnector(xc);
			iv.setQuestionFactory(questionFactory);
			program.jjtAccept(iv, null);
			System.out.println(Global.getFunction("foo").getParameters().size());
			System.out.println(Global.getFunction("foo").getSymbolTable().getLocalVariables().size());
			xc.draw("C:\\Users\\Eric\\Desktop\\cr.xaal");
						for (String line: program.getPseudocode())
						{
							System.out.println(line);
						}

		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}
