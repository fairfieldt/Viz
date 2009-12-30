package jxaal.jhave;

import org.jdom.Element;

import jxaal.XaalDoc;
import jxaal.XaalElement;
import jxaal.XaalNS;

public class AnswerOption extends XaalElement {

	public Boolean isCorrect;
	public String value;
	
	public AnswerOption(XaalDoc doc, String value) {
		super(doc, null, doc.namespaces.uriToNS.get(JhaveQuestions.JHAVE_URI));
		this.value = value;
	}

	@Override
	public String xaalSerialize(Element parent) {
		return null;
	}

}
