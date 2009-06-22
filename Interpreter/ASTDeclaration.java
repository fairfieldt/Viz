package Interpreter;

public class ASTDeclaration extends SimpleNode
{
	public ASTDeclaration(int id)
	{
		super(id);
	}
	
	public String getCode()
	{
		return jjtGetChild(0).getCode() + "\n";
	}

}
