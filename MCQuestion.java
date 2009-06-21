import java.util.ArrayList;

public class MCQuestion extends Question implements Drawable {
	ArrayList<String> choices;
	int answer;
	
	public MCQuestion(String questionText)
	{
		super(questionText);
	}
	
	public MCQuestion(String questionText, int slideId)
	{
		super(questionText, slideId);
	}
	
	@Override
	protected void setup()
	{
		choices = new ArrayList<String>();
	}
	
	/**
	 * 
	 * @param choice
	 * @return the index of the newly added choice
	 */
	public int addChoice(String choice)
	{
		choices.add(choice);
		return (choices.size() - 1);
	}
	
	//TODO: check if the given answer is bounds of choices
	public void setAnswer(int answer)
	{
		this.answer = answer;
	}
	
	
	@Override
	public void draw(XAALScripter scripter) {
		String[] choiceArray = new String[choices.size()];
		choices.toArray(choiceArray);
		
		try {
			scripter.addMCQuestion(questionText, getSlideId(), choiceArray, answer);
		} catch (SlideException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
