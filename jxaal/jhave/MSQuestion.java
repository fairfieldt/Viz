package jxaal.jhave;

import jxaal.Seq;
import jxaal.XaalDoc;

import org.jdom.Element;

public class MSQuestion extends Question {

	public MSQuestion(XaalDoc doc, Seq refSeq, String question) {
		super(doc, refSeq, question);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String xaalSerialize(Element parent) {
		Element e = new Element("question");
		parent.addContent(e);
		
		e.setAttribute("type", "MSQuestion");
		
		superclassSerialize(e);
		return null;
	}

}
