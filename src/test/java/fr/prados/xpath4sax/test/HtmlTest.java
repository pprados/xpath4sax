package fr.prados.xpath4sax.test;

/*
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.TestCase;
import nu.validator.htmlparser.common.DoctypeExpectation;
import nu.validator.htmlparser.common.XmlViolationPolicy;
import nu.validator.htmlparser.dom.HtmlDocumentBuilder;
import nu.validator.htmlparser.sax.HtmlParser;

import org.jaxen.JaxenException;
import org.jaxen.dom.DOMXPath;
import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import fr.prados.xpath4sax.SAXXPath;
import fr.prados.xpath4sax.XPathSyntaxException;
import fr.prados.xpath4sax.XPathXMLHandler;
import fr.prados.xpath4sax.test.SAXTest.MyXPathHandler;
public class HtmlTest extends TestCase
{
	static class XmlnsDropper implements ContentHandler {

	    private final ContentHandler delegate;

	    public XmlnsDropper(final ContentHandler delegate) {
	        this.delegate = delegate;
	    }

	    public void characters(char[] ch, int start, int length) throws SAXException {
	        delegate.characters(ch, start, length);
	    }

	    public void endDocument() throws SAXException {
	        delegate.endDocument();
	    }

	    public void endElement(String uri, String localName, String qName) throws SAXException {
	        delegate.endElement(uri, localName, qName);
	    }

	    public void endPrefixMapping(String prefix) throws SAXException {
	        delegate.endPrefixMapping(prefix);
	    }

	    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
	        delegate.ignorableWhitespace(ch, start, length);
	    }

	    public void processingInstruction(String target, String data) throws SAXException {
	        delegate.processingInstruction(target, data);
	    }

	    public void setDocumentLocator(Locator locator) {
	        delegate.setDocumentLocator(locator);
	    }

	    public void skippedEntity(String name) throws SAXException {
	        delegate.skippedEntity(name);
	    }

	    public void startDocument() throws SAXException {
	        delegate.startDocument();
	    }

	    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
	        AttributesImpl ai = new AttributesImpl();
	        for (int i = 0; i < atts.getLength(); i++) {
	            String u = atts.getURI(i);
	            String t = atts.getType(i);
	            String v = atts.getValue(i);
	            String n = atts.getLocalName(i);
	            String q = atts.getQName(i);
	            if (q != null) {
	                if ("xmlns".equals(q) || q.startsWith("xmlns:")) {
	                    continue;
	                }
	            }
	            ai.addAttribute(u, n, q, t, v); 
	        }
	        delegate.startElement(uri, localName, qName, ai);
	    }

	    public void startPrefixMapping(String prefix, String uri) throws SAXException {
	        delegate.startPrefixMapping(prefix, uri);
	    }
	    
	}
	
	static final String html=
		"<html>"+
		"<body>"+
		"<h1>Titre</h1>"+
		"hello<br>"+
		"</body>"+
		"</html>";
	public void testBr() throws Throwable { checkXPath("/xhtml:html"); }
	
    protected void checkXPath(String xpath) throws ParserConfigurationException, SAXException, IOException, JaxenException, XPathSyntaxException
    {
		System.out.println("=========== "+xpath);
        Document inputDoc;
        
//        HtmlParser parser=new HtmlParser(XmlViolationPolicy.ALLOW);
//        parser.setContentHandler( new XmlnsDropper(parser.getContentHandler()));
        HtmlDocumentBuilder builder;
        builder = new HtmlDocumentBuilder(XmlViolationPolicy.ALTER_INFOSET);
        //new HtmlParser().setProperty("http://validator.nu/properties/xmlns-policy", false);
		Document doc=builder.parse(new InputSource(new StringReader(html)));
		XMLTools.dump(doc);
		DOMXPath domXPath=new DOMXPath(xpath);
		domXPath.addNamespace("xhtml", "http://www.w3.org/1999/xhtml");
		List<Object> domResult=domXPath.selectNodes(doc);
		XMLTools.dumpDOM(xpath, domResult);
		
		HtmlParser htmlParser = new HtmlParser(XmlViolationPolicy.ALLOW);
		htmlParser.setDoctypeExpectation(DoctypeExpectation.NO_DOCTYPE_ERRORS);
		
		MyXPathHandler handler=new MyXPathHandler();
		handler.setXPaths(XPathXMLHandler.toXPaths(xpath));
		htmlParser.setContentHandler(handler);
		htmlParser.parse(new InputSource(new StringReader(html)));
		
		// Compare results
		assertTrue("ERROR with "+xpath,SAXTest.checkResults(handler.result, domResult));
    }
    
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
		handler.setXPaths(XPathXMLHandler.toXPaths("//body/h1"));
		HtmlParser htmlParser = new HtmlParser(XmlViolationPolicy.ALLOW);
		htmlParser.setDoctypeExpectation(DoctypeExpectation.NO_DOCTYPE_ERRORS);
		htmlParser.setContentHandler(handler);
		htmlParser.parse(new InputSource(new StringReader(html)));
    	
    }
    
    public void testDOM() throws Throwable
    {
		HtmlDocumentBuilder builder=new HtmlDocumentBuilder(XmlViolationPolicy.ALLOW);
		Document doc=builder.parse(new InputSource(new StringReader(html)));
//		for (String xpath:xpaths)
//		{  
//			List<Object> nodes=new DOMXPath(xpath).selectNodes(doc);
//			for (Object n:nodes)
//			{
//				match.match(xpath,n);
//			}
//		}
    }
}
*/