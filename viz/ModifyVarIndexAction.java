package viz;

public class ModifyVarIndexAction extends ModifyVarAction {
	int index = -1;
	
	public ModifyVarIndexAction(int newValue, Variable to, int toIndex, int snapNum)
	{
		super(newValue, to, snapNum);
		index = toIndex;
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
