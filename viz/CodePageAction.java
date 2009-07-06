package viz;

public abstract class CodePageAction extends FutureAction {
	private CodePage cp;
	
	public CodePageAction(CodePage cp, int slideNum)
	{
		super(slideNum);
		this.cp = cp;
	}
	
	public CodePage getCP()
	{
		return cp;
	}
	
}
