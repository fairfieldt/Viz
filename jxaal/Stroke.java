package jxaal;

import org.jdom.Element;

import jxaal.enums.*;
/**
 * Represents a stroke element in XAAL
 * @author Eric
 *
 */
public class Stroke extends XaalElement implements XaalSerializable {
	
	//the type of stroke
	public StrokeType type;
	//the width of the stroke
	public Integer width;
	/**
	 * @param doc the document this stroke belongs to
	 */
	public Stroke(XaalDoc doc) {
		super(doc, null, null);
	}

	@Override
	public String xaalSerialize(Element parent) {
		if (type != null || width != null)
		{
			Element e = XaalDoc.createElement("stroke");
			parent.addContent(e);
			
			if (type != null)
				e.setAttribute("type", type.toString());
			if (width != null)
				e.setAttribute("width", width.toString());
			
			superclassSerialize(e);
		}
		
		
		return null;
	}
}
