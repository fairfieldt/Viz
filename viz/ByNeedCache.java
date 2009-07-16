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
	private int sizeX = 200;
	private int sizeY = 450;
	
	
	private String color = "black";
	private boolean hidden = false;
	
	public ByNeedCache(Scope parent)
	{
		this.parent = parent;
	}
	
	public Scope getParent()
	{
		return parent;
	}
	
	public void addVariable(Variable v)
	{

		if (hidden)
		{
			v.setHidden(true);
		}
		v.setColor(color);
		
		this.vars.add(v);
	}
	
	public void setPosition(int xPos, int yPos)
	{
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public ArrayList<String> getIds()
	{
		return this.ids;
	}
	
	public ArrayList<Variable> getVars()
	{
		return vars;
	}
	
	public String getRectId()
	{
		return rectId;
	}
	
	private void sizeVariables()
	{
		int currentVarXPos = 0;
		int currentVarYPos = 0;
		
		for (int i = 0; i < vars.size(); i++)
		{
			Variable v = vars.get(i);
			
			if( i % 2 == 1)
			{
				currentVarXPos = 25 + 40 + 10;
			}
			else
			{
				currentVarXPos = 25;
			}
			
			if (i < 2)
			{
				currentVarYPos = 25 + 40 + 10;
			}
			else
			{
				currentVarYPos = 25;
			}
			
			v.setPosition(currentVarXPos, currentVarYPos);
		}
	}

	@Override
	public void draw(XAALScripter scripter) // set position in the draw method of the parent scope
	{
		String id1 = scripter.addRectangle(xPos, yPos, sizeX, sizeY, color, hidden, 2);
		rectId = id1;
		
		ids.add(id1);
		
		sizeVariables();
		
		for (Variable v : vars)
		{
			v.draw(scripter);
		}
	}
}