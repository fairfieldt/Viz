

public abstract class Question implements Drawable {
	
	private static int qNum = 0;
	protected String id;
	
	protected String questionText;
	
	protected Question(String questionText)
	{
		this.questionText = questionText;
		id = "question" + qNum;
		qNum++;
	}
	
	//TODO: look at this
	public void setQuestionText(String questionText){
        this.questionText = questionText.trim();
    }
	
	public String getId()
	{
		return id;
	}
	
	public abstract void draw(XAALScripter scripter);
}
