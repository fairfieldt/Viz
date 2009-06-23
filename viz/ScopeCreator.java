package viz;
import java.util.LinkedList;

import Interpreter.SymbolTable;

//TODO not sure about if this should be like this but we'll try
public class ScopeCreator {
	
	private static LinkedList<String> colors = new LinkedList<String>();
	
	static {
		colors.add("blue");
		colors.add("green");
		colors.add("red");
	}
	
	public static Scope createScope(SymbolTable symbols, String name)
	{
		return createScope(symbols, name, null);
	}
	
	public static Scope createScope(SymbolTable symbols, String name, Scope parent)
	{ 
		String[] symbolNames = new String[symbols.getLocalVarNames().size()];
		symbols.getLocalVarNames().toArray(symbolNames);
		
		boolean isGlobal = (parent == null);
		
		Scope retScope = new Scope(name, colors.pop(), isGlobal);

		//add scope to parent if parent exists
		if(!isGlobal)
			parent.addScope(retScope);
		
		//Global never starts hidden
		retScope.setHidden(!isGlobal);
		
		for(String s : symbolNames)
		{
			Variable v = new Variable(name, symbols.get(s), true);
			retScope.addVariable(v);
			
			Interpreter.Variable iv = symbols.getVariable(name);
			
			//if (iv instanceof ByVarVariable)
				//do nothing
			//else (iv instanceof ByRefVariable)
				//set reference
		}
				
		return retScope;
	}
}
