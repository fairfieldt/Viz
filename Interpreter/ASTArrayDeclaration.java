
package Interpreter;

public class ASTArrayDeclaration extends SimpleNode implements VizParserTreeConstants{
  public ASTArrayDeclaration(int id) {
    super(id);
  }

  public ASTArrayDeclaration(VizParser p, int id) {
    super(p, id);
  }

  public String getCode()
  {
  	String code = "{";
  	int numMembers = jjtGetNumChildren();
  	
  	for (int i = 0; i < numMembers; i++)
  	{
  		code += jjtGetChild(i).getCode() + (i < numMembers -1 ? ", " : "");
  	}
  	code += "}";
  	return code;
  }

  /** Accept the visitor. **/
  public Object jjtAccept(VizParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }



/*************EVERYTHING BELOW HERE IS USED BY RANDOMIZINGVISITOR************/

  public static ASTArrayDeclaration createArrayDecl(int[] nums)
  {
	  ASTArrayDeclaration decl = new ASTArrayDeclaration(JJTARRAYDECLARATION);
	  
	  
	  	for (int i = 0; i < nums.length; i++)
		{
			ASTNum num = ASTNum.createNum(nums[i]);
			decl.addChild(num, i);
		}
	  	
	  return decl;
  }
}
