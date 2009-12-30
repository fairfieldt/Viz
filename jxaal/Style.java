package jxaal;
import org.jdom.Element;

import jxaal.enums.*;

/**
 * Represents a style element in XAAL
 * @author Eric
 *
 */
public class Style extends XaalElement implements XaalSerializable {
	// the font part of the style
	private Font font;
	//the stroke part of the style
	private Stroke stroke;
	//the color part of the style
	public GenericColor color;
	//the fillcolor part of the style
	private GenericColor fillColor;
	
	/**
	 * 
	 * @param doc the xaal document the style belongs to
	 * @param id the id of the style. May be null for default
	 */
	public Style(XaalDoc doc, String id) {
		super(doc, id, null);
		this.color = new NamedColor("black");
	}

	/**
	 * Get the font size for the style, if any, in pixels
	 * @return  an Integer representing the font size. If not set, returns null.
	 */
	public Integer getFontSize()
	{
		if (font != null)
			return font.size;
		return null;
	}
	
	/**
	 * Set the font size of the style in pixels
	 * @param size the size of the font represented as Integer
	 */
	public void setFontSize(Integer size)
	{
		if (font == null)
			font = new Font(doc);
		font.size = size; 
	}
	
	/**
	 * Returns the name of the font in the style, if any
	 * @return a String containing the name of the font. If not set, returns null.
	 */
	public String getFontFamily()
	{
		if (font != null)
			return font.family;
		return null;
	}

	/**
	 * Set the font family of this style.
	 * @param family a String containing the font name 
	 */
	public void setFontFamily(String family)
	{
		if (font == null)
			font = new Font(doc);
		font.family = family;
	}
	
	/**
	 * Gets the stroke type of this style, if any.
	 * @return a StrokeType representing the stroke type. If not set, returns null.
	 */
	public StrokeType getStrokeType()
	{
		if (stroke != null)
			return stroke.type;
		return null;
	}
	
	/**
	 * Set the stroke type for this style
	 * @param type the new StrokeType for the style.
	 */
	public void setStrokeType(StrokeType type)
	{
		if (stroke == null)
			stroke = new Stroke(doc);
		stroke.type = type;
	}
	
	/**
	 * Gets the stroke width, in pixels, of this style, if any
	 * @return an Integer representing the stroke width. If not set, returns null.
	 */
	public Integer getStrokeWidth()
	{
		if (stroke != null)
			return stroke.width;
		return null;
	}
	
	/**
	 * Sets the stroke width, in pixels, of the style
	 * @param width an Integer fort the new stroke width.
	 */
	public void setStrokeWidth(Integer width)
	{
		if (stroke == null)
			stroke = new Stroke(doc);
		stroke.width = width;
	}
	
	/**
	 * Gets the fill color for this style
	 * @return a GenericColor representing the fill color of this style.
	 */
	public GenericColor getFillColor()
	{
		return fillColor;
	}
	
	/**
	 * Set the fill color for the style
	 * @param c a GenericColor representing the new fill color
	 */
	public void setFillColor(GenericColor c)
	{
		c.fillColor = true;
		fillColor = c;
	}

	@Override
	public String xaalSerialize(Element parent) {
		
		if (color != null || fillColor != null || font != null || stroke != null)
		{
			Element e = XaalDoc.createElement("style");
			parent.addContent(e);
			
			if (color != null)
				color.xaalSerialize(e);
			if (fillColor != null)
				fillColor.xaalSerialize(e);
			if (font != null)
				font.xaalSerialize(e);
			if (stroke != null)
				stroke.xaalSerialize(e);
			
			superclassSerialize(e);
		}
		return null;
	}
	
}
