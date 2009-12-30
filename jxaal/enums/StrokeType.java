package jxaal.enums;

/**
 * The stroke types in xaal
 * @author Eric
 *
 */
public enum StrokeType {

	SOLID {
		public String toString()
		{
			return "solid";
		}
	},
	
	DOTTED
	{
		public String toString()
		{
			return "dotted";
		}
	},
	DASHED
	{
		public String toString()
		{
			return "dashed";
		}
	}
}
