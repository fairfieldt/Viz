

public class Array extends Variable implements Drawable {
	
	int[] values;
	
	public Array(String name, int[] values)
	{
		super(name, 0);
		this.values = values;
	}
	
	/**
	 * 
	 * @return a clone of the values.
	 */
	public int[] getValues()
	{
		return values.clone();
	}
	
	@Override
	public void draw(XAALScripter scripter) {
		// TODO Auto-generated method stub
		
	}

}
