package jxaal.jhave;

import java.util.ArrayList;

import org.jdom.Element;
import jxaal.*;

public abstract class Question extends XaalElement {

	public String question;
	public ArrayList<AnswerOption> options;
	
	public Question(XaalDoc doc, Seq refSeq, String question) {
		super(doc, doc.seqs.indexOf(refSeq) + "", doc.namespaces.uriToNS.get(JhaveQuestions.JHAVE_URI));
		this.question = question;
		this.options = new ArrayList<AnswerOption>();
	}
	
	public void superclassSerialize(Element e)
	{
		if (options.size() > 0)
		{
			Element qe = new Element("question_text", JhaveQuestions.getJhaveNS());
			e.addContent(qe);
			qe.addContent(question);
			
			for (AnswerOption ao : options)
			{
				ao.xaalSerialize(e);
			}
		}
	}
}
