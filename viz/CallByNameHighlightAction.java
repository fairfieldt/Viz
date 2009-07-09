package viz;

public class CallByNameHighlightAction extends FutureAction{
	private String fadedScope;
	private String[] highlightScopes;
	private Variable[] highlightVars;
	private Variable modifiedVar;
	private int value;
	
	public CallByNameHighlightAction(Variable[] highlightVars, String fadedScope, 
			String[] highlightScopes, Variable modifiedVar, int value, int slideNum)
	{
		super(slideNum);
		this.fadedScope = fadedScope;
		this.highlightScopes = highlightScopes;
		this.highlightVars = highlightVars;
		this.modifiedVar = modifiedVar;
		this.value = value;
	}
	
	public Variable[] getHighlightVars()
	{
		return highlightVars;
	}
	
	public String getFadedScope()
	{
		return fadedScope;
	}
	
	public String[] getHighlightScopes()
	{
		return highlightScopes;
	}
	
	public Variable getModifiedVar()
	{
		return modifiedVar;
	}
	
	public int getValue()
	{
		return value;
	}
}
