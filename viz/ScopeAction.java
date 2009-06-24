package viz;

public abstract class ScopeAction extends FutureAction {
	private String scope = null;
	
	public ScopeAction(String scope, int snapNum)
	{
		super(snapNum);
		this.scope = scope;
	}
	
	public String getScope()
	{
		return scope;
	}
	
	public void setScope(String scope)
	{
		this.scope = scope;
	}
}
