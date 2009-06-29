package Interpreter;
import java.util.*;

public class ASTFunction extends SimpleNode
{
	private ArrayList<String> parameters;
	private String name;
	private SymbolTable localScopeSymbolTable;
	private int lineNumber = -1;
	
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
			default:
				v = new ByValVariable(-255);
				System.out.println("Error, default interpreter type reached");
		}
		v.setParam();

		if(!this.localScopeSymbolTable.put(name, v))
		{
			System.out.println("failed but making it true anyway");
			v = this.localScopeSymbolTable.getVariable(name);
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
		System.out.println("Getting code in Program");
		System.out.println(jjtGetChild(0));
		String code = "def " + this.name + "(";
		for (int i = 0; i < parameters.size(); i++)
		{
			code += parameters.get(i) + (i < parameters.size() -1 ?  ", " : "");
		}
		code += ")\n" + Global.lineNumber++ + ". {" + jjtGetChild(0).getCode() + "\n" + Global.lineNumber++ + ". }";
		
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
