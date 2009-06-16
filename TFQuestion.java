

public class TFQuestion extends Question implements Drawable {

	boolean answer;
	
	public TFQuestion(String questionText)
	{
		super(questionText);
	}
	
	//TODO: check if the given answer is bounds of choices
	public void setAnswer(boolean answer)
	{
		this.answer = answer;
	}
	
	@Override
	public void draw(XAALScripter scripter) {
		// TODO Auto-generated method stub

	}

}
