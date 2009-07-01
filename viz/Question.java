package viz;


public abstract class Question implements Drawable {
	
	protected int slideId = -1;
	
	protected String questionText;
	
	//The name of the var we care about
	protected String variable;
	
	//For a var, the index we asked about.
	protected int index = -1;
	
	//The value we want to use later for...
	protected int value = -1;
	
	
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
    
    	public void setText(String text)
    	{
    		this.questionText = text;
    	}
    	
    	public void setValue(int value)
    	{
    		this.value = value;
    	}
    	
    	public int getValue()
    	{
    		return this.value;
    	}
    	
        public void setIndex(int index)
        {
        	this.index = index;
        }
        
        public int getIndex()
        {
        	return this.index;
        }
        
        public void setVariable(String variable)
        {
        	this.variable = variable;
        }
        
        public String getVariable()
        {
        	return this.variable;
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
