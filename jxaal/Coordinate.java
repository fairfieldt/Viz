package jxaal;

import org.jdom.Element;

/**
 * represents a Coordinate element in XAAL
 * @author Eric
 *
 */
public class Coordinate extends XaalElement {
	// x position of the coordinate
	public int xPos;
	// y position of the coordinate
	public int yPos;
	
	/**
	 * 
	 * @param doc the document this coordinate belongs to
	 * @param xPos the x position of the coordinate
	 * @param yPos the y position of the coordinate
	 */
	public Coordinate(XaalDoc doc, int xPos, int yPos) {
		super(doc, null, null);
		this.xPos = xPos;
		this.yPos = yPos;
	}

	@Override
	public String xaalSerialize(Element parent) {
		Element e = new Element("coordinate");
		parent.addContent(e);
		
		e.setAttribute("x", xPos+"");
		e.setAttribute("y", yPos+ "");
		
		superclassSerialize(e);
		return null;
	}

}
