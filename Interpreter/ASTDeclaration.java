package Interpreter;

public class ASTDeclaration extends SimpleNode implements VizParserTreeConstants
{
	private int lineNumber = -1;
	
	public ASTDeclaration(int id)
	{
		super(id);
	}
	
	public void setLineNumber(int lineNumber)
	{
		this.lineNumber = lineNumber;
		if (((SimpleNode)jjtGetChild(0)).getId() == VizParserTreeConstants.JJTFUNCTION)
		{
			((ASTFunction)jjtGetChild(0)).setLineNumber(lineNumber);
		}
	}
	
	public int getLineNumber()
	{
		return this.lineNumber;
	}
	
	public String getCode()
	{
		return jjtGetChild(0).getCode() + "\n";
	}
	
	/*************EVERYTHING BELOW HERE IS USED BY RANDOMIZINGVISITOR************/
	
	public static ASTDeclaration createDeclWithChild(Node child)
	{
		ASTDeclaration decl = new ASTDeclaration(JJTDECLARATION);
		
		decl.addChild(child, 0);
		
		return decl;
	}

}
