package jxaal;

import java.util.ArrayList;

import org.jdom.Element;

public class ChangeStyle extends XaalElement {
	public Style newStyle;
	public ArrayList<XaalElement> modifiedShapes;
	public ChangeStyle(XaalDoc doc, String id, XaalNS elemNS) {
		super(doc, id, elemNS);
		modifiedShapes = new ArrayList<XaalElement>();
	}

	@Override
	public String xaalSerialize(Element parent) {
		if (newStyle != null && modifiedShapes.size() > 0)
		{
			Element e = XaalDoc.createElement("change-style");
			parent.addContent(e);
			
			for (XaalElement xe : modifiedShapes)
			{
				Element or = XaalDoc.createElement("object_ref");
				e.addContent(or);
				
				or.setAttribute("id", xe.id);
				
			}
			
			newStyle.xaalSerialize(e);
		}
		
		return null;
	}

}
