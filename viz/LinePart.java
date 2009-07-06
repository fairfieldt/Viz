package viz;

public class LinePart {
	protected String s;
	private static int lpNum = 0;
	protected String id;
	
	public LinePart(String s)
	{
		this.s = s;
		this.id = "lp" + lpNum;
		lpNum++;
	}
	
	public String getValue()
	{
		return s;
	}
	
	public void setValue(String s)
	{
		this.s = s;
	}
	
	public String getId()
	{
		return id;
	}
}
