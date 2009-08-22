package jxaal;
import org.jdom.Element;

import jxaal.enums.*;

public class Style extends XaalElement implements XaalSerializable {

	private Font font;
	private Stroke stroke;
	public GenericColor color;
	private GenericColor fillColor;
	
	public Style(XaalDoc doc, String id) {
		super(doc, id, null);
		this.color = new NamedColor("black");
	}

	public Integer getFontSize()
	{
		if (font != null)
			return font.size;
		return null;
	}
	
	public void setFontSize(Integer size)
	{
		if (font == null)
			font = new Font(doc);
		font.size = size; 
	}
	
	public String getFontFamily()
	{
		if (font != null)
			return font.family;
		return null;
	}
	
	public void setFontFamily(String family)
	{
		if (font == null)
			font = new Font(doc);
		font.family = family;
	}
	
	public StrokeType getStrokeType()
	{
		if (stroke != null)
			return stroke.type;
		return null;
	}
	
	public void setStrokeType(StrokeType type)
	{
		if (stroke == null)
			stroke = new Stroke(doc);
		stroke.type = type;
	}
	
	public Integer getStrokeWidth()
	{
		if (stroke != null)
			return stroke.width;
		return null;
	}
	
	public void setStrokeWidth(Integer width)
	{
		if (stroke == null)
			stroke = new Stroke(doc);
		stroke.width = width;
	}
	
	public GenericColor getFillColor()
	{
		return fillColor;
	}
	
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
