package viz;

public class ShowHideVarAction extends VarAction {
	//if true show, else hide
	private boolean showHide = false;
	
	public ShowHideVarAction(boolean show, Variable v, int snapNum) 
	{
		super(v, snapNum);
		showHide = show;
	}
	
	
	public boolean isShow()
	{
		return showHide;
	}
}
