package jxaal;

import org.jdom.Element;

public class Font extends XaalElement implements XaalSerializable {

	public Integer size;
	public String family;
	
	public Font(XaalDoc doc) {
		super(doc, null, null);
	}

	@Override
	public String xaalSerialize(Element parent) {
		// TODO Auto-generated method stub
		if (size != null || family != null)
		{
			Element font = new Element("font");
			parent.addContent(font);
			if (size != null)
				font.setAttribute("size", size + "");
			if (family != null)
				font.setAttribute("family", family);
			
			superclassSerialize(font);
		}
		
		return null;
	}
}
