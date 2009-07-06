
package Interpreter;

public class ASTStatementList extends SimpleNode {
  
  private boolean isFunction = true;
  private SymbolTable symbolTable; 
  
  public ASTStatementList(int id) {
    super(id);
  }

  public ASTStatementList(VizParser p, int id) {
    super(p, id);
  }
  
  public void setIsFunction(boolean isFunction)
  {
  	this.isFunction = isFunction;
  }
  
  public boolean getIsFunction()
  {
  	return this.isFunction;
  }
  
  public void setSymbolTable(SymbolTable s)
  {
  	symbolTable = s;
  }
  
  public SymbolTable getSymbolTable()
  {
  	return symbolTable;
  }

  public String getCode()
  {
  	System.out.println("ASS" + this.jjtGetParent());
  	boolean isNested = this.jjtGetParent() instanceof ASTStatement;
  	System.out.println("isNested " + isNested);
  	String code = "";
  	System.out.println("ASA" + this.jjtGetParent());
  	if (isNested)
  	{
  		code += "{";
  	}
  	for (int i = 0; i < jjtGetNumChildren(); i++)
  	{
  		if (isNested)
  		{
  			code += ((ASTStatement)jjtGetChild(i)).getCode(true);
  		}
  		else
  		{
  			code += jjtGetChild(i).getCode();
  		}
  	}
  	if (isNested)
  	{
  		code += "\n" + Global.lineNumber++ + ".\t}";
  	}
  	return code;
  }

  /** Accept the visitor. **/
  public Object jjtAccept(VizParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
