public class Variable implements Drawable
{
	private String name;
	private int value;
	private String color = "black";
	private boolean isReference = false;
	
	private int xPos;
	private int yPos;

	private int length = 0;
	
	public Variable(String name, int value, String color, boolean isReference)
	{
		this.name = name;
		this.value = value;
		this.color = color;
		this.isReference = isReference;
		this.length = (name.length() * 10) + 80;
	}
	
	public void setPosition(int xPos, int yPos)
	{
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public int getLength()
	{
		return this.length;
	}

	public void draw(XAALScripter scripter)
	{
		
		if (this.isReference)
		{
		
		}
		else
		{
			scripter.addRectangle(xPos, yPos, length, 40, color);
			
		}
		
		scripter.addText(xPos+15, yPos+25, name + " = " + value);
	}
	
}


