package jxaal.jhave;

import jxaal.Seq;
import jxaal.XaalDoc;

import org.jdom.Element;

public class TFQuestion extends Question {

	public Boolean answer;
	
	public TFQuestion(XaalDoc doc, Seq refSeq, String question) {
		super(doc, refSeq, question);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String xaalSerialize(Element parent) {
		if (answer != null)
		{
			Element e = new Element("question");
			parent.addContent(e);
			
			e.setAttribute("type", "TFQuestion");
			AnswerOption ao = new AnswerOption(doc, answer.toString());
			ao.isCorrect = true;
			this.options.add(ao);
			
			superclassSerialize(e);
		}
		
		return null;
	}

}
