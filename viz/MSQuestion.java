package viz;


import java.util.ArrayList;

public class MSQuestion extends Question implements Drawable {

	ArrayList<String> choices;
	ArrayList<Integer> answers;
	
	public MSQuestion(String questionText)
	{
		super(questionText);
	}
	
	public MSQuestion(String questionText, int slideId)
	{
		super(questionText, slideId);
	}
	
	@Override
	protected void setup()
	{
		choices = new ArrayList<String>();
		answers = new ArrayList<Integer>();
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
	
	//TODO: check if the given answer is in bounds of choices
	public void addAnswer(int answer)
	{
		answers.add(new Integer(answer));
	}
	
	@Override
	public void draw(XAALScripter scripter) {
		String[] choicesArray = new String[choices.size()];
		choices.toArray(choicesArray);
		
		//unbox this because Java won't do it for me *shakes fist at Java*
		int[] answersArray = new int[answers.size()];
		for (int i = 0; i < answers.size(); i++)
		{
			answersArray[i] = answers.get(i);
		}
		
		try {
			scripter.addMSQuestion(questionText, getSlideId(), choicesArray, answersArray);
		} catch (SlideException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
