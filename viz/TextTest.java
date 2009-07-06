package viz;

import java.io.FileWriter;
import java.io.IOException;

public class TextTest {

	static String[] code = {"var b[] = {5, 2, 5, 7};",
			"var h = 3;",
			"def foo(x, y, z)",
	        "	p = x + h;"};
	/**
	 * @param args
	 * @throws SlideException 
	 * @throws ParException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws SlideException, ParException, IOException {
		String[] psuedoCode = {""}; 
		XAALConnector conn = new XAALConnector(psuedoCode, "title");
		
		String codePageId = conn.addCodePage();
		
		conn.addLinePart(codePageId, "var g = 1;", 1);
		conn.addLinePart(codePageId, "def foo(x, y, z)", 2);
		conn.addLinePart(codePageId, "{", 3);
			conn.addLinePart(codePageId, "\tvar v = 5;", 4);
			conn.addLinePart(codePageId, "\t", 5);
			conn.addImportantPart(codePageId, "x", 5);
			conn.addLinePart(codePageId, " = ", 5);
			
		
		/*
		MultiText m = new MultiText();
		mc.add(m);
		
		for (int i = 0; i < code.length; i++)
		{
			m.addLine(i+1, code[i]);
		}
		
		mc.draw(scripter);
		
		scripter.startSlide();
		scripter.startPar();
		scripter.endPar();
		scripter.endSlide();
		
		FileWriter writer = new FileWriter("C:\\Users\\Eric\\Desktop\\tomtest.xaal");
		
		writer.write(scripter.toString());
		
		writer.close();
*/
	}

}
