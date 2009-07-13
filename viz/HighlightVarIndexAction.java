package viz;

public class HighlightVarIndexAction extends HighlightVarAction 
{
	private int index;
	public HighlightVarIndexAction(Variable v, int index, int currentPar, int snapNum)
	{
		super(v, currentPar, snapNum);
		this.index = index;
	}
	
	public int getIndex()
	{
		return this.index;
	}
}
