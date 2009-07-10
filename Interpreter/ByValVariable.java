package Interpreter;
import java.util.*;
import viz.*;
/**
 * class ByValVariable
 * @authors Tom Fairfield Eric Schultz
 */
public class ByValVariable extends AbstractVariable implements Variable
{
	//If a single variable, the value.
	private int value;
	
	private boolean isArray = false;
	
	private ASTExpression subscript = null;
	//If an array, the values
	private ArrayList<Integer> values = new ArrayList<Integer>();
	
	public ByValVariable()
	{
		super();
	}
	
	/**
	 * The constructor calls the super() constructor and stores the value
	 * @param value the starting value
	 */
	public ByValVariable(int value)
	{
		super();
		this.value = value;
	}
	
	public void setSubscript(ASTExpression exp)
	{
		this.subscript = exp;
	}
	
	public ASTExpression getSubscript()
	{
		return this.subscript;
	}
	
	/**
	 * get the value of a single variable
	 * @returns the value
	 */
	public int getValue()
	{
		return this.value;
	}
	
	/**
	 * Get a value from an array
	 * @param subscript the index to get the value for
	 * @returns the corresponding value
	 */
	public int getValue(int index) throws VizIndexOutOfBoundsException
	{
		System.out.println("This is " + index);
		if (!isArray)
		{

			return -255;
		}
		if (index > values.size() || index < 0) //being consistent would be nice
		{
			System.out.println("throwing exception");
			throw new VizIndexOutOfBoundsException();

		}
		else
		{
			System.out.println("Should be fine");
			return values.get(index);
		}
	}
	
	/**
	 *  set the value of a single variable
	 * @param value the value to set
	 */
	public void setValue(int value)
	{
		this.value = value;
	}
	
	/**
	 * set the value of an array member
	 * @param value the value to set
	 * @param the index to set
	 */
	public void setValue(int value, int index) throws VizIndexOutOfBoundsException
	{
		if (isArray)
		{
			if (index < values.size() && index >= 0)
			{
				values.set(index, new Integer(value));
			}
			else
			{
				throw new VizIndexOutOfBoundsException();
			}
		}
		else
		{
			System.out.println("trying to index a non-array");
		}
	}
	
	/**
	 * set the variable to be an array
	 */
	public void setArray()
	{
		this.value = -1000;
		this.isArray = true;
	}
	
	/**
	 * check if this variable is an array
	 * @returns true if an array, false otherwise
	 */
	public boolean getIsArray()
	{
		return this.isArray;
	}
	
	/**
	 * Set all the values of an array
	 * @param values an ArrayList<Integer> of values to set.
	 */
	public void setValues(ArrayList<Integer> values)
	{
		this.value = -1000;
		this.values = values;
	}
	
	/**
	 * get all of the values of an array
	 * @returns an ArrayList<Integer> of the values
	 */	
	public ArrayList<Integer> getValues()
	{
		if (this.value != -1000)
		{
if (XAALScripter.debug) {			System.out.println("Problem handling array");
}			return null;
		}
		return this.values;
	}
		
	/****** USED BY RANDOMIZER ******/
	
	public static ByValVariable createArrayVariable()
	{
		ByValVariable v = new ByValVariable(-255);
		v.setArray();
		
		return v;
	}
}
