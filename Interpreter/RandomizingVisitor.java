package Interpreter;

import java.util.*;

public class RandomizingVisitor implements VizParserVisitor, VizParserTreeConstants {

	
	String[] possVars = {"g","m","n", "v", "w"};
	
	@Override
	public Object visit(SimpleNode node, Object data) {
		
		if (node instanceof ASTProgram)
			this.visit((ASTProgram)node, null);
		else if (node instanceof ASTFunction)
			this.visit((ASTFunction)node, null);
		else if (node instanceof ASTDeclarationList)
			this.visit((ASTDeclarationList)node, null);
		else if (node instanceof ASTStatementList)
			this.visit((ASTStatementList)node, null);
		else if (node instanceof ASTDeclaration)
			this.visit((ASTDeclaration)node, null);
		return null;
	}

	@Override
	public Object visit(ASTProgram node, Object data) {
		ASTDeclarationList innerDecl = (ASTDeclarationList)node.jjtGetChild(0);
		
		// add 1-3 var decls
		Random r = new Random();
		int numOfVars = r.nextInt(3) + 1;
		
		SymbolTable symbols = Global.getSymbolTable();
		for (int i = 0; i < numOfVars; i++)
		{
			String varName = getRandomItem(possVars);
			while(symbols.get(varName) != -255)
			{
				varName = getRandomItem(possVars);
			}
			
			createVarDecl(innerDecl,varName, r.nextInt(5)+1, i, ASTDeclaration.class, true);
		}
		
		//TODO: add an array
	
		node.childrenAccept(this, null);
		return null;
	}

	@Override
	public Object visit(ASTVarDecl node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTFunction node, Object data) {
		
		if (node.getName().equals("main"))
		{
			visitMain(node, data);
		}
		else
		{
			visitFunc(node, data);
		}
		
		return null;
	}

	@Override
	public Object visit(ASTCall node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTVar node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTExpression node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object visit(ASTDeclarationList node, Object data) {
		node.childrenAccept(this, null);
		return null;
	}

	@Override
	public Object visit(ASTDeclaration node, Object data) {
		node.childrenAccept(this, null);
		return null;
	}

	@Override
	public Object visit(ASTNum node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object visit(ASTArrayDeclaration node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTStatementList node, Object data) {
		node.childrenAccept(this, null);
		return null;
	}

	@Override
	public Object visit(ASTStatement node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTOp node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Object visit(ASTAssignment node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTArgs node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * this limits the entire randomizer to one function other than main
	 * @param node
	 * @param data
	 * @return
	 */
	private Object visitMain(ASTFunction node, Object data)
	{
		ASTStatementList innerStatement = (ASTStatementList)node.jjtGetChild(0);
		// add 0-2 var decls
		Random r = new Random();
		int numOfVars = r.nextInt(3);
		
		SymbolTable symbols = node.getSymbolTable();
		for (int i = 0; i < numOfVars; i++)
		{
			//TODO: how do we decide which var names to use in here?
			String varName = getRandomItem(possVars);
			while(symbols.get(varName, true) != -255)
			{
				varName = getRandomItem(possVars);
			}
			
			createVarDecl(innerStatement,varName, r.nextInt(5)+1, i, ASTStatement.class);
			
		}
		// TODO: add 0-1 array decl
		
		// add funcCall
		ASTStatement surroundingStmt = new ASTStatement(JJTSTATEMENT);
		surroundingStmt.jjtSetParent(innerStatement);
		innerStatement.jjtAddChild(surroundingStmt, innerStatement.jjtGetNumChildren());
		
		
		ASTCall call = new ASTCall(JJTCALL);
		call.jjtSetParent(surroundingStmt);
		surroundingStmt.jjtAddChild(call, surroundingStmt.jjtGetNumChildren());
		
		HashMap<String, ASTFunction> funcs = Global.getFunctions();
		
		Set<String> keys = funcs.keySet();
		String callName = null;
		Iterator<String> iter = keys.iterator();
		while(iter.hasNext())
		{
			callName = iter.next();
			if (!callName.equals("main"))
				break;
		}
		
		call.setName(callName);
		
		//decide on the number of params the second func will have.
		
		HashSet<String> varNames = node.getSymbolTable().getCurrentVarNames();
		
		//if true 3 params, else 2
		if (r.nextBoolean())
		{
			String[] varNameArray = new String[varNames.size()]; 
			varNames.toArray(varNameArray);
			
			//TODO: get it so there's repeated params sometimes
			for (int i = 0; i < 3; i++)
			{
				//TODO: is this right?
				ASTVar var = new ASTVar(JJTVAR);
				
				var.setName(getRandomItem(varNameArray));
				
				call.addArg(var);
			}

			funcs.get(callName).addParams("x","y","z");
		}
		else
		{
			String[] varNameArray = new String[varNames.size()]; 
			varNames.toArray(varNameArray);
			
			//TODO: get it so there's repeated params more often
			for (int i = 0; i < 2; i++)
			{
				//TODO: is this right?
				ASTVar var = new ASTVar(JJTVAR);
				
				var.setName(getRandomItem(varNameArray));
				
				call.addArg(var);			
			}
			
			funcs.get(callName).addParams("x", "y");
		}
		
		
		
		return null;
	}
	
	private Object visitFunc(ASTFunction node, Object data)
	{
		
		// add 0-1 var decls
		Random r = new Random();
		int numOfVars = r.nextInt(2);
		
		SymbolTable symbols = node.getSymbolTable();
		
		for (int i = 0; i < numOfVars; i++)
		{
			String varName = getRandomItem(possVars);
			while(symbols.get(varName, true) != -255)
			{
				varName = getRandomItem(possVars);
			}
			
			createVarDecl(node.jjtGetChild(0),varName, r.nextInt(5)+ 1, i, ASTStatement.class);
		}
		
		//start making some crazy assignment statements
		String[] varNames = new String[symbols.getCurrentVarNames().size()];
		symbols.getCurrentVarNames().toArray(varNames);
		
		return null;
	}
	
	private <T> T getRandomItem(T[] array)
	{
		Random r = new Random();
		int rand = r.nextInt(array.length);
		return array[rand];
	}

	/**
	 * 
	 * @param set HashSet of T to get and remove from
	 * @param num number of items to remove from set
	 * @return an array of T
	 */
	/*
	private <T> T[] getAndRemoveRandomly (T[] set, int num)
	{
		Random r = new Random();
		ArrayList<T> list = new ArrayList<T>(num);
		for (int i = 0; i < num; i++)
		{
			int rNum = r.nextInt();
			set.
		}
		
		
	}
*//*
	private ASTStatement createRandStmt(Node parent, SymbolTable symbols, int index)
	{/*
		ASTStatement statement = new ASTStatement(JJTSTATEMENT);
		
		statement.jjtSetParent(parent);
		parent.jjtAddChild(statement, index);
		
		String[] varNames = new String[symbols.getCurrentVarNames().size()];
		
		symbols.getCurrentVarNames().toArray(varNames);
		
		ASTExpression stateChild = new ASTExpression(JJTEXPRESSION);
		
		stateChild.jjtSetParent(statement);
		statement.jjtAddChild(stateChild, 0);
		//TODO: this needs to have a chance of being numbers not vars
		/*
		ASTAssignment assignStmt = createRandAssign(getRandomItem(varNames), 
				getRandomItem(varNames),
				getRandomItem(varNames));
		
		assignStmt.jjtSetParent(stateChild);
		stateChild.jjtAddChild(assignStmt, 0);
		
		return statement;
		
	}*/
	
	private ASTAssignment createRandAssign(String lhs, String rhs1, String rhs2)
	{
		return null;
	}
	
	
	/**
	 * 
	 * @return an op node with addition or subtraction
	 */
	private ASTOp getRandomOpNode()
	{
		
		Random r = new Random();
		ASTOp op = new ASTOp(r.nextInt());
		if (r.nextBoolean())
			op.setOp("+");
		else
			op.setOp("-");
		return null;
	}
	
	private <T> void createVarDecl(Node parent, String varName, 
			int value, int indexInParent, T surroundingClass)
	{
		createVarDecl(parent, varName, value, indexInParent, surroundingClass, false);
	}
	
	/**
	 * Creates an ASTVarDecl in parent. Assumes that the parent is one step below an 
	 * ASTFunction or an ASTProgram. 
	 * @param parent the node that will contain the new ASTVarDecl.
	 * @param varName name of the variable
	 * @param value an integer that will be held in the variable.
	 * @param indexInParent the index in whatever list parent is.
	 * @param surroundingClass either ASTStatement or ASTDeclaration, depending on what you want
	 */
	private <T> void createVarDecl(Node parent, String varName, 
			int value, int indexInParent, T surroundingClass, boolean addSafely)
	{
		Node surroundingNode = null;
		if (surroundingClass == ASTDeclaration.class)
			surroundingNode = new ASTDeclaration(JJTDECLARATION);
		else
			surroundingNode = new ASTStatement(JJTSTATEMENT);
		
		surroundingNode.jjtSetParent(parent);
		if (addSafely)
			parent.jjtAddChildSafe(surroundingNode, indexInParent);
		else
			parent.jjtAddChild(surroundingNode, indexInParent);
		
		
		ASTVarDecl var = new ASTVarDecl(JJTVARDECL);

		
		var.jjtSetParent(surroundingNode);
		surroundingNode.jjtAddChild(var, 0);
		
		var.setName(varName);
		
		ASTExpression exp = new ASTExpression(JJTEXPRESSION);

		
		exp.jjtSetParent(var);
		var.jjtAddChild(exp, 0);
		
		ASTNum num = new ASTNum(JJTNUM);
	
		
		num.jjtSetParent(exp);
		exp.jjtAddChild(num, 0);
		
		num.setValue(value);
		
		if (parent.jjtGetParent() instanceof ASTProgram)
			Global.getSymbolTable().put(varName, new ByValVariable(value));
		else
			((ASTFunction)parent.jjtGetParent()).getSymbolTable().put(
					varName, new ByValVariable(value));
	}
	
}
