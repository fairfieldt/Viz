package viz;

public class ImpLinePart extends LinePart {
	
	private static int impNum = 0;
	
	public ImpLinePart(String s)
	{
		super(s);
		this.id = "impId" + impNum;
		impNum++;
	}
	

}
