package viz;

public class HighlightScopeAction extends ScopeAction 
{
	int currentPar;
	public HighlightScopeAction(String scope, int currentPar, int snapNum)
	{
		super(scope, snapNum);
		this.currentPar = currentPar;
	}
	
	public int getPar()
	{
		return currentPar;
	}
}
