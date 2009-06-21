package Interpreter;
import java.util.*;

public class ASTFunction extends SimpleNode
{
	private ArrayList<String> parameters;
	private String name;
	private SymbolTable localScopeSymbolTable;
	
	public ASTFunction(int id)
	{
		super(id);	
		this.localScopeSymbolTable = new SymbolTable(Global.getSymbolTable());
		this.parameters = new ArrayList<String>();
	}
	
	
	public ArrayList<String> getParameters()
	{
		return this.parameters;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void addParameter(String name)
	{
		this.parameters.add(name);
	}
	
	public void addParams(String...names)
	{
		for (String name : names)
		{
			this.parameters.add(name);
		}
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getCode()
	{
		String code = "def " + this.name + "(";
		for (int i = 0; i < parameters.size(); i++)
		{
			code += parameters.get(i) + (i < parameters.size() -1 ?  ", " : "");
		}
		code += ")\n{\n" + jjtGetChild(0).getCode() + "\n}\n";
		
		return code;
	}
		
	
	
}
