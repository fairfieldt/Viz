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
		Variable var1 = new Variable("x", 3, false);
		Variable var2 = new Variable("y", 12, false);
		s.addVariable(var1);
		s.addVariable(var2);
		Scope main = new Scope("main", "red");
		Variable var3 = new Variable("a", 11, true);
		var3.setReference(var1);
		main.addVariable(var3);
		s.addScope(main);
		s.addScope(new Scope("foo", "green"));
		s.draw(scripter);
		
		
		//Start writing slides
		scripter.startSlide();
		scripter.endSlide();
		
		
		FileWriter writer = new FileWriter("C:\\Users\\Eric\\Desktop\\tomxaal.xaal");
		
		writer.write(scripter.toString());
		
		writer.close();
	}

}
