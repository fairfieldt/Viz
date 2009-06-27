package Interpreter;

public class ASTProgram extends SimpleNode
{
	private String[] pseudocode;
	private boolean codeBuilt = false;
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
}
