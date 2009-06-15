package Main;
import java.util.*;

public class Variable implements Drawable
{
	private String name;
	private int value;
	private String color = "black";
	private boolean isReference = false;
	private ArrayList<String> ids;
	
	private boolean hidden = false;
	
	private int xPos;
	private int yPos;

	private int length = 0;
	
	private int copies = 1;
	
	private Variable ref =  null;
	
	public Variable(String name, int value, boolean isReference)
	{
		ids = new ArrayList<String>();
		this.name = name;
		this.value = value;
		this.isReference = isReference;
		this.length = (name.length() * 10) + 80;
	}
	
	public void setReference(Variable ref)
	{
		this.ref = ref;
	}
	
	public void setPosition(int xPos, int yPos)
	{
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public void setHidden(boolean isHidden)
	{
		hidden = isHidden;
	}
	public void setColor(String color)
	{
		this.color = color;
	}
	
	public void addCopy()
	{
		copies++;
	}
	public int getLength()
	{
		return this.length;
	}
	
	public int getXPos()
	{
		return this.xPos;
	}
	
	public int getYPos()
	{
		return this.yPos;
	}
	
	public ArrayList<String> getIds()
	{
		System.out.println("I have " + ids.size() + " ids");
		return this.ids;
	}

	public void draw(XAALScripter scripter)
	{
		
		if (this.isReference)
		{
			String id1 = scripter.addTriangle(xPos, yPos, 40, color, hidden);
			ids.add(id1);
			if (ref != null)
			{
				String id2 = scripter.addArrow(id1, ref.getIds().get(0), 200, false, hidden);
				ids.add(id2);
				String id3 = scripter.addText(xPos+15, yPos+25, name, "black",  hidden);
				ids.add(id3);

			}

		}
		else
		{
			String id1 = scripter.addRectangle(xPos, yPos, length, 40, color,  hidden);
			String id2 = scripter.addText(xPos+15, yPos+25, name + " = " + value, "black",  hidden);
			
			ids.add(id1);
			ids.add(id2);
			for (int i = 0; i < copies; i++)
			{
				ids.add(value + "");
			}
		}
		
	}
	
}


