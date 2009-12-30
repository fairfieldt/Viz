package jxaal;

/**
 * Represents an XML namespace for a XAAL document
 * @author Eric
 *
 */
public class XaalNS {
	// the url of the namespace
	public String url;
	//the prefix of the namespace
	public String prefix;
	
	/**
	 * @param url the uri of the namespace
	 * @param prefix the prefix of the namespace
	 */
	public XaalNS(String url, String prefix)
	{
		this.url = url;
		if (prefix != null)
		{
			this.prefix = prefix;
		}
		
	}
}
