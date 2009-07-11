package viz;

import org.jdom.*;
import org.jdom.output.*;
import org.jdom.xpath.XPath;

import java.util.*;

/**
 * <code>XAALScripter</code> provides methods to create a XAAL document, add
 * elements to it and write it out to a file. It isn't dependent on any other
 * classes in this project so anyone could use this class on its own for
 * creating XAAL documents themselves.
 * 
 * @author Eric
 * 
 */
public class XAALScripter {
	// the jdom document
	private Document document = new Document();
	// XAAL namespace
	private final Namespace defaultNS = Namespace
			.getNamespace("http://www.cs.hut.fi/Research/SVG/XAAL");

	// TODO: change the namespace to a final namespace eventually
	// namespace for JHAVE extensions to XAAL
	private final Namespace jhaveNS = Namespace.getNamespace("jhave",
			"http://www.uwosh.edu/jhave/ns");

	public static final int DEFAULT_FONT_SIZE = 16;
	public static final String DEFAULT_FONT_FAMILY = "Lucida Bright";
	public static final int DEFAULT_STROKE_WIDTH = 1;

	// the number used to create unique ids for each type of object.
	private int rectNum = 0;
	private int textNum = 0;
	private int lineNum = 0;
	private int triangleNum = 0;
	private int arrowNum = 0;

	// the current slide number
	private int slideNum = 0;

	// the current slide as a JDom Element
	private Element currentSlide = null;

	// the current par as a JDom Element
	private Element currentPar = null;
	
	//whether we should be running in debug mode and printing out debug info.
	public static boolean debug = false;

	/**
	 * Constructor for XAALScripter
	 */
	public XAALScripter() {
		Element xaalRoot = createElement("xaal");

		xaalRoot.setAttribute("version", 1.0 + "");

		Namespace xsi = Namespace.getNamespace("xsi",
				"http://www.w3.org/2001/XMLSchema-instance");
		xaalRoot.addNamespaceDeclaration(xsi);
		xaalRoot.addNamespaceDeclaration(jhaveNS);

		Attribute schemaLocation = new Attribute("schemaLocation",
				"http://www.cs.hut.fi/Research/SVG/XAAL xaal.xsd", xsi);
		xaalRoot.setAttribute(schemaLocation);

		Element initial = createElement("initial");
		xaalRoot.addContent(initial);

		Element animation = createElement("animation");
		xaalRoot.addContent(animation);

		Element questions = new Element("questions", jhaveNS);
		xaalRoot.addContent(questions);

		document.setRootElement(xaalRoot);
	}

	/**
	 * Adds a new non-hidden, solid, black rectangle with default line width to
	 * the initial element of a XAAL script.
	 * 
	 * @param x
	 *            y coordinate for the top left corner of the rectangle.
	 * @param y
	 *            y coordinate for the top left corner of the rectangle.
	 * @param width
	 *            width of the rectangle in pixels.
	 * @param height
	 *            height of the rectangle in pixels.
	 * @return a String containing the id of the rectangle added.
	 */
	public String addRectangle(int x, int y, int width, int height) {
		return addRectangle(x, y, width, height, "black");
	}

	/**
	 * Adds a new non-hidden, solid rectangle with default line width to the
	 * initial element of a XAAL script.
	 * 
	 * @param x
	 *            y coordinate for the top left corner of the rectangle.
	 * @param y
	 *            y coordinate for the top left corner of the rectangle.
	 * @param width
	 *            width of the rectangle in pixels.
	 * @param height
	 *            height of the rectangle in pixels.
	 * @param color
	 *            color of the rectangle's border. Must be a named XAAL color.
	 * @return a String containing the id of the rectangle added.
	 */
	public String addRectangle(int x, int y, int width, int height, String color) {
		return addRectangle(x, y, width, height, "black", false);
	}

	/**
	 * Adds a new solid rectangle with default line width to the initial element
	 * of a XAAL script.
	 * 
	 * @param x
	 *            y coordinate for the top left corner of the rectangle.
	 * @param y
	 *            y coordinate for the top left corner of the rectangle.
	 * @param width
	 *            width of the rectangle in pixels.
	 * @param height
	 *            height of the rectangle in pixels.
	 * @param color
	 *            color of the rectangle's border. Must be a named XAAL color.
	 * @param hidden
	 *            specifies whether the rectangle should be hidden initially.
	 * @return a String containing the id of the rectangle added.
	 */
	public String addRectangle(int x, int y, int width, int height,
			String color, boolean hidden) {
		return addRectangle(x, y, width, height, color, hidden,
				StrokeType.solid);
	}

	/**
	 * Adds a new rectangle to the initial element of a XAAL script.
	 * 
	 * @param x
	 *            y coordinate for the top left corner of the rectangle.
	 * @param y
	 *            y coordinate for the top left corner of the rectangle.
	 * @param width
	 *            width of the rectangle in pixels.
	 * @param height
	 *            height of the rectangle in pixels.
	 * @param color
	 *            color of the rectangle's border. Must be a named XAAL color.
	 * @param hidden
	 *            specifies whether the rectangle should be hidden initially.
	 * @param strokeType
	 *            whether the line should be solid, dashed or dotted.
	 * @return a String containing the id of the rectangle added.
	 */
	public String addRectangle(int x, int y, int width, int height,
			String color, boolean hidden, StrokeType strokeType) {
		return addRectangle(x, y, width, height, color, hidden, strokeType,
				DEFAULT_STROKE_WIDTH);
	}

	/**
	 * Adds a new solid rectangle to the initial element of a XAAL script.
	 * 
	 * @param x
	 *            y coordinate for the top left corner of the rectangle.
	 * @param y
	 *            y coordinate for the top left corner of the rectangle.
	 * @param width
	 *            width of the rectangle in pixels.
	 * @param height
	 *            height of the rectangle in pixels.
	 * @param color
	 *            color of the rectangle's border. Must be a named XAAL color.
	 * @param hidden
	 *            specifies whether the rectangle should be hidden initially.
	 * @param lineWidth
	 *            the width of the rectangle's border.
	 * @return a String containing the id of the rectangle added.
	 */
	public String addRectangle(int x, int y, int width, int height,
			String color, boolean hidden, int lineWidth) {
		return addRectangle(x, y, width, height, color, hidden,
				StrokeType.solid, lineWidth);
	}

	/**
	 * Adds a new solid rectangle to the initial element of a XAAL script.
	 * 
	 * @param x
	 *            y coordinate for the top left corner of the rectangle.
	 * @param y
	 *            y coordinate for the top left corner of the rectangle.
	 * @param width
	 *            width of the rectangle in pixels.
	 * @param height
	 *            height of the rectangle in pixels.
	 * @param color
	 *            color of the rectangle's border. Must be a named XAAL color.
	 * @param hidden
	 *            specifies whether the rectangle should be hidden initially.
	 * @param strokeType
	 * @param lineWidth
	 *            the width of the rectangle's border.
	 * @return a String containing the id of the rectangle added.
	 */
	public String addRectangle(int x, int y, int width, int height,
			String color, boolean hidden, StrokeType strokeType, int lineWidth) {
		return addRectangle(x, y, width, height, color, hidden, strokeType, lineWidth, null);
	}
	
	/**
	 * Adds a new solid rectangle to the initial element of a XAAL script.
	 * 
	 * @param x
	 *            y coordinate for the top left corner of the rectangle.
	 * @param y
	 *            y coordinate for the top left corner of the rectangle.
	 * @param width
	 *            width of the rectangle in pixels.
	 * @param height
	 *            height of the rectangle in pixels.
	 * @param color
	 *            color of the rectangle's border. Must be a named XAAL color.
	 * @param hidden
	 *            specifies whether the rectangle should be hidden initially.
	 * @param strokeType
	 * @param lineWidth
	 *            the width of the rectangle's border.
	 * @param fillColor color the rectangle should be filled with. If null, 
	 *            it won't be filled.
	 * @return a String containing the id of the rectangle added.
	 */
	public String addRectangle(int x, int y, int width, int height,
			String color, boolean hidden, StrokeType strokeType, int lineWidth, 
			String fillColor) 
	{
		Element initial = document.getRootElement().getChild("initial",
				defaultNS);

		Element rect = createElement("polyline");

		String idVal = "rectangle" + rectNum;
		rectNum++;

		rect.setAttribute("id", idVal);

		rect.setAttribute("hidden", hidden + "");

		// set up jhave width and height EXTENSION
		rect.setAttribute("shapeWidth", width + "", jhaveNS);
		rect.setAttribute("shapeHeight", height + "", jhaveNS);

		Element x1y1 = createElement("coordinate");
		x1y1.setAttribute("x", x + "");
		x1y1.setAttribute("y", y + "");
		rect.addContent(x1y1);

		Element x1y2 = createElement("coordinate");
		x1y2.setAttribute("x", x + "");
		x1y2.setAttribute("y", (y + height) + "");
		rect.addContent(x1y2);

		Element x2y2 = createElement("coordinate");
		x2y2.setAttribute("x", (x + width) + "");
		x2y2.setAttribute("y", (y + height) + "");
		rect.addContent(x2y2);

		Element x2y1 = createElement("coordinate");
		x2y1.setAttribute("x", (x + width) + "");
		x2y1.setAttribute("y", y + "");
		rect.addContent(x2y1);

		Element x1y1_2 = createElement("coordinate");
		x1y1_2.setAttribute("x", x + "");
		x1y1_2.setAttribute("y", y + "");
		rect.addContent(x1y1_2);

		Element closed = createElement("closed");
		closed.setAttribute("value", true + "");
		rect.addContent(closed);

		Element style = createElement("style");

		Element colorElem = createElement("color");
		colorElem.setAttribute("name", color);
		style.addContent(colorElem);

		Element strokeElem = createElement("stroke");
		strokeElem.setAttribute("width", lineWidth + "");
		strokeElem.setAttribute("type", strokeType.name());
		style.addContent(strokeElem);
		
		if (fillColor != null) {
			Element fillColorElem = createElement("fill-color");
			fillColorElem.setAttribute("name", fillColor);
			style.addContent(fillColorElem);
		}
		
		rect.addContent(style);

		initial.addContent(rect);

		return idVal;
	}

	/**
	 * Adds a new black, non-hidden, text block with default font size and
	 * family to the initial element of the XAAL script.
	 * 
	 * @param x
	 *            x coordinate for the top left corner of the text(?)
	 * @param y
	 *            y coordinate for the top left corner of the text(?)
	 * @param contents
	 *            the text string to be displayed.
	 * @return a String containing the id of the text(?) added.
	 */
	public String addText(int x, int y, String contents) {
		return addText(x, y, contents, "black");
	}

	/**
	 * Adds a new non-hidden, text block with default font size and family to
	 * the initial element of the XAAL script.
	 * 
	 * @param x
	 *            x coordinate for the top left corner of the text(?)
	 * @param y
	 *            y coordinate for the top left corner of the text(?)
	 * @param contents
	 *            the text string to be displayed.
	 * @param color
	 *            the color of the text string. Must be a named XAAL color.
	 * @return a String containing the id of the text(?) added.
	 */
	public String addText(int x, int y, String contents, String color) {
		return addText(x, y, contents, color, false);
	}

	/**
	 * Adds a new text block with default font size and family to the initial
	 * element of the XAAL script.
	 * 
	 * @param x
	 *            x coordinate for the top left corner of the text(?)
	 * @param y
	 *            y coordinate for the top left corner of the text(?)
	 * @param contents
	 *            the text string to be displayed.
	 * @param color
	 *            the color of the text string. Must be a named XAAL color.
	 * @param hidden
	 *            whether the text should be initially hidden.
	 * @return a String containing the id of the text(?) added.
	 */
	public String addText(int x, int y, String contents, String color,
			boolean hidden) {
		return addText(x, y, contents, color, hidden, DEFAULT_FONT_SIZE);
	}

	/**
	 * Adds a new text block with default font family to the initial element of
	 * the XAAL script.
	 * 
	 * @param x
	 *            x coordinate for the top left corner of the text(?)
	 * @param y
	 *            y coordinate for the top left corner of the text(?)
	 * @param contents
	 *            the text string to be displayed.
	 * @param color
	 *            the color of the text string. Must be a named XAAL color.
	 * @param hidden
	 *            whether the text should be initially hidden.
	 * @return a String containing the id of the text(?) added.
	 * @param fontSize
	 *            the font size of the text, in points.
	 * @return a String containing the id of the text(?) added.
	 */
	public String addText(int x, int y, String contents, String color,
			boolean hidden, int fontSize) {
		return addText(x, y, contents, color, hidden, fontSize,
				DEFAULT_FONT_FAMILY);
	}

	/**
	 * Adds a new text block to the initial element of the XAAL script.
	 * 
	 * @param x
	 *            x coordinate for the top left corner of the text(?)
	 * @param y
	 *            y coordinate for the top left corner of the text(?)
	 * @param contents
	 *            the text string to be displayed.
	 * @param color
	 *            the color of the text string. Must be a named XAAL color.
	 * @param hidden
	 *            whether the text should be initially hidden.
	 * @return a String containing the id of the text(?) added.
	 * @param fontSize
	 *            the font size of the text, in points.
	 * @param fontFamily
	 *            the name of font family to use when displaying text.
	 * @return a String containing the id of the text(?) added.
	 */
	public String addText(int x, int y, String contents, String color,
			boolean hidden, int fontSize, String fontFamily) {
		Element initial = document.getRootElement().getChild("initial",
				defaultNS);

		Element text = createElement("text");

		String idVal = "text" + textNum;
		textNum++;
		text.setAttribute("id", idVal);

		text.setAttribute("hidden", hidden + "");

		Element coordinate = createElement("coordinate");
		coordinate.setAttribute("x", x + "");
		coordinate.setAttribute("y", y + "");
		text.addContent(coordinate);

		Element contentsElem = createElement("contents");
		contents = contents.replace("\t", "        ");
		Text contentsVal = new Text(contents);
		contentsElem.addContent(contentsVal);

		text.addContent(contentsElem);

		Element style = createElement("style");

		Element colorElem = createElement("color");
		colorElem.setAttribute("name", color);
		style.addContent(colorElem);

		Element font = createElement("font");
		font.setAttribute("size", fontSize + "");
		font.setAttribute("family", fontFamily);
		style.addContent(font);

		text.addContent(style);

		initial.addContent(text);

		return idVal;
	}

	/**
	 * Adds a new non-hidden, solid, black line with the default line width to
	 * the initial element of the XAAL script.
	 * 
	 * @param x1
	 *            x coordinate for first point.
	 * @param y1
	 *            y coordinate for first point.
	 * @param x2
	 *            x coordinate for second point.
	 * @param y2
	 *            y coordinate for second point.
	 * @return a String containing the id of the line added.
	 */
	public String addLine(int x1, int y1, int x2, int y2) {
		return addLine(x1, y1, x2, y2, "black");
	}

	/**
	 * Adds a new non-hidden, solid line with the default line width to the
	 * initial element of the XAAL script.
	 * 
	 * @param x1
	 *            x coordinate for first point.
	 * @param y1
	 *            y coordinate for first point.
	 * @param x2
	 *            x coordinate for second point.
	 * @param y2
	 *            y coordinate for second point.
	 * @param color
	 *            the color of line. Must be a named XAAL color.
	 * @return a String containing the id of the line added.
	 */
	public String addLine(int x1, int y1, int x2, int y2, String color) {
		return addLine(x1, y1, x2, y2, color, false);
	}

	/**
	 * Adds a new solid line with the default line width to the initial element
	 * of the XAAL script.
	 * 
	 * @param x1
	 *            x coordinate for first point.
	 * @param y1
	 *            y coordinate for first point.
	 * @param x2
	 *            x coordinate for second point.
	 * @param y2
	 *            y coordinate for second point.
	 * @param color
	 *            the color of line. Must be a named XAAL color.
	 * @param hidden
	 *            whether the line should be initially hidden.
	 * @return a String containing the id of the line added.
	 */
	public String addLine(int x1, int y1, int x2, int y2, String color,
			boolean hidden) {
		return addLine(x1, y1, x2, y2, color, hidden, StrokeType.solid);
	}

	/**
	 * Adds a new line with the default line width to the initial element of the
	 * XAAL script.
	 * 
	 * @param x1
	 *            x coordinate for first point.
	 * @param y1
	 *            y coordinate for first point.
	 * @param x2
	 *            x coordinate for second point.
	 * @param y2
	 *            y coordinate for second point.
	 * @param color
	 *            the color of line. Must be a named XAAL color.
	 * @param hidden
	 *            whether the line should be initially hidden.
	 * @param strokeType
	 *            whether the line should be solid, dashed or dotted.
	 * @return a String containing the id of the line added.
	 */
	public String addLine(int x1, int y1, int x2, int y2, String color,
			boolean hidden, StrokeType strokeType) {
		return addLine(x1, y1, x2, y2, color, hidden, strokeType,
				DEFAULT_STROKE_WIDTH);
	}

	/**
	 * Adds a new solid line to the initial element of the XAAL script.
	 * 
	 * @param x1
	 *            x coordinate for first point.
	 * @param y1
	 *            y coordinate for first point.
	 * @param x2
	 *            x coordinate for second point.
	 * @param y2
	 *            y coordinate for second point.
	 * @param color
	 *            the color of line. Must be a named XAAL color.
	 * @param hidden
	 *            whether the line should be initially hidden.
	 * @param lineWidth
	 *            the width of the line.
	 * @return a String containing the id of the line added.
	 */
	public String addLine(int x1, int y1, int x2, int y2, String color,
			boolean hidden, int lineWidth) {
		return addLine(x1, y1, x2, y2, color, hidden, StrokeType.solid,
				lineWidth);
	}

	/**
	 * Adds a new line to the initial element of the XAAL script.
	 * 
	 * @param x1
	 *            x coordinate for first point.
	 * @param y1
	 *            y coordinate for first point.
	 * @param x2
	 *            x coordinate for second point.
	 * @param y2
	 *            y coordinate for second point.
	 * @param color
	 *            the color of line. Must be a named XAAL color.
	 * @param hidden
	 *            whether the line should be initially hidden.
	 * @param strokeType
	 *            whether the line should be solid, dashed or dotted.
	 * @param lineWidth
	 *            the width of the line.
	 * @return a String containing the id of the line added.
	 */
	public String addLine(int x1, int y1, int x2, int y2, String color,
			boolean hidden, StrokeType strokeType, int lineWidth) {
		Element initial = document.getRootElement().getChild("initial",
				defaultNS);

		Element line = createElement("line");

		String idVal = "line" + lineNum;
		lineNum++;
		line.setAttribute("id", idVal);

		line.setAttribute("hidden", hidden + "");

		Element coordinate = createElement("coordinate");
		coordinate.setAttribute("x", x1 + "");
		coordinate.setAttribute("y", y1 + "");
		line.addContent(coordinate);

		coordinate = createElement("coordinate");
		coordinate.setAttribute("x", x2 + "");
		coordinate.setAttribute("y", y2 + "");
		line.addContent(coordinate);

		Element style = createElement("style");

		Element colorElem = createElement("color");
		colorElem.setAttribute("name", color);
		style.addContent(colorElem);

		Element stroke = createElement("stroke");
		stroke.setAttribute("type", strokeType.name());
		stroke.setAttribute("width", lineWidth + "");
		style.addContent(stroke);

		line.addContent(style);

		initial.addContent(line);

		return idVal;
	}

	/**
	 * Adds a new non-hidden, black, solid equilateral triangle with the default
	 * line width to the initial element of a XAAL script.
	 * 
	 * @param x
	 *            y coordinate for the top left corner of the rectangular box
	 *            containing the triangle.
	 * @param y
	 *            y coordinate for the top left corner of the rectangular box
	 *            containing the triangle.
	 * @param width
	 *            width (and height) of the triangle in pixels.
	 * @return the String containing the id of the triangle added.
	 */
	public String addTriangle(int x, int y, int width) {
		return addTriangle(x, y, width, "black");
	}

	/**
	 * Adds a new non-hidden, solid equilateral triangle with the default line
	 * width to the initial element of a XAAL script.
	 * 
	 * @param x
	 *            y coordinate for the top left corner of the rectangular box
	 *            containing the triangle.
	 * @param y
	 *            y coordinate for the top left corner of the rectangular box
	 *            containing the triangle.
	 * @param width
	 *            width (and height) of the triangle in pixels.
	 * @param color
	 *            the border color of the triangle
	 * @return the String containing the id of the triangle added.
	 */
	public String addTriangle(int x, int y, int width, String color) {
		return addTriangle(x, y, width, color, false);
	}

	/**
	 * Adds a new equilateral, solid triangle with the default line width to the
	 * initial element of a XAAL script.
	 * 
	 * @param x
	 *            y coordinate for the top left corner of the rectangular box
	 *            containing the triangle.
	 * @param y
	 *            y coordinate for the top left corner of the rectangular box
	 *            containing the triangle.
	 * @param width
	 *            width (and height) of the triangle in pixels.
	 * @param color
	 *            the border color of the triangle.
	 * @param hidden
	 *            whether the triangle is hidden initially.
	 * @return the String containing the id of the triangle added.
	 */
	public String addTriangle(int x, int y, int width, String color,
			boolean hidden) {
		return addTriangle(x, y, width, color, hidden, StrokeType.solid);
	}

	/**
	 * Adds a new equilateral triangle with the default line width to the
	 * initial element of a XAAL script.
	 * 
	 * @param x
	 *            y coordinate for the top left corner of the rectangular box
	 *            containing the triangle.
	 * @param y
	 *            y coordinate for the top left corner of the rectangular box
	 *            containing the triangle.
	 * @param width
	 *            width (and height) of the triangle in pixels.
	 * @param color
	 *            the border color of the triangle.
	 * @param hidden
	 *            whether the triangle is hidden initially.
	 * @param strokeType
	 *            whether the line should be solid, dashed or dotted.
	 * @return the String containing the id of the triangle added.
	 */
	public String addTriangle(int x, int y, int width, String color,
			boolean hidden, StrokeType strokeType) {
		return addTriangle(x, y, width, color, hidden, strokeType,
				DEFAULT_STROKE_WIDTH);
	}

	/**
	 * Adds a new equilateral, solid triangle to the initial element of a XAAL
	 * script.
	 * 
	 * @param x
	 *            y coordinate for the top left corner of the rectangular box
	 *            containing the triangle.
	 * @param y
	 *            y coordinate for the top left corner of the rectangular box
	 *            containing the triangle.
	 * @param width
	 *            width (and height) of the triangle in pixels.
	 * @param color
	 *            the border color of the triangle.
	 * @param hidden
	 *            whether the triangle is hidden initially.
	 * @param lineWidth
	 *            the width of the border line.
	 * @return the String containing the id of the triangle added.
	 */
	public String addTriangle(int x, int y, int width, String color,
			boolean hidden, int lineWidth) {
		return addTriangle(x, y, width, color, hidden, StrokeType.solid,
				lineWidth);
	}

	/**
	 * Adds a new equilateral, unfilled triangle with the default line width to
	 * the initial element of a XAAL script.
	 * 
	 * @param x
	 *            y coordinate for the top left corner of the rectangular box
	 *            containing the triangle.
	 * @param y
	 *            y coordinate for the top left corner of the rectangular box
	 *            containing the triangle.
	 * @param width
	 *            width (and height) of the triangle in pixels.
	 * @param color
	 *            the border color of the triangle.
	 * @param hidden
	 *            whether the triangle is hidden initially.
	 * @param strokeType
	 *            whether the line should be solid, dashed or dotted.
	 * @param lineWidth
	 *            the width of the line.
	 * @return the String containing the id of the triangle added.
	 */
	public String addTriangle(int x, int y, int width, String color,
			boolean hidden, StrokeType strokeType, int lineWidth) {
		return addTriangle(x, y, width, color, hidden, strokeType, lineWidth,
				null);
	}

	/**
	 * Adds a new equilateral triangle with the default line width to the
	 * initial element of a XAAL script.
	 * 
	 * @param x
	 *            y coordinate for the top left corner of the rectangular box
	 *            containing the triangle.
	 * @param y
	 *            y coordinate for the top left corner of the rectangular box
	 *            containing the triangle.
	 * @param width
	 *            width (and height) of the triangle in pixels.
	 * @param color
	 *            the border color of the triangle.
	 * @param hidden
	 *            whether the triangle is hidden initially.
	 * @param strokeType
	 *            whether the line should be solid, dashed or dotted.
	 * @param lineWidth
	 *            the width of the line.
	 * @param fillColor
	 *            the color filling the triangle
	 * @return the String containing the id of the triangle added.
	 */
	public String addTriangle(int x, int y, int width, String color,
			boolean hidden, StrokeType strokeType, int lineWidth,
			String fillColor) {
		Element initial = document.getRootElement().getChild("initial",
				defaultNS);

		Element triangle = createElement("polyline");

		String idVal = "triangle" + triangleNum;
		triangleNum++;
		triangle.setAttribute("id", idVal);

		triangle.setAttribute("hidden", hidden + "");

		// set jhave width EXTENSION
		triangle.setAttribute("shapeWidth", width + "", jhaveNS);

		Element coordinate = createElement("coordinate");
		coordinate.setAttribute("x", (x + (width / 2)) + "");
		coordinate.setAttribute("y", y + "");
		triangle.addContent(coordinate);

		coordinate = createElement("coordinate");
		coordinate.setAttribute("x", (x + width) + "");
		coordinate.setAttribute("y", (y + width) + "");
		triangle.addContent(coordinate);

		coordinate = createElement("coordinate");
		coordinate.setAttribute("x", (x) + "");
		coordinate.setAttribute("y", (y + width) + "");
		triangle.addContent(coordinate);

		coordinate = createElement("coordinate");
		coordinate.setAttribute("x", (x + (width / 2)) + "");
		coordinate.setAttribute("y", y + "");
		triangle.addContent(coordinate);

		Element closed = createElement("closed");
		closed.setAttribute("value", true + "");
		triangle.addContent(closed);

		Element style = createElement("style");

		Element colorElem = createElement("color");
		colorElem.setAttribute("name", color);
		style.addContent(colorElem);

		Element stroke = createElement("stroke");
		stroke.setAttribute("type", strokeType.name());
		stroke.setAttribute("width", lineWidth + "");
		style.addContent(stroke);

		if (fillColor != null) {
			Element fillColorElem = createElement("fill-color");
			fillColorElem.setAttribute("name", fillColor);
			style.addContent(fillColorElem);
		}

		triangle.addContent(style);

		initial.addContent(triangle);

		return idVal;
	}

	/**
	 * Adds a new solid arrow with default line width to the initial element of
	 * the XAAL script.
	 * 
	 * @param originName
	 *            the element the arrow comes from.
	 * @param destName
	 *            the element the arrow goes to.
	 * @param padding
	 *            padding around... something?
	 * @param isDashed
	 *            whether the line is dashed or solid
	 * @param isHidden
	 *            should the arrow be hidden initially.
	 * @return the String containing the id of the arrow added.
	 */
	public String addArrow(String originName, String destName,
			boolean isDashed, boolean isHidden) {
		StrokeType type = StrokeType.solid;

		if (isDashed)
			type = StrokeType.dashed;

		return addArrow(originName, destName, "black", isHidden, type,
				DEFAULT_STROKE_WIDTH);
	}

	/**
	 * Adds a new solid arrow with default line width to the initial element of
	 * the XAAL script.
	 * 
	 * @param originName
	 *            the element the arrow comes from.
	 * @param destName
	 *            the element the arrow goes to.
	 * @param padding
	 *            padding around... something?
	 * @param color
	 *            color of the arrow.
	 * @param hidden
	 *            whether the arrow is hidden initially.
	 * @param strokeType
	 *            whether the line is solid, dashed or dotted.
	 * @param lineWidth
	 *            the width of the line.
	 * @return the String containing the id of the arrow added.
	 */
	public String addArrow(String originName, String destName, String color,
			boolean hidden, StrokeType strokeType, int lineWidth) {
		Element initial = document.getRootElement().getChild("initial",
				defaultNS);

		List elements = initial.getChildren();
		Element origin = null;
		Element dest = null;
		for (Object o : elements) {
			Element e = (Element) o;
			Attribute a = e.getAttribute("id");
			//System.out.println(a);
			if (e.getAttribute("id").getValue().equals(originName)) {
				origin = e;
			} else if (e.getAttribute("id").getValue().equals(destName)) {
				dest = e;
			}
		}
		// find our starting point

		Element startPos = origin.getChild("coordinate", defaultNS);
		Element endPos = dest.getChild("coordinate", defaultNS);
		int startX = 0;
		int startY = 0;
		int endX = 0;
		int endY = 0;
		try {
			startX = startPos.getAttribute("x").getIntValue() +
			    (origin.getAttribute("shapeWidth", jhaveNS).getIntValue() /2);
			startY = startPos.getAttribute("y").getIntValue() +
				(origin.getAttribute("shapeHeight", jhaveNS).getIntValue() /2);
			//System.out.println("X: " + startX + " Y: " + startY);

			// TODO: fix this so that we get the correct vars
			endX = endPos.getAttribute("x").getIntValue()
					+ (dest.getAttribute("shapeWidth", jhaveNS).getIntValue() / 2);
			if (endPos.getAttribute("y").getIntValue() > startY)
				endY = endPos.getAttribute("y").getIntValue();
			else
				endY = endPos.getAttribute("y").getIntValue()
						+ (dest.getAttribute("shapeHeight", jhaveNS)
								.getIntValue());
			//System.out.println("End X: " + endX + " End Y: " + endY);
		} catch (Exception e) {
			System.out.println(e);
		}

		Element arrow = createElement("polyline");

		Element coordinate = createElement("coordinate");
		coordinate.setAttribute("x", startX + "");
		coordinate.setAttribute("y", startY + "");
		arrow.addContent(coordinate);

		coordinate = createElement("coordinate");
		coordinate.setAttribute("x", endX + "");
		coordinate.setAttribute("y", endY + "");
		arrow.addContent(coordinate);

		/*
		 * coordinate = new Element("coordinate"); coordinate.setAttribute("x",
		 * (endX -10) + ""); coordinate.setAttribute("y", (endY +10) + "");
		 * 
		 * arrow.addContent(coordinate);
		 * 
		 * coordinate = createElement("coordinate");
		 * coordinate.setAttribute("x", endX + ""); coordinate.setAttribute("y",
		 * endY + ""); arrow.addContent(coordinate);
		 * 
		 * 
		 * coordinate = new Element("coordinate"); coordinate.setAttribute("x",
		 * (endX +10) + ""); coordinate.setAttribute("y", (endY +10) + "");
		 * 
		 * arrow.addContent(coordinate);
		 */
		arrow.setAttribute("hidden", hidden + "");

		Element style = createElement("style");

		Element colorElem = createElement("color");
		colorElem.setAttribute("name", color);
		style.addContent(colorElem);

		Element stroke = createElement("stroke");
		stroke.setAttribute("type", strokeType.name());
		stroke.setAttribute("width", lineWidth + "");
		style.addContent(stroke);

		arrow.addContent(style);

		String idVal = "arrow" + arrowNum++;
		arrow.setAttribute("id", idVal);

		initial.addContent(arrow);

		return idVal;
	}

	/**
	 * Begins a slide for the animation. Corresponds to the seq element.
	 * 
	 * @throws SlideException
	 *             a slide's already been started.
	 * @returns the id number of the current slide.
	 */
	public int startSlide() throws SlideException {
		slideNum++;

		if (inSlide())
			throw new SlideException("A slide has already been started. "
					+ "It must be ended before you can create another.");

		currentSlide = createElement("seq");

		return slideNum;
	}

	/**
	 * Closes the current open slide and inserts it in the animation section of
	 * the XAAL script.
	 * 
	 * @throws SlideException
	 *             No slide has been started yet
	 */
	public void endSlide() throws SlideException {
		if (!inSlide())
			throw new SlideException("No slide has been started yet.");

		Element animation = document.getRootElement().getChild("animation",
				defaultNS);

		animation.addContent(currentSlide);

		currentSlide = null;

	}

	/**
	 * Allows you to reopen a slide after its been created. Remember slide
	 * numbers start at 1 NOT 0.
	 * 
	 * @param slideId
	 *            the sequential number of the slide.
	 */
	public void reopenSlide(int slideId) {
		Element animation = document.getRootElement().getChild("animation",
				defaultNS);
		List slides = animation.getChildren();

		currentSlide = (Element) slides.get(slideId - 1);
	}

	/**
	 * Used to reclose a slide that had been opened using
	 * <code>reopenSlide</code>
	 */
	public void recloseSlide() {
		currentSlide = null;
	}

	/**
	 * Reopens the first par inside the current slide.
	 */
	public void reopenPar() {
		Element par = currentSlide.getChild("par", defaultNS);

		currentPar = par;
	}

	/**
	 * Reopens the par at <code>parIndex</code> inside the current slide.
	 * 
	 * @param parIndex
	 *            the zero-based index of the wanted par inside the current
	 *            slide
	 * @return true if succeeded, false if the index doesn't exist.
	 */
	public boolean reopenPar(int parIndex) {
		List pars = currentSlide.getChildren("par", defaultNS);
		if (pars.size() - 1 < parIndex) {
			return false;
		}

		currentPar = (Element) pars.get(parIndex);
		return true;
	}

	/**
	 * Reopens the last par inside the current slide.
	 * 
	 * @return true if succeeded, false if there are no pars.
	 */
	public boolean reopenLastPar() {
		List pars = currentSlide.getChildren("par", defaultNS);
		if (pars.size() == 0)
			return false;

		currentPar = (Element) pars.get(pars.size() - 1);
		return true;
	}

	/**
	 * Recloses a par after its been opened using either <code>reopenPar</code>
	 * or <code>reopenLastPar</code>.
	 */
	public void reclosePar() {
		currentPar = null;
	}

	/**
	 * Tells us if a slide has been started but not ended.
	 * 
	 * @return whether a slide is started.
	 */
	public boolean inSlide() {
		return currentSlide != null;
	}

	/**
	 * Begins a section that runs multiple changes in parallel. Corresponds to
	 * the par element in XAAL.
	 * 
	 * @throws SlideException
	 *             No slide is open.
	 * @throws ParException
	 *             Parallel section has already been started.
	 */
	public void startPar() throws SlideException, ParException {
		if (!inSlide())
			throw new SlideException(
					"No slide is open. Parallel sections can only be "
							+ "added to open slides");

		if (inPar())
			throw new ParException(
					"Parallel section has already been started. "
							+ "It must be ended before you can create another.");

		currentPar = createElement("par");
	}

	/**
	 * Closes a parallel section and writes it to the animation section of the
	 * XAAL script.
	 * 
	 * @throws ParException
	 *             No parallel section is open.
	 */
	public void endPar() throws ParException {
		if (!inPar())
			throw new ParException("No parallel section is open.");

		currentSlide.addContent(currentPar);

		currentPar = null;
	}

	/**
	 * Tells us if a parallel section has been started but not ended.
	 * 
	 * @return whether a parallel section is started.
	 */
	public boolean inPar() {
		return currentPar != null;
	}

	/**
	 * Adds a translate action to the open parallel section or creates one if
	 * necessary.
	 * 
	 * @param x
	 *            the number pixels the objects should move right. Use negative
	 *            if you want to move left.
	 * @param y
	 *            the number pixels the objects should move down. Use negative
	 *            if you want to move up.
	 * @param ids
	 *            a variable number of Strings containing the ids of objects to
	 *            be moved.
	 * @throws SlideException
	 *             No slide open.
	 */
	public void addTranslate(int x, int y, String... ids) throws SlideException {
		if (!inSlide())
			throw new SlideException(
					"You must create a slide before creating actions.");

		boolean closeParAtEnd = false;

		if (!inPar()) {
			try {
				startPar();
			} catch (ParException e) {

				e.printStackTrace();
			}
			closeParAtEnd = true;
		}

		Element parent = currentPar;

		Element move = createElement("move");
		move.setAttribute("type", "translate");

		for (String id : ids) {
			Element objRef = createElement("object-ref");
			objRef.setAttribute("id", id);
			move.addContent(objRef);
		}

		Element coordinate = createElement("coordinate");
		coordinate.setAttribute("x", x + "");
		coordinate.setAttribute("y", y + "");
		move.addContent(coordinate);

		parent.addContent(move);

		if (closeParAtEnd)
			try {
				endPar();
			} catch (ParException e) {

				e.printStackTrace();
			}
	}

	/**
	 * Adds a show action to the open parallel section or creates one if
	 * necessary.
	 * 
	 * @param ids
	 *            a variable number of Strings containing the ids of objects to
	 *            be shown.
	 * @throws SlideException
	 *             No slide open.
	 */
	public void addShow(String... ids) throws SlideException {
		if (!inSlide())
			throw new SlideException(
					"You must create a slide before creating actions.");

		boolean closeParAtEnd = false;

		if (!inPar()) {
			try {
				startPar();
			} catch (ParException e) {

				e.printStackTrace();
			}
			closeParAtEnd = true;
		}

		Element parent = currentPar;

		Element show = createElement("show");
		show.setAttribute("type", "selected");

		for (String id : ids) {
			Element objRef = createElement("object-ref");
			objRef.setAttribute("id", id);
			show.addContent(objRef);
		}

		parent.addContent(show);

		if (closeParAtEnd)
			try {
				endPar();
			} catch (ParException e) {

				e.printStackTrace();
			}
	}

	/**
	 * Adds a hide action to the open parallel section or creates one if
	 * necessary.
	 * 
	 * @param ids
	 *            a variable number of Strings containing the ids of objects to
	 *            be hidden.
	 * @throws SlideException
	 *             No slide open.
	 */
	public void addHide(String... ids) throws SlideException {
		//System.out.println("Adding hide for " + ids[0]);
		if (!inSlide())
			throw new SlideException(
					"You must create a slide before creating actions.");

		boolean closeParAtEnd = false;

		if (!inPar()) {
			try {
				startPar();
			} catch (ParException e) {

				e.printStackTrace();
			}
			closeParAtEnd = true;
		}

		Element parent = currentPar;

		Element hide = createElement("hide");
		hide.setAttribute("type", "selected");

		for (String id : ids) {
			Element objRef = createElement("object-ref");
			objRef.setAttribute("id", id);
			hide.addContent(objRef);
		}

		parent.addContent(hide);

		if (closeParAtEnd)
			try {
				endPar();
			} catch (ParException e) {

				e.printStackTrace();
			}
	}

	/**
	 * Adds a change-style action to the open parallel section or creates one if
	 * necessary.
	 * 
	 * @param color
	 *            the new color of the objects being changed.
	 * @param ids
	 *            a number of Strings containing the ids of the objects to be
	 *            modified.
	 * @throws SlideException
	 *             No slide open.
	 */
	public void addChangeStyle(String color, String... ids)
			throws SlideException 
	{
		addChangeStyle(color, false, ids);
	}
	
	public void addChangeStyle(String color,  boolean isFillColor, String... ids)
	throws SlideException 
	{
		if (!inSlide())
			throw new SlideException(
					"You must create a slide before creating actions.");
		
		boolean closeParAtEnd = false;
		
		if (!inPar()) {
			try {
				startPar();
			} catch (ParException e) {
		
				e.printStackTrace();
			}
			closeParAtEnd = true;
		}
		
		Element parent = currentPar;
		
		Element changeStyle = createElement("change-style");
		
		for (String id : ids) {
			Element objRef = createElement("object-ref");
			objRef.setAttribute("id", id);
			changeStyle.addContent(objRef);
		}
		
		Element style = createElement("style");
		
		Element colorElem = null;
		
		if (isFillColor)
			colorElem = createElement("fill-color");
		else
			colorElem = createElement("color");
		
		colorElem.setAttribute("name", color);
		
		style.addContent(colorElem);
		
		changeStyle.addContent(style);
		
		parent.addContent(changeStyle);
		
		if (closeParAtEnd)
			try {
				endPar();
			} catch (ParException e) {
		
				e.printStackTrace();
			}
	}
	
	public void addChangeStyle(StrokeType type, int strokeWidth, String...ids) throws SlideException 
	{
	
		if (!inSlide())
			throw new SlideException(
					"You must create a slide before creating actions.");

		boolean closeParAtEnd = false;

		if (!inPar()) {
			try {
				startPar();
			} catch (ParException e) {

				e.printStackTrace();
			}
			closeParAtEnd = true;
		}
		
		Element parent = currentPar;

		Element changeStyle = createElement("change-style");

		for (String id : ids) {
			Element objRef = createElement("object-ref");
			objRef.setAttribute("id", id);
			changeStyle.addContent(objRef);
		}

		Element style = createElement("style");
		
		Element stroke = createElement("stroke");
		stroke.setAttribute("type", type.toString());
		stroke.setAttribute("width", strokeWidth + "");
		
		style.addContent(stroke);
		
		changeStyle.addContent(style);
	
		parent.addContent(changeStyle);
		
		if (closeParAtEnd)
			try {
				endPar();
			} catch (ParException e) {

				e.printStackTrace();
			}
	}

	/**
	 * Adds a move for no object. Used for current pause hack. Move not
	 * implemented.
	 * 
	 * @param x
	 *            x coordinate of point to move objects to. Currently the number
	 *            of ms to pause.
	 * @param y
	 *            y coordinate of point to move objects to. Must be 0 for pause
	 *            to work.
	 * @throws SlideException
	 *             No slide open.
	 */
	private void addMove(int x, int y) throws SlideException {
		addMove(x, y, new String[0]);
	}

	/**
	 * Adds a move for no object. Used for current pause hack. Move not
	 * implemented.
	 * 
	 * @param x
	 *            x coordinate of point to move objects to. Currently the number
	 *            of ms to pause.
	 * @param y
	 *            y coordinate of point to move objects to. Must be 0 for pause
	 *            to work.
	 * @param ids
	 *            list of Strings containing the ids of the objects to move.
	 *            Make it empty for pause to work
	 * @throws SlideException
	 *             No slide open.
	 */
	private void addMove(int x, int y, String... ids) throws SlideException {
		if (!inSlide())
			throw new SlideException(
					"You must create a slide before creating actions.");

		boolean closeParAtEnd = false;

		if (!inPar()) {
			try {
				startPar();
			} catch (ParException e) {

				e.printStackTrace();
			}
			closeParAtEnd = true;
		}

		Element parent = currentPar;

		Element move = createElement("move");
		move.setAttribute("type", "move");

		for (String id : ids) {
			Element objRef = createElement("object-ref");
			objRef.setAttribute("id", id);
			move.addContent(objRef);
		}

		Element coordinate = createElement("coordinate");
		coordinate.setAttribute("x", x + "");
		coordinate.setAttribute("y", y + "");
		move.addContent(coordinate);

		parent.addContent(move);

		if (closeParAtEnd)
			try {
				endPar();
			} catch (ParException e) {

				e.printStackTrace();
			}
	}

	/**
	 * Adds a pause to the open parallel section or creates one if necessary.
	 * 
	 * @param ms
	 *            how long to pause in milliseconds.
	 * @throws SlideException
	 *             No slide open.
	 */
	public void addPause(int ms) throws SlideException {
		addMove(ms, 0);
	}

	/**
	 * Adds narrative to the current slide. Currently is used for pseudocode
	 * url.
	 * 
	 * @param text
	 *            the narrative for the slide. Currently is just pseudocode url.
	 * @throws SlideException
	 *             No slide open.
	 */
	private void addNarrative(String text) throws SlideException {
		if (!inSlide())
			throw new SlideException(
					"You must create a slide before creating actions.");

		Element narrative = createElement("narrative");

		narrative.setText(text);

		currentSlide.addContent(narrative);
	}

	/**
	 * Adds a pseudocode URL to the current slide
	 * 
	 * @param url
	 *            the URL of the page to be displayed in the pseudocode pane.
	 * @throws SlideException
	 *             No slide open.
	 */
	public void addPseudocodeUrl(String url) throws SlideException {
		addNarrative(url);
	}

	/**
	 * Adds a TF Question to the current slide.
	 * 
	 * @param question
	 *            The text of the question.
	 * @param answer
	 *            self-explanatory.
	 * @throws SlideException
	 *             No slide open.
	 */
	public void addTFQuestion(String question, boolean answer)
			throws SlideException {
		addTFQuestion(question, slideNum, answer);
	}

	/**
	 * 
	 * @param question
	 *            the text of the question.
	 * @param slideId
	 *            the id of the slide the question will be asked on.
	 * @param answer
	 *            self-explanatory.
	 * @throws SlideException
	 *             No slide exists for <code>slideId</code>.
	 */
	public void addTFQuestion(String question, int slideId, boolean answer)
			throws SlideException {
		Element questions = questionsElem();

		Element q = createQuestion("TFQUESTION", slideId, question);

		Element ao = new Element("answer_option", jhaveNS);

		ao.setAttribute("is_correct", "yes");

		ao.setText(answer + "");

		q.addContent(ao);

		questions.addContent(q);

	}

	/**
	 * Adds a fill-in-the-blank question to the current slide.
	 * 
	 * @param question
	 *            the text of the question.
	 * @param answers
	 *            a String array containing all possible answers.
	 * @throws SlideException
	 *             no slide open.
	 */
	public void addFibQuestion(String question, String... answers)
			throws SlideException {
		addFibQuestion(question, slideNum, answers);
	}

	/**
	 * 
	 * @param question
	 *            the text of the question.
	 * @param slideId
	 *            the id of the slide the question will be asked on.
	 * @param answers
	 *            a String array containing all possible answers.
	 * @throws SlideException
	 *             no slide with <code>slideId</code> exists.
	 */
	public void addFibQuestion(String question, int slideId, String... answers)
			throws SlideException {
		Element questionsElem = questionsElem();

		Element q = createQuestion("FIBQUESTION", slideId, question);

		Element aElem;
		for (String answer : answers) {
			aElem = new Element("answer_option", jhaveNS);

			aElem.setText(answer);

			q.addContent(aElem);
		}

		questionsElem.addContent(q);

	}

	/**
	 * Adds a multiple choice, single selection question to the current slide
	 * 
	 * @param question
	 *            the text of the question.
	 * @param choices
	 *            a String array containing the choices.
	 * @param answer
	 *            index of the element in <code>choices</code> that is correct.
	 * @throws SlideException
	 *             no slide open.
	 */
	public void addMCQuestion(String question, String[] choices, int answer)
			throws SlideException {
		addMCQuestion(question, slideNum, choices, answer);
	}

	/**
	 * 
	 * @param question
	 *            the text of the question.
	 * @param slideId
	 *            the id of the slide the question will be asked on.
	 * @param choices
	 *            a String array containing the choices.
	 * @param answer
	 *            index of the element in <code>choices</code> that is correct.
	 * @throws SlideException
	 *             no slide with <code>slideId</code> exists.
	 */
	public void addMCQuestion(String question, int slideId, String[] choices,
			int answer) throws SlideException {
		Element questionsElem = questionsElem();

		Element q = createQuestion("MCQUESTION", slideId, question);

		Element aElem;
		for (int i = 0; i < choices.length; i++) {
			aElem = new Element("answer_option", jhaveNS);

			if (i == answer)
				aElem.setAttribute("is_correct", "yes");
			else
				aElem.setAttribute("is_correct", "no");

			aElem.setText(choices[i]);

			q.addContent(aElem);
		}

		questionsElem.addContent(q);
	}

	/**
	 * Adds a multiple choice, multiple selection question to the current slide
	 * 
	 * @param question
	 *            the text of the question.
	 * @param choices
	 *            a String array containing the choices.
	 * @param answers
	 *            an int array containing the indices of the correct values in
	 *            <code>choices</code>
	 * @throws SlideException
	 *             no slide open.
	 */
	public void addMSQuestion(String question, String[] choices, int... answers)
			throws SlideException {
		addMSQuestion(question, slideNum, choices, answers);
	}

	/**
	 * 
	 * @param question
	 *            the text of the question.
	 * @param slideId
	 *            the id of the slide the question will be asked on.
	 * @param choices
	 *            a String array containing the choices.
	 * @param answers
	 *            an int array containing the indices of the correct values in
	 *            <code>choices</code>
	 * @throws SlideException
	 *             no slide with <code>slideId</code> exists.
	 */
	public void addMSQuestion(String question, int slideId, String[] choices,
			int... answers) throws SlideException {
		Element questionsElem = questionsElem();

		Element q = createQuestion("MSQUESTION", slideId, question);

		Element aElem;
		for (int i = 0; i < choices.length; i++) {
			aElem = new Element("answer_option", jhaveNS);

			boolean found = false;

			for (int j = 0; j < answers.length; j++) {
				if (answers[j] == i) {
					found = true;
					break;
				}
			}

			if (found)
				aElem.setAttribute("is_correct", "yes");
			else
				aElem.setAttribute("is_correct", "no");

			aElem.setText(choices[i]);

			q.addContent(aElem);
		}

		questionsElem.addContent(q);
	}

	/**
	 * 
	 * @param type
	 *            the type of question to create.
	 * @param slideId
	 *            the id of the slide the question will be asked on.
	 * @param question
	 *            the text of the question.
	 * @return a new question element with <code>type</code> and
	 *         <code>question</code>.
	 */
	private Element createQuestion(String type, int slideId, String question) {
		Element qElem = new Element("question", jhaveNS);

		qElem.setAttribute("type", type);
		qElem.setAttribute("id", slideId + "");

		Element text = new Element("question_text", jhaveNS);
		text.setText(question);

		qElem.addContent(text);

		return qElem;
	}

	/**
	 * Helper method to get the questions element.
	 * 
	 * @return the questions element.
	 */
	private Element questionsElem() {
		return document.getRootElement().getChild("questions", jhaveNS);
	}

	/**
	 * Creates an element in the defaultNS
	 * 
	 * @param name
	 *            the name of the element
	 * @return an Element with the specified name and belonging to defaultNS
	 */
	private Element createElement(String name) {
		return new Element(name, defaultNS);
	}

	/**
	 * Outputs the XAAL script as a string.
	 * 
	 * @return a String containing the XML XAAL script.
	 */
	public String toString() {
		runCodeCleanup();

		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		return outputter.outputString(document);
	}

	/**
	 * removes some code that either isn't needed or screws something up
	 */
	private void runCodeCleanup() {
		removeEmptyPars();
		fixDualColorChanges();
	}

	private void removeEmptyPars() {
	}

	/**
	 * Occasionally code is created that highlighting is turned off for the
	 * previous slide but then turned back on for the current slide at the same
	 * time. This causes highlighting to not work as expected. This method fixes
	 * this.
	 */
	private void fixDualColorChanges() {
		/*
		 * try
		 * 
		 * { XPath xp =
		 * XPath.newInstance("/xaal:xaal/xaal:animation/xaal:seq/xaal:par");
		 * XPath cSXp = XPath.newInstance("xaal:change-style"); XPath oRefXp =
		 * XPath.newInstance("xaal:object-ref"); xp.addNamespace("xaal",
		 * defaultNS.getURI()); cSXp.addNamespace("xaal", defaultNS.getURI());
		 * oRefXp.addNamespace("xaal", defaultNS.getURI());
		 * 
		 * List nodes = xp.selectNodes(document.getRootElement()); for (Object
		 * nodeObj : nodes) { Element node = (Element)nodeObj;
		 * 
		 * ListcSXp.selectNodes(node); } } catch(JDOMException e) {
		 * 
		 * }
		 */
	}
}
