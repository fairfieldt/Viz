package jxaal.jhave;

import jxaal.Seq;
import jxaal.XaalDoc;

import org.jdom.Element;

public class MCQuestion extends Question {

	public MCQuestion(XaalDoc doc, Seq refSeq, String question) {
		super(doc, refSeq, question);
	}

	@Override
	public String xaalSerialize(Element parent) {
		Element e = new Element("question");
		parent.addContent(e);
		
		e.setAttribute("type", "MCQuestion");
		
		superclassSerialize(e);
		return null;
	}

}
