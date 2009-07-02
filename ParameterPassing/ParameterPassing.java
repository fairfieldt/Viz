
package ParameterPassing;
import java.util.*;
import Interpreter.*;
import viz.*;

import java.io.*;
public class ParameterPassing {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Random r = new Random();
		int num = r.nextInt(3);
		
		switch (num)
		{
			case 0: byValue(args);
				break;
			case 1: byRef(args);
				break;
			case 2: byCR(args);
				break;
		}
	

	}
	
	private static void byValue(String[] args)
	{
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
			xc.draw(args[0]);
			System.out.println("Visualization file created");

		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	
	}
	
	private static void byRef(String[] args)
	{
		Global.InterpreterType = InterpreterTypes.BY_REFERENCE;
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

			RandomizingVisitor2<ByRefVariable> rv = new RandomizingVisitor2<ByRefVariable>(ByRefVariable.class);
				
			program.jjtAccept(rv, null);
		
			System.out.println("Successfully Parsed");
			System.out.println("________________\n");
			
			program.buildCode();
			
			XAALConnector xc = new XAALConnector(program.getPseudocode(), "foo");
		
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
	
	private static void byCR(String[] args)
	{
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

			RandomizingVisitor2<ByCopyRestoreVariable> rv = new RandomizingVisitor2<ByCopyRestoreVariable>(ByCopyRestoreVariable.class);
				
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
		}
		
	}

}
