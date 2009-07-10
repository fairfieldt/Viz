package viz;
import Interpreter.*;
import java.io.*;

public class NewTest
{
	public static void main(String[] args)
	{
		Global.InterpreterType = InterpreterTypes.BY_VALUE;
		
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

			RandomizingVisitor2<ByValVariable> rv = new RandomizingVisitor2<ByValVariable>(ByValVariable.class);
				
			program.jjtAccept(rv, null);

			System.out.println("Successfully Parsed");
			System.out.println("________________\n");

			program.dump("");
			program.buildCode();
			for (String s : program.getPseudocode())
			{
				System.out.println(s);
			}
			System.out.println("Built code");
			

			String[] info = {"Step 1. Copy in the arguments\nEach argument to the function being called is\ncopied textually into the function body.",
					 "Step 2. Copy in the function body\nThe entire function body is copied into the\ncalling function.  It has its own scope \nwhich is a subscope of the calling function.",
					 "Step 3. Run the program.  (Click next to begin)\nNow the program is run just as in other\nevaluation strategies."
					 };
					 
			XAALConnector xc = new XAALConnector(info, "Call by Macro");			//program.dump("");
			String p0 = xc.addCodePage(program.getPseudocode());
			Global.currentPage = p0;
			xc.startSnap(0);
			xc.startPar();
				xc.showCodePage(p0);
			xc.endPar();
			xc.endSnap();
			xc.startSnap(1);
			xc.startPar();
			System.out.println("asdf");
			ByMacroVisitor bm = new ByMacroVisitor();
			bm.setXAALConnector(xc);
			program.jjtAccept(bm, null);

			System.out.println("jkl;");
			
			//System.out.println("MACRO TIME");		
			Global.lineNumber = 1;
			program.codeBuilt = false;
			program.buildCode();
						for (String line: program.getPseudocode())
						{
							System.out.println(line);
						}
			System.out.println("ASDF" + removeLineNumbers(program.getPseudocode()));
			String p2 = xc.addCodePage(removeLineNumbers(program.getPseudocode()));
			xc.swapCodePage(p0, p2);			
			xc.endPar();
			xc.endSnap();

			xc.startSnap(2);
			ByMacroVisitor2 bm2 = new ByMacroVisitor2();
			program.jjtAccept(bm2, null);
			Global.lineNumber = 1;
			program.codeBuilt = false;
			program.buildCode();
for (String s : program.getPseudocode())
			{
				System.out.println(s);
			}
				String p3 = null;
			xc.startPar();
			
			//Prettier now
			System.out.println("Global.callLine: " + Global.callLine + " start " + Global.startScope + " end " + Global.endScope + " Global.endMain " + (Global.endMain+2));
			xc.replaceWithScope(p2, Global.callLine-1, Global.startScope, Global.endScope, Global.endMain+1);
			p3 = xc.addCodePage(program.getPseudocode());
			/*
				xc.hideCodePage(p2);
				xc.showCodePage(p3);
			*/
			xc.endPar();
			xc.startPar();
			xc.swapCodePage(p2, p3, true);			
			xc.endPar();
			
			
			xc.endSnap();
			
			xc.startSnap(3);
			xc.startPar();
				xc.hideCodePage(p3);
			xc.endPar();
			xc.endSnap();
			xc.startSnap(1, program.getPseudocode());
			xc.startPar();
			xc.endPar();
			xc.endSnap();
			
			//System.out.println("\n\n Testing Interpret Visitor");
			
			QuestionFactory questionFactory = new QuestionFactory();
			
			InterpretVisitor iv = new InterpretVisitor();
			iv.setXAALConnector(xc);
			iv.setQuestionFactory(questionFactory);
			program.jjtAccept(iv, null);
			
			xc.draw("/home/fairfieldt/Documents/!real.xaal");
			

		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	
	private static String[] removeLineNumbers(String[] code)
	{
		for (String s : code)
		{
			s = s.replaceFirst("(\\d)*.", "");
			System.out.println("D" + s);
		}
		return code;
	}

}
