package viz;

public class CallByNameHighlightAction extends FutureAction{
	private String fadedScope;
	private String[] highlightScopes;
	private Variable[] highlightVars;
	private int[] highlightVarIndexes;
	private Variable modifiedVar;
	private int modifiedVarIndex;
	private int value;
	
	public CallByNameHighlightAction(Variable[] highlightVars, int[] highlightVarIndexes, String fadedScope, 
			String[] highlightScopes, Variable modifiedVar, int modifiedVarIndex, int value, int slideNum)
	{
		super(slideNum);
		this.fadedScope = fadedScope;
		this.highlightScopes = highlightScopes;
		this.highlightVars = highlightVars;
		this.highlightVarIndexes = highlightVarIndexes;
		this.modifiedVar = modifiedVar;
		this.modifiedVarIndex = modifiedVarIndex;
		this.value = value;
	}
	
	public Variable[] getHighlightVars()
	{
		return highlightVars;
	}
	
	public int[] getHighlightVarIndexes()
	{
		return highlightVarIndexes;
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
	
	public int getModifiedVarIndex()
	{
		return modifiedVarIndex;
	}
	
	public int getValue()
	{
		return value;
	}
}
