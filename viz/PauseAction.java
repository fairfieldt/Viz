package viz;

public class PauseAction extends FutureAction 
{
	int currentPar;
	int ms;
	
	public PauseAction(int ms, int currentPar, int snapNum)
	{
		super(snapNum);
		this.ms = ms;
		this.currentPar = currentPar;
	}
	
	public int getPar()
	{
		return currentPar;
	}
	
	public int getMS()
	{
		return this.ms;
	}
}
