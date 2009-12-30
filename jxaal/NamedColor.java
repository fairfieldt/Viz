package jxaal;

import org.jdom.Element;

/**
 * Represents a named color in the XAAL spec
 * @author Eric
 *
 */
public class NamedColor extends GenericColor implements XaalSerializable {

	// the name of the color
	public String name;
	
	/**
	 * @param name the name of one of the named colors in the XAAL spec
	 */
	public NamedColor(String name) {
		super(false);
		this.name = name;
	}
	
	/**
	 * 
	 * @param name the name of one of the named colors in the XAAL spec
	 * @param fillColor is this a fill color?
	 */
	public NamedColor(String name, boolean fillColor) {
		super(fillColor);
		this.name = name;
	}

	@Override
	public String xaalSerialize(Element parent) {
		Element e = XaalDoc.createElement(getElementName());
		parent.addContent(e);
		
		e.setAttribute("name", name);

		return null;
	}

}
