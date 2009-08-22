package jxaal;

import org.jdom.Element;

import jxaal.enums.*;

public class Stroke extends XaalElement implements XaalSerializable {

	public StrokeType type;
	public Integer width;
	
	public Stroke(XaalDoc doc) {
		super(doc, null, null);
		// TODO Auto-generated constructor stub
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
