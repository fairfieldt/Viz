package viz;

public class MovePartCodePageAction extends CodePageAction {

	String[] toParts;
	String fromPart;
	public MovePartCodePageAction(CodePage cp, int slideNum, String fromPart, String...toParts)
	{
		super(cp, slideNum);
		this.fromPart = fromPart;
		this.toParts = toParts;
	}
	
	public String getFromPart()
	{
		return fromPart;
	}
	
	public String[] getToParts()
	{
		return toParts;
	}
}
