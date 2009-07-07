package viz;
import Interpreter.*;

import java.io.*;

public class NewTest2
{
	public static String currentPage;
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
				
//			program.jjtAccept(rv, null);

			System.out.println("Successfully Parsed");
			System.out.println("________________\n");

			program.dump("");
			program.buildCode();
			System.out.println("Built code");
			

			String[] info = {"Step 1. Copy in the arguments",
					 "Step 2. Copy in the function body",
					 "Step 3. Run the program.  (Click next to begin)"};
					 
			XAALConnector xc = new XAALConnector(info, "foo");			//program.dump("");
			String p0 = xc.addCodePage(program.getPseudocode());
			NewTest.currentPage = p0;
			xc.startSnap(0);
			xc.startPar();
				xc.showCodePage(p0);
			xc.endPar();
			xc.endSnap();
			xc.startSnap(1);
			xc.startPar();
			
			ByMacroVisitor bm = new ByMacroVisitor();
			bm.setXAALConnector(xc);
			program.jjtAccept(bm, null);
			
			System.out.println("MACRO TIME");		
			Global.lineNumber = 1;
			program.codeBuilt = false;
			program.buildCode();
						for (String line: program.getPseudocode())
						{
							System.out.println(line);
						}
			String p2 = xc.addCodePage(program.getPseudocode());
			xc.swapCodePage(p0, p2);
			xc.endPar();
			xc.endSnap();

			xc.startSnap(2);
			ByMacroVisitor2 bm2 = new ByMacroVisitor2();
			program.jjtAccept(bm2, null);
			Global.lineNumber = 1;
			program.codeBuilt = false;
			program.buildCode();
				String p3 = xc.addCodePage(program.getPseudocode());
			xc.startPar();
				xc.hideCodePage(p2);
				xc.showCodePage(p3);
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
			
			System.out.println("\n\n Testing Interpret Visitor");
			
			QuestionFactory questionFactory = new QuestionFactory();
			
			InterpretVisitor iv = new InterpretVisitor();
			iv.setXAALConnector(xc);
			iv.setQuestionFactory(questionFactory);
			program.jjtAccept(iv, null);
			System.out.println(Global.getFunction("foo").getParameters().size());
			System.out.println(Global.getFunction("foo").getSymbolTable().getLocalVariables().size());
			xc.draw("C:\\Users\\Eric\\Desktop\\bymacro.xaal");
			

		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}
