package viz;

import java.util.*;

public class Array extends Variable implements Drawable {
	
	ArrayList<Integer> values  = new ArrayList<Integer>();
	private ArrayList<Queue<Integer>> copiesToMake;
	
	private ArrayList<LinkedList<String>> copiesOwned;
	private ArrayList<Integer> xPositions;
	
	public Array(String name, ArrayList<Integer> values, boolean isParam)
	{
		super(name, 0, isParam);
		this.values = values;
		this.length = values.size()*40;
		
		copiesToMake = new ArrayList<Queue<Integer>>();
		copiesOwned = new ArrayList<LinkedList<String>>();
		
		for (int i = 0; i < values.size(); i++)
		{
			copiesToMake.add(new LinkedList<Integer>());
			copiesOwned.add(new LinkedList<String>());
		}
		
		xPositions = new ArrayList<Integer>();
	}
	
	/**
	 * 
	 * @return a clone of the values.
	 */
	public ArrayList<Integer> getValues()
	{
		return values;
	}
	
	public int getValue (int index)
	{
		return values.get(index).intValue();
	}
	 
	public void setElem(int index, int value)
	{
		values.set(index, value);
	}
	
	public int arrayLength()
	{
		return values.size();
	}
	
	public void addCopy(int index)
	{
		copiesToMake.get(index).offer(new Integer(values.get(index)));
	}
	
	public String popCopyId(int index)
	{
		return copiesOwned.get(index).pop();
	}
	
	/**
	 * Allows this variable to own the copy designated by id
	 * @param id
	 */
	public void receiveCopyOwnership(String id, int index)
	{
		copiesOwned.get(index).addFirst(id);
	}
	
	public int getXPos(int index)
	{
		return xPositions.get(index).intValue();
	}
	
	@Override
	public void draw(XAALScripter scripter) {
		String label = scripter.addText(getXPos(), getYPos()-5, name, "black", getHidden());
		ids.add(label);
		
		int arrayXPos = getXPos();
		for (int i = 0; i < values.size(); i++)
		{
			int indexXPos = arrayXPos + (i * 40);
			xPositions.add(new Integer(indexXPos));
			
			String rectangle = 
				scripter.addRectangle(indexXPos, getYPos(), 40, 40, getColor(), getHidden());
			String id = 
				scripter.addText(indexXPos + 15, getYPos() + 25, values.get(i) + "", "black", getHidden());
			
			ids.add(rectangle);
			ids.add(id);
			
			do 
			{
				Integer temp = copiesToMake.get(i).poll();
				if (temp == null)
					break;
				
				String newId = scripter.addText(indexXPos+15, yPos+25, temp.toString(), "black", hidden);
				copiesOwned.get(i).offer(newId);
				
			} while(true);
		}
		
	}

}
