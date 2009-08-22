package jxaal;

import org.jdom.Element;

public class RGBColor extends GenericColor implements XaalSerializable {
	public int r;
	public int g;
	public int b;
	
	public RGBColor(int r, int g, int b) {
		super(false);
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public RGBColor(int r, int g, int b, boolean fillColor) {
		super(fillColor);
		this.r = r;
		this.g = g;
		this.b = b;
	}

	@Override
	public String xaalSerialize(Element parent) {
		Element e = XaalDoc.createElement(getElementName());
		parent.addContent(e);
		
		e.setAttribute("red", r + "");
		e.setAttribute("green", g + "");
		e.setAttribute("blue", b + "");
		
		return null;
	}
}
