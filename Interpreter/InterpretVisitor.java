package Interpreter;
import java.util.*;

public class InterpretVisitor implements VizParserVisitor, VizParserTreeConstants
{

	public void update()
	{
		System.out.println(Global.getString());
	}
	public Object visit(SimpleNode node, Object data)
	{
		int id = node.getId();
		Object retVal = null;
		System.out.println(id);
		switch (id)
		{
			case JJTPROGRAM:
				handleProgram((ASTProgram)node);
				break;
			case JJTDECLARATIONLIST:
				handleDeclarationList((ASTDeclarationList)node);
				break;
			case JJTDECLARATION:
				handleDeclaration((ASTDeclaration)node);
				break;
			case JJTVARDECL:
				System.out.println("Vardecl!");
				handleVarDecl((ASTVarDecl)node);
				break;
			case JJTARRAYDECLARATION:
				handleArrayDeclaration((ASTArrayDeclaration)node);
				break;
			case JJTSTATEMENTLIST:
				handleStatementList((ASTStatementList)node);
				break;
			case JJTSTATEMENT:
				handleStatement((ASTStatement)node);
				break;
			case JJTCALL:
				retVal = handleCall((ASTCall)node);
				break;
			case JJTVAR:
				retVal = handleVar((ASTVar)node);
				break;
			case JJTASSIGNMENT:
				handleAssignment((ASTAssignment)node);
				break;
			case JJTEXPRESSION:
				retVal = handleExpression((ASTExpression)node);
				break;
			case JJTARGS:
				retVal = handleArgs((ASTArgs)node);
				break;
			case JJTOP:
				retVal = handleOp((ASTOp)node);
				break;
			case JJTNUM:
				retVal = handleNum((ASTNum)node);
				break;
			case JJTFUNCTION:
				handleFunction((ASTFunction)node);
				break;
			default:
				System.out.println("Unimplemented");
		}
		return retVal;
	}
	
	public void handleProgram(ASTProgram node)
	{
		System.out.println("visiting program");
		Global.setCurrentSymbolTable(Global.getSymbolTable()); //set current symbol table to the global one
		update();
		node.jjtGetChild(0).jjtAccept(this, null);
	}
	
	public void handleDeclarationList(ASTDeclarationList node)
	{
		System.out.println("Visiting declList");
		int numDecls = node.jjtGetNumChildren();
		for (int i = 0; i < numDecls; i++)
		{
			node.jjtGetChild(i).jjtAccept(this, null);
		}
	}
	
	public void handleDeclaration(ASTDeclaration node)
	{
		System.out.println("Visiting decl");
		SimpleNode child = (SimpleNode) node.jjtGetChild(0);
		if (child.getId() == JJTFUNCTION)
		{
			System.out.println("Ok, got to functions");
			ASTFunction main = Global.getFunction("main");
			main.jjtAccept(this, null);
		}
		else
		{
			node.jjtGetChild(0).jjtAccept(this, null);
			update();
		}	
	}
	
	public void handleVarDecl(ASTVarDecl node)
	{
		System.out.println("Visiting var decl");
		String name = node.getName();
		if (node.getIsArray())
		{
			//FIXME
			System.out.println("Array declaration unimplemented");
		}
		else
		{
			Integer value =(Integer) node.jjtGetChild(0).jjtAccept(this, null);
			SymbolTable s = Global.getCurrentSymbolTable();
			System.out.println("S: " + s);
			s.setValue(name, value);
			System.out.println("Value of " + name + " is " + Global.getCurrentSymbolTable().get(name));
		}	
	}
	
	public void handleFunction(ASTFunction node)
	{
		node.jjtGetChild(0).jjtAccept(this, null);
	}
	public void handleArrayDeclaration(ASTArrayDeclaration node)
	{
	
	}
	
	public void handleStatementList(ASTStatementList node)
	{
		int numStatements = node.jjtGetNumChildren();
		for (int i = 0; i < numStatements; i++)
		{
			node.jjtGetChild(i).jjtAccept(this, null);
		}
	}
	
	public void handleStatement(ASTStatement node)
	{
		node.jjtGetChild(0).jjtAccept(this, null);
		update();
	}
	
	public Integer handleCall(ASTCall node)
	{
		ASTFunction fun = Global.getFunction(node.getName());
		SymbolTable st = fun.getSymbolTable();
		ArrayList<String> parameters = fun.getParameters();
		ArrayList<Integer> args = (ArrayList<Integer>) node.jjtGetChild(0).jjtAccept(this, null);
		for (int i = 0; i < args.size(); i++)
		{
			st.setValue(parameters.get(i), args.get(i));
		}
		return 0;
	}
	
	public Integer handleVar(ASTVar node)
	{
		if (node.getIsArray())
		{
			System.out.println("Not implemented");
			return 99;
		}
		return Global.getCurrentSymbolTable().get(node.getName());
	}
	
	public void handleAssignment(ASTAssignment node)
	{
		String name = node.getName();
		Integer value = (Integer)node.jjtGetChild(1).jjtAccept(this, null);
		Global.getCurrentSymbolTable().setValue(name, value);
	}
	
	public Integer handleExpression(ASTExpression node)
	{
 		Integer value = (Integer) node.jjtGetChild(0).jjtAccept(this, null);
 		return value;
 	}
 	
 	public ArrayList<Integer> handleArgs(ASTArgs node)
 	{
 		ArrayList<Integer> args = new ArrayList<Integer>();
 		int numArgs = node.jjtGetNumChildren();
 		for (int i = 0; i < numArgs; i++)
 		{
 			args.add((Integer)node.jjtGetChild(i).jjtAccept(this, null));
 		}
 		return args;
 	}
	
	public Integer handleOp(ASTOp node)
	{
		if (node.getOp().equals("+"))
		{
			return (Integer)node.jjtGetChild(0).jjtAccept(this, null) +
				(Integer)node.jjtGetChild(1).jjtAccept(this, null);
		}
		else if (node.getOp().equals("-"))
		{
			return (Integer)node.jjtGetChild(0).jjtAccept(this, null) -
				(Integer)node.jjtGetChild(1).jjtAccept(this, null);
		}
		return 0;
	}
	
	public Integer handleNum(ASTNum node)
	{
		return node.getValue();
	}
	public Object visit(ASTProgram node, Object data)
	{
		handleProgram(node);
		return null;
	}
	
	public Object visit(ASTDeclarationList node, Object data)
	{
		handleDeclarationList(node);
		return null;
	}
	
	public Object visit(ASTDeclaration node, Object data)
	{
		handleDeclaration(node);
		return null;
	}
	
	public Object visit(ASTVarDecl node, Object data)
	{
		handleVarDecl(node);
		return null;
	}
	
	//FIXME
	public Object visit(ASTArrayDeclaration node, Object data)
	{
		handleArrayDeclaration(node);
		return null;
	}
	
	public Object visit(ASTFunction node, Object data)
	{	
		handleFunction(node);
		return null;
	}
	
	public Object visit(ASTStatementList node, Object data)
	{
		handleStatementList(node);
		return null;
	}
  	public Object visit(ASTStatement node, Object data)
  	{
  		handleStatement(node);
  		return null;
  	}
  	public Object visit(ASTCall node, Object data)
  	{
  		return handleCall(node);
  	}
  	public Object visit(ASTVar node, Object data)
  	{
  		return handleVar(node);
  	}
  	public Object visit(ASTAssignment node, Object data)
  	{
  		handleAssignment(node);
  		return null;
  	}
 	public Object visit(ASTExpression node, Object data)
 	{
		return handleExpression(node);
 	}
  	public Object visit(ASTArgs node, Object data)
  	{
  		return handleArgs(node);
  	}
  	public Object visit(ASTOp node, Object data)
  	{
  		return handleOp(node);
  	}
  	public Object visit(ASTNum node, Object data)
  	{
  		return handleNum(node);
  	}
}
