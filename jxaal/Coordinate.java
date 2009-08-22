package jxaal;

import org.jdom.Element;

public class Coordinate extends XaalElement {

	public int xPos;
	public int yPos;
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
