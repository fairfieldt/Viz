package viz;

public abstract class VarAction extends FutureAction {
	private Variable to;
	
	public VarAction(Variable to, int snapNum)
	{
		super(snapNum);
		this.to = to;
	}
	
	public Variable getTo()
	{
		return to;
	}
	
	public void setTo(Variable to)
	{
		this.to = to;
	}
}
