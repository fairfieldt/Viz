package jxaal;

import org.jdom.Element;

public abstract class TopLevelElem extends XaalElement {
	
	public TopLevelElem(XaalDoc doc)
	{
		super(doc, null, null);
	}
	
	public TopLevelElem(XaalDoc doc, String id)
	{
		super(doc, id, null);
	}
	
	public TopLevelElem(XaalDoc doc, String id, XaalNS ns)
	{
		super(doc, id, ns);
	}
	
	public void superclassSerialize(Element e)
	{
		super.superclassSerialize(e);
	}
}
