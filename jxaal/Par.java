package jxaal;

import java.util.ArrayList;

import org.jdom.Element;

public class Par extends XaalElement implements XaalSerializable {

	public ShowHide show;
	public ShowHide hide;
	public ArrayList<ChangeStyle> cs;
	
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
