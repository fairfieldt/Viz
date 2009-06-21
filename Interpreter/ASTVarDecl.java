
package Interpreter;

public class ASTVarDecl extends SimpleNode 
{
	private String name;
	
	public ASTVarDecl(int id) 
	{
		super(id);
	}

	public Object jjtAccept(VizParserVisitor visitor, Object data) 
	{
    		return visitor.visit(this, data);
  	}
  	
  	public void setName(String name)
  	{
  		this.name = name;
  	}
  	
  	public String getName(String name)
  	{
  		return this.name;
  	}
}

