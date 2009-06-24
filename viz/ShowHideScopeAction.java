package viz;

public class ShowHideScopeAction extends ScopeAction {
	//if true show, else hide
	private boolean showHide = false;
	
	public ShowHideScopeAction(boolean show, String scope, int snapNum)
	{
		super(scope, snapNum);
		showHide = show;
	}
	
	
	public boolean isShow()
	{
		return showHide;
	}
}