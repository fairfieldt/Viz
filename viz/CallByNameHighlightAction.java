package viz;

public class CallByNameHighlightAction extends VarAction {
	private String fadedScope;
	private String highlightScope;
	
	public CallByNameHighlightAction(Variable highlightVar, String fadedScope, 
			String highlightScope, int slideNum)
	{
		super(highlightVar, slideNum);
		this.fadedScope = fadedScope;
		this.highlightScope = highlightScope;
	}
	
	public Variable getHighlightVar()
	{
		return getTo();
	}
	
	public String getFadedScope()
	{
		return fadedScope;
	}
	
	public String getHighlightScope()
	{
		return highlightScope;
	}
}
