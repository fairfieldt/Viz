package viz;

public class MoveVarAction extends VarAction {

	private Variable from;
	
	public MoveVarAction(Variable from, Variable to, int snapNum)
	{
		super(to, snapNum);
		this.from = from;
	}
	
	public Variable getFrom()
	{
		return from;
	}
	
	public void setFrom(Variable from)
	{
		this.from = from;
	}
	
}
