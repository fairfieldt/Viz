

import java.util.ArrayList;

public class MSQuestion extends Question implements Drawable {

	String[] choices;
	ArrayList<Integer> answers;
	
	public MSQuestion(String questionText, String[] choices)
	{
		super(questionText);
		this.choices = choices;
		answers = new ArrayList<Integer>();
	}
	
	//TODO: check if the given answer is in bounds of choices
	public void addAnswer(int answer)
	{
		answers.add(new Integer(answer));
	}
	
	@Override
	public void draw(XAALScripter scripter) {
		// TODO Auto-generated method stub

	}

}
