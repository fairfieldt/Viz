package Interpreter;
import java.util.*;

public interface Variable
{
	public int getValue();
	
	public void setValue(int value);
	
	public UUID getUUID();
	
	public void setParam();
	
	public boolean isParam();

}
