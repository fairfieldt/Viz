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
			ASTDeclaration d = (ASTDeclaration) jjtGetChild(i);
			d.setLineNumber(Global.lineNumber);
			code += Global.lineNumber++ + ". " + d.getCode();
		}
		return code + "\n";
	}
}
