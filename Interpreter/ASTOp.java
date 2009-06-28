
package Interpreter;

public class ASTOp extends SimpleNode implements VizParserTreeConstants{

	private String op;
  public ASTOp(int id) {
    super(id);
  }

  public ASTOp(VizParser p, int id) {
    super(p, id);
  }

  public void setOp(String op)
  {
  	this.op = op;
  }
  
  public String getOp()
  {
  	return this.op;
  }
  
  public String getCode()
  {
  	return jjtGetChild(0).getCode() + " " + op + " " + jjtGetChild(1).getCode();
  }


  /** Accept the visitor. **/
  public Object jjtAccept(VizParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
  
  /*************EVERYTHING BELOW HERE IS USED BY RANDOMIZINGVISITOR************/
  
  private void addLHS(Node lhs)
  {
	  this.addChild(lhs, 0);
  }
  
  private void addRHS(Node rhs)
  {
	  this.addChild(ASTExpression.createExpWithChild(rhs), 1);
  }

  /**
   * lhs can be Num or Var, rhs can be Num or Var
   */
  public static ASTOp createOp(Node lhs, Node rhs, ValidOperations validOp)
  {
	  ASTOp op = new ASTOp(JJTOP);
	  
	  op.addLHS(lhs);
	  
	  op.setOp(validOp.toString());
	  
	  op.addRHS(rhs);
	  
	  return op;
  }
  
}

