package jxaal;

import org.jdom.Element;
/**
 * Represents a Text element in XAAL.
 * @author Eric
 *
 */
public class Text extends NodePrim {
	//the contents of the text element
	public String contents;
	
	/**
	 * @param doc the document this element belongs to
	 * @param id the id of the element, if any
	 * @param elemNS the namespace of the element, if any
	 */
	public Text(XaalDoc doc, String id, XaalNS elemNS) {
		super(doc, id, elemNS);
		
	}

	@Override
	public String xaalSerialize(Element parent) {
		if (contents != null)
		{
			Element e = XaalDoc.createElement("text");
			parent.addContent(e);
			
			Element c = XaalDoc.createElement("contents");
			e.addContent(c);
			
			c.addContent(contents);
			
			superclassSerialize(e);
			
		}
		
		return null;
		
		
	}

}
