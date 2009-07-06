package Interpreter;

import java.util.*;

public class ASTCall extends SimpleNode implements VizParserTreeConstants{

	private String name;
	private int lineNumber;
	private ArrayList<ASTVar> args = new ArrayList<ASTVar>();
	
	private boolean inUse = true;
  public ASTCall(int id) {
  
    super(id);
  }

  public ASTCall(VizParser p, int id) {
    super(p, id);
  }
  
  public void addArgs(ArrayList<ASTVar> args)
  {
  	System.out.println("Addargs called");
  	this.args = args;
  }
  
  public void setUsed(boolean used)
  {
  	this.inUse = used;
  }
  
  public boolean getUsed()
  {
  	return this.inUse;
  }
  
  public ArrayList<ASTVar> getArgs()
  {
  	return this.args;
  }
  public void setName(String name)
  {
  	this.name = name;
  }
  
  public String getName()
  {
  	return this.name;
  }
  
  public void setLineNumber(int lineNumber)
  {
  	this.lineNumber = lineNumber;
  }
  
  public int getLineNumber()
  {
  	return this.lineNumber;
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
  	if (!inUse)
  	{
  		return "";
  	}
  	return name + "(" + jjtGetChild(0).getCode() + ");";
  }

  /** Accept the visitor. **/
  public Object jjtAccept(VizParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
  
  
  /*********** RANDOMIZING VISITOR *****/
  
  public static ASTCall createCall(String name)
  {
	  ASTCall call = new ASTCall(JJTCALL);
	  
	  call.setName(name);
	  
	  return call;
  }
}

