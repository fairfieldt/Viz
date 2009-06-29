package viz;
import java.util.*;

public class Variable implements Drawable
{
	protected String name;
	private int value;
	protected String color = "black";
	private boolean isReference = false;
	private boolean isParam = false;
	private boolean isCopyRestore = false;
	protected ArrayList<String> ids;
	private Queue<Integer> copiesToMake;
	
	private LinkedList<String> copiesOwned;
	
	protected boolean hidden = false;
	
	protected int xPos;
	protected int yPos;

	protected int length = 0;
	
	private Variable ref =  null;
	
	private int refIndex = -255;
	
	public Variable(String name, int value, boolean isParam)
	{
		ids = new ArrayList<String>();
		this.name = name;
		this.value = value;
		this.isReference = false;
		this.isParam = isParam;
		this.length = (name.length() * 10) + 40;
		copiesToMake = new LinkedList<Integer>();
		copiesOwned = new LinkedList<String>();
	}
	
	public Variable(String name, Variable ref, boolean isParam)
	{
		ids = new ArrayList<String>();
		this.name = name;
		this.isReference = true;

		this.isParam = isParam;

		setReference(ref);
	}
	
	/**
	 * Use this for copy restore!
	 * @param name
	 * @param ref
	 * @param value
	 * @param isParam
	 */
	public Variable(String name, Variable ref, int value, boolean isParam)
	{
		ids = new ArrayList<String>();
		this.name = name;
		this.isCopyRestore = true;
		this.value = value;
		
		this.isParam = isParam;
		this.length = (name.length() * 10) + 60;
		copiesToMake = new LinkedList<Integer>();
		copiesOwned = new LinkedList<String>();
		setReference(ref);
	}
	
	public boolean getHidden()
	{
		return hidden;
	}
	
	public void setReference(Variable ref)
	{
		this.ref = ref;
		this.isReference = true;
	}
	
	public void setReference(Variable ref, int index)
	{
		this.ref = ref;
		this.refIndex = index;
		this.isReference = true;
	}
	
	public void setPosition(int xPos, int yPos)
	{
		System.out.println("Setting position of... " + name + " to " + xPos + "," + yPos);
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public void setHidden(boolean isHidden)
	{
		hidden = isHidden;
	}

	public void setIsParam(boolean isParam)
	{
		this.isParam = isParam;
	}
	
	public void setIsReference(boolean isReference)
	{
		this.isReference = isReference;
	}
	
	public boolean getIsReference()
	{
		return this.isReference;
	}
	
	public void setColor(String color)
	{
		this.color = color;
	}
	
	public String getColor()
	{
		return color;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public void setValue(int value)
	{
		this.value = value;
	}
	
	public void addCopy()
	{
		copiesToMake.offer(new Integer(value));
	}
	
	public String popCopyId()
	{
		return copiesOwned.pop();
	}
	
	public String peekCopyId()
	{
		return copiesOwned.peek();
	}
	
	/**
	 * Allows this variable to own the copy designated by id
	 * @param id
	 */
	public void receiveCopyOwnership(String id)
	{
		copiesOwned.addFirst(id);
	}
	
	public int getLength()
	{
		return this.length;
	}
	
	public int getXPos()
	{
		return this.xPos;
	}
	
	public int getYPos()
	{
		return this.yPos;
	}

	public boolean getIsParam()
	{
		return this.isParam;
	}
	
	public ArrayList<String> getIds()
	{
		System.out.println("I have " + ids.size() + " ids");
		return this.ids;
	}
	
	public String getName()
	{
		return name;
	}

	public void draw(XAALScripter scripter)
	{
		
		if (this.isCopyRestore)
		{
			String id1 = scripter.addRectangle(xPos, yPos, 40, 40, color,  hidden);
			//title
			String id2 = scripter.addText(xPos, yPos-5, name, "black", hidden);
			//pointer triangle
			String id3 = scripter.addTriangle(xPos + 16, yPos + 16 , 8, "black", hidden, 
					StrokeType.solid, 1, "black");
			//
			
			ids.add(id1);
			ids.add(id2);
			ids.add(id3);
			if (ref != null)
			{
				String id4 = null;
				if (refIndex < 0)
				{
					System.out.println("Ref pointing to something");
					id4 = scripter.addArrow(id3, ref.getIds().get(0), false, hidden);
				}
				else
				{
					Array arr = (Array) ref; 
					System.out.println("Ref pointing to arrayIndex");
					id4 = scripter.addArrow(id3, arr.getRect(refIndex), false, hidden);
				}
				ids.add(id4);
			}
			
			int rightXPos = xPos + 60;
			
			String id5 = scripter.addRectangle(rightXPos, yPos, 40, 40, color, hidden);
		
			int x1 = xPos + 40;
			int x2 = xPos + 60;
			int y = yPos + 20;
			String id6 = scripter.addLine(x1, y, x2, y, color, hidden, StrokeType.dotted);
			
			ids.add(id5);
			ids.add(id6);
			
			do 
			{
				Integer temp = copiesToMake.poll();
				if (temp == null)
					break;
				
				String newId = scripter.addText(rightXPos+15, yPos+25, temp.toString(), "black", hidden);
				copiesOwned.offer(newId);
				
			} while(true);
			
		}
		else if (this.isReference)
		{
			// rectangle
			String id1 = scripter.addRectangle(xPos, yPos, 40, 40, color,  hidden);
			//title
			String id2 = scripter.addText(xPos, yPos-5, name, "black", hidden);
			//pointer triangle
			String id3 = scripter.addTriangle(xPos + 16, yPos + 16 , 8, "black", hidden, 
					StrokeType.solid, 1, "black");
			//
			
			ids.add(id1);
			ids.add(id2);
			ids.add(id3);
			if (ref != null)
			{
				String id4 = null;
				if (refIndex < 0)
				{
					System.out.println("Ref pointing to something");
					id4 = scripter.addArrow(id3, ref.getIds().get(0), false, hidden);
				}
				else
				{
					Array arr = (Array) ref; 
					System.out.println("Ref pointing to arrayIndex");
					id4 = scripter.addArrow(id3, arr.getRect(refIndex), false, hidden);
				}
				ids.add(id4);
			}

		}
		else
		{
			String id1 = scripter.addRectangle(xPos, yPos, 40, 40, color,  hidden);
			String id2 = scripter.addText(xPos, yPos-5, name, "black", hidden);

			ids.add(id1);
			ids.add(id2);
			System.out.println(id1);
			System.out.println(id2);
			
			do 
			{
				Integer temp = copiesToMake.poll();
				if (temp == null)
					break;
				
				String newId = scripter.addText(xPos+15, yPos+25, temp.toString(), "black", hidden);
				copiesOwned.offer(newId);
				
			} while(true);
			/*
			for (int i = 0; i < copies; i++)
			{
				ids.add(scripter.addText(xPos+15, yPos+25, value + "", "black", hidden));
			}*/
		}
		
	}
	
}


