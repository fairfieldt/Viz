package viz;
//TODO: only handles moves now
public class FutureAction {
	
	private Variable from;
	private Variable to;
	private int snapNum;
	private int assign;
	
	public FutureAction(Variable from, Variable to, int SnapNum)
	{
		this.from = from;
		this.to = to;
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
}
