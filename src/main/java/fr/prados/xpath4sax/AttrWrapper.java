package fr.prados.xpath4sax;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;

/**
 * Partial wrapper to Attribute node.
 * @version 1.0
 * @since 1.0
 * @author Philippe PRADOS
 */
public class AttrWrapper implements Attr
{
	private String name;
	private String value;
	
	AttrWrapper(String name,String value)
	{
		this.name=name;
		this.value=value;
	}
	
	public String toString()
	{
		return name+"=\""+value+"\"";
	}
	public String getName()
	{
		return name;
	}

	public Element getOwnerElement()
	{
		return null;
	}

	public TypeInfo getSchemaTypeInfo()
	{
		return null;
	}

	public boolean getSpecified()
	{
		return false;
	}

	public String getValue()
	{
		return value;
	}

	public boolean isId()
	{
		return false;
	}

	public void setValue(String arg0) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public Node appendChild(Node arg0) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public Node cloneNode(boolean arg0)
	{
		throw new NoSuchMethodError();
	}

	public short compareDocumentPosition(Node arg0) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public NamedNodeMap getAttributes()
	{
		throw new NoSuchMethodError();
	}

	public String getBaseURI()
	{
		throw new NoSuchMethodError();
	}

	public NodeList getChildNodes()
	{
		throw new NoSuchMethodError();
	}

	public Object getFeature(String arg0, String arg1)
	{
		throw new NoSuchMethodError();
	}

	public Node getFirstChild()
	{
		return null;
	}

	public Node getLastChild()
	{
		return null;
	}

	public String getLocalName()
	{
		throw new NoSuchMethodError();
	}

	public String getNamespaceURI()
	{
		throw new NoSuchMethodError();
	}

	public Node getNextSibling()
	{
		return null;
	}

	public String getNodeName()
	{
		return name;
	}

	public short getNodeType()
	{
		return Node.ATTRIBUTE_NODE;
	}

	public String getNodeValue() throws DOMException
	{
		return value;
	}

	public Document getOwnerDocument()
	{
		return null;
	}

	public Node getParentNode()
	{
		return null;
	}

	public String getPrefix()
	{
		throw new NoSuchMethodError();
	}

	public Node getPreviousSibling()
	{
		return null;
	}

	public String getTextContent() throws DOMException
	{
		return value;
	}

	public Object getUserData(String arg0)
	{
		throw new NoSuchMethodError();
	}

	public boolean hasAttributes()
	{
		return false;
	}

	public boolean hasChildNodes()
	{
		return false;
	}

	public Node insertBefore(Node arg0, Node arg1) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public boolean isDefaultNamespace(String arg0)
	{
		throw new NoSuchMethodError();
	}

	public boolean isEqualNode(Node arg0)
	{
		throw new NoSuchMethodError();
	}

	public boolean isSameNode(Node arg0)
	{
		throw new NoSuchMethodError();
	}

	public boolean isSupported(String arg0, String arg1)
	{
		throw new NoSuchMethodError();
	}

	public String lookupNamespaceURI(String arg0)
	{
		throw new NoSuchMethodError();
	}

	public String lookupPrefix(String arg0)
	{
		throw new NoSuchMethodError();
	}

	public void normalize()
	{
	}

	public Node removeChild(Node arg0) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public Node replaceChild(Node arg0, Node arg1) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public void setNodeValue(String arg0) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public void setPrefix(String arg0) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public void setTextContent(String arg0) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public Object setUserData(String arg0, Object arg1, UserDataHandler arg2)
	{
		throw new NoSuchMethodError();
	}

}
