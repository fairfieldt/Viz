import java.util.*;

public class Scope implements Drawable
{
	private String name;
	private ArrayList<Variable> vars;

	private int xPos;
	private int yPos;
	private int sizeX;
	private int sizeY;

	private String color = "black";
	private boolean hidden = false;

	public Scope(String name)
	{
		vars = new ArrayList<Variable>();
		this.name = name;
	}

	public void addVariable(Variable v)
	{
		this.vars.add(v);
	}

	public void draw(XAALScripter scripter)
	{
		/*
		scripter.addRectangle(xPos, yPos, sizeX, sizeY, color, hidden);

		for (Variable v : vars)
		{
			v.draw();
		}*/
	}
	
}
