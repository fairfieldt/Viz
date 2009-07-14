package viz;

import java.util.*;

public class CallByNameAction extends FutureAction {
	
	private Queue<String> fadedScopes;
	private Queue<String> highlightScopes;
	private Queue<Variable> highlightVars;
	private Queue<Integer> highlightVarIndexes;
	private Variable modifiedVar;
	private int modifiedVarIndex;
	private int value;
	
	public CallByNameAction(int snapNum)
	{
		super(snapNum);
		highlightVars = new LinkedList<Variable>();
		highlightVarIndexes = new LinkedList<Integer>();
		fadedScopes = new LinkedList<String>();
		highlightScopes = new LinkedList<String>();
	}
	
	public void addHighlightVar(Variable v)
	{
		addHighlightVar(v, -1);
	}
	
	public void addHighlightVar(Variable v, int index)
	{
		highlightVars.offer(v);
		highlightVarIndexes.offer(index);
	}
	
	public Queue<Variable> getHighlightVars()
	{
		return highlightVars;
	}
	
	public Queue<Integer> getHighlightVarIndexes()
	{
		return highlightVarIndexes;
	}
	
	public void addFadedScope(String s)
	{
		fadedScopes.offer(s);
	}
	
	public Queue<String> getFadedScopes()
	{
		return fadedScopes;
	}
	
	public Queue<String> getHighlightScopes()
	{
		return highlightScopes;
	}
	
	public void addHighlightScope(String s)
	{
		highlightScopes.offer(s);
	}
	
	public Variable getModifiedVar()
	{
		return modifiedVar;
	}
	
	public void setModifiedVar(Variable v)
	{
		setModifiedVar(v, -1);
	}
	
	public void setModifiedVar(Variable v, int index)
	{
		modifiedVar = v;
		modifiedVarIndex = index;
	}
	
	public void setValue (int value)
	{
		this.value = value;
	}
	
	public int getModifiedVarIndex()
	{
		return modifiedVarIndex;
	}
	
	public int getValue()
	{
		return value;
	}
}
