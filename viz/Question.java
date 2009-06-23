package viz;


public abstract class Question implements Drawable {
	
	protected int slideId = -1;
	
	protected String questionText;
	
	protected Question(String questionText)
	{
		this.questionText = questionText;
		setup();
	}
	
	protected Question(String questionText, int slideId)
	{
		this.questionText = questionText;
		this.slideId = slideId;
		setup();
	}
	
	protected abstract void setup();
	
	//TODO: look at this
	public void setQuestionText(String questionText){
        this.questionText = questionText.trim();
    }
	
	public int getSlideId()
	{
		return slideId;
	}
	
	public String getText()
	{
		return this.questionText;
	}
	
	public void setSlideId(int slide)
	{
		slideId = slide;
	}
	
	public abstract void draw(XAALScripter scripter);
}
