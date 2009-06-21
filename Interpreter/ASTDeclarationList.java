package Interpreter;

public class ASTDeclarationList extends SimpleNode
{
	public ASTDeclarationList(int id)
	{
		super(id);
	}
	
	public String getCode()
	{
		String code = "";
		for (int i = 0; i < jjtGetNumChildren(); i++)
		{
			System.out.println("Getting declaration " + i);
			ASTDeclaration d = (ASTDeclaration) jjtGetChild(i);
			code += d.getCode();
		}
		return code;
	}
}
