package Interpreter;

public class ASTNum extends SimpleNode implements VizParserTreeConstants{

	private int value = -3;
  public ASTNum(int id) {
    super(id);
  }

  public ASTNum(VizParser p, int id) {
    super(p, id);
  }
  
  public void setValue(int value)
  {
  	this.value = value;
  }
  
  public int getValue()
  {
  	return this.value;
  }

  public String getCode()
  {
  	System.out.println("getCode in num");
  	return this.value + "";
  }

  /** Accept the visitor. **/
  public Object jjtAccept(VizParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

/*************EVERYTHING BELOW HERE IS USED BY RANDOMIZINGVISITOR************/

 	public static ASTNum createNum(int value)
 	{
 		ASTNum num = new ASTNum(JJTNUM);
 		
 		num.setValue(value);
 		
 		return num;
 	}
}
