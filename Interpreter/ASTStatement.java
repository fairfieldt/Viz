
package Interpreter;

public class ASTStatement extends SimpleNode {
  public ASTStatement(int id) {
    super(id);
  }

  public ASTStatement(VizParser p, int id) {
    super(p, id);
  }
  
  public String getCode()
  {
	return "\n\t" + jjtGetChild(0).getCode();
  }


  /** Accept the visitor. **/
  public Object jjtAccept(VizParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
