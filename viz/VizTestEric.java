package viz;

import java.io.FileWriter;
import java.io.IOException;

public class VizTestEric {

	/**
	 * @param args
	 * @throws ParException 
	 * @throws SlideException 
	 */
	public static void main(String[] args) throws SlideException, ParException 
	{
		XAALScripter scripter = new XAALScripter();
		
		String rect1 = scripter.addRectangle(50, 50, 100, 100);
		
		scripter.startSlide();
		scripter.startPar();
		scripter.addChangeStyle("gray", true, rect1);
		scripter.endPar();
		scripter.endSlide();
		
		
		// write to the file
		FileWriter writer;
		try {
			writer = new FileWriter("C:\\Users\\Eric\\Desktop\\neato.xaal");

			writer.write(scripter.toString());

			writer.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
