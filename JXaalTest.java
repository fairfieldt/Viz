
import java.io.FileWriter;
import java.io.IOException;

import jxaal.*;
import jxaal.enums.*;
import jxaal.jhave.*;


public class JXaalTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		XaalDoc doc = new XaalDoc();
	
		{
			Line l = new Line(doc, null, null);
			l.coordinates.add(new Coordinate(doc, 0, 0));
			l.coordinates.add(new Coordinate(doc, 50, 50));
			
			l.style.color = new NamedColor("red");
			doc.initials.add(l);
		}
		
			Polyline l = new Polyline(doc, null, null);
			doc.initials.add(l);
			l.coordinates.add(new Coordinate(doc, 200, 200));
			l.coordinates.add(new Coordinate(doc, 300, 200));
			l.coordinates.add(new Coordinate(doc, 300, 300));
			l.coordinates.add(new Coordinate(doc, 200, 300));
			l.coordinates.add(new Coordinate(doc, 200, 200));
			
			l.closed = true;
			l.hidden = true;
			l.style.color = new RGBColor(255, 0, 255);
			l.style.setFillColor(new NamedColor(ColorName.GREEN.toString()));
			l.style.setStrokeWidth(3);
			l.style.setStrokeType(StrokeType.DASHED);
		
		
		{
			Seq s = new Seq(doc);
			
			
			Par p = new Par(doc);
			s.pars.add(p);
			p.show.add(l);
		}
		
		{
			Text t = new Text(doc, null, null);
			t.contents = "home";
			doc.initials.add(t);
			t.coordinates.add(new Coordinate(doc, 15, 60));
			t.style.setFontSize(80);
			
			Seq s = new Seq(doc);
			
			Par p = new Par(doc);
			s.pars.add(p);
			p.cs.add(new ChangeStyle(doc, null, null));
			p.cs.get(0).modifiedShapes.add(t);
			p.cs.get(0).newStyle = new Style(doc, null);
			p.cs.get(0).newStyle.setFontSize(16);
			
			
		}
		
		
		FileWriter writer;
		try {
			writer = new FileWriter("C:\\Users\\Eric\\Desktop\\test.xaal");

			writer.write(doc.xaalSerialize(null));

			writer.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
