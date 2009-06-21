package Interpreter;
import java.util.*;

public class SymbolTable
{
	private HashMap<String, Variable> vars;
	
	private SymbolTable previous;
	
	public SymbolTable(SymbolTable previous)
	{
		this.previous = previous;
		this.vars = new HashMap<String, Variable>();
	}
	
	public int get(String varName)
	{
		int retVal = -255;
		if (vars.containsKey(varName))
		{
			retVal = vars.get(varName).getValue();
		}
		else if (previous != null)
		{
			retVal = previous.get(varName);
		}
		return retVal;
	}
	
	public boolean put(String varName, Variable v)
	{
		if (vars.containsKey(varName))
		{
			return false;
		}
		vars.put(varName, v);
		return true;
	}
	
	public HashSet<String> getCurrentVarNames()
	{
		//TODO: is this a copy of the keys or references?
		HashSet<String> variables = new HashSet<String>(vars.keySet());
		
		if (previous != null)
			variables.addAll(previous.getCurrentVarNames());
		
		return variables;
	
	}
}
