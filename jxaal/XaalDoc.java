package jxaal;

import java.util.*;

import org.jdom.*;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * Represents a XAAL Document
 * @author Eric
 *
 */
public class XaalDoc implements XaalSerializable{
	// the uri of the XAAL namespace
	public static final String XAAL_NS_URI = "http://www.cs.hut.fi/Research/SVG/XAAL";
	// the uri of XSI
	public final String XSI_URI = "http://www.w3.org/2001/XMLSchema-instance";
	// the xaal version number
	public final String XAAL_VERSION = "0.1";
	// the elements in the initial element
	public ArrayList<XaalElement> initials;
	// the elements in the animation element
	public ArrayList<Seq> seqs;
	// the namespaces for the document
	public NSContainer namespaces;
	// any custom top level elements in addition to the standard ones
	public ArrayList<TopLevelElem> customTopLevelElems;
	
	
	
	public XaalDoc()
	{
		namespaces = new NSContainer();
		namespaces.addNS(XAAL_NS_URI);
		namespaces.addNS(XSI_URI, "xsi");
		initials = new ArrayList<XaalElement>();
		seqs = new ArrayList<Seq>();
		customTopLevelElems = new ArrayList<TopLevelElem>();
	}
	
	/**
	 * Creates a new Seq element and adds it to the end of seqs
	 * @return
	 */
	public Seq newSlide()
	{
		Seq s = new Seq(this);
		seqs.add(s);
		return s;
	}
	 
	@Override
	public String xaalSerialize(Element parent) {
		Document doc = new Document();
		Element root = createElement("xaal");
		doc.setRootElement(root);
		
		for (XaalNS xNS : namespaces.namespaces)
		{
			String pref = xNS.prefix;
			String url = xNS.url;
			if (pref != "")
			{
				root.addNamespaceDeclaration
					(Namespace.getNamespace(pref, url));
			}
		}
		root.setAttribute("version", XAAL_VERSION);
		
		Namespace xsi = Namespace.getNamespace("xsi", XSI_URI);
		
		root.setAttribute("schemaLocation", "http://www.cs.hut.fi/Research/SVG/XAAL xaal.xsd", xsi);
		
		Element init = createElement("initial");
		root.addContent(init);
		
		for (XaalElement i : initials)
		{
			i.xaalSerialize(init);
		}
		
		Element anim = createElement("animation");
		root.addContent(anim);
		
		for (Seq s : seqs)
		{
			s.xaalSerialize(anim);
		}
		
		for (TopLevelElem e : customTopLevelElems)
		{
			e.xaalSerialize(root);
		}
		
		
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		return outputter.outputString(doc);
	}
	
	/**
	 * Allows you to create a JDOM element with the default XAAL ns.
	 * @param name the name of the element
	 * @return the JDOM element.
	 */
	static Element createElement(String name)
	{
		return new Element(name, Namespace.getNamespace(XAAL_NS_URI));
	}
	
	
}
