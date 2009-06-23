package Interpreter;
import java.util.*;

public class Global
{
	private static SymbolTable symbolTable = new SymbolTable(null);
	private static HashMap<String,ASTFunction> functions = new HashMap<String, ASTFunction>();
	
	private static SymbolTable currentSymbolTable = symbolTable;
	
	public static SymbolTable getSymbolTable()
	{
		return symbolTable;
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
		System.out.println("Setting current Symbol Table to\n" + table);
		currentSymbolTable = table;
	}
	
	public static boolean addFunction(ASTFunction fun)
	{
		//System.out.println("Adding a function");
		if (functions.containsKey(fun.getName()))
		{
			System.out.println("Found key " + fun.getName());
			return false;
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
