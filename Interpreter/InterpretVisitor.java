package Interpreter;
import viz.*;
import java.util.*;

public class InterpretVisitor implements VizParserVisitor, VizParserTreeConstants, UpdateReasons
{
	private QuestionFactory questionFactory;


	private Question assignmentQuestion;
	private Question callQuestion;
	private Question startQuestion;
	
	private XAALConnector connector;
		private static final int QUESTION_FREQUENCY = 65;
	public static final int LINE_NUMBER_END = -1;

	public void setQuestionFactory(QuestionFactory questionFactory)
	{
		this.questionFactory = questionFactory;
	}
	
	public void setXAALConnector(XAALConnector xc)
	{
		this.connector = xc;
	}

	//FIXME use this?
	public void update(int lineNumber, int reason)
	{
		System.out.println("Update on " + lineNumber);
		//System.out.println(Global.getCurrentSymbolTable().toString());
		//questionFactory.addAnswers(lineNumber, reason);
	}
	
	public void setAssignmentQuestionAnswer(int value)
	{
		if (assignmentQuestion instanceof FIBQuestion)
		{
			((FIBQuestion)assignmentQuestion).addAnswer(value + "");
		}
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
		//System.out.println("visiting program");
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
		
		
		int value = Global.getCurrentSymbolTable().get(startQuestion.getVariable());
		if (startQuestion instanceof FIBQuestion)
		{
		
			((FIBQuestion)startQuestion).addAnswer(value+"");
		}
		else if (startQuestion instanceof TFQuestion)
		{
			Random r = new Random();
			int prob = r.nextInt(10);
			int qa = value;
			if (prob >= 3 && value != startQuestion.getValue())
			{
				qa = startQuestion.getValue();
				((TFQuestion)startQuestion).setAnswer(false);
			}
			else
			{
				((TFQuestion)startQuestion).setAnswer(true);
				
			}
			startQuestion.setText(startQuestion.getText() + qa + ".");
		}
		
		
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
		connector.startSnap(Global.getFunction("main").getLineNumber());
		connector.startPar();
		
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
			startQuestion = questionFactory.getStartQuestion();
			connector.addQuestion(startQuestion);
			connector.endPar();
			connector.endSnap();
			ASTFunction main = Global.getFunction("main");
			connector.addScope(main.getSymbolTable(), "main", "Global");
			connector.startSnap(Global.getFunction("main").getLineNumber());
			connector.startPar();

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
		//System.out.println("Visiting var decl");
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
					connector.showVar(Global.getCurrentSymbolTable().getVariable(name));
	
	}
	
	public void handleFunction(ASTFunction node)
	{	
		//Get the function's symbol table, set it's previous to the
		// calling function's, and then set it to current.
		if (!node.getUsed())
		{
			System.out.println("Unused function");
			return;
		}
		SymbolTable currentSymbolTable = node.getSymbolTable();
		for (String p : node.getParameters())
		{
			ByValVariable v = new ByValVariable(-255);
			v.setParam();
			currentSymbolTable.put(p, v);
		}
		Global.setCurrentSymbolTable(currentSymbolTable);

		
		System.out.println("Executing function: " + node.getName());

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
		if (!node.getIsFunction())
		{
			Global.setCurrentSymbolTable(Global.getFunction("foo").getSymbolTable());
			System.out.println(Global.getCurrentSymbolTable());
			connector.addScope(Global.getCurrentSymbolTable(), "foo", "main");
			connector.showScope("foo");
		}

		int numStatements = node.jjtGetNumChildren();
		for (int i = 0; i < numStatements; i++)
		{
			System.out.println(node.jjtGetChild(i));
			node.jjtGetChild(i).jjtAccept(this, null);
		}
		if (!node.getIsFunction())
		{
			Global.setCurrentSymbolTable(Global.getCurrentSymbolTable().getPrevious());
		}
	}
	
	public void handleStatement(ASTStatement node)
	{

		//System.out.println(node.getCode());
		
		//Drawing
		connector.startSnap(node.getLineNumber());
			connector.startPar();
		
		//FIXME we'll see how this works	
		
		//Nested scope for by macro
		SimpleNode s = (SimpleNode) node.jjtGetChild(0);
		
		if (s instanceof ASTStatementList)
		{

			System.out.println("Nested scope!");
			System.out.println(Global.getCurrentSymbolTable());
			SymbolTable st = new SymbolTable(Global.getCurrentSymbolTable());
			st.setName("nested");
			System.out.println(st);
			Global.setCurrentSymbolTable(st);
			s.jjtAccept(this, null);
			Global.setCurrentSymbolTable(st.getPrevious());
		}
		
		
		node.jjtGetChild(0).jjtAccept(this, null);
		if (((SimpleNode)node.jjtGetChild(0)).getId() == JJTCALL)
		{
			((ASTCall)(node.jjtGetChild(0))).setLineNumber(node.getLineNumber());
		}
			
			connector.endPar();
		connector.endSnap();
		update(node.getLineNumber(), UPDATE_REASON_STATEMENT);
	}
	
	public Integer handleCall(ASTCall node)
	{
			boolean gotAQuestion = true; //FIXME HACK
		//Get the correct function head node
		ASTFunction fun = Global.getFunction(node.getName());
		System.out.println("Calling: " + fun.getName());
		//Get the parameters and put the correct values in the symbolTable
		SymbolTable st = fun.getSymbolTable();
		String name = fun.getName();
		ArrayList<String> parameters = fun.getParameters();		
		ArrayList<Integer> args = (ArrayList<Integer>) node.jjtGetChild(0).jjtAccept(this, null);
		ArrayList<ASTVar> argNames = ((ASTArgs)node.jjtGetChild(0)).getArgs();

		for (int i = 0; i < args.size(); i++)
		{
			st.setValue(parameters.get(i), args.get(i));
		}
		HashMap<String, String> pa = new HashMap<String, String>(); //Maps args to params
		for (int i = 0; i < parameters.size(); i++)
		{
			pa.put(parameters.get(i), argNames.get(i).getName());
		}
		Global.setCurrentParamToArg(pa);

	//QUESTION!!!
		callQuestion = questionFactory.getCallQuestion(name, pa);
		
		//Drawing Stuff
		connector.addScope(fun.getSymbolTable(), fun.getName(), "Global");
		connector.startSnap(node.getLineNumber());
			connector.startPar();
				connector.showScope(node.getName());
				
			connector.endPar();

				connector.showScope(node.getName());
				connector.addQuestion(callQuestion);			
			connector.startPar();
				for (int i = 0; i < parameters.size(); i++)
				{
					Variable v1 = Global.getCurrentSymbolTable().getVariable(argNames.get(i).getName());
					Variable v2 = st.getVariable(parameters.get(i));
					if (v1.getIsArray())
					{
						int index = argNames.get(i).getIndex();
						connector.moveValue(v1, index, v2);
					}
					else
					{
						connector.moveValue(v1, v2);
					}
				}
			connector.endPar();
		connector.endSnap();
				
		fun.jjtAccept(this, null);
		
		if(gotAQuestion)
				{

			int answer = Global.getFunction("main").getSymbolTable().get(callQuestion.getVariable());
			if (callQuestion instanceof FIBQuestion)
			{
				System.out.println("AAAA " + answer);
				((FIBQuestion)callQuestion).addAnswer(answer+"");
			}
			else if (callQuestion instanceof TFQuestion)
			{
				int qa = answer;
				//Getting the value of the var at the end of the function
				String paramName = Global.getCurrentParamToArg().get(callQuestion.getVariable());
				int prevVal = Global.getFunction("foo").getSymbolTable().get(paramName);
			
				Random r = new Random();
				int choose = r.nextInt(3);
				switch (choose)
				{
					case 0:
						qa = callQuestion.getValue();
						((TFQuestion)callQuestion).setAnswer(false);
						if (qa == answer) // Value is the same anyway
						{
							((TFQuestion)callQuestion).setAnswer(true);
						}
						break;
					case 1:
						qa = prevVal;
						((TFQuestion)callQuestion).setAnswer(false);
						if (qa == answer) // Value is the same anyway
						{
							((TFQuestion)callQuestion).setAnswer(true);
						}
						break;
					case 2:
						((TFQuestion)callQuestion).setAnswer(true);
						break;
				}
					
		
				callQuestion.setText(callQuestion.getText() + qa);
			}	
			else
			{
				System.out.println("CQC " + callQuestion);
			}
		}
		return 0;
	}
	
	public Integer handleVar(ASTVar node)
	{
		if (node.getIsArray())
		{
			int index = (Integer) node.jjtGetChild(0).jjtAccept(this, null);
			node.setIndex(index);
			return Global.getCurrentSymbolTable().get(node.getName(), index);
		}
		return Global.getCurrentSymbolTable().get(node.getName());
	}
	
	public void handleAssignment(ASTAssignment node)
	{
		Random r = new Random();
		int q = r.nextInt(100);
		
			boolean gotAQuestion = q < QUESTION_FREQUENCY;//HACK FOR NOW FIXME
		String name = node.getName();
		System.out.println("Assigning to " + name);
		Integer value = (Integer)node.jjtGetChild(1).jjtAccept(this, null);
		int index = 0;
		System.out.println("HRM");
		System.out.println(Global.getCurrentSymbolTable());
		ByValVariable v = (ByValVariable) Global.getCurrentSymbolTable().getVariable(name);
		System.out.println("VVVV" + v.getIsArray() + " " + name);
		if (v.getIsArray())
		{
			System.out.println("Dealing with an array");
			index = (Integer) node.jjtGetChild(0).jjtGetChild(0).jjtAccept(this, null);
			System.out.println("Index: " + index);
			v.setValue(value, index);
		if (gotAQuestion)
		{
			assignmentQuestion = questionFactory.getAssignmentQuestion(node.getLineNumber(), name, index);
		}
		}
		else
		{
			if (gotAQuestion)
			{
				assignmentQuestion = questionFactory.getAssignmentQuestion(node.getLineNumber(), name);
			}
			Global.getCurrentSymbolTable().setValue(name, value);
		}
		
		//Drawing stuff. snap and par should be opened from enclosing statement
		if (gotAQuestion)
		{
			int i;
			if (assignmentQuestion.getIndex() != -1)
			{
				System.out.println("This might be wrong");
				i = Global.getCurrentSymbolTable().get(name, assignmentQuestion.getIndex());
			}
			else
			{
				System.out.println("FFF " + assignmentQuestion.getVariable());
				i = Global.getCurrentSymbolTable().get(assignmentQuestion.getVariable());
			}
			setAssignmentQuestionAnswer(i);
			connector.addQuestion(assignmentQuestion);
			connector.endPar();
			connector.endSnap();
			connector.startSnap(node.getLineNumber());
			connector.startPar();
		}
		
			if (v.getIsArray())
			{
				connector.modifyVar(Global.getCurrentSymbolTable().getVariable(name), index, value);
			}
			else
			{
				connector.modifyVar(Global.getCurrentSymbolTable().getVariable(name), value);
			}
		
		update(node.getLineNumber(), UPDATE_REASON_ASSIGNMENT);
		
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
