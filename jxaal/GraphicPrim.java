package jxaal;

import org.jdom.Element;

public abstract class GraphicPrim extends XaalElement {

	public Boolean hidden;
	
	public Style style;
	
	public GraphicPrim(XaalDoc doc, String id, XaalNS elemNS) {
		super(doc, id, elemNS);
		style = new Style(doc, null);
	}
	
	public void superclassSerialize(Element e)
	{
		super.superclassSerialize(e);
		if (hidden != null)
			e.setAttribute("hidden", hidden.toString());
		style.xaalSerialize(e);
	}
}
