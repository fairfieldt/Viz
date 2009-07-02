package viz;
import java.util.*;

public class Scope implements Drawable
{
	private String name;
	private ArrayList<Variable> vars;
	private ArrayList<Variable> params;
	private ArrayList<Scope> scopes;
	
	private ArrayList<String> ids;

	private int xPos = 0;
	private int yPos = 50;
	private int sizeX = 600;
	private int sizeY = 450;
	
	private int currentParamXPos = 0;
	private int currentParamYPos = 50;
	
	private int currentVarXPos = 0;
	private int currentVarYPos = 50;
	
	private String color = "black";
	private boolean hidden = false;
	private boolean isGlobal = false;

	public Scope(String name, String color, boolean isGlobal)
	{
		vars = new ArrayList<Variable>();
		params = new ArrayList<Variable>();
		scopes = new ArrayList<Scope>();
		ids = new ArrayList<String>();
		this.name = name;
		this.color = color;
		this.isGlobal = isGlobal;
	}

	public void addVariable(Variable v)
	{
		//If the scope is hidden we should hide all variables too

		if (hidden)
		{
			v.setHidden(true);
		}
		v.setColor(color);
		
		if (v.getIsParam())
		{
			params.add(v);
		}
		else 
		{
			this.vars.add(v);
		}
		
	}
	
	public void addScope(Scope s)
	{
		//Again, if the scope is hidden so are all sub-scopes
		if (hidden)
		{
			s.setHidden(true);
		}
		scopes.add(s);
	}
	
	public void setPosition(int xPos, int yPos)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		this.currentVarXPos = xPos;
		this.currentVarYPos = yPos;
		this.currentParamXPos = xPos;
		this.currentParamYPos = yPos;
		
	}
	
	public void setHidden(boolean isHidden)
	{
		hidden = isHidden;
		
		//added this so if you set hidden after you added vars, the vars are still hidden
		//TODO: should this be just for setting hidden true or for both true and false?
		if (hidden)
			for (Variable v : vars)
			{
				v.setHidden(true);
			}
			
	}
	
	public void setSize(int sizeX, int sizeY)
	{
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}
	
	public ArrayList<String> getIds()
	{
		return this.ids;
	}
	
	private void sizeScopes()
	{
		int subScopeXPos = xPos + 10;
		int subScopeYPos = yPos + 100;
		int subScopeYSize = (sizeY-10) / (scopes.size()+1);
		System.out.println("SubSize:: " + subScopeYSize);
		System.out.println("Scopes.size: " + scopes.size());
		for (int i = scopes.size(); i > 0; i--)
		{
			Scope s = scopes.get(i-1);
			System.out.println("SubSize: " + subScopeYSize);
			s.setPosition(subScopeXPos, subScopeYPos);
			s.setSize(sizeX - 20, subScopeYSize);
			subScopeYPos += subScopeYSize + 40;
		}		 		
			
	}
	
	private void sizeVariables()
	{
		for (Variable v : vars)
		{
			if (isGlobal)
			{
				v.setPosition(currentVarXPos + 25, currentVarYPos +35);
				
			}
			else 
			{
				v.setPosition(currentVarXPos + 25, currentVarYPos +90);
			}
			currentVarXPos += v.getLength() + 10;
			System.out.println("Size var " + v.getName() + " to x: " +  xPos + " y: " + yPos);
		}
		
		for (Variable v: params)
		{
			v.setPosition(currentParamXPos + 25, currentParamYPos + 25);
			currentParamXPos+=v.getLength() + 10;
			
			System.out.println("Size param " + v.getName() + " to x: " +  xPos + " y: " + yPos);
		}
	}
	
	public ArrayList<Variable> getParams()
	{
		ArrayList<Variable> params = new ArrayList<Variable>();
		
		for (Variable v : this.params)
		{
			params.add(v);
		}
		
		return params;
	}
	
	public ArrayList<Variable> getLocalVariables()
	{
		return vars;
	}
	

	public void draw(XAALScripter scripter)
	{
		System.out.println("Drawing scope: " + name);
		System.out.println("XPos: " + xPos + " YPos: " + yPos);
		int captionLength = name.length() * 13;
		String id1 = scripter.addRectangle(xPos, yPos, sizeX, sizeY, color, hidden, 6);
		String id3 = scripter.addText(xPos+3, yPos-5, name, "black", hidden);
		
		ids.add(id1);
		ids.add(id3);
		
		
		
		sizeVariables();
		for (Variable v : params)
		{
			v.draw(scripter);
		}
		for (Variable v : vars)
		{
			v.draw(scripter);
		}
		
		sizeScopes();
		
		if (name.equals("Global"))
		{
			//draw main
			scopes.get(0).draw(scripter);
			
			//draw foo -- MUST BE THE LAST ONE
			scopes.get(1).draw(scripter);
		}
		else
		{
			for (Scope s : scopes)
			{
				System.out.println("Drawing subScope");
				s.draw(scripter);
			}
		}
	}
}
