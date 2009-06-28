
package Interpreter;

public class ASTVarDecl extends SimpleNode implements VizParserTreeConstants
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
  	
  	 /*************EVERYTHING BELOW HERE IS USED BY RANDOMIZINGVISITOR************/
  	/**
  	 * 
  	 * @param name
  	 * @param value
  	 * @return
  	 */
  	public static ASTVarDecl createVarDecl(String name, int value)
  	{
  		ASTVarDecl varDecl = new ASTVarDecl(JJTVARDECL);
  		
  		varDecl.setName(name);
  		
  		ASTNum num = ASTNum.createNum(value);
  		
  		varDecl.addChild(ASTExpression.createExpWithChild(num), 0);
  		
  		return varDecl;
  	}
}

