package Main;

import java.util.*;

public class FIBQuestion extends Question implements Drawable {

	String[] choices;
	ArrayList<String> answers;
	
	public FIBQuestion(String questionText, String[] choices)
	{
		super(questionText);
		this.choices = choices;
		answers = new ArrayList<String>();
	}
	
	public void addAnswer(String answer)
	{
		answers.add(answer);
	}
	
	@Override
	public void draw(XAALScripter scripter) {
		// TODO Auto-generated method stub

	}

}
