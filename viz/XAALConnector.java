package viz;

import java.util.*;

public class XAALConnector {

	private static LinkedList<String> scopeColors;
	private int currentSnapNum;
	
	private Queue<FutureAction> actions;
	
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
	Scope globalScope;
	
	String[] psuedoCode;
	String title;
	PsuedoSerializer psuedo;
	
	public XAALConnector(String[] psuedoCode, String title)
	{
		scripter = new XAALScripter();
		varToVar = new HashMap<UUID, Variable>();
		scopes = new HashMap<String, Scope>();
		questions = new ArrayList<Question>();
		currentSnapNum = 0;
		this.psuedoCode = psuedoCode;
		this.title = title;
		this.psuedo = new PsuedoSerializer(psuedoCode, title);
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
		
		if (isGlobal)
			globalScope = retScope;
		
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
		
		setVarValue(v, var.getValue());
		
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
			//TODO: add the first id
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
	
	/**
	 * TODO: check if you're actually on a slide
	 * @param var
	 */
	public void showVar(Interpreter.Variable var)
	{

		Variable v = varToVar.get(var.getUUID());
		actions.offer(new FutureAction(true, v, currentSnapNum));
	}
	
	// TODO: check if you're actually on a slide
	public void hideVar(Interpreter.Variable var)
	{
		Variable v = varToVar.get(var.getUUID());
		actions.offer(new FutureAction(false, v, currentSnapNum));
	}
	
	public boolean startSnap(int lineNum)
	{
		if (currentSnapNum >= 0)
			return false;
			
		try {
			currentSnapNum = scripter.startSlide();
			scripter.addPseudocodeUrl(psuedo.toPsuedoPage(lineNum));
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
		
		q.setSlideId(currentSnapNum);
		
		questions.add(q);
		
		return true;
	}
	
	
	public boolean moveValue (Interpreter.Variable from, Interpreter.Variable to)
	{
		if (currentSnapNum < 0)
			return false;
		
		Variable fromVar = varToVar.get(from.getUUID());
		Variable toVar = varToVar.get(to.getUUID());
		
		//add a copy of the currentValue to fromVar
		fromVar.addCopy();
		actions.offer(new FutureAction(fromVar, toVar, currentSnapNum));
		
		toVar.setValue(fromVar.getValue());
		
		return true;
	}
	
	public boolean modifyVar(Interpreter.Variable iv, int newValue)
	{
		if (currentSnapNum < 0)
			return false;
		
		Variable v = varToVar.get(iv.getUUID());
		v.setValue(newValue);
		
		v.addCopy();
		
		actions.offer(new FutureAction(newValue, v, currentSnapNum));
		return true;
		
	}
	
	/**
	 * by default add a copy to the list
	 * @param var
	 * @param value
	 */
	public void setVarValue(Variable var, int value)
	{
		setVarValue(var, value, true);
	}
	
	private void setVarValue(Variable var, int value, boolean addCopy)
	{
		var.setValue(value);
		
		if (addCopy)
			var.addCopy();
		
		var.addCopy();
		actions.offer(new FutureAction(value, var, currentSnapNum));
		
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
	
	/**
	 * where the magic happens
	 * @param filename
	 */
	public void draw(String filename)
	{
	
		//first calls draw on the global scope which then draws all of the children
		globalScope.draw(scripter);
		
		//perform and write future actions to the scripter
		FutureAction action = null;
		do
		{
			action = actions.poll();
			if (action == null)
				break;
			
			if(action.isShowOrHide())// its a show or hide action
			{
				if (action.isShow()) // its a show action
				{
					
				}
				else // its a hide action
				{
					
				}
			}
			else if(action.getNewValue() == -1) // this is a movement from one var to another
			{
				writeMove(action);
			}
			else // a variable is being set by a constant
			{
				writeVarModify(action);
			}
			
		} while (true);
		
		//write out all the questions
		
		for (Question q : questions)
		{
			q.draw(scripter);
		}
		
		//write to the file
	}
	

	/**
	 * a move consists of:
	 * 1. reopening the slide
	 * 1.5 reopen par
	 * 2. getting a copy from the first variable.
	 * 3. performing a show on that copy.
	 * 4. getting a copy from the second variable.
	 * 5. hiding the copy from the second variable.
	 * 6. perform the move
	 * 7. give ownership to second variable.
	 * 8. setting the value of the second variable to the new value.
	 * 9. reclose par
	 * 9.5 reclose slide
	 * @param action
	 */
	private void writeMove(FutureAction action)
	{
		try {
		// reopen a slide
		scripter.reopenSlide(action.getSnapNum());
		
		// reopen par
		scripter.reopenPar();
		
		Variable from = action.getFrom();
		Variable to = action.getTo();
		
		//get copy for the first variable
		String copy1 = from.popCopyId();
		
		//show copy1
		scripter.addShow(copy1);
		
		// get copy from second variable
		String copy2 = to.popCopyId(); 
		
		//hide copy2
		scripter.addHide(copy2);
		
		//perform the move!!!
		
		int startX = from.getXPos();
		int startY = from.getYPos();
		int endX = to.getXPos();
		int endY = to.getYPos();
		
		int moveX = startX - endX;
		int moveY = startY - endY;
		
		scripter.addTranslate(-moveX, -moveY, copy1);
		
		// give ownership of copy1 to second variable.
		to.receiveCopyOwnership(copy1);
		
		// set the value of 'to' to from's value
		to.setValue(from.getValue());
		
		//reclose the par
		scripter.reclosePar();
		//reclose the slide
		scripter.recloseSlide();
		}
		catch(Exception e)
		{
			
		
		}
	
		
	} // after this method completes every variable's value must equal the head of 
	//its copiesOwned queue
	
	/**
	 * a modify consists of:
	 * 1. reopening the slide
	 * 1.5 reopen par
	 * 2. pop the copy of the currentValue
	 * 3. hide this copy
	 * 4. pop the copy of the newValue
	 * 5. show the new copy
	 * 6. give ownership of this copy BACK to the variable (its a hack)
	 * 7. set the value of the variable to its new value
	 * 8. reclose the par
	 * 8.5 reclose the slide
	 */
	private void writeVarModify(FutureAction action) 
	{
		try {
			// reopen a slide
			scripter.reopenSlide(action.getSnapNum());
			
			// reopen par
			scripter.reopenPar();
			
			Variable v = action.getTo();
			
			// pop copy of current value
			String oldCopy = v.popCopyId();
			
			//hide oldCopy
			scripter.addHide(oldCopy);
			
			// pop copy of new value
			String newCopy = v.popCopyId();
			
			//show new copy
			scripter.addShow(newCopy);
			
			//give ownership of newCopy back to variable
			v.receiveCopyOwnership(newCopy);
			
			//set the value of variable to its new value
			v.setValue(action.getNewValue());
			
			//reclose the par
			scripter.reclosePar();
			//reclose the slide
			scripter.recloseSlide();
		}
		catch (Exception e)
		{
			//we're in trouble
		}
		
	}
	
	/**
	 * 1. reopening the slide
	 * 1.5 reopen par
	 * 2. pop the copy of current value from the variable
	 * 3. show the value.
	 * 4. give ownership of this copy BACK to the variable (its a hack)
	 * 5. reclose par
	 * 5.5 reclose slide
	 * @param action
	 */
	private void writeVarShow(FutureAction action)
	{
		try {
			// reopen a slide
			scripter.reopenSlide(action.getSnapNum());
			
			// reopen par
			scripter.reopenPar();
			
			Variable v = action.getTo();
			
			// pop copy of current value
			String copy = v.popCopyId();
			
			//show copy
			scripter.addShow(copy);
			
			// give ownership of the copy back
			v.receiveCopyOwnership(copy);
			
			//reclose the par
			scripter.reclosePar();
			//reclose the slide
			scripter.recloseSlide();
		}
		catch (Exception e)
		{
			//we're in trouble
		}
	}
	
	/**
	 * 1. reopening the slide
	 * 1.5 reopen par
	 * 2. pop the copy of current value from the variable
	 * 3. hide the value.
	 * 4. give ownership of this copy BACK to the variable (its a hack)
	 * 5. reclose par
	 * 5.5 reclose slide
	 * @param action
	 */
	private void writeVarHide(FutureAction action)
	{
		try {
			// reopen a slide
			scripter.reopenSlide(action.getSnapNum());
			
			// reopen par
			scripter.reopenPar();
			
			Variable v = action.getTo();
			
			// pop copy of current value
			String copy = v.popCopyId();
			
			//hide copy
			scripter.addHide(copy);
			
			// give ownership of the copy back
			v.receiveCopyOwnership(copy);
			
			//reclose the par
			scripter.reclosePar();
			//reclose the slide
			scripter.recloseSlide();
		}
		catch (Exception e)
		{
			//we're in trouble
		}
	}
}
