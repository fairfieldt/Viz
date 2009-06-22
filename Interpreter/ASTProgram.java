package Interpreter;

public class ASTProgram extends SimpleNode
{
	public ASTProgram(int id)
	{
		super(id);
	}
	
	public String getCode()
	{
		return ((ASTDeclarationList)jjtGetChild(0)).getCode();
	}
	
	public Object jjtAccept(VizParserVisitor visitor, Object data)
	{
		return visitor.visit(this, data);
	}
}
