package viz;

import java.util.ArrayList;

public class ByNeedCache implements Drawable
{
	
	private ArrayList<Variable> vars;
	private Scope parent;
	
	private ArrayList<String> ids;
	private String rectId;
	
	private int xPos = 0;
	private int yPos = 50;
	private int sizeX = 600;
	private int sizeY = 450;
	
	public ByNeedCache(Scope parent)
	{
		this.parent = parent;
	}
	
	public Scope getParent()
	{
		return parent;
	}
	
	public void addText(String str)
	{
		
	}
	
	public void setPosition(int xPos, int yPos)
	{
		this.xPos = xPos;
		this.yPos = yPos;
	}

	@Override
	public void draw(XAALScripter scripter) // set position in the draw method of the parent scope
	{
	
		
	}
}