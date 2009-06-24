package viz;
//TODO: clean this god forsaken mess up
public class FutureAction {
	
	private Variable from;
	private Variable to;
	private int snapNum;
	private int newValue = -1;
	
	//if true show, else hide
	private boolean showHide = false;
	
	private boolean showOrHide = false;
	
	private String scope = null;
	
	public FutureAction(Variable from, Variable to, int snapNum)
	{
		this.from = from;
		this.to = to;
		this.snapNum = snapNum;
	}
	
	public FutureAction(int newValue, Variable to, int snapNum)
	{
		this.newValue = newValue;
		this.to = to;
		this.snapNum = snapNum;
	}
	
	
	public FutureAction(boolean show, Variable v, int snapNum)
	{
		this.to = v;
		showOrHide = true;
		
		showHide = show;
		
		this.snapNum = snapNum;
	}
	
	public FutureAction(boolean show, String scope, int snapNum)
	{
		this.scope = scope;
		showOrHide = true;
		
		showHide = show;
		
		this.snapNum = snapNum;
	}
	
	public Variable getFrom()
	{
		return from;
	}
	
	public void setFrom(Variable from)
	{
		this.from = from;
	}
	
	public Variable getTo()
	{
		return to;
	}
	
	public void setTo(Variable to)
	{
		this.to = to;
	}
	
	public int getSnapNum()
	{
		return snapNum;
	}
	
	public void setSnapNum(int snapNum)
	{
		this.snapNum = snapNum;
	}
	
	public int getNewValue()
	{
		return  newValue;
	}
	
	public void setNewValue(int newValue)
	{
		this.newValue = newValue;
	}
	
	public String getScope()
	{
		return scope;
	}
	
	public boolean isShowOrHide()
	{
		return showOrHide;
	}
	
	public boolean isShow()
	{
		return showHide;
	}
}
