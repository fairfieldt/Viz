package jxaal;

public abstract class GenericColor implements XaalSerializable 
{
	public boolean fillColor = false;
	
	public GenericColor(boolean fillColor)
	{
		this.fillColor = fillColor;
	}
	
	protected String getElementName()
	{
		return fillColor ? "fillColor" : "color";
	}
}
