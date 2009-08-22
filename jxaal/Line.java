package jxaal;

import org.jdom.Element;

public class Line extends NodePrim {

	public Line(XaalDoc doc, String id, XaalNS elemNS) {
		super(doc, id, elemNS);
		// TODO Auto-generated constructor stub
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
