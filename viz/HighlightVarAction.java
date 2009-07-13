package viz;

public class HighlightVarAction extends VarAction 
{
	protected int currentPar;
	public HighlightVarAction(Variable to, int currentPar, int snapNum )
	{
		super(to, snapNum);
		this.currentPar = currentPar;
	}
	
	public int getPar()
	{
		return currentPar;
	}
}
