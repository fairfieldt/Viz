package viz;

public class MoveVarIndexAction extends MoveVarAction {
	private int index = -1;
	
	public MoveVarIndexAction(Variable from, int index, Variable to,  int snapNum)
	{
		super(from, to, snapNum);
		this.index = index;
	}
	
	public int getIndex()
	{
		return index;
	}
	
	public void setIndex(int index)
	{
		this.index = index;
	}
}
