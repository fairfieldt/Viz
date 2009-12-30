package jxaal;

import java.util.*;

import org.jdom.Element;

/**
 * Represents a Seq element
 * @author Eric
 *
 */
public class Seq extends TopLevelElem implements XaalSerializable
{
	//all the par elements that occur during this Seq in the order they occur
	public ArrayList<Par> pars;
	//the narrative for the Seq element
	public String narrative;
	
	/**
	 * @param doc the document this Seq belongs to
	 */
	public Seq(XaalDoc doc) {
		super(doc);
		doc.seqs.add(this);
		pars = new ArrayList<Par>();
	}

	@Override
	public String xaalSerialize(Element parent) {
		Element e = XaalDoc.createElement("seq");
		parent.addContent(e);
		
		if (narrative != null)
		{
			Element n = XaalDoc.createElement("narrative");
			e.addContent(n);
			
			n.addContent(narrative);
		}
		
		for (Par p : pars)
		{
			p.xaalSerialize(e);
		}
		
		superclassSerialize(e);
		
		return null;
	}
}
