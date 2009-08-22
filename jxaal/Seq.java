package jxaal;

import java.util.*;

import org.jdom.Element;

public class Seq extends TopLevelElem 
	implements XaalSerializable{

	public ArrayList<Par> pars;
	public String narrative;
	
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
