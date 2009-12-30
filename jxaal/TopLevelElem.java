package jxaal;

import org.jdom.Element;

/**
 * Represents a Top Level element (metadata, defs, initial, and 
 * animation in the XAAL specs) in a XAAL Document.
 * @author Eric
 *
 */
public abstract class TopLevelElem extends XaalElement {
	
	/**
	 * @param doc the XAAL document the element belongs to.
	 */
	public TopLevelElem(XaalDoc doc)
	{
		super(doc, null, null);
	}
	
	/**
	 * @param doc the XAAL document the element belongs to.
	 * @param id the id of the top level element, may be null.
	 */
	public TopLevelElem(XaalDoc doc, String id)
	{
		super(doc, id, null);
	}
	
	/**
	 * @param doc the XAAL document the element belongs to.
	 * @param id the id of the top level element, may be null.
	 * @param ns the namespace of the top level element. If null, the default XAAL namespace is used
	 */
	public TopLevelElem(XaalDoc doc, String id, XaalNS ns)
	{
		super(doc, id, ns);
	}
	
	@Override
	protected void superclassSerialize(Element e)
	{
		super.superclassSerialize(e);
	}
}
