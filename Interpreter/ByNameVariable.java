package Interpreter;
import java.util.*;
import viz.*;
/**
 * class ByValVariable
 * @authors Tom Fairfield Eric Schultz
 */
public class ByNameVariable extends AbstractVariable implements Variable
{
	private ASTVar ref;
	private SymbolTable symbolTable;
	private ByValVariable var;
	private int index = -1;
	private boolean isArray = false;
	public ByNameVariable()
	{
	
	}
	
	public ByNameVariable(ASTVar v)
	{
		this.ref = v;
	}
	
	public void setRef(ASTVar v)
	{
		this.ref = v;
	}
	
	public ASTVar getRef()
	{
		return this.ref;
	}
	
	public void setVariable(ByValVariable v)
	{
		this.var = v;
		setSymbolTable(Global.getFunction("main").getSymbolTable());
	}
	public void setVariable(ByValVariable v, int index)
	{
		this.var = v;
		this.index = index;
		setArray();
		setSymbolTable(Global.getFunction("main").getSymbolTable());
	}
	
	public ByValVariable getVariable()
	{
		return this.var;
	}
	public void setSymbolTable(SymbolTable st)
	{
		this.symbolTable = st;
	}
	
	public SymbolTable getSymbolTable()
	{
		return symbolTable;
	}
	
	public void setArray()
	{
		this.isArray = true;
	}
	
	public boolean getIsArray()
	{
		return this.isArray;
	}
	
	public int getValue()
	{
		SymbolTable temp = Global.getCurrentSymbolTable();
		Global.setCurrentSymbolTable(symbolTable);
		InterpretVisitor iv = new  InterpretVisitor();
		Integer value = (Integer) ref.jjtAccept(iv, null);
		Global.setCurrentSymbolTable(temp);
		
		return value;
	}
	
	public int getValue(int asdf)
	{
		System.out.println("Dont use this");
		return -1000;
	}
	public void setValue(int value)
	{
		if (index == -1)
		{
			var.setValue(value);
		}
		else
		{
			try
			{
				var.setValue(value, index);
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
		}
	}
	public ArrayList<Integer> getValues()
	{
		return null;
	}	
	public void setValue(int x, int y)
	{
		index = x;
		setValue(y);
	}
}
