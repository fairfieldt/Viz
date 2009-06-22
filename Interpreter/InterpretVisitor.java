package Interpreter;

public class InterpretVisitor implements VizParserVisitor, VizParserTreeConstants
{
	public Object visit(SimpleNode node, Object data)
	{
		int id = node.getId();
		Integer retVal = null;
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
				handleVarDecl((ASTVarDecl)node);
				break;
			case JJTARRAYDECLARATION:
				handleArrayDeclaration((ASTArrayDeclaration)node);
				break;
			case JJTSTATEMENTLIST:
				handleStatementList((ASTStatementList)node);
				break;
			case JJTEXPRESSION:
				retVal = handleExpression((ASTExpression)node);
				break;
				
			default:
				System.out.println("Unimplemented");
		}
		return retVal;
	}
	
	public void handleProgram(ASTProgram node)
	{
		System.out.println("visiting program");
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
		node.jjtGetChild(0).jjtAccept(this, null);	
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
			Global.getCurrentSymbolTable().setValue(name, value);
			System.out.println("Value of " + name + " is " + Global.getCurrentSymbolTable().get(name));
		}	
	}
	
	public void handleStatementList(ASTStatementList node)
	{
		int numStatements = node.jjtGetNumChildren();
		for (int i = 0; i < numStatements; i++)
		{
			node.jjtGetChild(i).jjtAccept(this, null);
		}
	}
	
	public Integer handleExpression(ASTExpression node)
	{
 		Integer value = (Integer) node.jjtGetChild(0).jjtAccept(this, null);
 		return value;
 	}
	
	public Object visit(ASTProgram node, Object data)
	{
		handleProgram(node);
		return null;
	}
	
	public Object visit(ASTDeclarationList node, Object data)
	{

		return null;
	}
	
	public Object visit(ASTDeclaration node, Object data)
	{
		return null;
	}
	
	public Object visit(ASTVarDecl node, Object data)
	{
		return null;
	}
	
	//FIXME
	public Object visit(ASTArrayDeclaration node, Object data)
	{
		return null;
	}
	
	public Object visit(ASTFunction node, Object data)
	{
		return null;
	}
	
	public Object visit(ASTStatementList node, Object data)
	{
		return null;
	}
  	public Object visit(ASTStatement node, Object data)
  	{
  		return null;
  	}
  	public Object visit(ASTCall node, Object data)
  	{
  		return null;
  	}
  	public Object visit(ASTVar node, Object data)
  	{
  		return null;
  	}
  	public Object visit(ASTAssignment node, Object data)
  	{
  		return null;
  	}
 	public Object visit(ASTExpression node, Object data)
 	{
		return null;
 	}
  	public Object visit(ASTArgs node, Object data)
  	{
  		return null;
  	}
  	public Object visit(ASTOp node, Object data)
  	{
  		return null;
  	}
  	public Object visit(ASTNum node, Object data)
  	{
  		return null;
  	}
}
