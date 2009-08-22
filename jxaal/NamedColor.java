package jxaal;

import org.jdom.Element;

public class NamedColor extends GenericColor implements XaalSerializable {

	public String name;
	
	public NamedColor(String name) {
		super(false);
		this.name = name;
	}
	
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
