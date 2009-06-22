
package Interpreter;

public class ASTCall extends SimpleNode {

	private String name;
  public ASTCall(int id) {
    super(id);
  }

  public ASTCall(VizParser p, int id) {
    super(p, id);
  }
  
  public void setName(String name)
  {
  	this.name = name;
  }
  
  public String getName(String name)
  {
  	return this.name;
  }
  
  public void addArg(ASTVar arg)
  {
  	ASTArgs args = (ASTArgs) jjtGetChild(0);
  	arg.jjtSetParent(args);
  	
  }

  public String getCode()
  {
  	return name + "(" + jjtGetChild(0).getCode() + ");";
  }

  /** Accept the visitor. **/
  public Object jjtAccept(VizParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}

