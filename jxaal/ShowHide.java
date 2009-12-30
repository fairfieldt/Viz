package jxaal;

import java.util.ArrayList;

import org.jdom.Element;

/**
 * Represents a show or hide element in XAAL
 * @author Eric
 *
 */
public class ShowHide extends ArrayList<XaalElement> implements XaalSerializable {

	//Eclipse complains if this isn't here
	private static final long serialVersionUID = -6193122182522690397L;
	//if true, this is a show element; if false, its a hide element
	public boolean show;

	// the type of the element
	public String type = "selected";
	
	/**
	 * @param show true if this is a show element; false if its a hide element
	 */
	public ShowHide(boolean show)
	{
		this.show = show;
	}

	@Override
	public String xaalSerialize(Element parent) {
		String e_name = show ? "show" : "hide";
		Element e = XaalDoc.createElement(e_name);
		parent.addContent(e);
		
		e.setAttribute("type", type);
		
		for (XaalElement xe : this)
		{
			Element or = XaalDoc.createElement("object_ref");
			e.addContent(or);
			
			or.setAttribute("id", xe.id);
		}
		
		return null;
	}
}
