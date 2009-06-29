package viz;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import Interpreter.ASTProgram;
import Interpreter.InterpretVisitor;
import Interpreter.RandomizingVisitor2;
import Interpreter.VizParser;

public class EricTest2 {

	public static void main(String[] args)
	{
		FileInputStream stream = null;
		try {
			stream = new FileInputStream(args[0]);
		} catch (FileNotFoundException e1) {

			e1.printStackTrace();
		}
		VizParser parser = new VizParser(stream);
		try
		{
			ASTProgram program = (ASTProgram)parser.program();

			RandomizingVisitor2<Interpreter.ByValVariable> rv = new RandomizingVisitor2<Interpreter.ByValVariable>(Interpreter.ByValVariable.class);
				
			program.jjtAccept(rv, null);
		
			System.out.println("Successfully Parsed");
			System.out.println("________________\n");
			program.buildCode();
			
		
			for (String s : program.getPseudocode())
			{
				System.out.println(s);
			}
			

		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}
