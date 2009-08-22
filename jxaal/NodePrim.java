package jxaal;

import java.util.ArrayList;

import org.jdom.Element;

public abstract class NodePrim extends GraphicPrim {

	public ArrayList<Coordinate> coordinates;
	
	public NodePrim(XaalDoc doc, String id, XaalNS elemNS) {
		super(doc, id, elemNS);
		coordinates = new ArrayList<Coordinate>();
	}
	
	public void superclassSerialize(Element e)
	{
		super.superclassSerialize(e);
		for (Coordinate c : coordinates)
		{
			c.xaalSerialize(e);
		}
	}

}
