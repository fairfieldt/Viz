package viz;

public class SwapCodePageAction extends CodePageAction {

	private CodePage newCP;
	private boolean replaceScope = false;
	
	public SwapCodePageAction(CodePage prevCodePage, CodePage newCodePage, int slideNum)
	{
		super(prevCodePage, slideNum);
		this.newCP = newCodePage;
	}
	
	public SwapCodePageAction(CodePage prevCodePage, CodePage newCodePage, boolean replaceScope, int slideNum)
	{
		super(prevCodePage, slideNum);
		this.replaceScope = true;
		this.newCP = newCodePage;
	}
	
	public CodePage getNewCP()
	{
		return newCP;
	}
	
	public boolean getReplaceScope()
	{
		return replaceScope;
	}
	 
}
