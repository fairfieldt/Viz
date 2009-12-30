package jxaal;

import java.util.ArrayList;

import org.jdom.Element;

/**
 * Represents a par element
 * @author Eric
 *
 */
public class Par extends XaalElement implements XaalSerializable {

	//the elements that will be unhidden during this par
	public ShowHide show;
	// the elements that will be hidden during this par
	public ShowHide hide;
	// ChangeStyle elements to be applied during this par
	public ArrayList<ChangeStyle> cs;
	
	/**
	 * 
	 * @param doc the XaalDoc the par belongs to
	 */
	public Par(XaalDoc doc)
	{
		super(doc, null, null);
		show = new ShowHide(true);
		hide = new ShowHide(false);
		cs = new ArrayList<ChangeStyle>();
	}

	@Override
	public String xaalSerialize(Element parent) {
		Element e = XaalDoc.createElement("par");
		parent.addContent(e);
		
		if (show.size() > 0)
			show.xaalSerialize(e);
		if (hide.size() > 0)
			hide.xaalSerialize(e);
		
		for(ChangeStyle c : cs)
		{
			c.xaalSerialize(e);
		}
		
		
		superclassSerialize(e);
		
		return null;
	}
}
