package Interpreter;
import java.util.*;

public interface Variable
{
	public int getValue();
	
	public ArrayList<Integer> getValues();
	
	public int getValue(int subscript);
	
	public void setValue(int value);
	
	public void setValue(int value, int index);
	
	public UUID getUUID();
	
	public void setParam();
	
	public boolean isParam();
	
	public boolean getIsArray();
	
	public void setArray();

}
