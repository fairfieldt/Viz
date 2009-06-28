package Interpreter;

import java.util.*;

public class RandomizingVisitor2 implements VizParserTreeConstants,
		VizParserVisitor
{
	Random rand = new Random();
	
	final String[] possVars = {"g","m","n", "v", "w", "x", "y", "z"};
	final String[] paramNames = {"x", "y", "z" };	
	final int minVarDeclsInGlobal = 3;
	final int maxVarDeclsInGlobal = 5;
	
	final int minIntInDecl = 1;
	final int maxIntInDecl = 5;
	
	final int minVarDeclsInMain = 0;
	final int maxVarDeclsInMain = 1;
	
	final int minFooParams = 2;
	final int maxFooParams = 3;
	
	
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
		ArrayList<String> origVarNames = table.getCurrentVarNamesArray();
		ArrayList<String> varNames = new ArrayList<String>();
		
		if (bannedNames != null)
		{
			
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
			
			return ASTVar.createVarWithIndex(randomVar, getRandomItem(nonArrayVars));
		}

		return ASTVar.createVar(randomVar);

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
	
	private int randomDeclInt()
	{
		return randomNum(minIntInDecl, maxIntInDecl);
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
	
	
}
