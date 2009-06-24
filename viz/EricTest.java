package viz;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import Interpreter.*;

public class EricTest
{
	public static void main(String[] args)
	{
		FileInputStream stream = null;
		try {
			stream = new FileInputStream(args[0]);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		VizParser parser = new VizParser(stream);
		try
		{
			ASTProgram program = (ASTProgram)parser.program();

			RandomizingVisitor rv = new RandomizingVisitor();
				
			program.jjtAccept(rv, null);
		
			System.out.println("Successfully Parsed");
			System.out.println("________________\n");
			program.buildCode();
			
			XAALConnector xc = new XAALConnector(program.getPseudocode(), "foo");
		
			for (String s : program.getPseudocode())
			{
				System.out.println(s);
			}
			System.out.println("\n\n Testing Interpret Visitor");
			
			QuestionFactory questionFactory = new QuestionFactory();
			
			InterpretVisitor iv = new InterpretVisitor();
			iv.setXAALConnector(xc);
			iv.setQuestionFactory(questionFactory);
			program.jjtAccept(iv, null);
			
			xc.draw("C:\\Users\\Eric\\Desktop\\EricTest.xaal");

		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}

