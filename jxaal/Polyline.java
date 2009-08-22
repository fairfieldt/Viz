package jxaal;

import org.jdom.Element;

public class Polyline extends NodePrim {
	public Boolean closed;
	
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
