package viz;

public class MoveVarToIndexAction extends MoveVarAction {
	private int index = -1;
	
	public MoveVarToIndexAction(Variable fromVar, Variable toVar, int toIndex, int snapNum)
	{
		super(fromVar, toVar, snapNum);
		this.index = toIndex;
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
