package viz;

import java.util.StringTokenizer;
import java.util.regex.*;

/**
 * extremely dependent on the format of the code
 * @author Eric
 *
 */
public class CodePageLoader 
{
	Pattern callP2 = Pattern.compile("\\w*foo\\(([a-z](?:\\[[\\d]\\])?), ([a-z](?:\\[[\\d]\\])?)\\);\\w*");
	Pattern callP3 = Pattern.compile("\\w*foo\\(([a-z]), ([a-z]), ([a-z])\\);\\w*");
	Pattern def2 = Pattern.compile("def foo\\(x, y\\)");
	Pattern def3 = Pattern.compile("def foo\\(x, y, z\\)");
	Pattern endSquigBrkt = Pattern.compile("\\}");
	
	String[] code;
	public CodePageLoader(String[] code)
	{
		this.code = code;
	}
	
	public CodePage createCodePage(String id)
	{
		CodePage cp = new CodePage(id);
		String xArg = null;
		String yArg = null;
		String zArg = null;
		
		for (int i = 0; i < code.length; i++)
		{
			String test = code[i];
			
			if (test.contains("foo(") && !test.contains("def")) // its a call
			{
				StringTokenizer t = new StringTokenizer(test, "(,)");
				boolean insideCall = false;
				for(int j =0; j < t.countTokens(); j++)
				{
					String s = t.nextToken();
					//if (s.equals("("))
						
						
				}
			}
			else if (test.matches(callP3.toString()))
			{
			
			}
		}
		
		return null;
	}
}
