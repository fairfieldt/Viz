package Interpreter;
import viz.*;
import java.util.*;

public class ByNameInterpretVisitor implements VizParserVisitor, VizParserTreeConstants, UpdateReasons
{
	private QuestionFactory questionFactory;


	private Question assignmentQuestion;
	private Question callQuestion;
	private Question startQuestion;
	
	private ASTProgram program;
	
	private XAALConnector connector;
		private static final int QUESTION_FREQUENCY = 65;
	public static final int LINE_NUMBER_END = -1;
	
	private boolean byMacroFlag = false;

	public void setQuestionFactory(QuestionFactory questionFactory)
	{
		this.questionFactory = questionFactory;
	}
	
	public void setXAALConnector(XAALConnector xc)
	{
		this.connector = xc;
	}
	
	public void setByMacroFlag()
	{
		byMacroFlag = true;
	}

	//FIXME use this?
	public void update(int lineNumber, int reason)
	{
if (XAALScripter.debug) {		System.out.println("Update on " + lineNumber);
}if (XAALScripter.debug) {		System.out.println(Global.getCurrentSymbolTable().toString());
}		//questionFactory.addAnswers(lineNumber, reason);
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
if (XAALScripter.debug) {				System.out.println("Unimplemented");
}		}
		return retVal;
	}
	
	public void handleProgram(ASTProgram node)
	{
		program = node;
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
		
		
		int value = 0;
		try
		{
			value = Global.getCurrentSymbolTable().get(startQuestion.getVariable());
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
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
				// we can't hide foo in by macro cuz it doesn't exist
				if (!byMacroFlag)
					connector.hideScope("foo");
if (XAALScripter.debug) {				System.out.println("BLAH");
}			connector.endPar();
		connector.endSnap();
if (XAALScripter.debug) {		System.out.println("Done");
}	}
	
	public void handleDeclarationList(ASTDeclarationList node)
	{
		connector.startSnap(Global.getFunction("main").getLineNumber());
		connector.startPar();
		
if (XAALScripter.debug) {		System.out.println("Visiting declList");
}		int numDecls = node.jjtGetNumChildren();
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
if (XAALScripter.debug) {		System.out.println("VDCL " + s + " " + name);
}		ArrayList<Integer> values;

		if (node.getIsArray())
		{
			ByValVariable v = (ByValVariable) s.getVariable(name);
			v.setArray();
if (XAALScripter.debug) {			System.out.println("BLAH" + node.jjtGetChild(0));
}			 values = (ArrayList<Integer>)handleArrayDeclaration((ASTArrayDeclaration)node.jjtGetChild(0));
if (XAALScripter.debug) {			System.out.println("Values: " + values);
}			v.setValues(values);
		}
		else
		{
			Integer value =(Integer) node.jjtGetChild(0).jjtAccept(this, null);
			s.setValue(name, value);
		}

			//Drawing Stuff
if (XAALScripter.debug) {			System.out.println(s.getName());
}			connector.addVariable(s.getVariable(name), name, s.getName());
			
			//This is a snapshot
					connector.showVar(Global.getCurrentSymbolTable().getVariable(name));
	
	}
	
	public void handleFunction(ASTFunction node)
	{	
		//Get the function's symbol table, set it's previous to the
		// calling function's, and then set it to current.
		if (!node.getUsed())
		{
			return;
		}
		SymbolTable currentSymbolTable = node.getSymbolTable();
		for (String p : node.getParameters())
		{
			ByNameVariable v = new ByNameVariable();
			v.setParam();
			currentSymbolTable.put(p, v);
		}
		Global.setCurrentSymbolTable(currentSymbolTable);

		
		node.jjtGetChild(0).jjtAccept(this, null);
		leaveScope();
	}
	
	
	public ArrayList<Integer> handleArrayDeclaration(ASTArrayDeclaration node)
	{
		ArrayList<Integer> values = new ArrayList<Integer>();
		for (int i = 0; i < node.jjtGetNumChildren(); i++)
		{
			Integer value = (Integer)node.jjtGetChild(i).jjtAccept(this, null);
			values.add(value);
		}
		return values;
	}
	
	public void handleStatementList(ASTStatementList node)
	{
		if (!node.getIsFunction())
		{
			Global.setCurrentSymbolTable(Global.getFunction("foo").getSymbolTable());
if (XAALScripter.debug) {			System.out.println(Global.getCurrentSymbolTable());
}			connector.addScope(Global.getCurrentSymbolTable(), "", "main");
			connector.showScope("");
		}

		int numStatements = node.jjtGetNumChildren();
		for (int i = 0; i < numStatements; i++)
		{
if (XAALScripter.debug) {			System.out.println(node.jjtGetChild(i));
}			node.jjtGetChild(i).jjtAccept(this, null);
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

if (XAALScripter.debug) {			System.out.println("Nested scope!");
}if (XAALScripter.debug) {			System.out.println(Global.getCurrentSymbolTable());
}			SymbolTable st = new SymbolTable(Global.getCurrentSymbolTable());
			st.setName("nested");
if (XAALScripter.debug) {			System.out.println(st);
}			Global.setCurrentSymbolTable(st);
			s.jjtAccept(this, null);
			Global.setCurrentSymbolTable(st.getPrevious());
if (XAALScripter.debug) {			System.out.println("Done with nested scope!");
}		}
		else
		{
		
			node.jjtGetChild(0).jjtAccept(this, null);
			if (((SimpleNode)node.jjtGetChild(0)).getId() == JJTCALL)
			{
				((ASTCall)(node.jjtGetChild(0))).setLineNumber(node.getLineNumber());
			}
				
				connector.endPar();
			connector.endSnap();
			update(node.getLineNumber(), UPDATE_REASON_STATEMENT);
		}
	}
	
	public Integer handleCall(ASTCall node)
	{
			boolean gotAQuestion = true; //FIXME HACK
		//Get the correct function head node
		ASTFunction fun = Global.getFunction(node.getName());
if (XAALScripter.debug) {		System.out.println("Calling: " + fun.getName());
}		//Get the parameters and put the correct values in the symbolTable
		SymbolTable st = fun.getSymbolTable();
		String name = fun.getName();
		ArrayList<String> parameters = fun.getParameters();		
		ArrayList<Integer> args = (ArrayList<Integer>) node.jjtGetChild(0).jjtAccept(this, null);
		ArrayList<ASTVar> argNames = ((ASTArgs)node.jjtGetChild(0)).getArgs();

		for (int i = 0; i < args.size(); i++)
		{
			ByNameVariable v = (ByNameVariable) st.getVariable(parameters.get(i));
			v.setRef(argNames.get(i));
			ByValVariable argVar = (ByValVariable) Global.getCurrentSymbolTable().getVariable(argNames.get(i).getName());

			if (argVar.getIsArray())
			{
				v.setVariable(argVar, argNames.get(i).getIndex());
			}
			else
			{
				v.setVariable(argVar);
			}
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

			int answer = 0;
			try
			{
				Global.getFunction("main").getSymbolTable().get(callQuestion.getVariable());
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
			if (callQuestion instanceof FIBQuestion)
			{
if (XAALScripter.debug) {				System.out.println("AAAA " + answer);
}				((FIBQuestion)callQuestion).addAnswer(answer+"");
			}
			else if (callQuestion instanceof TFQuestion)
			{
				int qa = answer;
				//Getting the value of the var at the end of the function
				String paramName = Global.getCurrentParamToArg().get(callQuestion.getVariable());
				int prevVal = 0;
				try
				{
					Global.getFunction("foo").getSymbolTable().get(paramName);
				}
				catch (Exception e)
				{
					System.out.println(e);
				}
			
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
if (XAALScripter.debug) {				System.out.println("CQC " + callQuestion);
}			}
		}
		return 0;
	}
	
	public Integer handleVar(ASTVar node)
	{
		System.out.println(node.getName());
		Integer value = -256;
		Variable v = Global.getCurrentSymbolTable().getVariable(node.getName());
		if (node.getIsArray())
		{
			int index = (Integer) node.jjtGetChild(0).jjtAccept(this, null);
			System.out.println(node.jjtGetChild(0));
			node.setIndex(index);
			try
			{
				System.out.println(index);
				System.out.println(v);
				value = v.getValue(index);
				System.out.println("Got value " + value + " from index " + index);
			}
			catch (VizIndexOutOfBoundsException e)
			{
				System.out.println(e);
				ASTExpression exp = (ASTExpression)node.jjtGetChild(0);
				ASTNum num = new ASTNum(JJTNUM);
				Random r= new Random();
				num.setValue(r.nextInt(6));
				exp.jjtAddChild(num, 0);
				try
				{
					index = (Integer) exp.jjtAccept(this, null);
					value = v.getValue(index);
				}
				catch (VizIndexOutOfBoundsException f)
				{
					System.out.println("oops...");
				}
				program.codeBuilt = false;
				Global.lineNumber = 1;
				program.buildCode();
				connector.modifyPseudocodeOnAll(program.getPseudocode());
			}
		}
		else
		{
			try
			{
				value = v.getValue();
			}	
			catch (VizIndexOutOfBoundsException f)
			{
				System.out.println(f);
			}	
		}
		return value;
	}
	
	public void handleAssignment(ASTAssignment node)
	{
		Random r = new Random();
		int q = r.nextInt(100);
		
			boolean gotAQuestion = q < QUESTION_FREQUENCY;//HACK FOR NOW FIXME
		String name = node.getName();
		if (XAALScripter.debug) {System.out.println("Assigning to " + name);}
		Integer value = (Integer)node.jjtGetChild(1).jjtAccept(this, null);
		int index = 0;
		
		Variable v = Global.getCurrentSymbolTable().getVariable(name);

		if (v.getIsArray())
		{
			index = (Integer) node.jjtGetChild(0).jjtGetChild(0).jjtAccept(this, null);
			System.out.println("Index " + index + " of " + node.getName());
			try
			{
				v.setValue(value, index);
			}
			catch (VizIndexOutOfBoundsException e)
			{
			/*
				System.out.println(e);
				ASTExpression exp = (ASTExpression)node.jjtGetChild(0).jjtGetChild(0);
				ASTNum num = new ASTNum(JJTNUM);

				int val = r.nextInt(6);
				num.setValue(val);
				exp.jjtAddChild(num, 0);
				index = (Integer) node.jjtGetChild(0).jjtGetChild(0).jjtAccept(this, null);
				try
				{
					v.setValue(value, index);
				}
				catch (VizIndexOutOfBoundsException f)
				{
					System.out.println("oops");
				}
				Global.lineNumber = 1;

				program.codeBuilt = false;

				program.buildCode();
				connector.modifyPseudocodeOnAll(program.getPseudocode());				
			*/
				System.out.println(e);
				
			}
			System.out.println("that was close");
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
			int i = -256;
			if (assignmentQuestion.getIndex() != -1)
			{
				if (assignmentQuestion.aboutArg)
				{
					try
					{
						i = Global.getFunction("main").getSymbolTable().get(assignmentQuestion.getVariable(), assignmentQuestion.getIndex());
					}
					catch (Exception e)
					{
						System.out.println(e);
					}
				}
				else
				{
					try
					{
						i = Global.getCurrentSymbolTable().get(name, assignmentQuestion.getIndex());
					}
					catch (Exception e)
					{
						System.out.println(e);
					}
				}
			}
			else
			{
				if (assignmentQuestion.aboutArg)
				{
					System.out.println("Getting " + name);
					try
					{
						i = Global.getFunction("main").getSymbolTable().get(assignmentQuestion.getVariable());
					}
					catch (Exception e)
					{
						System.out.println(e);
					}
				}
				else
				{
					try
					{
						i = Global.getCurrentSymbolTable().get(name);
					}
					catch (Exception e)
					{
						System.out.println(e);
					}
				}
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
if (XAALScripter.debug) {		System.out.println("gg" + node.getValue());
}		return node.getValue();
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
if (XAALScripter.debug) {  		System.out.println("Leaving scope " + Global.getCurrentSymbolTable().getName());
}
  		update(-1, UPDATE_REASON_LEAVEFUN);
  	}
}
