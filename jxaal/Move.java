package jxaal;

import java.util.ArrayList;

import org.jdom.Element;

/**
 * represents a move element
 * @author Eric
 *
 */
public class Move extends XaalElement {

	//the type of the move element, either translate or move
	String type = "translate";
	//the shapes that will be moved in some fashion
	ArrayList<XaalElement> modifiedShapes;
	//a coordinate representing how much the shapes will move (if type is "translate") or the
	// the position the shapes will move to (if type is "move")
	Coordinate coor;
	
	/**
	 * @param doc the document this move element belongs to.
	 */
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
