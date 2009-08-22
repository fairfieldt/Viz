package jxaal.enums;

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
