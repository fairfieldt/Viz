import java.io.*;

public class Test {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		XAALScripter scripter = new XAALScripter();
		
		scripter.addRectangle(12, 30, 100, 200);
		scripter.addText(50, 20, "awesome");
		
		FileWriter writer = new FileWriter("C:\\Users\\Eric\\Desktop\\test.xaal");
		
		writer.write(scripter.toString());
		
		writer.close();
	}

}
