
package Interpreter;

public class ASTVarDecl extends SimpleNode 
{
	private String name;
	private boolean isArray = false;
	
	public ASTVarDecl(int id) 
	{
		super(id);
	}

	public Object jjtAccept(VizParserVisitor visitor, Object data) 
	{
    		return visitor.visit(this, data);
  	}
  	
  	public void setIsArray(boolean isArray)
  	{
  		this.isArray = isArray;
  	}
  	
  	public boolean getIsArray()
  	{
  		return this.isArray;
  	}
  	
  	public void setName(String name)
  	{
  		this.name = name;
  	}
  	
  	
  	public String getName()
  	{
  		return this.name;
  	}
  	
  	public String getCode()
  	{
  		return "var " + this.name + (isArray ? "[]" : "") + 
  			" = " + jjtGetChild(0).getCode() + ";"; 
  	}
}

