package jxaal;

import java.util.*;

import org.jdom.Element;
import org.jdom.Namespace;

/**
 * parent class of all XAAL elements
 * @author Eric
 *
 */
public abstract class XaalElement implements XaalSerializable
{
	//the attributes for the element
	public ArrayList<Attribute> attribs;
	//the namespace of the element
	public XaalNS ns;
	// the document the element belongs to
	protected XaalDoc doc;
	// the id of the element
	protected String id;
	// the next number to use when you create an element with a default id.
	private static int defaultIdNum = 0;
	
	/**
	 * 
	 * @param doc the XaalDoc the element belongs to
	 * @param id the id for the element. If null, a default is created
	 * @param elemNS the namespace for the element. If null, the default for the document is used. 
	 */
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
	
	/**
	 * Adds an attribute to the document with the default namespace of the element.
	 * @param name the name of the attribute 
	 * @param value the value of the attribute as as string 
	 */
	public void addAttribute(String name, String value)
	{
		addAttribute(name, value, ns);
	}
	
	/**
	 * Adds an attribute to the document.
	 * @param name the name of the attribute 
	 * @param value the value of the attribute as as string 
	 * @param attribNS the namespace of the attribute
	 */
	public void addAttribute(String name, String value, XaalNS attribNS)
	{
		Attribute temp = new Attribute(name, attribNS, value);
		attribs.add(temp);
	}
	
	/**
	 * Provides a way for child classes to serialize the attributes of the parent without
	 * creating a new element.
	 * @param e the element which should have the necessary attribs added to it.
	 */
	protected void superclassSerialize(Element e)
	{
		e.setAttribute("id", id);
		
		e.setNamespace(Namespace.getNamespace(ns.prefix, ns.url));
	}
	
	
	/**
	 * Returns a new number for the default ids
	 * @return the number to append to "XaalElement_" for a default ID
	 */
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
