package jxaal;

import org.jdom.Element;

public class Text extends NodePrim {
	public String contents;
	
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
