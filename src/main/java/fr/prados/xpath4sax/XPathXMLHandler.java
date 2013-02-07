package fr.prados.xpath4sax;

import java.io.StringReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

/**
 * The SAX handle to detect many XPath.
 * 
 * @version 0.1
 * @since 1.0
 * @author Philippe PRADOS
 */
public abstract class XPathXMLHandler extends DefaultHandler2
{
	/** An array with all xpaths. */
	private SAXXPath[]	xpaths_;
	/** The current pos of detected node for each xpaths. */
	private int[]		xpathspos_;
	/** The current start position for each xpaths. */
	private int[]		start_;
	/** The current position for each node for each xpaths. */
	private int[][]		pos_;
	/** The current node to build if find something.*/
	private Node[]		find_;
	/** The current level inside the tree. */
	int level;

	/** A special node to signal an only text event. */
	static private final Node textEvent=new TextWrapper(null);

	/**
	 * Convert a set of string to a set of XPath.
	 * 
	 * @param xpaths The set.
	 * @return The set of XPath.
	 * @throws XPathSyntaxException If syntax error.
	 */
	public static Set<SAXXPath> toXPaths(Set<CharSequence> xpaths) throws XPathSyntaxException
	{
		HashSet<SAXXPath> set=new HashSet<SAXXPath>(xpaths.size());
		for (CharSequence xpath:xpaths)
		{
			set.add(new SAXXPath(xpath));
		}
		return set;
	}

	/**
	 * Convert array of string to set of XPath.
	 * Using:
	 * <pre>
	 * XPathXMLHandler.toXPaths("/toto","/toto/tata");
	 * </pre>
	 * @param xpaths
	 * @return
	 * @throws XPathSyntaxException
	 */
	public static Set<SAXXPath> toXPaths(String ... xpaths) throws XPathSyntaxException
	{
		HashSet<SAXXPath> set=new HashSet<SAXXPath>(xpaths.length);
		for (String xpath:xpaths)
		{
			set.add(new SAXXPath(xpath));
		}
		return set;
	}
	
	/**
	 * Convert array of string to set of XPath.
	 * Using:
	 * <pre>
	 * XPathXMLHandler.toXPaths("/toto","/toto/tata");
	 * </pre>
	 * @param xpaths
	 * @return
	 * @throws XPathSyntaxException
	 */
	public static Set<SAXXPath> toXPaths(SAXXPath ... xpaths) throws XPathSyntaxException
	{
		HashSet<SAXXPath> set=new HashSet<SAXXPath>(xpaths.length);
		for (SAXXPath xpath:xpaths)
		{
			set.add(xpath);
		}
		return set;
	}
	
	/**
	 * Set a list of XPath to analyse.
	 * 
	 * @param xpaths The xpaths.
	 * @return this.
	 * @throws XPathSyntaxException If syntax error.
	 */
	public final XPathXMLHandler setXPaths(Set<SAXXPath> xpaths) throws XPathSyntaxException
	{
		xpaths_=new SAXXPath[xpaths.size()];
		xpathspos_=new int[xpaths.size()];
		start_=new int[xpaths.size()];
		pos_=new int[xpaths.size()][];
		find_=new Node[xpaths.size()];
		int i=0;
		for (SAXXPath xpath:xpaths)
		{
			xpaths_[i]=xpath;
			pos_[i]=new int[xpaths_[i].pos_.length];
			++i;
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void startDocument()
	{
		if (xpaths_==null)
			throw new IllegalAccessError("Set xpaths before parse");
		++level;
		for (int i=0;i<xpaths_.length;++i)
		{
			final SAXXPath xpath=xpaths_[i];
			xpathspos_[i]=1;
			if (xpath.xpath_.charAt(0)=='\'')
			{
				assert xpath!=null;
				findXpathNode(xpath, xpath.xpath_.toString().substring(1,xpath.xpath_.length()-1));
			}
			else if ("0123456789".indexOf(xpaths_[i].xpath_.charAt(0))!=-1)
			{
				findXpathNode(xpath,Double.parseDouble(xpaths_[i].xpath_.toString()));
			}
			else if (xpath.nodes_.length==0)
			{
				findXpathNode(xpath,new DocumentWrapper());
			}
		}		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public InputSource resolveEntity(String x,String y)
	{
		return new InputSource(new StringReader(""));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InputSource resolveEntity(String w,String x,String y,String z)
	{
		return new InputSource(new StringReader(""));
	}
	// public void startElement(String uri, String localName, String qName,
	// Attributes2 atts)
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException
	{
		//System.out.println("start <"+qName+">");		
		ElementWrapper element=null; // Shared element between xpaths
		for (int i=0;i<xpaths_.length;++i)
		{
			SAXXPath xpath=xpaths_[i];
			if (xpath==null) continue;
			CharSequence[] nodes=xpath.nodes_;
			int start=start_[i];
			int locallevel=level-start;
			assert(locallevel>=0 && locallevel<=level);

			final int[] pos=pos_[i];
			switch(xpath.match(level,start,uri,localName,qName,attributes))
			{
				case Nothing:
					break;
					
				case AttrStart:
					start=start_[i]=level-1;
					locallevel=level-start;
					assert(locallevel>=0 && locallevel<=level);
					xpathspos_[i]=1;
					for (int j=locallevel+1; j<pos.length;++j)
						pos[j]=0;
				case Attr:
					boolean onlyAttr=false;
					if ((xpath.full) || nodes.length<1 || nodes[1].charAt(0)!='@')
							++locallevel;
					else
						onlyAttr=true;
					++xpathspos_[i];
					++pos[locallevel];
					if (onlyAttr || xpathspos_[i]==nodes.length-1)
					{
						String name=(onlyAttr ? nodes[1] : nodes[xpathspos_[i]]).toString().substring(1);
						if ("*".equals(name))
						{
							for (int a=0;a<attributes.getLength();++a)
							{
								if (xpath.pos_[locallevel]==0 || xpath.pos_[locallevel]==a+1)
									findXpathNode(xpath, new AttrWrapper(attributes.getLocalName(a),attributes.getValue(a)));
							}
						}
						else if (attributes.getIndex(name)!=-1) 
							findXpathNode(xpath, new AttrWrapper(name,attributes.getValue(name)));
					}
					//--locallevel;
					break;
					
				case ElementStart:
					start=start_[i]=level-1;
					locallevel=level-start;
					assert(locallevel>=0 && locallevel<=level);
					xpathspos_[i]=1;
					for (int j=locallevel+1; j<pos.length;++j)
						pos[j]=0;
				case Element:
					++pos[locallevel];
					++xpathspos_[i];
					for (int j=locallevel+1; j<pos.length;++j)
						pos[j]=0;
					if (xpathspos_[i]==nodes.length)
					{
						boolean valid=true;
						for (int l=level-start;l>=0;--l)
						{
							if (xpath.pos_[l]==0) continue;
							if (xpath.pos_[l]!=pos[l])
								valid=false;
						}
						if (valid)
						{
							if (element==null) 
								element=new ElementWrapper(qName,attributes);
							find_[i]=element;
						}
					}
					else if ((nodes.length>locallevel+1) && nodes[locallevel+1].charAt(0)=='@')
					{
						++locallevel;
						if (nodes[locallevel].equals("@*")
								|| attributes.getValue(nodes[locallevel].toString().substring(1)) != null)
						{
							String name=nodes[xpathspos_[i]].toString().substring(1);
							if ("*".equals(name))
							{
								for (int a=0;a<attributes.getLength();++a)
								{
									if (xpath.pos_[locallevel]==0 || xpath.pos_[locallevel]==a+1)
										findXpathNode(xpath, new AttrWrapper(attributes.getLocalName(a),attributes.getValue(a)));
								}
							}
							else if (attributes.getIndex(name)!=-1) 
								findXpathNode(xpath, new AttrWrapper(name,attributes.getValue(name)));
							
						}
					}
					break;
					
				case TextStart:
					start=start_[i]=level-1;
					locallevel=level-start;
					assert(locallevel>=0 && locallevel<=level);
					xpathspos_[i]=1;
					for (int j=locallevel+1; j<pos.length;++j)
						pos[j]=0;
				case Text:
					++xpathspos_[i];
					locallevel=level-start;
					assert(locallevel>=0 && locallevel<=level);
					++pos[level-start];
					for (int j=locallevel+1; j<pos.length;++j)
						pos[j]=0;
					find_[i]=textEvent; // Signal
					break;
			}
		}
		++level;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void characters(char[] ch, int startBuf, int length)
	{
//		System.out.println("Event "+new String(ch,start,length));
		TextWrapper txt=null;  // Shared element between xpaths
txt=new TextWrapper(new String(ch, startBuf, length));		
		for (int i=0;i<xpaths_.length;++i)
		{
//			if ((level-start_[i]==xpaths_[i].nodes_.length-1) && "node()".equals(xpaths_[i].nodes_[level-start]))
//			{
//				if (txt==null) txt=new TextWrapper(new String(ch, start, length));
//				if (find_ [i]==null) find(xpaths_[i],txt);
//				continue;
//			}
			final SAXXPath xpath=xpaths_[i];
			final int start=start_[i];
			final int[] pos=pos_[i];
			Node node=find_[i];
			int locallevel=level-start;
			assert(locallevel>=0 && locallevel<=level);

			if (locallevel<xpath.nodes_.length && 
					"text()".equals(xpath.nodes_[locallevel]) && pos.length>locallevel)
			{
				++pos[locallevel];
				node=textEvent;
			}
			for (int j=locallevel+1; j<pos.length;++j)
				pos[j]=0;
			if (node instanceof Element)
			{
				if (xpathspos_[i]==locallevel && locallevel==xpath.nodes_.length)
				{
					boolean valid=true;
					for (int l=locallevel-2;l>=0;--l)
					{
						if (xpath.pos_[l]==0) continue;
						if (xpath.pos_[l]!=pos[l])
							valid=false;
					}
					if (valid)
					{
						if (txt==null) txt=new TextWrapper(new String(ch, startBuf, length));
						((ElementWrapper)node).childNodes.list.add(txt);
					}
				}
			}
			else if ((node==textEvent) && locallevel==xpaths_[i].nodes_.length-1)
			{
				if (xpathspos_[i]==locallevel && locallevel==xpath.nodes_.length-1)
				{
					boolean valid=true;
					for (int l=locallevel;l>=0;--l)
					{
						if (xpath.pos_[l]==0) continue;
						if (xpath.pos_[l]!=pos[l])
							valid=false;
					}
					if (valid)
					{
						if (txt==null) txt=new TextWrapper(new String(ch, startBuf, length));
						findXpathNode(xpath,txt);
					}
				}
			}					
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void endElement(String uri, String localName, String qName)
	{
		//System.out.println("start </"+qName+">");		
		--level;
		for (int i=0;i<xpaths_.length;++i)
		{
			final SAXXPath xpath=xpaths_[i];
			final Node node=find_[i];
			final int locallevel=level-start_[i];
			assert(locallevel>=0 && locallevel<=level);
			final int[] pos=pos_[i];
			if (node!=null)
			{
				if (node==textEvent) 
				{
					if (level==xpath.nodes_.length-1)
					{
						find_[i]=null;
						start_[i]=0;
					}
				}
				else
				{
					if (locallevel==xpath.nodes_.length-1)
					{
						findXpathNode(xpaths_[i], node);
						find_[i]=null;
					}
				}
			}
//			else if (!xpath.full &&	xpath.nodes_.length>0 && "text()".equals(xpath.nodes_[1]))
//			{
////				if (locallevel<pos.length) 
////					--pos[locallevel];
//				start_[i]=0;
//			}
			if (xpathspos_[i]>locallevel)
			{
				if (--xpathspos_[i]==1)
					start_[i]=0;
			}
		}
	}

	/**
	 * Call-back when a node is detected.
	 * 
	 * @param xpath The xpath was used to detect the node.
	 * @param node A read-only orphan node.
	 */
	public abstract void findXpathNode(SAXXPath xpath, Object node);
};
