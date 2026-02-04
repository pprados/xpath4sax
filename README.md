[![Ask DeepWiki](https://deepwiki.com/badge.svg)](https://deepwiki.com/pprados/xpath4sax)

A quick XPath analyser with a SAX Parser.
Some syntaxes are invalides, but all using syntax are presents.
It's possible to catch many XPath in the same time.
```
XPathXMLHandler handler=new XPathXMLHandler()
{
  @Override
  public void findXPathNode(SAXXPath xpath, Object node)
  {
     System.out.println("node="+node);
  }
};
handler.setXPaths(XPathXMLHandler.toXPaths("//b[@at_a='s3']/c"));
SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
parser.parse(new InputSource(new StringReader(xml)), handler);
```
The node is orphan, with attributs and all childs texts nodes.
It's possible to use with [HtmlParser](http://nu.validator.htmlparser).
```
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
```

# Accepted syntax

## Valide syntax

 * `"'fixe'"`
 * `"1"`
 * `"/"`
 * `"/a/b/c"`
 * `"/a/text()"`
 * `"/a/@at_a"`
 * `"/a/b/@*"`
 * `"/a/b[@*]"`
 * `"/a/b[@at_a = 's3']"`
 * `"/a/b[@at_a > 2]"`
 * `"/a/b[@at_a >= 2]"`
 * `"/a/b[@at_a = @at_b]"`
 * `"/a/b[@at_a != @at_b]"`
 * `"/a/b[@at_a < 5]"`
 * `"/a/b[@at_a <= 2]"`
 * `"/a/b[@at_b='s2' or @at_b='3']"`
 * `"/a/b[@at_a='3' and @at_b='3']"`
 * `"/a/b/text()['toto']"`
 * `"/a/b[2]"`
 * `"/a/b/text()[2]"`
 * `"/a/b/@*[2]"`
 * `"//b/c"`
 * `"//b/c[2]"`
 * `"//b[2]/c[2]"`
 * `"//b[@at_a='s3']/c"`
 * `"//c/text()"`
 * `"//b/@at_a"`
 * `"//@at_a"`


## Invalide syntax
 * `"//text()"`
 * `"//@*"`
 * using dot, node(), functions, etc.
 * using double predicat (`"/a/b[@at_a][1]"`)
 * using or (`"/a | /b"`)
