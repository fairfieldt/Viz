package viz;

public abstract class CodePageAction extends FutureAction {
	private CodePage to;
	
	public CodePageAction(CodePage to, int slideNum)
	{
		super(slideNum);
		this.to = to;
	}
	
	public CodePage getTo()
	{
		return to;
	}
	
}
