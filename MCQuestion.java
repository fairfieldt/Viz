


public class MCQuestion extends Question implements Drawable {
	String[] choices;
	int answer;
	
	public MCQuestion(String questionText, String[] choices)
	{
		super(questionText);
		this.choices = choices;
	}
	
	//TODO: check if the given answer is bounds of choices
	public void setAnswer(int answer)
	{
		this.answer = answer;
	}
	
	@Override
	public void draw(XAALScripter scripter) {
		

	}

}
