package viz;

public class GreyScopeAction extends ScopeAction {
	int currentPar;
	public GreyScopeAction(String scope, int currentPar, int snapNum)
	{
		super(scope, snapNum);
		this.currentPar = currentPar;
	}
	
	public int getPar()
	{
		return currentPar;
	}
}
