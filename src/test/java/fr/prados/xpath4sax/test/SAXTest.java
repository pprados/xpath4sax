package fr.prados.xpath4sax.test;



import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.jaxen.JaxenException;
import org.jaxen.dom.DOMXPath;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import fr.prados.xpath4sax.SAXXPath;
import fr.prados.xpath4sax.XPathSyntaxException;
import fr.prados.xpath4sax.XPathXMLHandler;

public class SAXTest 
{ 
	public static final String xml=
		"<a at_a='s1'>" +
		  "<b at_a='3' at_b='3'>" + 
		  	"<c>Level a/b/c 1</c>" + 
		  	"Level a/b 1" + 
		  	"<c>Level a/b/c 2</c>"	+ 
		  	"<c>Level a/b/c 3</c>" +
		  	"Level a/b 2" + 
		  "</b>" + 
		  "<d at_b='s2'>Level a/d 1</d>" + 
		  "<b at_a='3' at_b='4'>" + 
		  	"<c>Level a/b/c 4</c>" + 
			"Level a/b 3" + 
		  "</b>" + 
		  "<b/>" +
		  "Level a 1"+
		  "<b at_a='s3'>"	+
		  	"<c>Level a/b/c 5</c>" + 
		  	"Level a/b 4" +
		  	"<c>Level a/b/c 6</c>" + 
		  	"Level a/b 5" +
		  	"<c>Level a/b/c"+
		  	  "<c>Level a/b/c/c" +
		  	    "<d>Level a/b/c/c/d</d>"+
		  	  "</c>"+
		  	"</c>"+
		  "</b>" +
		  "Level a 2"+
		"</a>";

	@Test
    public void testDemo() throws Throwable
    {
    	XPathXMLHandler handler=new XPathXMLHandler()
    	{
			@Override
    		public void findXpathNode(SAXXPath xpath, Object node)
    		{
    			System.out.println("node="+node);
    		}
    	};
    	handler.setXPaths(XPathXMLHandler.toXPaths("//b[@at_a='s3']/c"));
    	SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		parser.parse(new InputSource(new StringReader(xml)), handler);
    }
	@Test public void testParsing_01() throws Throwable	{ new SAXXPath("a[@id]"); }
	@Test public void testParsing_02() throws Throwable	{ new SAXXPath("a[@id='with space']"); }
	@Test public void testParsing_03() throws Throwable	{ new SAXXPath("a[@id=\"with '\" or 'a\"b' = 'ab']"); }

	@Test public void testFixe_01() throws Throwable { checkXPath("'fixe'"); }
	@Test public void testFixe_02() throws Throwable { checkXPath("1"); }
	
	@Test public void testNavigation_01() throws Throwable {	checkXPath("/"); }
	@Test public void testNavigation_02() throws Throwable {	checkXPath("/a"); }
	@Test public void testNavigation_03() throws Throwable {	checkXPath("/d"); }
	@Test public void testNavigation_04() throws Throwable {	checkXPath("/a/b"); }
	@Test public void testNavigation_05() throws Throwable {	checkXPath("/a/d"); }
	@Test public void testNavigation_06() throws Throwable {	checkXPath("/a/b/c"); }
	
	@Test public void testText_01() throws Throwable {	checkXPath("/a/text()"); }
	@Test public void testText_02() throws Throwable {	checkXPath("/a/b/text()"); }
	@Test public void testText_03() throws Throwable {	checkXPath("/a/d/text()"); }
	@Test public void testText_04() throws Throwable {	checkXPath("/a/b/c/text()"); }
	
	@Test public void testAttribut_01() throws Throwable {	checkXPath("/a/@at_a"); }
	@Test public void testAttribut_02() throws Throwable {	checkXPath("/a/b/@*"); }
	@Test public void testAttribut_03() throws Throwable {	checkXPath("/a/d[@at_a]"); }

	@Test public void testPredicat_01() throws Throwable {	checkXPath("/a/b[@*]"); }
	@Test public void testPredicat_02() throws Throwable {	checkXPath("/a/b[@at_a = 's3']"); } 
	@Test public void testPredicat_03() throws Throwable {	checkXPath("/a/b[@at_a > 2]"); } 
	@Test public void testPredicat_04() throws Throwable {	checkXPath("/a/b[@at_a >= 2]"); } 
	@Test public void testPredicat_05() throws Throwable {	checkXPath("/a/b[@at_a = @at_b]"); }
	@Test public void testPredicat_06() throws Throwable {	checkXPath("/a/b[@at_a != @at_b]"); } 
	@Test public void testPredicat_07() throws Throwable {	checkXPath("/a/b[@at_a < 5]"); }
	@Test public void testPredicat_08() throws Throwable {	checkXPath("/a/b[@at_a <= 2]"); }
	@Test public void testPredicat_09() throws Throwable {	checkXPath("/a/b[@at_b='s2' or @at_b='3']"); }
	@Test public void testPredicat_10() throws Throwable {	checkXPath("/a/b[@at_a='3' and @at_b='3']"); }
	@Test public void testPredicat_11() throws Throwable {	checkXPath("/a/b/text()['toto']"); }

	@Test public void testPosition_01() throws Throwable {	checkXPath("/a/text()[2]"); }
	@Test public void testPosition_02() throws Throwable {	checkXPath("/a/b/text()[2]"); }
	@Test public void testPosition_03() throws Throwable {	checkXPath("/a/d/text()[2]"); }
	@Test public void testPosition_04() throws Throwable {	checkXPath("/a/d/text()[3]"); }
	@Test public void testPosition_05() throws Throwable {	checkXPath("/a/b/@*[2]"); }
	@Test public void testPosition_06() throws Throwable {	checkXPath("/a/b[2]"); }
	@Test public void testPosition_07() throws Throwable {	checkXPath("/a/b[3]"); }

	//@Test public void testRelativeNavigation_01() throws Throwable {	checkXPath("//c"); } // Recursive result not supported
	@Test public void testRelativeNavigation_02() throws Throwable {	checkXPath("//b"); }
	@Test public void testRelativeNavigation_03() throws Throwable {	checkXPath("//b/c"); }
	@Test public void testRelativeNavigation_04() throws Throwable {	checkXPath("//a/b/c"); }
	@Test public void testRelativeNavigation_05() throws Throwable {	checkXPath("//c/d"); }
	@Test public void testRelativeNavigation_06() throws Throwable {	checkXPath("//b/c[2]"); }
	@Test public void testRelativeNavigation_07() throws Throwable {	checkXPath("//b[2]/c"); }
	@Test public void testRelativeNavigation_08() throws Throwable {	checkXPath("//b[2]/c[2]"); }
	@Test public void testRelativeNavigation_09() throws Throwable {	checkXPath("//b[@at_a='s3']/c"); }
	@Test public void testRelativeNavigation_10() throws Throwable {	checkXPath("//c/text()"); }
	@Test public void testRelativeNavigation_11() throws Throwable {	checkXPath("//b/@at_a"); }
	@Test public void testRelativeNavigation_12() throws Throwable {	checkXPath("//@at_a"); }
//	@Test public void testRelativeNavigation_11() throws Throwable {	checkXPath("//text()"); }
//	@Test public void testRelativeNavigation_12() throws Throwable {	checkXPath("//text()[2]"); }
//	@Test public void testRelativeNavigation_10() throws Throwable {	checkXPath("//@at_a[2]"); }
//	@Test public void testRelativeNavigation_11() throws Throwable {	checkXPath("//@*"); }
//	@Test public void testRelativeNavigation_11() throws Throwable {	checkXPath("//@*[2]"); }

	// Syntax not supported with SAX model
//	@Test public void testDot_01() throws Throwable {	checkXPath("/a/."); }
//	@Test public void testDot_02() throws Throwable {	checkXPath("/./.[@at_a]"); }
//	@Test public void testDot_01() throws Throwable {	checkXPath("/a[b]"); }
//
//	@Test public void testNode_01() throws Throwable {	checkXPath("/a/node()"); }
//	@Test public void testNode_02() throws Throwable {	checkXPath("/a/node()/node()"); }
//	@Test public void testNode_03() throws Throwable {	checkXPath("/node()/node()"); }
//	@Test public void testNode_04() throws Throwable {	checkXPath("/node()/node()[@at_a]"); }
//	@Test public void testNode_05() throws Throwable {	checkXPath("/a/node()[2]"); }
//
//	@Test public void testDblPredicat_01() throws Throwable {	checkXPath("/a/b[@at_a][1]"); }
//	@Test public void testDblPredicat_02() throws Throwable {	checkXPath("/./.[@at_a][1]"); }

	@Test 
	@Ignore
	public void testBenchRelatif_01() throws Throwable
	{
		SAXXPath absolu=new SAXXPath("/a/b/c");
		SAXXPath relatif=new SAXXPath("//b/c");
		
		XPathXMLHandler handler=new XPathXMLHandler()
		{

			@Override
			public void findXpathNode(SAXXPath xpath, Object node)
			{
			}
			
		};
		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		
		long start;

		handler.setXPaths(XPathXMLHandler.toXPaths(absolu));
		start=System.currentTimeMillis();
		for (int i=0;i<100000;++i)
		{
			parser.parse(new InputSource(new StringReader(xml)),handler );
		}
		long abs=System.currentTimeMillis()-start;
		
		handler.setXPaths(XPathXMLHandler.toXPaths(relatif));
		start=System.currentTimeMillis();
		for (int i=0;i<100000;++i)
		{
			parser.parse(new InputSource(new StringReader(xml)),handler );
		}
		long rel=System.currentTimeMillis()-start;
		System.out.println("rel="+rel+" abs="+abs);
	}

	static final DocumentBuilderFactory DOMfactory=DocumentBuilderFactory.newInstance();
	static
	{
		DOMfactory.setValidating(false);
	}
	static class MyXPathHandler extends XPathXMLHandler
	{
		ArrayList<Object> result=new ArrayList<Object>();
		@Override
		public void findXpathNode(SAXXPath xpath, Object node)
		{
			result.add(node);
			System.out.println(xpath + " => " + node);
		}
	}

	final DocumentBuilder builder;
	{
		try
		{
			builder=DOMfactory.newDocumentBuilder();
		}
		catch (ParserConfigurationException e)
		{
			throw new IllegalAccessError();
		}
	}

    protected void benchXPath(String xpath) throws ParserConfigurationException, SAXException, IOException, JaxenException, XPathSyntaxException
    {
    	System.gc();
    	int MAX_BENCH=10000;
    	XPathXMLHandler handler=new XPathXMLHandler()
    	{
    		ArrayList<Object> result=new ArrayList<Object>();
    		@Override
			public void findXpathNode(SAXXPath xpath, Object node)
    		{
    			result.add(node);
    		}
    	};

		long start=System.nanoTime();
		for (int i=0;i<MAX_BENCH;++i)
			new DOMXPath(xpath).selectNodes(builder.parse(new InputSource(new StringReader(xml))));
		long end=System.nanoTime();
		long domtime=end-start;

		handler.setXPaths(XPathXMLHandler.toXPaths(xpath));
		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		start=System.nanoTime();
		for (int i=0;i<MAX_BENCH;++i)
			parser.parse(new InputSource(new StringReader(xml)),handler );
		end=System.nanoTime();
		long saxtime=end-start;
		System.out.println("DOM:"+domtime/100000+" SAX:"+saxtime/100000+" "+((domtime>saxtime) ? "Optimise "+(int)(((double)domtime/saxtime)*100)+"%" : "NO OPTIMIZE"));
		assertTrue(saxtime<domtime);
    }
    
    protected void checkXPath(String xpath) throws ParserConfigurationException, SAXException, IOException, JaxenException, XPathSyntaxException
    {
    	checkXPath(xpath,xml);
    }
    protected void checkXPath(String xpath,String theXml) throws ParserConfigurationException, SAXException, IOException, JaxenException, XPathSyntaxException
    {
		System.out.println("=========== "+xpath);
		Document doc=builder.parse(new InputSource(new StringReader(theXml)));
		List<?> domResult=new DOMXPath(xpath).selectNodes(doc);
		XMLTools.dumpDOM(xpath, domResult);

		System.out.println();
		MyXPathHandler handler=new MyXPathHandler();
		handler.setXPaths(XPathXMLHandler.toXPaths(xpath));
		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		parser.parse(new InputSource(new StringReader(xml)),handler );

		// Compare results
		assertTrue("ERROR with "+xpath,checkResults(handler.result, domResult));
		// TODO assertTrue("ERROR with "+xpath,checkResults(domResult,handler.result));
    }

	public static boolean checkResults(List<?> saxResult,
			List<?> domResult)
	{
		ArrayList<Object> finded=new ArrayList<Object>();
		if (saxResult.size()!=domResult.size())
			return false;
		boolean find;
		for (Object n1:saxResult)
		{
			find=false;
			for (Object n2:domResult)
			{
				if (finded.contains(n2))
					continue;
				if (n1 instanceof Element)
				{
					if (((Node)n1).getNodeName().equals(((Node)n2).getNodeName()) && ((Node)n1).getNodeType()==((Node)n2).getNodeType())
					{
						if (checkElements((Element)n1,(Element)n2))
						{
							finded.add(n2);
							find=true;
						}
					}
				}
				else if (n1 instanceof Text)
				{
					if (((Text)n1).getNodeValue().equals(((Text)n2).getNodeValue()))
					{
						finded.add(n2);
						find=true;
					}
				}
				else if (n1 instanceof Attr)
				{
					Attr a1=(Attr)n1;
					Attr a2=(Attr)n2;
					if ((a1.getName().equals(a2.getName()) && (a2.getValue().equals(a2.getValue()))))
					{
							finded.add(n2);
							find=true;
					}
				}
				else if (n1 instanceof String)
				{
					if (n1.equals(n2))
					{
						finded.add(n2);
						find=true;
					}
				}
				else if (n1 instanceof Double)
				{
					if (n1.equals(n2))
					{
						finded.add(n2);
						find=true;
					}
				}
				else if (n1 instanceof Document)
				{
					if (n2 instanceof Document)
					{
						finded.add(n2);
						find=true;
					}
				}
				if (find)
					break;
			}
			if (!find)
				return false;
		}
		if (finded.size()!=domResult.size())
			return false;
		return true;
	}

	private static boolean checkElements(Element e1,Element e2)
	{
		// Check attributes
		for (int a=0;a<e1.getAttributes().getLength();++a)
		{
			Attr attr1=(Attr)e1.getAttributes().item(a);
			Attr attr2=e2.getAttributeNode(attr1.getName());
			if (attr2==null) return false; // Pas le même attribut
			if (!attr1.getNodeValue().equals(attr2.getNodeValue()))
				return false; // Pas la même valeur
		}
		
		// Check texts nodes in this order
		NodeList c1=e1.getChildNodes();
		NodeList c2=e2.getChildNodes();
		ArrayList<Text> finded=new ArrayList<Text>();
		for (int i=0;i<c1.getLength();++i)
		{
			Node n1=c1.item(i);
			if (!(n1 instanceof Text))
				continue;
			Text t1=(Text)n1;
			for (int j=0;j<c2.getLength();++j)
			{
				Node n2=c2.item(j);
				if (!(n2 instanceof Text))
					continue;
				Text t2=(Text)n2;
				if (finded.contains(t2))
					continue;
				if (!t1.getNodeValue().equals(t2.getNodeValue()))
					return false;
				finded.add(t2);
				break;
			}
		}
		if (finded.size()!=c1.getLength())
			return false;
		return true;
	}
}
