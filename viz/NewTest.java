package viz;
import Interpreter.*;

public class NewTest
{
	public static void main(String[] args)
	{
		VizParser parser = new VizParser(System.in);
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

		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}
