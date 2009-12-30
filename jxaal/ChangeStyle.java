package jxaal;

import java.util.ArrayList;

import org.jdom.Element;
/**
 * represents a ChangeStyle element
 * @author Eric
 *
 */
public class ChangeStyle extends XaalElement {
	//the new style
	public Style newStyle;
	//which shapes should have the new style
	public ArrayList<XaalElement> modifiedShapes;
	
	/**
	 * @param doc the document that this element belongs to
	 * @param id the id of this element. May be null.
	 * @param elemNS the ns of this element. May be null.
	 */
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
