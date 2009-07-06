package viz;

public class MoveArgCodePageAction extends CodePageAction {
	int fromLine;
	int fromPos;
	String fromStr;
	int toLine;
	int toPos;
	
	
	public MoveArgCodePageAction(CodePage cp, int slideNum, int fromLine, int fromPos,
			int toLine, int toPos)
	{
		super(cp, slideNum);
		this.fromLine = fromLine;
		this.fromPos = fromPos;
		this.toLine = toLine;
		this.toPos = toPos;
	}
	
	public int getFromLine()
	{
		return fromLine;
	}
	
	public int fromPos()
	{
		return fromPos;
	}
	
	public int toLine()
	{
		return toLine;
	}
	
	public int toPos()
	{
		return toPos;
	}
}
