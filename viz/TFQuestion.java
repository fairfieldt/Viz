package viz;


public class TFQuestion extends Question implements Drawable {

	boolean answer;
	private int expectedValue;
	
	public TFQuestion(String questionText)
	{
		super(questionText);
	}
	
	public TFQuestion(String questionText, int slideId)
	{
		super(questionText, slideId);
	}
	
	@Override
	protected void setup() {}
	
	//TODO: check if the given answer is bounds of choices
	public void setAnswer(boolean answer)
	{
		this.answer = answer;
	}
	
	public boolean getAnswer()
	{
		return this.answer;
	}
	
	public void flipAnswer()
	{
		if(this.answer)
		{
			this.answer = false;
		}
		else
		{
			this.answer = true;
		}
	}
	
	public void setExpectedValue(int value)
	{
		expectedValue = value;
	}
	
	public int getExpectedValue()
	{
		return expectedValue;
	}
	
	@Override
	public void draw(XAALScripter scripter) {
		try {
			scripter.addTFQuestion(questionText, slideId, answer);
		} catch (SlideException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

}
