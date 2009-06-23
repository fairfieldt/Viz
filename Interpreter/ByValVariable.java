package Interpreter;

public class ByValVariable extends AbstractVariable implements Variable
{
	private int value;
	
	public ByValVariable(int value)
	{
		super();
		this.value = value;
	}
	
	public int getValue()
	{
		return this.value;
	}
	
	public void setValue(int value)
	{
		this.value = value;
	}
}
