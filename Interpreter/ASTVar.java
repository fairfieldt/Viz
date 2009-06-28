package Interpreter;

public class ASTVar extends SimpleNode implements VizParserTreeConstants{
  
  private String name;
  private boolean isArray = false;
  private int index;
  public ASTVar(int id) {
    super(id);
  }

  public ASTVar(VizParser p, int id) {
    super(p, id);
  }
  
  public void setName(String name)
  {
  	this.name = name;
  }
  
  public void setIsArray(boolean isArray)
  {
  	this.isArray = isArray;
  }
  
  public void setIndex(int index)
  {
  	this.index = index;
  }
  
  public int getIndex()
  {
  	return this.index;
  }
  public boolean getIsArray()
  {
  	return this.isArray;
  }
  public String getName()
  {
  	return this.name;
  }

  public String getCode()
  {
  	return this.name  + (isArray ? "[" + jjtGetChild(0).getCode() + "]" : "");
  }
  /** Accept the visitor. **/
  public Object jjtAccept(VizParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
  
  /*************EVERYTHING BELOW HERE IS USED BY RANDOMIZINGVISITOR************/

  
  	private void addIndex(Node indexNode)
  	{
  		this.addChild(ASTDeclaration.createDeclWithChild(indexNode), 0);
  	}
  	
	public static ASTVar createVar(String name)
	{
		ASTVar var = new ASTVar(JJTVAR);
		
		var.setName(name);
		
		return var;
	}
	
	public static ASTVar createVarWithIndex(String name, int index)
	{
		Node indexNode = ASTNum.createNum(index);
		
		return ASTVar.createVarWithIndex(name, indexNode);
	}
	
	public static ASTVar createVarWithIndex(String name, String index)
	{
		Node indexNode = ASTVar.createVar(index);
		
		return ASTVar.createVarWithIndex(name, indexNode);
	}
	
	private static ASTVar createVarWithIndex(String name, Node indexNode)
	{
		ASTVar var = new ASTVar(JJTVAR);
		
		var.setName(name);	
		
		var.addIndex(indexNode);
		
		return var;
	}
	
	
}

