package jxaal;

import java.util.ArrayList;

import org.jdom.Element;

public class Move extends XaalElement {

	String type = "translate";
	ArrayList<XaalElement> modifiedShapes;
	Coordinate coor;
	
	public Move(XaalDoc doc) {
		super(doc, null, null);
		modifiedShapes = new ArrayList<XaalElement>();
	}

	@Override
	public String xaalSerialize(Element parent) {
		if (coor != null)
		{
			if (type.equals("translate") && modifiedShapes.size() > 0)
			{
				Element e = XaalDoc.createElement("move");
				parent.addContent(e);
				
				e.setAttribute("type", "translate");
				
				for (XaalElement xe : modifiedShapes)
				{
					Element or = XaalDoc.createElement("object-ref");
					e.addContent(or);
					
					or.setAttribute("id", xe.id);
				}
				
				coor.xaalSerialize(e);
			}
			else if (type.equals("move") && modifiedShapes.size() == 0)
			{
				Element e = XaalDoc.createElement("move");
				parent.addContent(e);
				
				e.setAttribute("type", "move");
				coor.xaalSerialize(e);
			}
		}
		return null;
	}

}
