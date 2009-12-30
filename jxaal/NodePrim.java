package jxaal;

import java.util.ArrayList;

import org.jdom.Element;


/**
 * Parent class of all the displayable elements that consist of nodes
 * @author Eric
 *
 */
public abstract class NodePrim extends GraphicPrim {

	//list of the coordinates of the nodes
	public ArrayList<Coordinate> coordinates;
	
	/**
	 * 
	 * @param doc the document this coordinate belongs to
	 * @param xPos the x position of the coordinate
	 * @param yPos the y position of the coordinate
	 */
	public NodePrim(XaalDoc doc, String id, XaalNS elemNS) {
		super(doc, id, elemNS);
		coordinates = new ArrayList<Coordinate>();
	}
	
	protected void superclassSerialize(Element e)
	{
		super.superclassSerialize(e);
		for (Coordinate c : coordinates)
		{
			c.xaalSerialize(e);
		}
	}

}
