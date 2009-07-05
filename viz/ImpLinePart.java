package viz;

public class ImpLinePart extends LinePart {
	private String id;
	private static int impNum = 0;
	
	public ImpLinePart(String s)
	{
		super(s);
		this.id = "impId" + impNum;
	}
	
	public String getId()
	{
		return id;
	}
}
