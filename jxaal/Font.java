package jxaal;

import org.jdom.Element;

/**
 * Represents a font element in XAAL
 * @author Eric
 *
 */
public class Font extends XaalElement implements XaalSerializable {

	//the size of the font in pixels
	public Integer size;
	//the name of the font family
	public String family;
	
	/**
	 * @param doc the document the font element belongs to
	 */
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
