package Interpreter;

import java.util.UUID;

public abstract class AbstractVariable implements Variable {

	private UUID uuid;
	protected boolean isParam = false;
	protected boolean isArg = false;
	protected AbstractVariable()
	{
		this.uuid = UUID.randomUUID();
	}
	
	@Override
	public abstract int getValue() throws VizIndexOutOfBoundsException;

	@Override
	public abstract void setValue(int value) ;
	
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
	
	public boolean isArg()
	{
		return isArg;
	}
	
	public void setArg()
	{
		this.isArg = true;
	}
	public void setSubscript(ASTExpression exp)
	{
		
	}
	
		public ASTExpression getSubscript()
		{
			return null;
		}
	
}
