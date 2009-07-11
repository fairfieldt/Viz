package Interpreter;
import java.util.*;
import viz.*;

public class ASTFunction extends SimpleNode
{
	private ArrayList<String> parameters;
	private String name;
	private SymbolTable localScopeSymbolTable;
	private int lineNumber = -1;
	private boolean used = true;
	
	public ASTFunction(int id)
	{
		super(id);	
		this.localScopeSymbolTable = new SymbolTable(Global.getSymbolTable());
		this.parameters = new ArrayList<String>();
	}
	
	public void setUsed(boolean used)
	{
		this.used = used;
	}
	
	public boolean getUsed()
	{
		return used;
	}
	
	public ArrayList<String> getParameters()
	{
		return this.parameters;
	}
	public void setName(String name)
	{
		this.name = name;
		localScopeSymbolTable.setName(name);
	}
	
	public int getLineNumber()
	{
		return lineNumber;
	}
	
	public void setLineNumber(int lineNumber)
	{
		this.lineNumber = lineNumber;
	}
	
	public void addParameter(String name)
	{
		this.parameters.add(name);
		Variable v;
		switch (Global.InterpreterType)
		{
			case InterpreterTypes.BY_VALUE:
				v = new ByValVariable(-255);
				break;
			case InterpreterTypes.BY_REFERENCE:
				v = new ByRefVariable(null);
				break;
			case InterpreterTypes.BY_COPY_RESTORE:
				v = new ByCopyRestoreVariable();
				break;
			case InterpreterTypes.BY_NAME:
				v = new ByNameVariable();
				break;
			default:
				v = new ByValVariable(-255);
if (XAALScripter.debug) {				System.out.println("Error, default interpreter type reached");
}		}
		v.setParam();

		if(!this.localScopeSymbolTable.put(name, v))
		{
if (XAALScripter.debug) {			System.out.println("failed but making it true anyway");
}			v = this.localScopeSymbolTable.getVariable(name);
			//v.setParam();
		}

	}
	
	public void addParams(String...names)
	{
		for (String name : names)
		{
			addParameter(name);
		}
	}
	
	
	public String getName()
	{
		return this.name;
	}

	public SymbolTable getSymbolTable()
	{
		return localScopeSymbolTable;
	}

	public String getCode()
	{
		if (!used)
		{
			return "";
		}
if (XAALScripter.debug) {		System.out.println(jjtGetChild(0));
}		String code = "def " + this.name + "(";
		for (int i = 0; i < parameters.size(); i++)
		{
			code += parameters.get(i) + (i < parameters.size() -1 ?  ", " : "");
		}
		int startLineNumber = Global.lineNumber++;
		code += ")\n" + startLineNumber + ". {" + jjtGetChild(0).getCode() + "\n";
		int endLineNumber = Global.lineNumber++;
		code += endLineNumber + ". }";
		if (this.name.equals("foo"))
		{
			Global.startScope = startLineNumber;
			Global.endScope = endLineNumber;
		}
		else
		{
			Global.endMain = endLineNumber;
		}
		return code;
	}

	/*************EVERYTHING BELOW HERE IS USED BY RANDOMIZINGVISITOR************/
	
	/**
	 * While the function's child isn't actually a vardecl, arraydecl, assignment or call,
	 * logically it is. This abstracts adding the child to the ASTFunction node.
	 * 
	 * EXPECTS VARDECL, ARRAYDECL, CALL, OR ASSIGNMENT, NOT DECLARATION!
	 */
	public boolean addLogicalChild(Node n, int i)
	{
		
		Node stmtListNode = jjtGetChild(0);
		
		ASTStatement stmt = ASTStatement.createStmtWithChild(n);
		
		stmtListNode.addChild(stmt, i);
		
		return true;
	}
}
