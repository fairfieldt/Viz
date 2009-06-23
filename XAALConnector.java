import java.util.*;

public class XAALConnector {

	private static LinkedList<String> scopeColors;
	private int currentSnapNum;
	
	static {
		scopeColors = new LinkedList<String>();
		scopeColors.add("blue");
		scopeColors.add("red");
		scopeColors.add("green");
	}
	
	XAALScripter scripter;
	HashMap<UUID, Variable> varToVar;
	HashMap<String, Scope> scopes;
	ArrayList<Question> questions;
	
	
	public XAALConnector()
	{
		scripter = new XAALScripter();
		varToVar = new HashMap<UUID, Variable>();
		scopes = new HashMap<String, Scope>();
		questions = new ArrayList<Question>();
		currentSnapNum = 0;
	}
	
	/**
	 * Add a scope to the visualization. Also adds its parameters.
	 * @param symbols
	 * @param name
	 * @param parent
	 */
	public void addScope(Interpreter.SymbolTable symbols, String name, String parent)
	{
		boolean isGlobal = parent == null;
		
		Scope retScope = new Scope(name, scopeColors.pop(), isGlobal);
		
		scopes.put(name, retScope);
		
		//add scope to parent if parent exists
		if(!isGlobal)
			scopes.get(parent).addScope(retScope);
		
		//Global never starts hidden
		retScope.setHidden(!isGlobal);
		
		String[] symbolNames = new String[symbols.getLocalVarNames().size()];
		symbols.getLocalVarNames().toArray(symbolNames);
		
		for(String s : symbolNames)
		{
			Variable v = new Variable(name, symbols.get(s), true);
			retScope.addVariable(v);
			
			Interpreter.Variable iv = symbols.getVariable(name);
			
			varToVar.put(iv.getUUID(), v);
			
			//if (iv instanceof ByVarVariable)
				//do nothing
			//else (iv instanceof ByRefVariable)
				//set reference
		}
	}
	
	public void addVariable(Interpreter.Variable var, String varName, String scope)
	{
		Variable v = new Variable(varName, var.getValue(), false);
		
		varToVar.put(var.getUUID(), v);
		
		scopes.get(scope).addVariable(v);
	}
	
	
	public void showScope(String s)
	{
		privStartSnap();
		privStartPar();
		
		Scope scope = scopes.get(s);
		
		ArrayList<String> ids = scope.getIds();
		
		for (String id : ids)
		{
			try {
				scripter.addShow(id);
			} catch (SlideException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		ArrayList<Variable> params = scope.getParams();
		for (Variable v : params)
		{
			ArrayList<String> paramIds = v.getIds();
			for (String id : paramIds)
			{
				try {
					scripter.addShow(id);
				} catch (SlideException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		privEndPar();
		privEndSnap();
	}
	
	public void showVar(Interpreter.Variable var)
	{
		privStartSnap();
		privStartPar();
		
		Variable v = varToVar.get(var.getUUID());
		ArrayList<String> ids = v.getIds();
		
		for (String id : ids)
		{
			try {
				scripter.addShow(id);
			} catch (SlideException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		privEndPar();
		privEndSnap();
	}
	
	public boolean startSnap(int lineNum)
	{
		if (currentSnapNum >= 0)
			return false;
			
		try {
			currentSnapNum = scripter.startSlide();
		} catch (SlideException e) {
			return false;
		}
		return true;
	}
	
	public boolean endSnap()
	{
		if (currentSnapNum < 0)
			return false;
		
		try {
			scripter.endSlide();
		} catch (SlideException e) {
			return false;
		}
		currentSnapNum = -1;
		return true;
	}
	
	
	public boolean addQuestion(Question q)
	{
		if (currentSnapNum < 0)
			return false;
		
		questions.add(q);
		
		return true;
	}

	private void privStartSnap()
	{
	}
	
	private void privEndSnap()
	{
	}
	
	private void privStartPar()
	{
	}
	
	private void privEndPar()
	{
	}
}
