package Interpreter;

public class VizIndexOutOfBoundsException extends Exception
{
	private String varName;
	public VizIndexOutOfBoundsException()
	{
		super();
	}
	
	public VizIndexOutOfBoundsException(String varName)
	{
		this.varName = varName;
	}
	
	public String getVarName()
	{
		return varName;
	}
	
	public void setVarName(String varName)
	{
		this.varName = varName;
	}
}
