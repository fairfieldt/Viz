package Interpreter;
import java.util.*;

public class Global
{
	//Line number for pseudocode.
	public static int InterpreterType;
	public static int lineNumber = 1;
	
	public static boolean debug = false;
	private static SymbolTable symbolTable = new SymbolTable(null);
	private static HashMap<String,ASTFunction> functions = new HashMap<String, ASTFunction>();
	
	private static SymbolTable currentSymbolTable = symbolTable;
	
	private static HashMap<String, String> currentParamToArg;
	
	public static SymbolTable getSymbolTable()
	{
		return symbolTable;
	}
	
	public static HashMap<String, String> getCurrentParamToArg()
	{
		return currentParamToArg;
	}
	
	public static void setCurrentParamToArg(HashMap<String, String> pa)
	{
		currentParamToArg = pa;
	}
	
	public static ASTFunction getFunction(String name)
	{
		return functions.get(name);
	}
	public static HashMap<String, ASTFunction> getFunctions()
	{
		return functions;
	}
	public static SymbolTable getCurrentSymbolTable()
	{
		return currentSymbolTable;
	}
	
	public static void setCurrentSymbolTable(SymbolTable table)
	{
		currentSymbolTable = table;
if (Global.debug) {		System.out.println("done here");
}	}
	
	public static boolean addFunction(ASTFunction fun)
	{
		//System.out.println("Adding a function");
		if (functions.containsKey(fun.getName()))
		{
if (Global.debug) {			System.out.println("Found key " + fun.getName());
}			return false;
		}
		functions.put(fun.getName(), fun);
		return true;
	}
	
	public static String getString()
	{
		String code = "Functions:\n\t";
		for (String key : functions.keySet())
		{
			code += key + "\n\t";
		}
		code += "\n" + symbolTable.toString();
		
		return code;
	}
}
