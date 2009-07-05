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
		XAALScripter scripter = new XAALScripter();
		CodePageContainer mc = new CodePageContainer();
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
