package viz;

public class ScopeReplaceCodePageAction extends CodePageAction {
	private int callLineNum;
	private int startScopeLNum;
	private int endScopeLNum;
	private int endOfMainBrktLNum;
	
	public ScopeReplaceCodePageAction(CodePage cp, int slideNum, int callLineNum, 
			  int startScopeLNum, int endScopeLNum, int endOfMainBrktLNum)
	{
		super(cp, slideNum);
		this.callLineNum = callLineNum;
		this.startScopeLNum = startScopeLNum;
		this.endScopeLNum = endScopeLNum;
		this.endOfMainBrktLNum = endOfMainBrktLNum;
	}
	
	public int getCallLineNum()
	{
		return this.callLineNum;
	}
	
	public int getStartScopeLNum()
	{
		return this.startScopeLNum;
	}
	
	public int getEndScopeLNum()
	{
		return this.endScopeLNum;
	}
	
	public int getEndOfMainBrktLNum()
	{
		return this.endOfMainBrktLNum;
	}
}
