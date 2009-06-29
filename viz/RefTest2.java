package viz;
import Interpreter.*;

import java.io.*;

public class RefTest2
{
	public static void main(String[] args)
	{
		System.out.println("Starting");
		Global.InterpreterType = InterpreterTypes.BY_REFERENCE;
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

			RandomizingVisitor2<Interpreter.ByRefVariable> rv = new RandomizingVisitor2<Interpreter.ByRefVariable>(Interpreter.ByRefVariable.class);
				
			program.jjtAccept(rv, null);
		
			System.out.println("Successfully Parsed");
			System.out.println("________________\n");
			
			program.dump("");
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
			
			ByRefInterpretVisitor iv = new ByRefInterpretVisitor();
			iv.setXAALConnector(xc);
			iv.setQuestionFactory(questionFactory);
			program.jjtAccept(iv, null);
			System.out.println(Global.getFunction("foo").getParameters().size());
			System.out.println(Global.getFunction("foo").getSymbolTable().getLocalVariables().size());
			xc.draw("C:\\Users\\Eric\\Desktop\\byref.xaal");
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
