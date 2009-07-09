package Interpreter;
import java.util.*;
import viz.*;
public class ByCopyRestoreVariable extends AbstractVariable implements Variable
{
	//The ByValVariable we're referring to
	private ByValVariable ref;
	private int refIndex = -1;
	private int value = 99;
	
	public ByCopyRestoreVariable()
	{
	
	}
	public ByCopyRestoreVariable(ByValVariable ref)
	{
		setRef(ref);
	}
	
	public void setRef(ByValVariable ref)
	{
		this.ref = ref;
		this.value = ref.getValue();
	}
	
	public void setValue(int value)
	{
if (XAALScripter.debug) {		System.out.println("My value is now " + value);
}		this.value = value;
	}
	
	public void setRef(ByValVariable ref, int index)
	{
if (XAALScripter.debug) {		System.out.println("Setting ref to array");
}		this.ref = ref;
		this.refIndex = index;
		this.value = ref.getValue(index);
	}	
	
	public int getRefIndex()
	{
		return this.refIndex;
	}
	
	public void copyOut()
	{
		if (this.refIndex == -1)
		{
if (XAALScripter.debug) {			System.out.println("Copying out value " + this.value);
}			this.ref.setValue(this.value);
		}
		else
		{
if (XAALScripter.debug) {			System.out.println("coping out value " + this.value + " to " + refIndex);
}if (XAALScripter.debug) {			System.out.println(this.ref.getIsArray());
}			this.ref.setValue(value, this.refIndex);
		}
	}
	
	public ByValVariable getRef()
	{
		return this.ref;
	}
	
	public int getValue()
	{
if (XAALScripter.debug) {		System.out.println("Getting value " + this.value);
}		return this.value;
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
if (XAALScripter.debug) {		System.out.println("A CopyRestore variable can't be an array, ERROR");
}	}
	
	public boolean getIsArray()
	{
		return false;
	}
	
	public ArrayList<Integer> getValues()
	{
if (XAALScripter.debug) {		System.out.println("Not an array, ERROR");
}		return null;
	}
	
	public int getValue(int subscript)
	{
if (XAALScripter.debug) {		System.out.println("Not an array, ERROR");
}		return 0;
	}

	public void setValue(int value, int index)
	{
if (XAALScripter.debug) {		System.out.println("Not an array, ERROR");
}	}
}
