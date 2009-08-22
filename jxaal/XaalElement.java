package jxaal;

import java.util.*;

import org.jdom.Element;
import org.jdom.Namespace;

public abstract class XaalElement implements XaalSerializable
{
	public ArrayList<Attribute> attribs;
	public XaalNS ns;
	protected XaalDoc doc;
	protected String id;
	private static int defaultIdNum = 0;
	
	public XaalElement(XaalDoc doc, String id, XaalNS elemNS)
	{
		attribs = new ArrayList<Attribute>();
		if (elemNS != null)
		{
			ns = elemNS;
		}
		else
		{
			ns = doc.namespaces.nameToNS.get("");
		}
		this.doc = doc;
		
		if (id == null)
		{
			id = "XaalElement_" + getNewDefaultId();
		}
		this.id = id;
	}
	
	public void addAttribute(String name, String value)
	{
		addAttribute(name, value, ns);
	}
	
	public void addAttribute(String name, String value, XaalNS attribNS)
	{
		Attribute temp = new Attribute(name, attribNS, value);
		attribs.add(temp);
	}
	
	public void superclassSerialize(Element e)
	{
		e.setAttribute("id", id);
		
		e.setNamespace(Namespace.getNamespace(ns.prefix, ns.url));
	}
	
	
	private static int getNewDefaultId()
	{
		if (defaultIdNum == 0)
		{
			defaultIdNum =1;
			return 0;
		}
		else
			return defaultIdNum += 1;
	}

}
