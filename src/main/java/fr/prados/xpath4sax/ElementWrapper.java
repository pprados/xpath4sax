package fr.prados.xpath4sax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;
import org.xml.sax.Attributes;

/**
 * Partial wrapper to Element node.
 * @version 1.0
 * @since 1.0
 * @author Philippe PRADOS
 */
public class ElementWrapper implements Element
{
	static class NodeListWrapper implements NodeList
	{
		ArrayList<Text> list=new ArrayList<Text>(2);
		public int getLength()
		{
			return list.size();
		}

		public Node item(int location)
		{
			return list.get(location);
		}

	}

	String name;
	NamedNodeMap attributes;
	NodeListWrapper childNodes=new NodeListWrapper(); // For all childrens text() nodes
	// Node level 3
	// NodeListWrapper textsNodes=new NodeListWrapper(); // For all text() nodes, childrens and sub-childrens
	
	ElementWrapper(String name,Attributes attributes)
	{
		this.name=name;
		this.attributes=new NamedNodeMap()
		{
			HashMap<String,Node> map=new HashMap<String,Node>();
			List<Node> list=new ArrayList<Node>();

			public int getLength()
			{
				return map.size();
			}

			public Node getNamedItem(String name)
			{
				return map.get(name);
			}

			public Node getNamedItemNS(String u, String name)
			{
				return map.get(name);
			}

			public Node item(int pos)
			{
				return list.get(pos);
			}

			public Node removeNamedItem(String arg0) throws DOMException
			{
				throw new NoSuchMethodError();
			}

			public Node removeNamedItemNS(String arg0, String arg1)
					throws DOMException
			{
				throw new NoSuchMethodError();
			}

			public Node setNamedItem(Node node) throws DOMException
			{
				map.put(node.getNodeName(),node);
				list.add(node);
				return node;
			}

			public Node setNamedItemNS(Node node) throws DOMException
			{
				map.put(node.getNodeName(),node);
				list.add(node);
				return node;
			}
			
		};
		for (int i=0;i<attributes.getLength();++i)
			this.attributes.setNamedItem(new AttrWrapper(attributes.getLocalName(i), attributes.getValue(i)));
	}
	public String toString()
	{
		StringBuilder buf=new StringBuilder();
		buf.append("<").append(name);
		for (int i=0;i<attributes.getLength();++i)
		{
			Attr attr=(Attr)attributes.item(i);
			buf
				.append(' ')
				.append(attr.getNodeName())
				.append("='")
				.append(attr.getNodeValue())
				.append('\'');
		}
		NodeList childs=getChildNodes();
		if ((childs!=null) && childs.getLength()!=0)
		{
			buf.append('>');
			for (int i=0;i<childs.getLength();++i)
			{
				buf.append(childs.item(i));
			}
			buf.append('<')
				.append(name)
				.append('>');
		}
		else
			buf.append("/>");
		return buf.toString();
	}

	public String getAttribute(String name)
	{
		Node node=attributes.getNamedItem(name);
		if (node==null)
			return null;
		return node.getNodeValue();
	}

	public String getAttributeNS(String namespaceURI, String localName)
			throws DOMException
	{
		Node node=attributes.getNamedItem(name);
		if (node==null)
			return null;
		return node.getNodeValue();
	}

	public Attr getAttributeNode(String name)
	{
		return (Attr)attributes.getNamedItem(name);
	}

	public Attr getAttributeNodeNS(String namespaceURI, String localName)
			throws DOMException
	{
		return (Attr)attributes.getNamedItem(name);
	}

	public NodeList getElementsByTagName(String name)
	{
		throw new NoSuchMethodError();
	}

	public NodeList getElementsByTagNameNS(String namespaceURI, String localName)
			throws DOMException
	{
		throw new NoSuchMethodError();
	}

//	public TypeInfo getSchemaTypeInfo()
//	{
//		throw new NoSuchMethodError();
//	}

	public String getTagName()
	{
		return name;
	}

	public boolean hasAttribute(String name)
	{
		return attributes.getNamedItem(name)!=null;
	}

	public boolean hasAttributeNS(String namespaceURI, String localName)
			throws DOMException
	{
		return attributes.getNamedItem(name)!=null;
	}

	public void removeAttribute(String name) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public void removeAttributeNS(String namespaceURI, String localName)
			throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public Attr removeAttributeNode(Attr oldAttr) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public void setAttribute(String name, String value) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public void setAttributeNS(String namespaceURI, String qualifiedName,
			String value) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public Attr setAttributeNode(Attr newAttr) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public Attr setAttributeNodeNS(Attr newAttr) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public void setIdAttribute(String name, boolean isId) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public void setIdAttributeNS(String namespaceURI, String localName,
			boolean isId) throws DOMException
	{
		throw new NoSuchMethodError();
	}

	public void setIdAttributeNode(Attr idAttr, boolean isId)
			throws DOMException
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
		return attributes;
	}

	public String getBaseURI()
	{
		throw new NoSuchMethodError();
	}

	public NodeList getChildNodes()
	{
		return childNodes;
	}

	public Object getFeature(String feature, String version)
	{
		throw new NoSuchMethodError();
	}

	public Node getFirstChild()
	{
		return (childNodes.getLength()>0) ? childNodes.item(0) : null;
	}

	public Node getLastChild()
	{
		return (childNodes.getLength()>0) ? childNodes.item(childNodes.getLength()-1) : null;
	}

	public String getLocalName()
	{
		return name;
	}

	public String getNamespaceURI()
	{
		return null;
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
		return Node.ELEMENT_NODE;
	}

	public String getNodeValue() throws DOMException
	{
		return null;
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
		return null;
	}

	public Node getPreviousSibling()
	{
		return null;
	}

	// Node level 3
//	public String getTextContent() throws DOMException
//	{
//		StringBuilder builder=new StringBuilder();
//		for (Text n:textsNodes.list)
//		{
//			builder.append(n.getNodeValue());
//		}
//		return builder.toString();
//	}

	public Object getUserData(String key)
	{
		throw new NoSuchMethodError();
	}

	public boolean hasAttributes()
	{
		return attributes.getLength()!=0;
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
	public TypeInfo getSchemaTypeInfo()
	{
		// TODO Auto-generated method stub
		return null;
	}
	public String getTextContent() throws DOMException
	{
		// TODO Auto-generated method stub
		return null;
	}
}
