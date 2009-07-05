package viz;

public class ShowHideCodePageAction extends CodePageAction {
	//if true show, else hide
	private boolean showHide = false;
	
	public ShowHideCodePageAction(boolean show, CodePage cp, int slideNum)
	{
		super(cp, slideNum);
		this.showHide = show;
	}
	
	public boolean isShow()
	{
		return showHide;
	}
	
}
