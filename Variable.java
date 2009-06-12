public class Variable implements Drawable
{
	private String name;
	private int value;
	private String color = "black";
	private boolean isReference = false;
	private String id = "";
	
	private boolean hidden = false;
	
	private int xPos;
	private int yPos;

	private int length = 0;
	
	private Variable ref =  null;
	
	public Variable(String name, int value, boolean isReference)
	{
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
	
	public int getLength()
	{
		return this.length;
	}
	
	public String getId()
	{
		return this.id;
	}

	public void draw(XAALScripter scripter)
	{
		
		if (this.isReference)
		{
			id = scripter.addTriangle(xPos, yPos, 40, color, hidden ? true : false);
			if (ref != null)
			{
				scripter.addArrow(id, ref.getId(), 200, false, hidden ? true : false);
				scripter.addText(xPos+15, yPos+25, name, "black",  hidden ? true : false);
			}
		}
		else
		{
			id = scripter.addRectangle(xPos, yPos, length, 40, color,  hidden ? true : false);
			scripter.addText(xPos+15, yPos+25, name + " = " + value, "black",  hidden ? true : false);
		}
		
	}
	
}


