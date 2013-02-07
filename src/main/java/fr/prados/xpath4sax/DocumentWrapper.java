package fr.prados.xpath4sax;

import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.w3c.dom.UserDataHandler;

/**
 * Partial wrapper to Document node.
 * @version 1.0
 * @since 1.0
 * @author Philippe PRADOS
 */
public class DocumentWrapper implements Document
{

	public Node adoptNode(Node source) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public Attr createAttribute(String name) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public Attr createAttributeNS(String namespaceURI, String qualifiedName)
			throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public CDATASection createCDATASection(String data) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public Comment createComment(String data)
	{
		throw new NoSuchMethodError();
	}

	public DocumentFragment createDocumentFragment()
	{
		throw new NoSuchMethodError();
	}

	public Element createElement(String tagName) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public Element createElementNS(String namespaceURI, String qualifiedName)
			throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public EntityReference createEntityReference(String name)
			throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public ProcessingInstruction createProcessingInstruction(String target,
			String data) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public Text createTextNode(String data)
	{
		throw new NoSuchMethodError();
	}

	public DocumentType getDoctype()
	{
		throw new NoSuchMethodError();
	}

	public Element getDocumentElement()
	{
		throw new NoSuchMethodError();
	}

	public String getDocumentURI()
	{
		throw new NoSuchMethodError();
	}

	public DOMConfiguration getDomConfig()
	{
		throw new NoSuchMethodError();
	}

	public Element getElementById(String elementId)
	{
		throw new NoSuchMethodError();
	}

	public NodeList getElementsByTagName(String tagname)
	{
		throw new NoSuchMethodError();
	}

	public NodeList getElementsByTagNameNS(String namespaceURI, String localName)
	{
		throw new NoSuchMethodError();
	}

	public DOMImplementation getImplementation()
	{
		throw new NoSuchMethodError();
	}

	public String getInputEncoding()
	{
		throw new NoSuchMethodError();
	}

	public boolean getStrictErrorChecking()
	{
		throw new NoSuchMethodError();
	}

	public String getXmlEncoding()
	{
		throw new NoSuchMethodError();
	}

	public boolean getXmlStandalone()
	{
		throw new NoSuchMethodError();
	}

	public String getXmlVersion()
	{
		throw new NoSuchMethodError();
	}

	public Node importNode(Node importedNode, boolean deep) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public void normalizeDocument()
	{
	}

	public Node renameNode(Node n, String namespaceURI, String qualifiedName)
			throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public void setDocumentURI(String documentURI)
	{
		throw new NoSuchMethodError();
	}

	public void setStrictErrorChecking(boolean strictErrorChecking)
	{
		throw new NoSuchMethodError();
	}

	public void setXmlStandalone(boolean xmlStandalone) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public void setXmlVersion(String xmlVersion) throws DOMException
	{
		throw new NoSuchMethodError();
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

	public Object getFeature(String feature, String version)
	{
		throw new NoSuchMethodError();
	}

	public Node getFirstChild()
	{
		throw new NoSuchMethodError();
	}

	public Node getLastChild()
	{
		throw new NoSuchMethodError();
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
		throw new NoSuchMethodError();
	}

	public String getNodeName()
	{
		throw new NoSuchMethodError();
	}

	public short getNodeType()
	{
		return Node.DOCUMENT_NODE;
	}

	public String getNodeValue() throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public Document getOwnerDocument()
	{
		return this;
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
		throw new NoSuchMethodError();
	}

	public String getTextContent() throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public Object getUserData(String key)
	{
		throw new NoSuchMethodError();
	}

	public boolean hasAttributes()
	{
		throw new NoSuchMethodError();
	}

	public boolean hasChildNodes()
	{
		throw new NoSuchMethodError();
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
		throw new NoSuchMethodError();
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
	public String toString()
	{
		return "[#document: null]";
	}
}
