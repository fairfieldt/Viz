
package Interpreter;

public class ASTStatement extends SimpleNode implements VizParserTreeConstants
{

  private int lineNumber = -1;
  
  public ASTStatement(int id) {
    super(id);
  }

  public ASTStatement(VizParser p, int id) {
    super(p, id);
  }
  
  public int getLineNumber()
  {
  	return this.lineNumber;
  }
  public String getCode()
  {
  	//if lineNumber == -1 we haven't set yet, so set it and increment the global line counter.  Otherwise do nothing
  	if (this.lineNumber == -1)
  	{
  		this.lineNumber = Global.lineNumber++;
  	}
	return "\n" + this.lineNumber + ".\t" + jjtGetChild(0).getCode();
  }


  /** Accept the visitor. **/
  public Object jjtAccept(VizParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
  
  
  
  /*************EVERYTHING BELOW HERE IS USED BY RANDOMIZINGVISITOR************/
	
	public static ASTStatement createStmtWithChild(Node child)
	{
		ASTStatement stmt = new ASTStatement(JJTSTATEMENT);
		
		stmt.addChild(child, 0);
		
		return stmt;
	}
}
