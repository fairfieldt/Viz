package viz;
import java.awt.Color;
import java.util.*;

public class Scope implements Drawable
{
	private String name;
	private ArrayList<Variable> vars;
	private ArrayList<Variable> params;
	private ArrayList<Scope> scopes;
	
	private ByNeedCache cache = null;
	
	private ArrayList<String> ids;
	private String rectId;

	private int xPos = 0;
	private int yPos = 50;
	private int sizeX = 600;
	private int sizeY = 450;
	
	private int currentParamXPos = 0;
	private int currentParamYPos = 50;
	
	private int currentVarXPos = 0;
	private int currentVarYPos = 50;
	
	private String color = "black";
	private boolean hidden = false;
	private boolean isGlobal = false;
	
	private String highlightRectId = null;
	private String fadedRectId = null;
	
	private boolean createHighlight;
	private boolean createFaded;

	public Scope(String name, String color, boolean isGlobal)
	{
		vars = new ArrayList<Variable>();
		params = new ArrayList<Variable>();
		scopes = new ArrayList<Scope>();
		ids = new ArrayList<String>();
		this.name = name;
		this.color = color;
		this.isGlobal = isGlobal;
	}

	public void addVariable(Variable v)
	{
		//If the scope is hidden we should hide all variables too

		if (hidden)
		{
			v.setHidden(true);
		}
		v.setColor(color);
		
		if (v.getIsParam())
		{
			params.add(v);
		}
		else 
		{
			this.vars.add(v);
		}
		
	}
	
	public void addScope(Scope s)
	{
		//Again, if the scope is hidden so are all sub-scopes
		if (hidden)
		{
			s.setHidden(true);
		}
		scopes.add(s);
	}
	
	public void setPosition(int xPos, int yPos)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		this.currentVarXPos = xPos;
		this.currentVarYPos = yPos;
		this.currentParamXPos = xPos;
		this.currentParamYPos = yPos;
	}
	
	public void setHidden(boolean isHidden)
	{
		hidden = isHidden;
		
		//added this so if you set hidden after you added vars, the vars are still hidden
		//TODO: should this be just for setting hidden true or for both true and false?
		if (hidden)
			for (Variable v : vars)
			{
				v.setHidden(true);
			}
	}
	
	public void setSize(int sizeX, int sizeY)
	{
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}
	
	public ArrayList<String> getIds()
	{
		return this.ids;
	}
	
	public String getRectId()
	{
		return rectId;
	}
	
	public void createHighlight()
	{
		createHighlight = true;
	}
	
	public void createFaded()
	{
		createFaded = true;
	}
	
	public String getHighlightId()
	{
		return highlightRectId;
	}
	
	public String getFadedId()
	{
		return fadedRectId;
	}
	
	/**
	 * It's your job to make sure the variable makes sense in the scope!
	 * @param v
	 */
	public void addVariableToCache(Variable v)
	{
		Variable newV = new Variable (v);
		if (cache == null)
			cache = new ByNeedCache(this);
		cache.addVariable(newV);
	}
	
	public void addVariableToCache (Variable v, int i)
	{
		 Variable newV = new Variable(v);
		 if (cache == null)
				cache = new ByNeedCache(this);
		 newV.setName(v.name + "[" + i + "]");
		 Array a = (Array)v;
		 newV.setValue(a.getValue(i));
		 
		 cache.addVariable(newV);
	}
	
	private void sizeScopes()
	{
		int subScopeXPos = xPos + 10;
		int subScopeYPos = yPos + 100;
		int subScopeYSize = (sizeY-10) / (scopes.size()+ (scopes.size() == 1 ? 0 : 1));
		if (scopes.size() == 1)
		{
			subScopeYSize -= 100;
		}
	//	System.out.println("SubSize:: " + subScopeYSize);
	//	System.out.println("Scopes.size: " + scopes.size());
		for (int i = scopes.size(); i > 0; i--)
		{
			Scope s = scopes.get(i-1);
		//	System.out.println("SubSize: " + subScopeYSize);
			s.setPosition(subScopeXPos, subScopeYPos);
			s.setSize(sizeX - 20, subScopeYSize);
			subScopeYPos += subScopeYSize + 40;
		}
			
	}
	
	private void sizeVariables()
	{
		if (name.equals(""))
		{
			currentVarYPos -= 50;
		}
		for (Variable v : vars)
		{
			if (isGlobal || scopes.size() >0)
			{
				v.setPosition(currentVarXPos + 25, currentVarYPos +35);
				
			}
			else 
			{
				v.setPosition(currentVarXPos + 25, currentVarYPos +90);
			}
			currentVarXPos += v.getLength() + 10;
		//	System.out.println("Size var " + v.getName() + " to x: " +  xPos + " y: " + yPos);
		}
		
		for (Variable v: params)
		{
			v.setPosition(currentParamXPos + 25, currentParamYPos + 25);
			currentParamXPos+=v.getLength() + 10;
			
		//	System.out.println("Size param " + v.getName() + " to x: " +  xPos + " y: " + yPos);
		}
	}
	
	public ArrayList<Variable> getParams()
	{
		return params;
	}
	
	public ArrayList<Variable> getLocalVariables()
	{
		return vars;
	}
	

	public void draw(XAALScripter scripter)
	{
		//System.out.println("Drawing scope: " + name);
		//System.out.println("XPos: " + xPos + " YPos: " + yPos);
		//int captionLength = name.length() * 13;
		String id1 = scripter.addRectangle(xPos, yPos, sizeX, sizeY, color, hidden, 6);
		rectId = id1;
		
		if (createHighlight)
		{
			highlightRectId  = scripter.addRectangle(xPos, yPos, sizeX, sizeY, color,  true, 10);
			
		}
		
		if (createFaded)
		{
			fadedRectId = scripter.addRectangle(xPos, yPos, sizeX, sizeY, new Color(192,192,192), true, 
					StrokeType.solid, 6, new Color(192,192,192));
		}
		
		String id3 = scripter.addText(xPos+3, yPos-5, name, "black", hidden);
		
		ids.add(id1);
		ids.add(id3);
		
		
		
		sizeVariables();
		for (Variable v : params)
		{
			v.draw(scripter);
		}
		for (Variable v : vars)
		{
			v.draw(scripter);
		}
		
		sizeScopes();
		
		if (name.equals("Global"))
		{
			//draw main
			scopes.get(0).draw(scripter);
			
			//draw foo -- MUST BE THE LAST ONE
			if (scopes.size() > 1)
			{
				scopes.get(1).draw(scripter);
			}
		}
		else
		{
			for (Scope s : scopes)
			{
			//	System.out.println("Drawing subScope");
				s.draw(scripter);
			}
		}
		
		if (cache != null) //there's a cache we need to draw it
		{
			cache.setPosition(xPos + 400, yPos);
			cache.draw(scripter);
		}
	}
}
