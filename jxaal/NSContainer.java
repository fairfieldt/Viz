package jxaal;

import java.util.*;

/**
 * A container for all the namespaces in a document.
 * 
 * NOTE: Only add namespaces using the addNS methods, never 
 * directly into the arraylist and hashmaps
 * @author Eric
 *
 */
public class NSContainer
{
	//list of the namespaces
	public ArrayList<XaalNS> namespaces;
	//a hashmap with key being the prefix of the ns 
	//and the value being the actual namespace object
	public HashMap<String, XaalNS> nameToNS;
	//a hashmap with key being the uri of the ns 
	//and the value being the actual namespace object
	public HashMap<String, XaalNS> uriToNS;
	
	public NSContainer()
	{
		namespaces = new ArrayList<XaalNS>();
		nameToNS = new HashMap<String,XaalNS>();
		uriToNS = new HashMap<String,XaalNS>();
	}
	
	/**
	 * Add a default namespace with a blank prefix to the the NSContainer
	 * @param uri the uri of the namespace
	 * @return the XaalNS representing the newly created ns
	 */
	public XaalNS addNS(String uri)
	{
		return addNS(uri, "");
	}
	
	/**
	 * Add a namespace to the NSContainer
	 * @param uri the uri of the namespace
	 * @param prefix the prefix of the namespace
	 * @return the XaalNS representing the newly created ns
	 */
	public XaalNS addNS(String uri, String prefix)
	{
		XaalNS ns = new XaalNS(uri, prefix);
		if (uriToNS.get(uri) != null)
		{
			XaalNS testns = uriToNS.get(uri);
			namespaces.remove(testns);
		}
		namespaces.add(ns);
		uriToNS.put(uri, ns);
		nameToNS.put(prefix, ns);
		return ns;
	}
	
}