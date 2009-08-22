package jxaal;

public class XaalNS {
	public String url;
	public String prefix;
	
	public XaalNS(String url, String prefix)
	{
		this.url = url;
		if (prefix != null)
		{
			this.prefix = prefix;
		}
		
	}
}
