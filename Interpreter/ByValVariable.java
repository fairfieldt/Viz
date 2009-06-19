package Interpreter;
import java.util.*;

public class ByValVariable implements Variable
{
	private int value;
	
	public ByValVariable(int value)
	{
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
