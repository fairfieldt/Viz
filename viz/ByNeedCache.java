package viz;

public class ByNeedCache 
{
	private Scope parent;
	
	public ByNeedCache(Scope parent)
	{
		this.parent = parent;
	}
	
	public Scope getParent()
	{
		return parent;
	}
}
