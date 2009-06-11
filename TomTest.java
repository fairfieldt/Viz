import java.io.*;

public class TomTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception 
	{
		XAALScripter scripter = new XAALScripter();
		
		Scope s = new Scope("Global", "blue");
		s.addVariable("x", 3);
		s.addVariable("y", 12);
		s.addScope(new Scope("main", "red"));
		s.draw(scripter);
		
		
		//Start writing slides
		scripter.startSlide();
		scripter.endSlide();
		
		
		FileWriter writer = new FileWriter("/home/fairfieldt/Documents/!test.xaal");
		
		writer.write(scripter.toString());
		
		writer.close();
	}

}
