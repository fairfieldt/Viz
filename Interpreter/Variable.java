package Interpreter;
import java.util.*;

public interface Variable
{
	public int getValue() throws VizIndexOutOfBoundsException;
	
	public ArrayList<Integer> getValues();
	
	public int getValue(int subscript) throws VizIndexOutOfBoundsException;
	
	public void setValue(int value);
	
	public void setValue(int value, int index) throws VizIndexOutOfBoundsException;
	
	public UUID getUUID();
	
	public void setParam();
	
	public boolean isParam();
	
	public boolean getIsArray();
	
	public void setArray();
	
	public void setSubscript(ASTExpression exp);

	public ASTExpression getSubscript();

}
