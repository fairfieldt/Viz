package jxaal;

import org.jdom.Element;

/**
 * represents a polyline element
 * @author Eric
 *
 */
public class Polyline extends NodePrim {
	//is the polyline closed? (ie: is it possible to have a fillcolor
	public Boolean closed;
	/**
	 * 
	 * @param doc the document this coordinate belongs to
	 * @param xPos the x position of the coordinate
	 * @param yPos the y position of the coordinate
	 */
	public Polyline(XaalDoc doc, String id, XaalNS elemNS) {
		super(doc, id, elemNS);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String xaalSerialize(Element parent) {
		Element e = XaalDoc.createElement("polyline");
		parent.addContent(e);
		
		superclassSerialize(e);
		
		if (closed != null)
		{
			Element ce = XaalDoc.createElement("closed");
			e.addContent(ce);
			
			ce.setAttribute("value", closed.toString());
		}
		
		return null;
	}

}
