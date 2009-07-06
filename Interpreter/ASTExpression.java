
package Interpreter;

public class ASTExpression extends SimpleNode implements VizParserTreeConstants 
{
  public ASTExpression(int id) {
    super(id);
  }

  public ASTExpression(VizParser p, int id) {
    super(p, id);
  }
  
  public String getCode()
  {
  	return jjtGetChild(0).getCode();
  }


  /** Accept the visitor. **/
  public Object jjtAccept(VizParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }


/*************EVERYTHING BELOW HERE IS USED BY RANDOMIZINGVISITOR************/

	public static ASTExpression createExpWithChild(Node n)
	{
		ASTExpression exp = new ASTExpression(JJTEXPRESSION);
		
		exp.addChild(n, 0);
		
		return exp;
	}
}
