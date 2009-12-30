package jxaal;

import org.jdom.Element;

/**
 * Represents a color defined by its red, green and blue values
 * @author Eric
 *
 */
public class RGBColor extends GenericColor implements XaalSerializable {
	//red value
	public int r;
	//green value
	public int g;
	//blue value
	public int b;
	
	/**
	 * 
	 * @param r the red value from 0 to 255
	 * @param g the green value from 0 to 255
	 * @param b the blue value from 0 to 255
	 */
	public RGBColor(int r, int g, int b) {
		super(false);
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	/**
	 * 
	 * @param r the red value from 0 to 255
	 * @param g the green value from 0 to 255
	 * @param b the blue value from 0 to 255
	 * @param fillColor is this a fill color or a regular color
	 */
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
