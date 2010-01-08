package viz;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 * Represents a page of code displayed on the first few slides of by-macro.
 * @author Eric Schultz and Tom Fairfield
 *
 */
public class CodePage implements Drawable {
	
	//the starting x positions of copies of values 
	public final int[] fromPosX = {100,120,130};
	public final int[] toPosX = {60, 90, 120};
	
	// the code to be displayed, separated by lines in an array
	private String[] code;

	// each position 0,1 or 2 has a list of strings it has to make
	private ArrayList<Queue<String>> copiesToMake = new ArrayList<Queue<String>>();
	
	//each position 0,1 or 2 has a list of copy ids it owns
	private ArrayList<LinkedList<String>> copiesOwned = new ArrayList<LinkedList<String>>();
	
	//all the ids for normal text strings and ones that have been used already
	private ArrayList<String> ids;
	
	//the XAALIds for each line
	private ArrayList<String> lineToXaalId;
	
	//the line number at which the call will happen and the copies should be written
	private int callLineNum = -1;
	
	//the unique id of the codepage
	private String id;

	// the height of a line
	private int lineHeight;
	
	//is this hidden at the start?
	private boolean hidden = true;
	
	//where the top of the codepage starts on the page
	public final int y = 10;
	
	//where the left side of the codepage starts
	public final int x = 0;
	
	/**
	 * Constructor for CodePage
	 * @param id the unique id of the CodePage
	 * @param code the source code to be displayed separated by lines
	 */
	public CodePage(String id, String[] code)
	{
		setup();
		this.code = new String[code.length];
		
		for (int i = 0; i < code.length; i++)
		{
			String temp = code[i].replaceFirst("\\d{1,2}\\.", "");
			temp = temp.replaceAll("\\|", "[");
			this.code[i] = temp;
		}
		
		this.id = id;
		for (int i = 0; i < 3; i++)
		{
			copiesToMake.add(i, new LinkedList<String>());
			copiesOwned.add(i, new LinkedList<String>());
		}
	}
	
	/**
	 * A setup method used by the constructor. I thought we were going to have multiple
	 * constructors but we don't so this isn't really all that needed.
	 */
	private void setup()
	{
		copiesToMake = new ArrayList<Queue<String>>();
		copiesOwned = new ArrayList<LinkedList<String>>();
		lineToXaalId = new ArrayList<String>();
		ids = new ArrayList<String>();
	}

	
	/**
	 * Adds a copy of str to the CodePage at pos. These copies will be used for moving later.
	 * @param pos the from x-position. Must be 0, 1 or 2.
	 * @param str the string to make a copy of.
	 */
	public void addCopy(int pos, String str)
	{
		copiesToMake.ensureCapacity(pos);
	
		copiesToMake.get(pos).offer(str);

			
	}
	/**
	 * Retrieves this object's id.
	 * @return the unique id
	 */
	public String getId()
	{
		return this.id;
	}
	
	public boolean getHidden()
	{
		return hidden;
	}
	
	
	public void setHidden(boolean hidden)
	{
		this.hidden = hidden;
	}
	
	/**
	 * Retrieves all the ids "owned" by this CodePage.
	 * @return
	 */
	public ArrayList<String> getIds()
	{
		return ids;
	}
	
	public ArrayList<String> getLineToXaalId()
	{
		return lineToXaalId;
	}
	
	public void setCallLineNum(int lineNum)
	{
		if (callLineNum == -1)
			callLineNum = lineNum;
	}
	
	public int numOfCopies(int pos)
	{
		return copiesOwned.get(pos).size();
	}

	public String peekCopy(int pos)
	{
		return copiesOwned.get(pos).peek();
	}
	
	public String popCopy(int pos)
	{
		return copiesOwned.get(pos).pop();
	}
	
	public void receiveCopyOwnership(String copyId)
	{
		ids.add(copyId);
	}
	
	public int getLineHeight()
	{
		return lineHeight;
	}
	
	@Override
	public void draw(XAALScripter scripter) {

		int dy = 0;
		int x = 0;
		Graphics g = (new BufferedImage(1,1, 
                BufferedImage.TYPE_INT_RGB)).getGraphics();
		//change to Lucida Bright when we post
		g.setFont(new Font("Monospaced", 
				Font.PLAIN, 20));
		
		FontMetrics fm = g.getFontMetrics();
		lineHeight = fm.getHeight();
		
		//write out all the code
		for (int i = 0; i < code.length; i++)
		{
			String id = scripter.addText(x, y + dy, code[i], "black", hidden);
			ids.add(id);
			lineToXaalId.add(id);
			dy += lineHeight;
		}
		
		//write out all the copies
		for (int i = 0; i < copiesToMake.size(); i++)
		{
			copiesOwned.add(new LinkedList<String>());
			Queue<String> posCopiesToMake = copiesToMake.get(i);
			
			while (posCopiesToMake.size() > 0)
			{
				String temp = posCopiesToMake.poll();
				String id = scripter.addText(fromPosX[i], y + (lineHeight*(callLineNum-1))
						, temp, "black", true);
				copiesOwned.get(i).offer(id);
			} 
		}
		
	}

}
