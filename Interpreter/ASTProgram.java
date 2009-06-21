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
}
