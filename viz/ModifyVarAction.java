package viz;

public class ModifyVarAction extends VarAction {
	private int newValue = -1;
	
	public ModifyVarAction(int newValue, Variable to, int snapNum)
	{
		super(to, snapNum);
		this.newValue = newValue;
	}
	
	public int getNewValue()
	{
		return  newValue;
	}
	
	public void setNewValue(int newValue)
	{
		this.newValue = newValue;
	}
}
