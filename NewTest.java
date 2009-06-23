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
			System.out.println(program.getCode());
		
			System.out.println("\n\n Testing Interpret Visitor");
		
			InterpretVisitor iv = new InterpretVisitor();
			program.jjtAccept(iv, null);

		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		
		
		QuestionFactory questionFactory = new QuestionFactory();
	}
}
