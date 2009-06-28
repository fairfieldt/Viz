package Interpreter;
import java.util.*;

public class ByRefVariable extends AbstractVariable implements Variable
{
	//The ByValVariable we're referring to
	private ByValVariable ref;
	
	public ByRefVariable(ByValVariable ref)
	{
		setRef(ref);
	}
	
	public void setRef(ByValVariable ref)
	{
		this.ref = ref;
	}
	
	public ByValVariable getRef()
	{
		return this.ref;
	}
	
	public void setValue(int value)
	{
		this.ref.setValue(value);
	}
	
	public int getValue()
	{
		return this.ref.getValue();
	}
	
	public void setParam()
	{
		this.isParam = true;
	}
	
	public boolean getIsParam()
	{
		return this.isParam;
	}
	
	public void setArray()
	{
		System.out.println("A Reference variable can't be an array, ERROR");
	}
	
	public boolean getIsArray()
	{
		return false;
	}
	
	public ArrayList<Integer> getValues()
	{
		System.out.println("Not an array, ERROR");
		return null;
	}
	
	public int getValue(int subscript)
	{
		System.out.println("Not an array, ERROR");
		return 0;
	}

	public void setValue(int value, int index)
	{
		System.out.println("Not an array, ERROR");
	}
}
