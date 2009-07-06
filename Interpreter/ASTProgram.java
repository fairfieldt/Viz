package Interpreter;

public class ASTProgram extends SimpleNode
{
	private String[] pseudocode;
	public boolean codeBuilt = false;
	public ASTProgram(int id)
	{
		super(id);
	}
	
	//Can only call this once
	public void buildCode()
	{
		if (!codeBuilt)
		{
			pseudocode = ((ASTDeclarationList)jjtGetChild(0)).getCode().split("\n");
			codeBuilt = true;
		}
	}
	
	public String[] getPseudocode()
	{
		return pseudocode;
	}
	
	public Object jjtAccept(VizParserVisitor visitor, Object data)
	{
		return visitor.visit(this, data);
	}
	
	
	/*************EVERYTHING BELOW HERE IS USED BY RANDOMIZINGVISITOR************/
	
	/**
	 * While the program's child isn't actually a vardecl or arraydecl, logically it is. 
	 * This abstracts adding the child to the ASTProgram node.
	 * 
	 * EXPECTS VARDECL OR ARRAYDECL, NOT DECLARATION!
	 */
	public boolean addLogicalChild(Node n, int i)
	{
		
		Node declarationListNode = jjtGetChild(0);
		
		ASTDeclaration decl = ASTDeclaration.createDeclWithChild(n);
		
		declarationListNode.addChildSafe(decl, i);
		
		return true;
	}
}
