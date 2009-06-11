public class Variable implements Drawable
{
	private String name;
	private int value;
	private boolean isReference;
	
	private int xPos;
	private int yPos;

	public Variable(String name, int value, boolean isReference)
	{
		this.name = name;
		this.value = value;
		this.isReference = isReference;
	}

	public void draw(XAALScripter scripter)
	{
		
		if (this.isReference)
		{
			scripter.addCircle(xPos, yPos);
		}
		else
		{
			scripter.addRectangle(xPos, yPos);
		}
		
		scripter.addText(name + " = " + value, xPos, yPos);
	}
	
}


