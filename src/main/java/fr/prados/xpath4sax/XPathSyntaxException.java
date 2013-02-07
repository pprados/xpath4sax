package fr.prados.xpath4sax;

/**
 * Exception if the XPath detect a syntax error.
 * 
 * @version 1.0
 * @since 1.0
 * @author Philippe PRADOS
 */
public class XPathSyntaxException extends Exception
{
	/**
	 * <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	
	public XPathSyntaxException(String msg)
	{
		super(msg);
	}
	
	public XPathSyntaxException(Throwable e)
	{
		super(e);
	}
	public XPathSyntaxException(String msg, Throwable e)
	{
		super(msg,e);
	}
}
