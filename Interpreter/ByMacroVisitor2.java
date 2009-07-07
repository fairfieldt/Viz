package Interpreter;
import viz.*;
import java.util.*;

public class ByMacroVisitor2 implements VizParserVisitor, VizParserTreeConstants, UpdateReasons
{
	private XAALConnector connector;
		private static final int QUESTION_FREQUENCY = 65;
	public static final int LINE_NUMBER_END = -1;
	
	private int callLineNumber = 3;
	private int currentLineNumber = 1;
	
	private ASTProgram program;
	
	private boolean inNested = false;
	
	private boolean readyForCall = false;
	private ASTCall call;

	
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
					node.childrenAccept(this, null);
					break;
			case JJTVARDECL:
				node.childrenAccept(this, null);
				break;
			case JJTCALL:
				System.out.println("CALL");
				handleCall((ASTCall)node);
				node.childrenAccept(this, null);
				break;
			case JJTVAR:
				node.childrenAccept(this, null);
				break;
			case JJTASSIGNMENT:
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
	
	

	public void handleCall(ASTCall node)
	{	
		/*	
		if (!readyForCall)
		{
			call = node;
			return;
		}
		//Draw the intermediate step before changing the code again
		connector.endSnap();
		connector.startSnap(0);
		connector.endPar();
		connector.startPar();
			program.codeBuilt = false;
			Global.lineNumber = 1;
			program.buildCode();
			String p = connector.addCodePage(program.getPseudocode());
			connector.hideCodePage(NewTest.currentPage);
			NewTest.currentPage = p;
			connector.showCodePage(NewTest.currentPage);
		connector.endPar();
		connector.endSnap();
		connector.startSnap(0);
		connector.startPar();
		*/
		//Get the correct function head node
		ASTFunction fun = Global.getFunction(node.getName());
		System.out.println("Calling!: " + fun.getName());
		
		//The call's grandparent knows the line number and we need to know it later
		SimpleNode sn = (SimpleNode) node.jjtGetParent();
		sn = (SimpleNode) sn.jjtGetParent();
		System.out.println("Got grandparent");
		
		callLineNumber = sn.getLineNumber();
		System.out.println("AHHHH " + callLineNumber);
		
		
		//Get the parameters and put the correct values in the symbolTable
		SymbolTable st = fun.getSymbolTable();
		st.setPrevious(Global.getCurrentSymbolTable());
		System.out.println(Global.getCurrentSymbolTable());
		String name = fun.getName();
		
		System.out.println(name);
		ArrayList<String> parameters = fun.getParameters();		

		
		ArrayList<ASTVar> args = node.getArgs();

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
		System.out.println(node);
		System.out.println(node.jjtGetParent());
		ASTExpression exp = (ASTExpression) node.jjtGetParent();
		ASTStatement stmnt = (ASTStatement) exp.jjtGetParent();
		stmnt.children[0] = fun.jjtGetChild(0);
		fun.jjtGetChild(0).jjtSetParent(stmnt);
		
		((ASTStatementList)stmnt.jjtGetChild(0)).setIsFunction(false);
		((ASTStatementList)stmnt.jjtGetChild(0)).setSymbolTable(fun.getSymbolTable());
		fun.getSymbolTable().setName("");

		System.out.println("asas");
		
		//Remove the function declaration
		fun.setUsed(false);
		node.setUsed(false);
	}
	

	public Object visit(ASTProgram node, Object data)
	{
		program = node;
		node.childrenAccept(this, null);
		//readyForCall = true;
		//call.jjtAccept(this, null);
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
		if (! (node.jjtGetParent() instanceof ASTStatement))
		{
			currentLineNumber++;
		}
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
  		node.childrenAccept(this, null);
  		return null;
  	}
  	public Object visit(ASTAssignment node, Object data)
  	{
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
