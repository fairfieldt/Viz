package viz;

public class SwapCodePageAction extends CodePageAction {

	private CodePage newCP;
	
	public SwapCodePageAction(CodePage prevCodePage, CodePage newCodePage, int slideNum)
	{
		super(prevCodePage, slideNum);
		this.newCP = newCodePage;
	}
	
	public CodePage getNewCP()
	{
		return newCP;
	}
	 
}
