package Interpreter;

import java.util.*;
import viz.*;
public class SymbolTable
{
	private HashMap<String, Variable> vars;
	public HashMap<String, Variable> cache;
	private SymbolTable previous = null;
	private String name = "Global";
	public SymbolTable(SymbolTable previous)
	{
		if ( previous != null)
		{
			this.previous = previous;
		}
		else
		{
			this.previous = Global.getSymbolTable();
		}
		this.vars = new HashMap<String, Variable>();
		this.cache = new HashMap<String, Variable>();
	}
	
	public int get(String varName) throws VizIndexOutOfBoundsException
	{
		return get(varName, false);
	}
	
	public void clearVars()
	{
		vars = new HashMap<String, Variable>();
	}
	public Variable getVariable(String varName)
	{
		Variable v = null;
		if (vars.containsKey(varName))
		{
			v = vars.get(varName);
		}
		else if (previous != null)
		{
			v = previous.getVariable(varName);
		}
		else
		{
		}
		return v;
	}
	
	public Variable getCacheVariable(String varName)
	{
		Variable v = null;
		if (cache.containsKey(varName))
		{
			v = cache.get(varName);
		}
		else
		{
			System.out.println("Not in cache");
		}
		return v;
	}
	
	public void addCacheVariable(String varName, int value)
	{
		System.out.println("Cached " + varName + " as " + value);
		cache.put(varName, new ByValVariable(value));
	}
	
	public void addCacheVariable(String varName, int value, int subscript)
	{
		ByValVariable v = new ByValVariable(value);
		v.index = subscript;
		cache.put(varName, v);
	}
	
	public String getNameByVariable(Variable v)
	{
		String ret = null;
		
		ArrayList<String> names = getCurrentVarNamesArray();
		
		for (String name : names)
		{
			Variable test = this.getVariable(name);
			if (test == v)
			{
				ret = name;
				break;
			}
		}
		
		return ret;
	}
	
	public HashMap<String, Variable> getLocalVariables()
	{
		return vars;
	}
	
	public String getName()
	{	
		return this.name;
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
	public int get(String varName, boolean localOnly) throws VizIndexOutOfBoundsException
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
	
	public void makeByName(String varName)
	{
		Variable v= vars.get(varName);

		ByNameVariable vv = new ByNameVariable();
		if (v.getIsArray())
		{
			vv.setArray();
			vv.setSubscript(v.getSubscript());
		}
		vars.put(varName, vv);
	}
	
	//This is the version to get an array value
	public int get(String varName, int index, boolean localOnly) throws VizIndexOutOfBoundsException
	{
		int retVal = -255;
		if (vars.containsKey(varName))
		{
			retVal = vars.get(varName).getValue(index);
		}
		else if (previous != null && !localOnly)
		{
			retVal = previous.get(varName, index);
		}
		return retVal;
	}
	
	public int get(String varName, int index) throws VizIndexOutOfBoundsException
	{
		return get(varName, index, false);
	}
	
	public boolean put(String varName, Variable v)
	{
		if (vars.containsKey(varName) && Global.InterpreterType != InterpreterTypes.BY_NAME)
		{
			return false;
		}
		vars.put(varName, v);
		return true;
	}
	
	public void setValue(String name, int value)
	{
		Variable v = getVariable(name);
		if (v != null)
		{
			v.setValue(value);
		}
		else
		{
if (XAALScripter.debug) {			System.out.println("error! variable not found: " + name);
}		}
	}
	
	public HashSet<String> getCurrentVarNames()
	{
		//TODO: is this a copy of the keys or references?
		HashSet<String> variables = new HashSet<String>(vars.keySet());
		
		if (previous != null)
			variables.addAll(previous.getCurrentVarNames());
		
		return variables;
	
	}
	
	public ArrayList<String> getCurrentVarNamesArray()
	{
		return new ArrayList<String>(getCurrentVarNames());
	}
	
	public ArrayList<String> getCurrentVarNamesArray(VarRetrRest restrict)
	{
		ArrayList<String> retArray = new ArrayList<String>();
		
		ArrayList<String> currentVarNames = getCurrentVarNamesArray();
		for (String name : currentVarNames)
		{
			Variable test = this.getVariable(name);
			
			if(restrict == VarRetrRest.ParamOnly)
			{
				if (test.isParam())
					retArray.add(name);
			}
			else if (restrict == VarRetrRest.NotParamOnly)
			{
				if (!test.isParam())
					retArray.add(name);
			}
			else if (restrict == VarRetrRest.ArrayOnly)
			{
				if (test.getIsArray())
					retArray.add(name);
			}
			
			else if (restrict == VarRetrRest.NotArrayOnly)
			{
				if(!test.getIsArray())
					retArray.add(name);
			}
			else if (restrict == VarRetrRest.NotParamOrArrayOnly)
			{
				if (!test.getIsArray() && !test.isParam())
					retArray.add(name);
			}
		}
		
		return retArray;
	}
	
	public SymbolTable getPrevious()
	{
		return previous;
	}
	
	public void setPrevious(SymbolTable st)
	{
		this.previous = st;
	}
	
	public HashSet<String> getLocalVarNames()
	{
		return new HashSet<String>(vars.keySet());
	}
	
	public ArrayList<String> getLocalVarNamesArray()
	{
		return new ArrayList<String>(getLocalVarNames());
	}
	
	public String toString() 
	{
		String ret = name + "\n";
		try
		{
			for (String s : vars.keySet())
			{
				ret += s + " " + (vars.get(s) instanceof ByValVariable? vars.get(s).getValue() : "")+"\n";
			}
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		return ret + previous;
	}
}
