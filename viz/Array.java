package viz;

import java.util.*;

public class Array extends Variable implements Drawable {
	
	ArrayList<Integer> values  = new ArrayList<Integer>();
	
	public Array(String name, ArrayList<Integer> values, boolean isParam)
	{
		super(name, 0, isParam);
		this.values = values;
		this.length = values.size()*40;
	}
	
	/**
	 * 
	 * @return a clone of the values.
	 */
	public ArrayList<Integer> getValues()
	{
		return values;
	}
	
	public void setElem(int index, int value)
	{
		values.set(index, value);
	}
	
	public int arrayLength()
	{
		return values.size();
	}
	
	@Override
	public void draw(XAALScripter scripter) {
		String label = scripter.addText(getXPos(), getYPos()-5, name, "black", getHidden());
		ids.add(label);
		
		int arrayXPos = getXPos();
		for (int i = 0; i < values.size(); i++)
		{
			String rectangle = 
				scripter.addRectangle(arrayXPos + (i * 40), getYPos(), 40, 40, getColor(), getHidden());
			String id = 
				scripter.addText(arrayXPos + (i * 40) + 15, getYPos() + 25, values.get(i) + "", "black", getHidden());
			
			ids.add(rectangle);
			ids.add(id);
		}
		
	}

}
