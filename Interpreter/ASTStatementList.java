
package Interpreter;

public class ASTStatementList extends SimpleNode {
  public ASTStatementList(int id) {
    super(id);
  }

  public ASTStatementList(VizParser p, int id) {
    super(p, id);
  }

  public String getCode()
  {
  	String code = "";
  	for (int i = 0; i < jjtGetNumChildren(); i++)
  	{
  		code += jjtGetChild(i).getCode();
  	}
  	return code;
  }

  /** Accept the visitor. **/
  public Object jjtAccept(VizParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
