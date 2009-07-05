package viz;

import java.util.*;

public class CodePageContainer extends HashMap<String, CodePage> implements Drawable
{

	private static final long serialVersionUID = -8369702287728917615L;

	private int mtNum = 0;
	
	public String createCodePage()
	{
		CodePage cp = new CodePage("ml" + mtNum);
		mtNum++;
		this.put(cp.getId(), cp);
		
		return cp.getId();
	}
	
	@Override
	public void draw(XAALScripter scripter) {
		Set<String> keys = this.keySet();
		for(String s : keys)
		{
			this.get(s).draw(scripter);
		}
		
	}

}
