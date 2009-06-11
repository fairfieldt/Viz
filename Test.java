import java.io.*;

public class Test {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		XAALScripter scripter = new XAALScripter();
		
		String rect1 = scripter.addRectangle(10, 10, 60 , 60);
		String rect2 = scripter.addRectangle(10, 110, 60, 160);
		String text1 = scripter.addText(50, 20, "awesome");
		System.out.println("Rect1: " + rect1);
		scripter.addArrow(rect1, rect2, false);
		FileWriter writer = new FileWriter("!test.xaal");
		
		writer.write(scripter.toString());
		
		writer.close();
	}

}
