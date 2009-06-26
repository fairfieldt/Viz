package Interpreter;
import java.util.*;

public class ByValVariable extends AbstractVariable implements Variable
{
	private int value;
	private boolean isArray = false;
	private ArrayList<Integer> values = new ArrayList<Integer>();
	public ByValVariable(int value)
	{
		super();
		this.value = value;
	}
	
	public int getValue()
	{
		return this.value;
	}
	
	public int getValue(int subscript)
	{
		System.out.println("Values: ");
		for (Integer i : values)
		{
			System.out.println(i);
		}
		if (!isArray)
		{
			System.out.println("Trying to access non-array as an array");
			return -255;
		}
		if (values.size() -1 > subscript)
		{
			System.out.println("Error, array index out of bounds");
			return -255;
		}
		else
		{
			return values.get(subscript);
		}
	}
	
	public void setValue(int value)
	{
		this.value = value;
	}
	
	public void setValue(int value, int index)
	{
		if (isArray)
		{
			System.out.println("Setting index " + index + " to " + value);
			System.out.println(values);
			values.set(index, new Integer(value));
		}
		else
		{
			System.out.println("trying to index a non-array");
		}
		System.out.println("Returning from setValue");
	}
	
	public void setArray()
	{
		System.out.println("An array!");
		this.value = -1000;
		this.isArray = true;
	}
	
	public boolean getIsArray()
	{
		return this.isArray;
	}
	
	public void setValues(ArrayList<Integer> values)
	{
		this.value = -1000;
		this.values = values;
	}
	
	public ArrayList<Integer> getValues()
	{
		if (this.value != -1000)
		{
			System.out.println("Problem handling array");
			return null;
		}
		return this.values;
	}
		
	
}
