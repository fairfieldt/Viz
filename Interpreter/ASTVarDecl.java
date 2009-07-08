
package Interpreter;

import java.util.*;

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
if (Global.debug) {  		System.out.println("Getcode in vardecl");
}  		return "var " + this.name + (isArray ? "[]" : "") + 
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
  	
  	
  	public static ASTVarDecl createArrayDecl(String name, int[] values)
  	{
  		ASTVarDecl varDecl = new ASTVarDecl(JJTVARDECL);
  		
  		varDecl.setIsArray(true);
  		
  		varDecl.setName(name);
  		
  		ASTArrayDeclaration arrayDecl = ASTArrayDeclaration.createArrayDecl(values); 
 		
  		varDecl.addChild(arrayDecl, 0);
  		
  		return varDecl;
  	}
  	
  	public static ASTVarDecl createArrayDecl(String name, ArrayList<Integer> values)
  	{
  		int[] val = new int[values.size()];
  		
  		for (int i = 0; i < values.size(); i++)
  		{
  			val[i] = values.get(i).intValue();
  		}
  		
  		return createArrayDecl(name, val);
  	 
  	}
  	
}

