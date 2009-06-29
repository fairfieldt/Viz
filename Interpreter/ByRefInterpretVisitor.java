package Interpreter;
import viz.*;
import java.util.*;

public class ByRefInterpretVisitor implements VizParserVisitor, VizParserTreeConstants, UpdateReasons
{
	private static final int QUESTION_FREQUENCY = 7;
	private QuestionFactory questionFactory;
	private Question assignmentQuestion;
	private XAALConnector connector;
	public static final int LINE_NUMBER_END = -1;

	public void setQuestionFactory(QuestionFactory questionFactory)
	{
		this.questionFactory = questionFactory;
	}
	
	public void setXAALConnector(XAALConnector xc)
	{
		this.connector = xc;
	}
	
	public boolean addQuestion(int lineNumber, String varName, int reason)
	{
		Random r = new Random();
		int chance = r.nextInt(10);
		
		//We'll add a question some % of the time
		if (chance > QUESTION_FREQUENCY)
		{
			/*
			switch (reason)
			{
				case QUESTION_REASON_BEGIN:
					break;
				case QUESTION_REASON_END:
					break;
				case QUESTION_REASON_CALL:
					break;
				case QUESTION_REASON_ASSIGNMENT:
					assignmentQuestion = questionFactory.getAssignmentQuestion(lineNumber, varName);		
					break;
			}*/
		}
		return true;
	}

	//FIXME use this?
	public void update(int lineNumber, int reason)
	{
		System.out.println("Update on " + lineNumber);
		//System.out.println(Global.getCurrentSymbolTable().toString());
		//questionFactory.addAnswers(lineNumber, reason);
	}
	
	//FIXME use this?
	private Question getStartQuestion()
	{
		//FIXME random
		return null;
		//return startQuestions.get(0);
	}
	public Object visit(SimpleNode node, Object data)
	{
		int id = node.getId();
		Object retVal = null;

		switch (id)
		{
			case JJTPROGRAM:
				handleProgram((ASTProgram)node);
				break;
			case JJTDECLARATIONLIST:
				handleDeclarationList((ASTDeclarationList)node);
				break;
			case JJTDECLARATION:
				retVal = handleDeclaration((ASTDeclaration)node);
				break;
			case JJTVARDECL:
				handleVarDecl((ASTVarDecl)node);
				break;
			case JJTARRAYDECLARATION:
				retVal = handleArrayDeclaration((ASTArrayDeclaration)node);
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
		Global.setCurrentSymbolTable(Global.getSymbolTable()); 
		update(1, UPDATE_REASON_BEGIN);
		
		//Drawing Stuff
		connector.addScope(Global.getSymbolTable(), "Global", null);
		connector.startSnap(1);
		connector.startPar();
			connector.showScope("Global");
		connector.endPar();
		connector.endSnap();
		
		node.jjtGetChild(0).jjtAccept(this, null);
		update(LINE_NUMBER_END, UPDATE_REASON_END);
		
		//TODO Write the last snap nicely
		connector.startSnap(node.getPseudocode().length);
			connector.startPar();
				connector.hideScope("foo");
			connector.endPar();
		connector.endSnap();
		System.out.println("Done");
	}
	
	public void handleDeclarationList(ASTDeclarationList node)
	{
		System.out.println("Visiting declList");
		int numDecls = node.jjtGetNumChildren();
		for (int i = 0; i < numDecls; i++)
		{
			//A Declaration returned false which means we ran.
			if(((Boolean)node.jjtGetChild(i).jjtAccept(this, null)) == false)
			{
				return;
			}
		}
	}
	
	//returning false means we're done executing now.
	public Boolean handleDeclaration(ASTDeclaration node)
	{
		//System.out.println("Visiting decl");
		SimpleNode child = (SimpleNode) node.jjtGetChild(0);
		if (child.getId() == JJTFUNCTION)
		{
			ASTFunction main = Global.getFunction("main");
			connector.addScope(main.getSymbolTable(), "main", "Global");
			connector.startSnap(Global.getFunction("main").getLineNumber());
			connector.startPar();
				//connector.addQuestion(questionFactory.addBeginQuestion());
			connector.endPar();
			connector.startPar();
				connector.showScope("main");
			connector.endPar();
			connector.endSnap();
			main.jjtAccept(this, null);
			return false;
		}
		else
		{
			node.jjtGetChild(0).jjtAccept(this, null);
			//update();
			return true;
		}	
		
	}
	
	public void handleVarDecl(ASTVarDecl node)
	{
		System.out.println("Visiting var decl");
		String name = node.getName();
		node.setLineNumber(((SimpleNode)node.jjtGetParent()).getLineNumber());
		SymbolTable s = Global.getCurrentSymbolTable();
		ArrayList<Integer> values;
		if (node.getIsArray())
		{
			ByValVariable v = (ByValVariable) s.getVariable(name);
			v.setArray();
			System.out.println("BLAH" + node.jjtGetChild(0));
			 values = (ArrayList<Integer>)handleArrayDeclaration((ASTArrayDeclaration)node.jjtGetChild(0));
			System.out.println("Values: " + values);
			v.setValues(values);
		}
		else
		{
			Integer value =(Integer) node.jjtGetChild(0).jjtAccept(this, null);
			s.setValue(name, value);
		}
			//Drawing Stuff
			connector.addVariable(s.getVariable(name), name, s.getName());
			
			//This is a snapshot
			connector.startSnap(node.getLineNumber());
				connector.startPar();
					connector.showVar(Global.getCurrentSymbolTable().getVariable(name));
				connector.endPar();
			connector.endSnap();	
	}
	
	public void handleFunction(ASTFunction node)
	{	
		//Get the function's symbol table, set it's previous to the
		// calling function's, and then set it to current.
		
		System.out.println("Visiting function");
		SymbolTable currentSymbolTable = node.getSymbolTable();
		for (String p : node.getParameters())
		{
			ByRefVariable v = new ByRefVariable(null);
			v.setParam();
			currentSymbolTable.put(p, v);
		}
		Global.setCurrentSymbolTable(currentSymbolTable);

		//Drawing Stuff:
		//connector.addScope(currentSymbolTable, currentSymbolTable.getName(), "Global");
		System.out.println("Added scope " + currentSymbolTable.getName());
		//Drawing the actually running
		connector.startSnap(node.getLineNumber());
			connector.startPar();

			connector.endPar();
		connector.endSnap();
			
		
		System.out.println("Executing function: " + node.getName());
		update(node.getLineNumber(), UPDATE_REASON_FUNCTION);
		node.jjtGetChild(0).jjtAccept(this, null);
		leaveScope();
	}
	public ArrayList<Integer> handleArrayDeclaration(ASTArrayDeclaration node)
	{
		ArrayList<Integer> values = new ArrayList<Integer>();
		for (int i = 0; i < node.jjtGetNumChildren(); i++)
		{
			Integer value = (Integer)node.jjtGetChild(i).jjtAccept(this, null);
			System.out.println("ff " + value);
			values.add(value);
		}
		System.out.println(values);
		return values;
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

		//System.out.println(node.getCode());
		
		//Drawing
		connector.startSnap(node.getLineNumber());
			connector.startPar();
		
		//FIXME we'll see how this works	
		node.jjtGetChild(0).jjtAccept(this, null);
		if (((SimpleNode)node.jjtGetChild(0)).getId() == JJTCALL)
		{
			((ASTCall)(node.jjtGetChild(0))).setLineNumber(node.getLineNumber());
		}
			
			connector.endPar();
		connector.endSnap();

	}
	
	public Integer handleCall(ASTCall node)
	{
		//Get the correct function head node
		ASTFunction fun = Global.getFunction(node.getName());
		System.out.println("Calling: " + fun.getName());
		//Get the parameters and put the correct values in the symbolTable
		SymbolTable st = fun.getSymbolTable();

		ArrayList<String> parameters = fun.getParameters();		
		ArrayList<Integer> args = (ArrayList<Integer>) node.jjtGetChild(0).jjtAccept(this, null);
		ArrayList<ASTVar> argNames = ((ASTArgs)node.jjtGetChild(0)).getArgs();
		System.out.println("Ready to set args");
		for (int i = 0; i < args.size(); i++)
		{
			Variable vv = st.getVariable(parameters.get(i));
			System.out.println(vv + " ");
			ByRefVariable v = (ByRefVariable)st.getVariable(parameters.get(i));
			ByValVariable ref = (ByValVariable)Global.getCurrentSymbolTable().getVariable(argNames.get(i).getName());
			if (ref.getIsArray())
			{
				System.out.println("Setting ref to index " + argNames.get(i).getIndex());
				v.setRef(ref, argNames.get(i).getIndex());
			}
			else
			{
				v.setRef(ref);
			}
		}
		System.out.println("Set args");
		HashMap<String, String> pa = new HashMap<String, String>(); //Maps args to params
		for (int i = 0; i < parameters.size(); i++)
		{
			pa.put(parameters.get(i), argNames.get(i).getName());
		}
		Global.setCurrentParamToArg(pa);
		//Ok lets set refs now	
	
		//Drawing Stuff
		connector.addScope(fun.getSymbolTable(), fun.getName(), "Global");
		connector.startSnap(node.getLineNumber());
			connector.startPar();
				connector.showScope(node.getName());
				connector.addQuestion(questionFactory.addCallQuestion(Global.getCurrentParamToArg(), fun.getName()));
			connector.endPar();
			
			connector.startPar();
				for (int i = 0; i < parameters.size(); i++)
				{ 	
					Variable v1 = Global.getCurrentSymbolTable().getVariable(argNames.get(i).getName());				Variable v2 = st.getVariable(parameters.get(i));
					((ByRefVariable)v2).setRef(((ByValVariable)v1));		
					if (v1.getIsArray())
					{
						connector.addVariableReference(v2, v1, argNames.get(i).getIndex());
					}
					else
					{
						connector.addVariableReference(v2, v1);	
					}
					 //Now in interpreter we should be pointing correctly.  				
					
					System.out.println("Adding a reference from " + argNames.get(i).getName() +
						" to " + parameters.get(i));
				}
				
			connector.endPar();
		connector.endSnap();
				
		fun.jjtAccept(this, null);
		return 0;
	}
	
	public Integer handleVar(ASTVar node)
	{
		Integer value;
		Variable v = Global.getCurrentSymbolTable().getVariable(node.getName());
		if (node.getIsArray())
		{
			int index = (Integer) node.jjtGetChild(0).jjtAccept(this, null);
			node.setIndex(index);
			value = v.getValue(index);
		}
		else
		{
			value = v.getValue();
		}
		return value;
	}
	
	public void handleAssignment(ASTAssignment node)
	{
		String name = node.getName();
		//boolean gotAQuestion = addQuestion(node.getLineNumber(), name, QUESTION_REASON_ASSIGNMENT);
		Integer value = (Integer)node.jjtGetChild(1).jjtAccept(this, null);
		System.out.println("Assigning to " + name + " value of " + value);
		int index = 0;
		Variable v = Global.getCurrentSymbolTable().getVariable(name);

		if (v.getIsArray())
		{
			index = (Integer) node.jjtGetChild(0).jjtGetChild(0).jjtAccept(this, null);
			v.setValue(value, index);
		}
		else
		{
			v.setValue(value);
		}
		System.out.println("Ok, set value");
		//Drawing stuff. snap and par should be opened from enclosing statement
		/*
		if (gotAQuestion)
		{
			connector.endPar();
			connector.endSnap();
			connector.startSnap(node.getLineNumber());
			connector.startPar();
		}*/
			if (v.getIsArray())
			{
				connector.modifyVar(Global.getCurrentSymbolTable().getVariable(name), index, value);
			}
			else if (v instanceof ByValVariable)
			{
				connector.modifyVar(Global.getCurrentSymbolTable().getVariable(name), value);
			}
			
			else 
			{
				ByRefVariable var = (ByRefVariable)Global.getCurrentSymbolTable().getVariable(name);
				System.out.println("It's a reference variable named " + name);
				ByValVariable val = var.getRef();
				if (val.getIsArray())
				{
					connector.modifyVar(val, var.getRefIndex(), value);
				}
				else
				{
					connector.modifyVar(
						val,
						 value);
				}
			}
		
		update(node.getLineNumber(), UPDATE_REASON_ASSIGNMENT);
		System.out.println("Leaving assignment");
		
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
 		node.gatherArgs();
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
		System.out.println("gg" + node.getValue());
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
  	
  	public void leaveScope()
  	{
  		System.out.println("Leaving scope " + Global.getCurrentSymbolTable().getName());

  		update(-1, UPDATE_REASON_LEAVEFUN);
  	}
}
