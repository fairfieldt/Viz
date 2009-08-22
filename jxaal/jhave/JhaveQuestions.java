package jxaal.jhave;

import java.util.ArrayList;

import org.jdom.Element;
import org.jdom.Namespace;

import jxaal.*;

public class JhaveQuestions extends TopLevelElem {
	public static final String JHAVE_URI = "http://www.uwosh.edu/jhave/ns";
	public ArrayList<Question> questions;
	
	public JhaveQuestions(XaalDoc doc) {
		super(doc, null, doc.namespaces.uriToNS.get(JHAVE_URI));
		doc.customTopLevelElems.add(this);
		questions = new ArrayList<Question>();
	}

	public static Namespace getJhaveNS()
	{
		return Namespace.getNamespace(JHAVE_URI, "jhave");
	}
	
	@Override
	public String xaalSerialize(Element parent) {
		Element e = new Element("questions", JhaveQuestions.getJhaveNS());
		parent.addContent(e);
		
		for (Question q : questions)
		{
			q.xaalSerialize(e);
		}
		
		superclassSerialize(e);
		
		return null;
	}

}
