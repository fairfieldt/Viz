

import java.util.*;

public class FIBQuestion extends Question implements Drawable {

	ArrayList<String> answers;
	
	public FIBQuestion(String questionText)
	{
		super(questionText);
	}
	
	public FIBQuestion(String questionText, int slideId)
	{
		super(questionText, slideId);
	}
	
	@Override
	protected void setup()
	{
		answers = new ArrayList<String>();
	}
	
	public void addAnswer(String answer)
	{
		answers.add(answer);
	}
	
	@Override
	public void draw(XAALScripter scripter) {
		String[] answerArray = new String[answers.size()];
		answers.toArray(answerArray);
		
		try {
			scripter.addFibQuestion(questionText, getSlideId(), answerArray);
		} catch (SlideException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
