package jxaal;

import org.jdom.Element;

/**
 * represents a line element
 * @author Eric
 *
 */
public class Line extends NodePrim {

	/**
	 * 
	 * @param doc the document this coordinate belongs to
	 * @param xPos the x position of the coordinate
	 * @param yPos the y position of the coordinate
	 */
	public Line(XaalDoc doc, String id, XaalNS elemNS) {
		super(doc, id, elemNS);
	}

	@Override
	public String xaalSerialize(Element parent) {

		if (coordinates.size() == 2)
		{
			Element e = XaalDoc.createElement("line");
			parent.addContent(e);
			
			superclassSerialize(e);
		}
		
		return null;
	}

}
