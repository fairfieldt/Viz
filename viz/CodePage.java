package viz;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 *
 * @author Eric
 *
 */
public class CodePage implements Drawable {
	private ArrayList<ArrayList<LinePart>> lines;
	private HashMap<String, Integer> copiesToMake;
	
	private HashMap<String, LinkedList<String>> copiesOwned;

	private String id;
	
	public CodePage(String id)
	{
		setup();
		this.id = id;
	}
	
	private void setup()
	{
		
		lines = new ArrayList<ArrayList<LinePart>>();
		copiesToMake = new HashMap<String, Integer>();
	}
	
	/**
	 * lines starts at 1!!!
	 * @param l
	 */
	public void addLine(int l, String s)
	{
		
		lines.add(new ArrayList<LinePart>());
		lines.get(l-1).add(new LinePart(s));
	}
	
	public void addLinePart(int l, String s)
	{
		addLine(l, s);
	}
	
	public void addArg(int l, String s, String id)
	{
		addCopy(id);
		lines.get(l-1).add(new ImpLinePart(s, id));
	}
	
	public void addVar(int l, String s, String id)
	{
		addArg(l, s, id);
	}
	
	public void addCopy(String s)
	{
		if (copiesToMake.get(s) == null)
			copiesToMake.put(s, new Integer(0));
		
		int value = copiesToMake.get(s).intValue();
		copiesToMake.put(s, new Integer(value++));
	}
	
	public String getId()
	{
		return this.id;
	}
	
	@Override
	public void draw(XAALScripter scripter) {
		int y = 10;
		
		Graphics g = (new BufferedImage(1,1, 
                BufferedImage.TYPE_INT_RGB)).getGraphics();
		//change to Lucida Bright when we post
		g.setFont(new Font("Serif", 
				Font.PLAIN, XAALScripter.DEFAULT_FONT_SIZE));
		
		FontMetrics fm = g.getFontMetrics();
		int lineHeight = fm.getHeight();
		
		
		for(int i = 0; i < lines.size(); i++)
		{
			ArrayList<LinePart> line = lines.get(i);
			int lastEndingX = 0; 
			for (int j = 0; j < line.size(); j++ )
			{
				String id = null;
				if (line.get(j) instanceof ImpLinePart)
				{
					ImpLinePart ilp = (ImpLinePart)line.get(j);
					id = scripter.addText(lastEndingX, y, line.get(j).getValue(), "black", false);
					for (int n = 0; n < copiesToMake.get(ilp.getId()).intValue(); n++)
					{
						String ni = scripter.addText(lastEndingX, y, line.get(j).getValue(), "black", false);
						
					}
				}
				else
				{
					id = scripter.addText(lastEndingX, y, line.get(j).getValue(), "black", false);
				}
				
				
				lastEndingX += fm.stringWidth(line.get(j).getValue());
			}
			
			y += lineHeight;
		}
	}

}
