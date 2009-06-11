import java.io.*;

public class Test {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		XAALScripter scripter = new XAALScripter();
		
		Scope s = new Scope("Global", "blue");
		s.addVariable("x", 3);
		s.addVariable("y", 12);
		s.draw(scripter);
		FileWriter writer = new FileWriter("/home/fairfieldt/Documents/!test.xaal");
		
		writer.write(scripter.toString());
		
		writer.close();
	}

}
