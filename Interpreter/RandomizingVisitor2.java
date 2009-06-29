package Interpreter;

import java.util.*;

public class RandomizingVisitor2<T> implements VizParserTreeConstants,
		VizParserVisitor
{
	Random rand = new Random();
	
	Class<T> varClass = null;
	
	final String[] possVars = {"g","i", "j", "k", "m","n", "v", "w"};
	final String[] paramNames = {"x", "y", "z" };	
	final int minVarDeclsInGlobal = 5;
	final int maxVarDeclsInGlobal = 5;
	
	final int minIntInDecl = 1;
	final int maxIntInDecl = 5;
	
	final int minVarDeclsInMain = 0;
	final int maxVarDeclsInMain = 1;
	
	final int minFooParams = 2;
	final int maxFooParams = 3;
	
	final int minFooVarDecls = 2;
	final int maxFooVarDecls = 2;
	
	final int minFooAOStmts = 4;
	final int maxFooAOStmts = 6;
	
	final int minArrayIndex = 0;
	final int maxArrayIndex = 5;
	
	final double chanceOfNumToVar = 1.0/10.0;
	final double chanceOfAssignToOp = 1.5/10.0;
	final double chanceOfPlusToMinus = 1.0/2.0;
	
	/**
	 * 
	 * @param clazz the subclass of AbstractVariable that you want the randomizer to use
	 */
	public RandomizingVisitor2(Class<T> clazz)
	{
		varClass = clazz;
	}
	
	
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
		SymbolTable localTable= Global.getSymbolTable();
		
		int numVarDecls = numOfGlobalVarDecls();
		
		//create VarDecls
		for (int i = 0; i < numVarDecls; i++)
		{
			 ArrayList<String> badNames = localTable.getCurrentVarNamesArray();
			 
			 String newName = getNewVarName(badNames);
			 int value = randomDeclInt();
			 
			 ASTVarDecl newVarDecl = createVarDecl(newName, value);
			 node.addLogicalChild(newVarDecl, i);
			 
			 localTable.put(newName, new ByValVariable(value));
		}
		
		int numArrayDecls = numOfGlobalArrayDecls();
		
		for (int i = 0; i < numArrayDecls; i++)
		{
			ArrayList<String> badNames = localTable.getCurrentVarNamesArray();
			//the array name here CAN'T be a param name
			for (int j = 0; j < paramNames.length; j++)
			{
				badNames.add(paramNames[j]);
			}
			
			String newName = getNewVarName(badNames);
			
			ASTVarDecl arrayDecl = createArrayDecl(newName);
			
			node.addLogicalChild(arrayDecl, numVarDecls + i);
			
			localTable.put(newName, ByValVariable.createArrayVariable());
		}
		
		visitMain(findChildFuncOfProg(node, "main"));
		visitFoo(findChildFuncOfProg(node, "foo"));
		
		return node.childrenAccept(this, data);
	}

	@Override
	public Object visit(ASTDeclarationList node, Object data) {
		return node.childrenAccept(this, data);
	}

	@Override
	public Object visit(ASTDeclaration node, Object data) {
		node.childrenAccept(this, null);
		return null;
	}

	@Override
	public Object visit(ASTVarDecl node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTArrayDeclaration node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTFunction node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTStatementList node, Object data) {
		return node.childrenAccept(this, data);
	}

	@Override
	public Object visit(ASTStatement node, Object data) {
		// TODO Auto-generated method stub
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
	public Object visit(ASTAssignment node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTExpression node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTArgs node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTOp node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object visit(ASTNum node, Object data) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void visitMain(ASTFunction main)
	{
		SymbolTable localTable = main.getSymbolTable();
		int numVars = numOfMainVarDecls();
		
		for (int i = 0; i < numVars; i++)
		{
			ArrayList<String> badNames = localTable.getLocalVarNamesArray();
			String name = getNewVarName(badNames);
			int value = randomDeclInt();
			 
			ASTVarDecl newVarDecl = createVarDecl(name, value);
			main.addLogicalChild(newVarDecl, i);
			 
			localTable.put(name, new ByValVariable(value));
		}
		
		int numOfParams = numOfFooParams();
		addParamsToFoo(numOfParams);
		
		ASTCall fooCall = ASTCall.createCall("foo");
		main.addLogicalChild(fooCall, numVars);
		
		for (int i = 0; i < numOfParams; i++)
		{
			ASTVar var = createVar(localTable);
			fooCall.addArg(var);
		}
		
	}
	
	private void visitFoo(ASTFunction foo)
	{
		SymbolTable localTable = foo.getSymbolTable();
		
		ArrayList<String> params = foo.getParameters();
		for(String p : params)
		{
			Variable v = null;
			try {
				v = (Variable)varClass.newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (varClass != ByRefVariable.class)
				v.setValue(-255);
			
			v.setParam();
			
			localTable.put(p, v);
		}
		
		int numVarDecls = numOfFooVarDecls();
		
		for(int i = 0; i < numVarDecls; i++)
		{
			ArrayList<String> badVars = localTable.getLocalVarNamesArray();
			badVars = getBadLHSNames(localTable, badVars);
			String name = getNewVarName(badVars);
			int value = randomDeclInt();
			
			ASTVarDecl v = createVarDecl(name, value);
			foo.addLogicalChild(v, i);
			
			localTable.put(name, new ByValVariable(value));
		}
		
		//TODO: Add Array Declaration
		
		ArrayList<String> safeIndexVars = new ArrayList<String>();
		safeIndexVars.add(createSafeIndexVar(localTable));
		
		int numAOStmts = numOfFooAOStmts();
		
		//this pos to use the ArrayIndex
		int posForArrayIndex = randomNum(0, numAOStmts-1);
		
		for (int i = 0; i < numAOStmts; i++)
		{
			ASTAssignment assign = null;
			
			if (i == posForArrayIndex)// the operation with teh array index
			{
				assign = createIndexedAssign(localTable, safeIndexVars);
			
			}
			else //non array index action
			{
				if(assignOrOp()) //basic assignment
				{
					assign = createBasicAssign(localTable, safeIndexVars);
				}
				else //assign with op
				{
					assign = createOpAssign(localTable, safeIndexVars);
				}
			}
			
			foo.addLogicalChild(assign, numVarDecls + i);
		}
	}
	
	private ASTVarDecl createVarDecl(String name, int value)
	{
		return ASTVarDecl.createVarDecl(name, value);
	}
	
	private ASTVarDecl createArrayDecl(String name)
	{
		ArrayList<Integer> values = createArrayList();
		return ASTVarDecl.createArrayDecl(name, values);
	}
	
	private ArrayList<Integer> createArrayList()
	{
		ArrayList<Integer> ret = new ArrayList<Integer>();
		
		int numElems = numOfArrayElems();
		
		for (int i = 0; i < numElems; i++)
		{
			ret.add(new Integer(randomDeclInt()));
		}
		
		return ret;
	}
	
	private ASTVar createVar(SymbolTable table)
	{
		return createVar(table, null);
	}
	
	private ASTVar createVar(SymbolTable table, ArrayList<String> bannedNames)
	{
		return createVar(table, bannedNames, false);
	}
	
	private ASTVar createVar(SymbolTable table, ArrayList<String> bannedNames, boolean indexIsNum)
	{
		ArrayList<String> origVarNames = table.getCurrentVarNamesArray();
		ArrayList<String> varNames = new ArrayList<String>();
		
		if (bannedNames != null)
		{
			for(String v : origVarNames)
			{
				if (!bannedNames.contains(v))
					varNames.add(v);
			}
		}
		else
		{
			varNames = origVarNames;
		}
		
		String randomVar = getRandomItem(varNames);
		
		Variable v = table.getVariable(randomVar);
		
		if (v.getIsArray())
		{
			ArrayList<String> nonArrayVars = table.getCurrentVarNamesArray(VarRetrRest.NotArrayOnly);
			
			testNonArrayVars(nonArrayVars, table);
			if (indexIsNum == true) //index must be a num
			{
				return ASTVar.createVarWithIndex(randomVar, randomArrayIndex());
			}
			else //index will be a var
			{
				return ASTVar.createVarWithIndex(randomVar, getRandomItem(nonArrayVars));
			}
			
		}

		return ASTVar.createVar(randomVar);

	}
	
	private String createSafeIndexVar(SymbolTable localTable)
	{
		ArrayList<String> safeVars = localTable.getCurrentVarNamesArray(VarRetrRest.NotArrayOnly);
		
		testSafeIndexVars(safeVars, localTable);
		
		return getRandomItem(safeVars);
	}
	
	private ASTAssignment createBasicAssign(SymbolTable localTable, ArrayList<String> badNames)
	{
		ArrayList<String> badLHSNames = getBadLHSNames(localTable, badNames);
		
		ASTVar lhs = createVar(localTable, badLHSNames, true);
		ASTNum rhs = ASTNum.createNum(randomDeclInt());
		
		return ASTAssignment.createAssignment(lhs, rhs);
	}
	
	private ASTAssignment createOpAssign(SymbolTable localTable, ArrayList<String> badNames)
	{
		return createOpAssign(localTable, badNames, null);
	}
	
	private ASTAssignment createOpAssign(SymbolTable localTable, ArrayList<String> badNames, 
			ASTVar lhs)
	{
		if (lhs == null)
			lhs = createVar(localTable, badNames, true);
		
		Node rhs1 = createOperand(localTable);
		Node rhs2 = createOperand(localTable);
		
		ValidOperations operator = ValidOperations.Sub;
		if (plusOrMinus())
			operator = ValidOperations.Add;
		
		ASTOp op = ASTOp.createOp(rhs1, rhs2, operator);
		
		return ASTAssignment.createAssignment(lhs, op);
	}
	
	private ASTAssignment createIndexedAssign(SymbolTable localTable, ArrayList<String> safeVars)
	{
		ArrayList<String> arrays = localTable.getCurrentVarNamesArray(VarRetrRest.ArrayOnly);
		
		testArrayEmpty(arrays);
		
		testArrayVars(arrays, localTable);
		
		ASTVar lhs = ASTVar.createVarWithIndex(getRandomItem(arrays), getRandomItem(safeVars));
		
		return createOpAssign(localTable, null, lhs);
	}
	
	private Node createOperand(SymbolTable localTable)
	{
		if (numOrVar()) //num
		{
			return ASTNum.createNum(randomDeclInt());
		}
		//var
		
		return createVar(localTable, null, true);
	}
	
	private void addParamsToFoo(int numToAdd)
	{
		ASTFunction foo = Global.getFunctions().get("foo");
		
		String[] params = new String[numToAdd];
		
		for(int i = 0; i < numToAdd; i++)
		{
			params[i] = paramNames[i];
		}
		
		foo.addParams(params);
	}
	
	/**
	 * random number between min and max inclusive
	 * @param min
	 * @param max
	 * @return
	 */
	private int randomNum(int min, int max)
	{
		return rand.nextInt(max+1-min) + min;
	}
	
	private int numOfGlobalVarDecls()
	{
		return randomNum(minVarDeclsInGlobal, maxVarDeclsInGlobal);
	}
	
	private int numOfGlobalArrayDecls()
	{
		return 1;
	}
	
	private int numOfMainVarDecls()
	{
		return randomNum(minVarDeclsInMain, maxVarDeclsInMain);
	}
	
	private int numOfArrayElems()
	{
		return 6;
	}
	
	private int numOfFooParams()
	{
		return randomNum(minFooParams, maxFooParams);
	}
	
	private int numOfFooVarDecls()
	{
		return randomNum(minFooVarDecls, maxFooVarDecls);
	}
	
	private int numOfFooAOStmts()
	{
		return randomNum(minFooAOStmts, maxFooAOStmts);
	}
	
	private int randomDeclInt()
	{
		return randomNum(minIntInDecl, maxIntInDecl);
	}
	
	private int randomArrayIndex()
	{
		return randomNum(minArrayIndex, maxArrayIndex);
	}
	
	/**
	 * This function gets a new name from a list of possible names, excluding 
	 * the banned names.
	 * @param bannedNames
	 * @return
	 */
	private String getNewVarName(ArrayList<String> bannedNames)
	{
		ArrayList<String> possNames = new ArrayList<String>();
		
		for(String name : this.possVars)
		{
			if( !bannedNames.contains(name))
				possNames.add(name);
		}
		
		return getRandomItem(possNames);
		
	}
	
	private <T> T getRandomItem(ArrayList<T> items)
	{
		return items.get(rand.nextInt(items.size()));
	}
	
	/**
	 * 
	 * @return true for num, false for var.
	 */
	private boolean numOrVar()
	{
		return binDecision(chanceOfNumToVar);
	}
	
	/**
	 * 
	 * @return true for assignment with operators, 
	 * false for basic assignment.
	 */
	private boolean assignOrOp()
	{
		return binDecision(chanceOfAssignToOp);
	}
	
	private boolean plusOrMinus()
	{
		return binDecision(chanceOfPlusToMinus);
	}
	
	private boolean binDecision(double probability)
	{
		Random r = new Random();
		double test = r.nextDouble();
		
		return test <= probability;
	}
	
	private ArrayList<String> getBadLHSNames(SymbolTable localTable, ArrayList<String> badNames)
	{
		ArrayList<String> ret = new ArrayList<String>(badNames);
		
		ArrayList<String> arrays = localTable.getCurrentVarNamesArray(VarRetrRest.ArrayOnly);
		
		testArrayVars(arrays, localTable);
		
		ret.addAll(arrays);
		
		return ret;
	}
	
	private ASTFunction findChildFuncOfProg(ASTProgram node, String name)
	{
		ASTFunction ret = null;
		
		ASTDeclarationList list = (ASTDeclarationList)node.jjtGetChild(0);
		
		int numChild = list.jjtGetNumChildren();
		
		for (int i = 0; i < numChild; i++)
		{
			ASTDeclaration decl = (ASTDeclaration)list.jjtGetChild(i);
			
			Node child = decl.jjtGetChild(0);
			if (child instanceof ASTFunction)
			{
				ASTFunction func = (ASTFunction)child;
				
				if (func.getName().equals(name))
				{
					ret = func;
					break;
				}
			}
			
		}
		
		return ret;
	}
	
	private void programNodeTest(ASTProgram node, SymbolTable symbolTable)
	{
		
	}
	
	private void testNonArrayVars(ArrayList<String> vars, SymbolTable symbols)
	{
		for(String v : vars)
		{
			if (symbols.getVariable(v).getIsArray())
				throw new AssumptionFailedException();
		}
	}
	
	private void testSafeIndexVars(ArrayList<String> vars, SymbolTable symbols)
	{
		for (String v : vars)
		{
			if (symbols.getVariable(v).getIsArray())
				throw new AssumptionFailedException();
		}
	}
	
	private void testArrayVars(ArrayList<String> vars, SymbolTable symbols)
	{
		for (String v: vars)
		{
			if (!symbols.getVariable(v).getIsArray())
				throw new AssumptionFailedException();
		}
	}
	
	private void testArrayEmpty(ArrayList<String> vars)
	{
		if (vars.size() == 0)
			throw new AssumptionFailedException();
	
	}
	
}
