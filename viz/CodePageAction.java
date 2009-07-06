package viz;

public abstract class CodePageAction extends FutureAction {
	private CodePage cp;
	
	public CodePageAction(CodePage to, int slideNum)
	{
		super(slideNum);
		this.cp = to;
	}
	
	public CodePage getCP()
	{
		return cp;
	}
	
}
