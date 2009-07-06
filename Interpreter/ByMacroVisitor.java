package Interpreter;
import viz.*;
import java.util.*;

public class ByMacroVisitor implements VizParserVisitor, VizParserTreeConstants, UpdateReasons
{
	private XAALConnector connector;
		private static final int QUESTION_FREQUENCY = 65;
	public static final int LINE_NUMBER_END = -1;
	
	private boolean inNested = false;

	
	public void setXAALConnector(XAALConnector xc)
	{
		this.connector = xc;
	}

	
	public Object visit(SimpleNode node, Object data)
	{
		int id = node.getId();
		Object retVal = null;
		System.out.println("Visiting " + id);
		switch (id)
		{
			case JJTSTATEMENTLIST:
				System.out.println("STMT LIST");
							node.childrenAccept(this, null);
							break;
			case JJTVARDECL:
				handleVarDecl((ASTVarDecl)node);
				node.childrenAccept(this, null);
				break;
			case JJTCALL:
				System.out.println("CALL");
				handleCall((ASTCall)node);
				node.childrenAccept(this, null);
				break;
			case JJTVAR:
				handleVar((ASTVar)node);
				node.childrenAccept(this, null);
				break;
			case JJTASSIGNMENT:
				handleAssignment((ASTAssignment)node);
				node.childrenAccept(this, null);
				break;
			default:
				for (int i = 0; i < node.jjtGetNumChildren(); i++)
				{
					System.out.println(node.jjtGetChild(i));
					SimpleNode s = (SimpleNode)node.jjtGetChild(i);
					s.jjtAccept(this, null);
				}
				System.out.println("AFTER");
		}
		return retVal;
	}
	
	public void handleVar(ASTVar node)
	{
		if (node.getIsArray())
		{
			ByValVariable v = (ByValVariable) Global.getCurrentSymbolTable().getVariable(node.getName());
			v.setSubscript((ASTExpression)node.jjtGetChild(0));
		}
		if (!inNested)
		{
			return;
		}
		String argName = Global.getCurrentParamToArg().get(node.getName());
		System.out.println("Var " + argName);
		if (argName != null)
		{
			node.setName(argName);
			System.out.println("Substituted " + argName);
			ByValVariable arg =(ByValVariable) Global.getCurrentSymbolTable().getVariable(argName);
			if (arg.getIsArray())
			{
				System.out.println("An array");
				node.setIsArray(true);
				System.out.println(arg.getSubscript());
				node.jjtAddChild(arg.getSubscript(), 0);
			}
		}
	}
	
	public void handleVarDecl(ASTVarDecl node)
	{
		return;
	}
	public void handleAssignment(ASTAssignment node)
	{
		if (!inNested)
		{
			return;
		}
		String argName = Global.getCurrentParamToArg().get(node.getName());
		System.out.println("Assignemnt " + argName);
		if (argName != null)
		{
			node.setName(argName);
			System.out.println("Subbed in assignment " + argName);
			System.out.println(node.getName());
			ByValVariable arg = (ByValVariable) Global.getCurrentSymbolTable().getVariable(argName);
			if (arg.getIsArray())
			{
				//node.setIsArray(true);
			}
		}
	}
	
	public void handleCall(ASTCall node)
	{		//Get the correct function head node
		ASTFunction fun = Global.getFunction(node.getName());
		System.out.println("Calling: " + fun.getName());
		//Get the parameters and put the correct values in the symbolTable
		SymbolTable st = fun.getSymbolTable();
		st.setPrevious(Global.getCurrentSymbolTable());
		System.out.println(Global.getCurrentSymbolTable());
		String name = fun.getName();
		ArrayList<String> parameters = fun.getParameters();		

		ArrayList<ASTVar> args = ((ASTArgs)node.jjtGetChild(0)).getArgs();

		HashMap<String, String> pa = new HashMap<String, String>(); //Maps args to params
		for (int i = 0; i < parameters.size(); i++)
		{
			pa.put(parameters.get(i), args.get(i).getName());
			args.get(i).jjtAccept(this, null);
		}
		Global.setCurrentParamToArg(pa);
		
		inNested = true;
		fun.jjtAccept(this, null);
		inNested = false;
		
		//Now attach the function's statementlist to the expression where the call was.
		ASTExpression exp = (ASTExpression) node.jjtGetParent();
		exp.children[0] = fun.jjtGetChild(0);
		
		((ASTStatementList)exp.jjtGetChild(0)).setIsFunction(false);
		((ASTStatementList)exp.jjtGetChild(0)).setSymbolTable(fun.getSymbolTable());
		((ASTStatementList)exp.jjtGetChild(0)).getSymbolTable().setPrevious(Global.getCurrentSymbolTable());
		System.out.println("asas");
		System.out.println(fun.getSymbolTable());
		
		//Remove the function declaration
		fun.setUsed(false);
	}
	

	public Object visit(ASTProgram node, Object data)
	{
		node.childrenAccept(this, null);
		return null;
	}
	
	public Object visit(ASTDeclarationList node, Object data)
	{	
		System.out.println("Handle decl list");
		node.childrenAccept(this, null);
		return null;
	}
	
	public Object visit(ASTDeclaration node, Object data)
	{
		node.childrenAccept(this, null);
		return null;
	}
	
	public Object visit(ASTVarDecl node, Object data)
	{
		handleVarDecl((ASTVarDecl)node);
		node.childrenAccept(this, null);
		return null;
	}
	
	//FIXME
	public Object visit(ASTArrayDeclaration node, Object data)
	{
		node.childrenAccept(this, null);
		return null;
	}
	
	public Object visit(ASTFunction node, Object data)
	{	
		System.out.println("Visiting function");
		
		node.childrenAccept(this, null);
		return null;
	}
	
	public Object visit(ASTStatementList node, Object data)
	{
		System.out.println("here");
		for (int i = 0; i < node.jjtGetNumChildren(); i++)
		{
			System.out.println(node.jjtGetChild(i));
			node.jjtGetChild(i).jjtAccept(this, null);
		}
		return null;
	}
  	public Object visit(ASTStatement node, Object data)
  	{
	  	for (int i = 0; i < node.jjtGetNumChildren(); i++)
		{
			System.out.println(node.jjtGetChild(i));
			node.jjtGetChild(i).jjtAccept(this, null);
		}
  		return null;
  	}
  	public Object visit(ASTCall node, Object data)
  	{
  		handleCall((ASTCall)node);
 		node.childrenAccept(this, null);
  		return null;
  	}
  	public Object visit(ASTVar node, Object data)
  	{
  		handleVar((ASTVar)node);
  		node.childrenAccept(this, null);
  		return null;
  	}
  	public Object visit(ASTAssignment node, Object data)
  	{
  		handleAssignment((ASTAssignment)node);
  		System.out.println("asdf");
  		node.childrenAccept(this, null);
  		return null;
  	}
 	public Object visit(ASTExpression node, Object data)
 	{
		for (int i = 0; i < node.jjtGetNumChildren(); i++)
		{
			System.out.println(node.jjtGetChild(i));
			node.jjtGetChild(i).jjtAccept(this, null);
		}
		return null;
 	}
  	public Object visit(ASTArgs node, Object data)
  	{
  		node.childrenAccept(this, null);
  		return null;
  	}
  	public Object visit(ASTOp node, Object data)
  	{
  		node.childrenAccept(this, null);
  		return null;
  	}
  	public Object visit(ASTNum node, Object data)
  	{
  		node.childrenAccept(this, null);
  		return null;
  	}
  	

}
