package jxaal;

import org.jdom.Element;
/**
 * Interface for XAAL elements that can be serialized to text
 * @author Eric
 *
 */
public interface XaalSerializable {
	/**
	 * Serializes the XAAL element as part of the JDOM tree
	 * @param parent the JDOM parent of this element once serialized
	 * @return not sure?
	 */
	String xaalSerialize(Element parent);
}
