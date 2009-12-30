package jxaal.enums;

/**
 * the named colors in XAAL
 * @author Eric
 *
 */
public enum ColorName {
	MAROON,
	RED,
	ORANGE,

	YELLOW,
	OLIVE,
	PURPLE,
	FUCHSIA,
	WHITE,
	LIME,
	GREEN,
	NAVY,
	AQUA,
	TEAL,
	BLACK,
	SILVER,
	GRAY,
	BLUE;

	@Override
	public String toString()
	{
		String s = name();
		return s.toLowerCase();
	}
}
