package Interpreter;

import java.util.*;

public class SymbolTable
{
	private HashMap<String, Variable> vars;
	
	private SymbolTable previous;
	private String name = "";
	public SymbolTable(SymbolTable previous)
	{
		this.previous = previous;
		this.vars = new HashMap<String, Variable>();
	}
	
	public int get(String varName)
	{
		return get(varName, false);
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * Gets an int representing a var in the symbol table.
	 * @param varName name of the var to look for.
	 * @param localOnly should you only look in the local symbol table or all of its parents.
	 * @return an int representing the var or -255 if none was found.
	 */
	public int get(String varName, boolean localOnly)
	{
		int retVal = -255;
		if (vars.containsKey(varName))
		{
			retVal = vars.get(varName).getValue();
		}
		else if (previous != null && !localOnly)
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
	
	public void setValue(String name, int value)
	{
		Variable v = vars.get(name);
		v.setValue(value);
	}
	
	public HashSet<String> getCurrentVarNames()
	{
		//TODO: is this a copy of the keys or references?
		HashSet<String> variables = new HashSet<String>(vars.keySet());
		
		if (previous != null)
			variables.addAll(previous.getCurrentVarNames());
		
		return variables;
	
	}
	
	public HashSet<String> getLocalVarNames()
	{
		return new HashSet<String>(vars.keySet());
	}
	
	public String toString()
	{
		String code = name + ":\n";
		for (String key : vars.keySet())
		{
			code += key + ":" + vars.get(key).getValue() + "\n";
		}
		return code;
	}
}
