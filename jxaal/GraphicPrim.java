package jxaal;

import org.jdom.Element;

/**
 * the superclass of all elements which can be displayed
 * @author Eric
 *
 */
public abstract class GraphicPrim extends XaalElement {
	// is this element hidden?
	public Boolean hidden;
	//the element's style
	public Style style;
	
	/**
	 * @param doc the document that this element belongs to
	 * @param id the id of this element. May be null.
	 * @param elemNS the ns of this element. May be null.
	 */
	public GraphicPrim(XaalDoc doc, String id, XaalNS elemNS) {
		super(doc, id, elemNS);
		style = new Style(doc, null);
	}
	
	protected void superclassSerialize(Element e)
	{
		super.superclassSerialize(e);
		if (hidden != null)
			e.setAttribute("hidden", hidden.toString());
		style.xaalSerialize(e);
	}
}
