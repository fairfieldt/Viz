package jxaal;

/**
 * 
 * @author Eric Schultz
 * Superclass of the colors in JXAAL
 *
 */
public abstract class GenericColor implements XaalSerializable 
{
	//is this color a fill color?
	public boolean fillColor = false;
	
	/**
	 * @param fillColor is the color a fill color for writing out the element
	 */
	public GenericColor(boolean fillColor)
	{
		this.fillColor = fillColor;
	}
	
	/**
	 * Get the proper element name depending on whether this is a fill color or a regular one.
	 * @return the proper element name, either fillColor or color
	 */
	protected String getElementName()
	{
		return fillColor ? "fillColor" : "color";
	}
}
