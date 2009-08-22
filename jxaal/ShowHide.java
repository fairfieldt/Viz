package jxaal;

import java.util.ArrayList;

import org.jdom.Element;

public class ShowHide extends ArrayList<XaalElement> implements XaalSerializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6193122182522690397L;
	public boolean show;
	public String type = "selected";
	
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
