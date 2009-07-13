package Interpreter;
import viz.*;
import java.util.*;

public class ByNameVisitor0 implements VizParserVisitor, VizParserTreeConstants, UpdateReasons
{
	private XAALConnector connector;
		private static final int QUESTION_FREQUENCY = 65;
	public static final int LINE_NUMBER_END = -1;
	
	private int callLineNumber = 3;
	private int currentLineNumber = 1;
	
	private ASTProgram program;
	
	private boolean inNested = false;
	public static String nextCodePageId;
	private boolean readyForCall = false;
	private ASTCall theCall;

	
	public void setXAALConnector(XAALConnector xc)
	{
		this.connector = xc;
	}

	
	public Object visit(SimpleNode node, Object data)
	{
		int id = node.getId();
		Object retVal = null;
		switch (id)
		{
			case JJTSTATEMENTLIST:
							currentLineNumber++;
							node.childrenAccept(this, null);
							currentLineNumber++;
							break;
			case JJTVARDECL:
				handleVarDecl((ASTVarDecl)node);
				node.childrenAccept(this, null);
				break;
			case JJTCALL:
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
					SimpleNode s = (SimpleNode)node.jjtGetChild(i);
					s.jjtAccept(this, null);
				}
		}
		return retVal;
	}
	
	public void handleVar(ASTVar node)
	{
				
		SimpleNode subscript = null;
		/*if (node.getIsArray())		//Let the variable know its subscript
		{
			Variable v = Global.getCurrentSymbolTable().getVariable(node.getName());
			
			ASTExpression sub = (ASTExpression)node.jjtGetChild(0);
		}*/
		
		
		if (!inNested)			//We don't have to worry about this one, not in foo
		{
			return;
		}
		String name = node.getName();
		String argName = Global.getCurrentParamToArg().get(name);
		SymbolTable fooST =Global.getFunction("foo").getSymbolTable();
		if (argName != null)		//It's one of the args
		{
			node.setName(argName);  //Changed its name.  
			node.setArg();		//Set it as an arg
			
			System.out.println(fooST + " should be foo");
			Variable arg = Global.getCurrentSymbolTable().getVariable(argName);  //The Entry for the argument.  
			ByNameVariable v = new ByNameVariable();		
			ASTArgs args = (ASTArgs) theCall.jjtGetChild(0);		
			if (arg.getIsArray())
			{
				System.out.println("An array");
				node.setIsArray(true);
				v.setArray();

				//here we want to get the correct subscript

				
				if (name.equals("x"))
				{
					subscript = (SimpleNode) args.jjtGetChild(0).jjtGetChild(0);
				}
				else if (name.equals("y"))
				{
					subscript = (SimpleNode) args.jjtGetChild(1).jjtGetChild(0);
				}
				else if (name.equals("z"))
				{
					subscript = (SimpleNode) args.jjtGetChild(2).jjtGetChild(0);
				}
				else
				{
					System.out.println("Something is wrong");
				}
				
				node.jjtAddChild(subscript, 0); //Now our node has the name of the arg as well as its subscript.  
				v.setSubscript((ASTExpression)subscript);
			}
			//here we set the reference to the correct ASTVar
			if (name.equals("x"))
			{
				v.setRef((ASTVar)args.jjtGetChild(0));
			}
			else if (name.equals("y"))
			{
				v.setRef((ASTVar)args.jjtGetChild(1));
			}
			else if (name.equals("z"))
			{
				v.setRef((ASTVar)args.jjtGetChild(2));
			}
			else
			{
				System.out.println("trouble");
			}
			//finally we want to set the ByNameVariable's reference variable to the correct one
			v.setVariable((ByValVariable)Global.getFunction("main").getSymbolTable().getVariable(argName));
			v.setArg(); //now we know this is an arg.  
			//and add it to the symbolTable
			fooST.put(argName+"_", v);
	
			//Add the graphical move
			int pos = 2;
			int endPos = 0;
			int lineNumber = 0;
			Object[] params =  Global.getCurrentParamToArg().keySet().toArray();
			String expectedArgName = Global.getCurrentParamToArg().get((String)params[1]);
			//FIXME handle case with 2 args same name
			if (argName.equals(expectedArgName))
			{
				pos = 1;
			}
			else if (params.length > 2 && argName.equals(Global.getCurrentParamToArg().get((String)params[2])))
			{
				pos = 0;
			}
			
			//Three cases
			SimpleNode sn = (SimpleNode)node.jjtGetParent();
			
			if (sn instanceof ASTAssignment)  //LHS of an assignment
			{
				endPos = 0;
				lineNumber = sn.getLineNumber();
if (XAALScripter.debug) {				System.out.println("Line no: " + lineNumber);
}			}
			else if (sn instanceof ASTOp) // Left hand operand of an op
			{
				endPos = 1;
				//The parent's parent knows line number
				SimpleNode temp = (SimpleNode) sn.jjtGetParent();
				temp = (SimpleNode) temp.jjtGetParent();
				lineNumber = temp.getLineNumber();
if (XAALScripter.debug) {				System.out.println("Line yes: " + lineNumber);
}			}
			else if (sn instanceof ASTExpression) //This could either be a standalone assignment or the rhs of an op
			{
				SimpleNode temp = (SimpleNode) sn.jjtGetParent();
				if (temp instanceof ASTAssignment) //Standalone assigment, so temp knows line number
				{
					endPos = 1;
					lineNumber = temp.getLineNumber();
				}
				else if (temp instanceof ASTOp) // RHS of op grandparent knows lineNumber
				{
					endPos = 2;
					SimpleNode temp2 = (SimpleNode) temp.jjtGetParent();
					temp2 = (SimpleNode) temp2.jjtGetParent();
					lineNumber = temp2.getLineNumber();
				}
				else
				{
					lineNumber = ((SimpleNode)temp.jjtGetParent()).getLineNumber();
				}
			}
			else
			{
			}
			if (node.getIsArray()) //An array, we have to put the subscript on for the move
			{
				argName = argName + "[" + subscript.getCode() + "]";
			}
			System.out.println(Global.currentPage + " " + callLineNumber + " " + pos + " " + argName + " " + lineNumber + " " + endPos);
			connector.moveArgs(Global.currentPage, callLineNumber, pos, argName, lineNumber, endPos);
			
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
		if (argName != null)
		{
			node.setName(argName);
			/*ByValVariable arg = (ByValVariable) Global.getCurrentSymbolTable().getVariable(argName);
			if (arg.getIsArray())
			{
				//node.setIsArray(true);
			}*/
		}
	}
	
	public void handleCall(ASTCall node)
	{	
		theCall = node;
		//Get the correct function head node
		ASTFunction fun = Global.getFunction(node.getName());
		
		//The call's grandparent knows the line number and we need to know it later
		SimpleNode sn = (SimpleNode) node.jjtGetParent();
		sn = (SimpleNode) sn.jjtGetParent();
		
		callLineNumber = sn.getLineNumber();		
		
		//Get the parameters and put the correct values in the symbolTable
		SymbolTable st = fun.getSymbolTable();
		st.setPrevious(Global.getCurrentSymbolTable());
		String name = fun.getName();
		
		
		ArrayList<String> parameters = fun.getParameters();		
		ASTArgs argsNode = (ASTArgs) node.jjtGetChild(0);
		ArrayList<ASTVar> args = node.getArgs();		
		
		HashMap<String, String> pa = new HashMap<String, String>(); //Maps args to params
		for (int i = 0; i < parameters.size(); i++)
		{
			pa.put(parameters.get(i), args.get(i).getName());
			args.get(i).jjtAccept(this, null);
		}
		Global.setCurrentParamToArg(pa);

		inNested = true;
		for (int i = 0; i < argsNode.jjtGetNumChildren(); i++)
		{
			((ASTVar)argsNode.jjtGetChild(0)).jjtAccept(this, null);
		}
		fun.jjtAccept(this, null);
		inNested = false;
		
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
if (XAALScripter.debug) {		System.out.println("Handle decl list");
}		node.childrenAccept(this, null);
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
		currentLineNumber+=2;
	
		node.childrenAccept(this, null);
		currentLineNumber++;
		return null;
	}
	
	public Object visit(ASTStatementList node, Object data)
	{
		for (int i = 0; i < node.jjtGetNumChildren(); i++)
		{
if (XAALScripter.debug) {			System.out.println(node.jjtGetChild(i));
}			node.jjtGetChild(i).jjtAccept(this, null);
		}
		return null;
	}
  	public Object visit(ASTStatement node, Object data)
  	{
  		currentLineNumber++;
	  	for (int i = 0; i < node.jjtGetNumChildren(); i++)
		{
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
if (XAALScripter.debug) {  		System.out.println("asdf");
}if (XAALScripter.debug) {  		System.out.println(node.getName());
}if (XAALScripter.debug) {  		System.out.println(Global.getCurrentSymbolTable());
}  		node.childrenAccept(this, null);
  		return null;
  	}
 	public Object visit(ASTExpression node, Object data)
 	{
		for (int i = 0; i < node.jjtGetNumChildren(); i++)
		{
if (XAALScripter.debug) {			System.out.println(node.jjtGetChild(i));
}			node.jjtGetChild(i).jjtAccept(this, null);
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
