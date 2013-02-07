package fr.prados.xpath4sax.test;

import java.util.List;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class XMLTools
{
	public static void dump(Document document)
	{
		dump1(document);
	}
	private static void dump1(Document document)
	{
        System.out.println("\n<xml version=\"1.0\">\n");
		printNode(document.getDocumentElement());
	}
	private static void printNode(Node node)  
	{

        switch (node.getNodeType()) 
        {

            case Node.DOCUMENT_NODE:
//                System.out.println("<xml version=\"1.0\">\n");
                // recurse on each child
                NodeList nodes = node.getChildNodes();
                if (nodes != null) 
                {
                    for (int i=0; i<nodes.getLength(); i++) 
                    {
                        printNode(nodes.item(i));
                    }
                }
                break;
                
            case Node.ELEMENT_NODE:
                String name = node.getNodeName();
                NodeList children = node.getChildNodes();
               	System.out.print("<" + name);
                NamedNodeMap attributes = node.getAttributes();
                for (int i=0; i<attributes.getLength(); i++) 
                {
                    Node current = attributes.item(i);
                    System.out.print(
                        " " + current.getNodeName() +
                        "=\"" + current.getNodeValue() +
                        "\"");
                }
                if (children.getLength()==0)
                	System.out.print("/>");
                else
                {
                	System.out.print(">");
                
	                // recurse on each child
	                if (children != null) 
	                {
	                    for (int i=0; i<children.getLength(); i++) 
	                    {
	                        printNode(children.item(i));
	                    }
	                }
	                System.out.println("</" + name + ">");
                }
                break;

            case Node.TEXT_NODE:
                System.out.print(node.getNodeValue());
                break;
        }

    }    
 	
//	public static void dump2(Document document)
//	{
//		try
//		{
//			DOMSource domSource = new DOMSource(document);
//			StreamResult result = new StreamResult(System.out);
//			TransformerFactory tf = TransformerFactory.newInstance();
//			Transformer transformer = tf.newTransformer();
//			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
//			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//			transformer.transform(domSource, result);
//		}
//		catch (TransformerException e)
//		{
//			e.printStackTrace();
//		}
//	}

	public static void dumpDOM(String xpath, List<?> domResult)
	{
		for (Object n:domResult)
		{
			System.out.print(xpath+" -> ");
			if (n instanceof String)
			{
				System.out.println(n);
			}
			if (n instanceof Element)
			{
				Element e=(Element)n;
				System.out.print("<"+e.getNodeName());
				NamedNodeMap attrs=e.getAttributes();
				for (int j=0;j<attrs.getLength();++j)
				{
					Attr attr=(Attr)attrs.item(j);
					System.out.print(" "+attr.getName()+"='"+attr.getNodeValue()+"'");
				}
				NodeList childs=e.getChildNodes();
				if (childs.getLength()==0)
					System.out.println("/>");
				else
				{
					System.out.print(">");
					for (int k=0;k<childs.getLength();++k)
					{
						Node child=childs.item(k);
						if (child instanceof Text)
						{
							System.out.print("[#text: "+((Text)child).getNodeValue()+"]");
						}
					}
					System.out.println("<"+e.getNodeName()+">");
					
				}
			}
			else if (n instanceof Text)
			{
				System.out.println(((Text)n).getNodeValue());
			}
			else if (n instanceof Attr)
			{
				System.out.println(((Attr)n).getName()+"="+((Attr)n).getValue());
			}
			else if (n instanceof Document)
			{
				System.out.println("[#document: null]");
			}
		}
	}
}
