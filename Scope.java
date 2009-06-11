import java.util.*;

public class Scope implements Drawable
{
	private String name;
	private ArrayList<Variable> vars;
	private ArrayList<Scope> scopes;

	private int xPos = 50;
	private int yPos = 50;
	private int sizeX = 400;
	private int sizeY = 400;
	
	private int currentVarPos = 0;

	private String color = "black";
	private boolean hidden = false;

	public Scope(String name, String color)
	{
		vars = new ArrayList<Variable>();
		scopes = new ArrayList<Scope>();
		this.name = name;
		this.color = color;
	}

	public void addVariable(String name, int value)
	{
		Variable v = new Variable(name, value, color, false);
		v.setPosition(xPos + currentVarPos + 25, yPos+25);
		this.vars.add(v);
		currentVarPos+=v.getLength() + 10;
	}
	
	public void addScope(Scope s)
	{
		scopes.add(s);
	}
	
	public void setPosition(int xPos, int yPos)
	{
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public void setSize(int sizeX, int sizeY)
	{
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}
	
	private void sizeScopes()
	{
		int subScopeXPos = xPos + 10;
		int subScopeYPos = yPos + 100;
		
		for (Scope s : scopes)
		{
			s.setPosition(subScopeXPos, subScopeYPos);
			s.setSize(sizeX - 20, sizeY-150 / (scopes.size()+1));
		}		 		
			
	}

	public void draw(XAALScripter scripter)
	{
		System.out.println("Drawing scope: " + name);
		System.out.println("XPos: " + xPos + " YPos: " + yPos);
		int captionLength = name.length() * 10;
		scripter.addRectangle(xPos, yPos, sizeX, sizeY, color, hidden);
		scripter.addRectangle(xPos, yPos-30, captionLength, 30, color, hidden);
		scripter.addText(xPos+3, yPos-10, name);

		for (Variable v : vars)
		{
			v.draw(scripter);
		}
		sizeScopes();
		for (Scope s : scopes)
		{
			System.out.println("Drawing subScope");
			s.draw(scripter);
		}
	}
}
