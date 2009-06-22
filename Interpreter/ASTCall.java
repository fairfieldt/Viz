
package Interpreter;

public class ASTCall extends SimpleNode implements VizParserTreeConstants{

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
  
  public String getName()
  {
  	return this.name;
  }
  
  public void addArg(ASTVar arg)
  {
	if (jjtGetNumChildren() == 0)
	{
		ASTArgs args = new ASTArgs(JJTARGS);
		args.jjtSetParent(this);
		this.jjtAddChild(args, 0);
	}
  	ASTArgs args = (ASTArgs) jjtGetChild(0);
  	arg.jjtSetParent(args);
  	args.jjtAddChild(arg, args.jjtGetNumChildren());
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

