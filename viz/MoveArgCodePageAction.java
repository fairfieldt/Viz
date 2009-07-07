package viz;

public class MoveArgCodePageAction extends CodePageAction {
	int fromLine;
	int fromPos;
	String fromStr;
	int toLine;
	int toPos;
	
	
	public MoveArgCodePageAction(CodePage cp, int slideNum, int fromLine, int fromPos,
			int toLine, int toPos, String fromStr)
	{
		super(cp, slideNum);
		this.fromLine = fromLine;
		this.fromPos = fromPos;
		this.toLine = toLine;
		this.toPos = toPos;
		this.fromStr = fromStr;
	}
	
	public int getFromLine()
	{
		return fromLine;
	}
	
	public int getFromPos()
	{
		return fromPos;
	}
	
	public int getToLine()
	{
		return toLine;
	}
	
	public int getToPos()
	{
		return toPos;
	}
}
