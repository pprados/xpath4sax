package fr.prados.xpath4sax;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.UserDataHandler;

/**
 * Partial wrapper to Text node.
 * @version 1.0
 * @since 1.0
 * @author Philippe PRADOS
 */
public class TextWrapper implements Text
{
	String value;
	
	TextWrapper(String value)
	{
		this.value=value;
	}
	
	public String toString()
	{
		return "[#text: "+value+"]";
	}
	public String getWholeText()
	{
		return value;
	}

	public boolean isElementContentWhitespace()
	{
		return value.indexOf(' ')>=0;
	}

	public Text replaceWholeText(String content) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public Text splitText(int offset) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public void appendData(String arg) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public void deleteData(int offset, int count) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public String getData() throws DOMException
	{
		return value;
	}

	public int getLength()
	{
		return value.length();
	}

	public void insertData(int offset, String arg) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public void replaceData(int offset, int count, String arg)
			throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public void setData(String data) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public String substringData(int offset, int count) throws DOMException
	{
		return value.substring(offset,count);
	}

	public Node appendChild(Node newChild) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public Node cloneNode(boolean deep)
	{
		throw new NoSuchMethodError();
	}

	public short compareDocumentPosition(Node other) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public NamedNodeMap getAttributes()
	{
		return null;
	}

	public String getBaseURI()
	{
		throw new NoSuchMethodError();
	}

	public NodeList getChildNodes()
	{
		return null;
	}

	public Object getFeature(String feature, String version)
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
		return null;
	}

	public short getNodeType()
	{
		return Node.TEXT_NODE;
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

	public Object getUserData(String key)
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

	public Node insertBefore(Node newChild, Node refChild) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public boolean isDefaultNamespace(String namespaceURI)
	{
		throw new NoSuchMethodError();
	}

	public boolean isEqualNode(Node arg)
	{
		throw new NoSuchMethodError();
	}

	public boolean isSameNode(Node other)
	{
		throw new NoSuchMethodError();
	}

	public boolean isSupported(String feature, String version)
	{
		throw new NoSuchMethodError();
	}

	public String lookupNamespaceURI(String prefix)
	{
		throw new NoSuchMethodError();
	}

	public String lookupPrefix(String namespaceURI)
	{
		throw new NoSuchMethodError();
	}

	public void normalize()
	{
	}

	public Node removeChild(Node oldChild) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public Node replaceChild(Node newChild, Node oldChild) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public void setNodeValue(String nodeValue) throws DOMException
	{
		value=nodeValue;
	}

	public void setPrefix(String prefix) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public void setTextContent(String textContent) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public Object setUserData(String key, Object data, UserDataHandler handler)
	{
		throw new NoSuchMethodError();
	}

}
