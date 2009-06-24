package Interpreter;

import java.util.UUID;

public abstract class AbstractVariable implements Variable {

	private UUID uuid;
	private boolean isParam = false;
	
	protected AbstractVariable()
	{
		this.uuid = UUID.randomUUID();
	}
	
	@Override
	public abstract int getValue();

	@Override
	public abstract void setValue(int value);
	
	public UUID getUUID()
	{
		return uuid;
	}
	
	public void setParam()
	{
		isParam = true;
	}
	
	public boolean isParam()
	{
		return isParam;
	}
}
