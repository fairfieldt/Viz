/* Generated By:JJTree: Do not edit this line. ASTOp.java Version 4.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY= */
package Interpreter;

public class ASTOp extends SimpleNode {

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
}
/* JavaCC - OriginalChecksum=227a85fdbbeab6702f0ff14ec5e96371 (do not edit this line) */
