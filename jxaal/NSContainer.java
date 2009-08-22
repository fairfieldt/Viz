package jxaal;

import java.util.*;

public class NSContainer
{
	public ArrayList<XaalNS> namespaces;
	public HashMap<String, XaalNS> nameToNS;
	public HashMap<String, XaalNS> uriToNS;
	
	public NSContainer()
	{
		namespaces = new ArrayList<XaalNS>();
		nameToNS = new HashMap<String,XaalNS>();
		uriToNS = new HashMap<String,XaalNS>();
	}
	
	public XaalNS addNS(String uri)
	{
		return addNS(uri, "");
	}
	
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