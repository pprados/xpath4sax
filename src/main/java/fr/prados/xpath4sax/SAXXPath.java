package fr.prados.xpath4sax;

import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xml.sax.Attributes;

/**
 * XPath to used with SAX Parser.
 * Accept a part of XPath syntax. Refuse syntax when it's necessary to use a previous or next node.
 * Accept full path to a node, or a partial path if is in the end. (start with // but no // in middle).
 * Accept only element ou text node. No comment, processing instructions, etc.
 * The predicat can accept attributes (@x, @*), values comparisons (=, !=), logical test (or|and)
 * but no function (not(), last(), count(), etc.), subnodes [a/b] or axes. 
 * A VERIFIER OU CODER 
 * //abc/def /node() (equilavlent à .) /abc[@*] name(),
 * local-name(), position() not() id(id), fonctions string..., boolean, true(), false(), etc
 * 
 * Invalids:
 * /a//b (backtrack) 
 * /a[b] (fronttrack)
 * /a[@a=1][2] (two predicats)
 * /a[2][@a=1] (two predicats)
 * /a | /b (use two xpath)
 * 
 * @version 0.1
 * @since 1.0
 * @author Philippe PRADOS
 */
public class SAXXPath
{
	static Pattern predicatRegExpr_ = Pattern.compile("([^\\[]+)(\\[(.*)\\])?");

	static interface Predicat
	{
		public abstract Object value(String uri, String localName,
				String qName, Attributes attributes);
	}

	/**
	 * Parse the XPath and create an instance.
	 * 
	 * @param xpath The xpath string.
	 * @throws XPathSyntaxException If syntax error.
	 */
	public SAXXPath(CharSequence xpath) throws XPathSyntaxException
	{
		if (xpath.charAt(0) == '\'')
		{
			xpath_ = xpath;
			pos_ = new int[]{ 0 };
			nodes_=new String[0];
			return;
		}
		xpath_ = xpath;
		String[] splits = xpath.toString().split("/");
		if (splits.length>1 && splits[1].length()==0)
		{
			// Detect //
			full=false;
			String[] newSplits=new String[splits.length-1];
			System.arraycopy(splits, 1, newSplits, 0, newSplits.length);
			splits=newSplits;
		}
		else
			full=true;
		nodes_ = new String[splits.length];
		predicat_ = new Predicat[splits.length];
		pos_ = new int[splits.length];
		for (int i = 0; i < splits.length; ++i)
		{
			if (splits[i].length()!=0)
			{
				Matcher matcher = predicatRegExpr_.matcher(splits[i]);
				if (!matcher.find())
				{
					throw new XPathSyntaxException(splits[i]
							+ ": syntax unknown or not implemented");
				}
				String node = matcher.group(1);
				// Remove namespace
				int namespace=node.indexOf(':');
				if (namespace!=-1)
					node=node.substring(namespace+1);
				
				nodes_[i] = node;
				String filtertoken = null;
				if ((matcher.groupCount() > 1) && matcher.group(2) != null)
				{
					filtertoken = matcher.group(3);
					Couple couple = parsePredicat(filtertoken);
					predicat_[i] = couple.predicat;
					pos_[i] = couple.pos;
				}
			}
		}
	}

	/**
	 * Convert variant to boolean.
	 * 
	 * @param x
	 * @return boolean
	 */
	private static boolean toBoolean(Object x)
	{
		if (x instanceof Boolean)
			return (Boolean) x;
		if (x instanceof String)
		{
			return Boolean.parseBoolean((String) x);
		}
		return false;
	}

	/**
	 * Convert variant to double.
	 * 
	 * @param x
	 * @return double
	 * @throws NumberFormatException
	 */
	private static double toDouble(Object x) throws NumberFormatException
	{
		if (x instanceof Double)
			return (Double) x;
		if (x instanceof String)
		{
			return Double.parseDouble((String) x);
		}
		return 0;
	}

	/**
	 * Combine position and predicate.
	 * Position is for special syntax : {1].
	 * 
	 * @version 1.0
	 * @since 1.0
	 */
	static private class Couple
	{
		int pos;
		Predicat predicat;
	}

	/**
	 * Parse string to tokens.
	 * Accept string with quote or double-quote.
	 * 
	 * @param line
	 * @return An array of tokens.
	 */
	 // Not using a private builder, to share the "line" buffer in each substrings.
	private static ArrayList<CharSequence> parseString(CharSequence line)
	{
		ArrayList<CharSequence> rc=new ArrayList<CharSequence>(5);
		final int length=line.length();
		int start=0;
		int stop=0;
		final String sline=line.toString();
		for (int pos=0;pos<length;++pos)
		{
			char c=line.charAt(pos);
			if ("/()[]=<> ".indexOf(c)!=-1)
			{
				if (start!=stop) rc.add(sline.substring(start,stop));
				if (c!=' ') rc.add(sline.substring(pos,pos+1));
				start=stop=pos+1;
			}
			else if (c=='"')
			{
				while (++pos<length)
				{
					if (line.charAt(pos)=='\"')
						break;
				}
				rc.add(sline.substring(start,pos+1));
				start=stop=pos;
			}
			else if (c=='\'')
			{
				while (++pos<length)
				{
					if (line.charAt(pos)=='\'')
						break;
				}
				rc.add(sline.substring(start,pos+1));
				start=stop=pos;
			}
			else
				++stop;
		}
		if (start!=stop) rc.add(sline.substring(start,stop));
		return rc;
	}
	
	/**
	 * Parse predicate.
	 * 
	 * @param expr The predicate.
	 * @return A position or predicate.
	 * @throws XPathSyntaxException
	 */
	private static Couple parsePredicat(CharSequence expr)
			throws XPathSyntaxException
	{
		Couple couple = new Couple();
		Stack<Predicat> stack = new Stack<Predicat>();
		parse(parseString(expr), 0, 2, stack);
		if (stack.size() > 1)
			throw new XPathSyntaxException(expr.toString());
		Predicat predicat = stack.pop();
		if (predicat instanceof DoublePredicat)
		{
			couple.pos = (int)((DoublePredicat) predicat).doubleValue;
			predicat = null;
		}
		if (predicat instanceof AttributPredicat)
		{
			final String attrname=((AttributPredicat)predicat).name;
			predicat=new Predicat()
			{
				
				public Object value(String uri, String localName, String qName,
						Attributes attributes)
				{
					if ("*".equals(attrname))
						return attributes.getLength()!=0;
					return attributes.getIndex(attrname)!=-1;
				}
			};
		}
		couple.predicat = predicat;
		return couple;
	}

	/**
	 * Special predicate to manage double value.
	 * 
	 * @version 1.0
	 * @since 1.0
	 */
	static class DoublePredicat implements Predicat
	{
		private final double doubleValue;
		
		DoublePredicat(double doubleValue)
		{
			this.doubleValue = doubleValue;
		}

		public Object value(String uri, String localName, String qName,
				Attributes attributes)
		{
			return doubleValue;
		}

		@Override
		public String toString()
		{
			return Double.toString(doubleValue);
		}
	};

	/**
	 * Special predicate to return attribute value.
	 * 
	 * @version 1.0
	 * @since 1.0
	 */
	static class AttributPredicat implements Predicat
	{
		String name;

		AttributPredicat(CharSequence name)
		{
			this.name = name.toString().substring(1);
		}

		public Object value(String uri, String localName, String qName,
				Attributes attributes)
		{
			if ("*".equals(name))
				return attributes.getLength()!=0;
			return attributes.getValue(name);
		}

		@Override
		public String toString()
		{
			return name;
		}
	};

	/**
	 * Parse a predicate.
	 * 
	 * @param tokens The tokens of predicate.
	 * @param start The start position of token.
	 * @param priomax The maximum operator priority to accept.
	 * @param stack The stack with predicate.
	 * @return The current position in tokens list.
	 * @throws XPathSyntaxException
	 */
	private static int parse(ArrayList<CharSequence> tokens, int start, int priomax,
			Stack<Predicat> stack) throws XPathSyntaxException
	{
		for (int i = start; i < tokens.size(); ++i)
		{
			final String token = tokens.get(i).toString();
			if (token.charAt(0) == '@')
			{
				stack.push(new AttributPredicat(token));
			}
			else if ((token.charAt(0)=='\'') || (token.charAt(0)=='\"'))
			{
				final String value = token.substring(1,token.length()-1);
				stack.push(new Predicat()
				{
					public Object value(String uri, String localName,
							String qName, Attributes attributes)
					{
						return value;
					}

					@Override
					public String toString()
					{
						return "'" + value + "'";
					}
				});
			}
			else if ("0123456789".indexOf(token.charAt(0)) != -1)
			{
				final int integer = Integer.parseInt(token);
				stack.push(new DoublePredicat(integer));
			}
			else if ("=".equals(token))
			{
				if (priomax < 1)
					return i - 1;
				i = parse(
					tokens, i + 1, 0, stack);
				final Predicat right = stack.pop();
				final Predicat left = stack.pop();
				stack.push(new Predicat()
				{
					public Object value(String uri, String localName,
							String qName, Attributes attributes)
					{
						Object lval = left.value(
							uri, localName, qName, attributes);
						Object rval = right.value(
							uri, localName, qName, attributes);
						if ((lval == null) || (rval == null))
							return false;
						return lval.equals(rval);
					}

					@Override
					public String toString()
					{
						return left.toString() + "=" + right.toString();
					}
				});
			}
			else if ("!".equals(token))
			{
				if (priomax < 1)
					return i - 1;
				if ("=".equals(tokens.get(i + 1)))
				{
					i = parse(
						tokens, i + 2, 0, stack);
					final Predicat right = stack.pop();
					final Predicat left = stack.pop();
					stack.push(new Predicat()
					{
						public Object value(String uri, String localName,
								String qName, Attributes attributes)
						{
							Object lval = left.value(
								uri, localName, qName, attributes);
							Object rval = right.value(
								uri, localName, qName, attributes);
							if ((lval == null) || (rval == null))
								return false;
							return !lval.equals(rval);
						}

						@Override
						public String toString()
						{
							return left.toString() + "!=" + right.toString();
						}
					});
				}
			}
			else if ("<".equals(token))
			{
				if (priomax < 1)
					return i - 1;
				if ("=".equals(tokens.get(i + 1)))
				{
					i = parse(
						tokens, i + 2, 0, stack);
					final Predicat right = stack.pop();
					final Predicat left = stack.pop();
					stack.push(new Predicat()
					{
						public Object value(String uri, String localName,
								String qName, Attributes attributes)
						{
							try
							{
								Object l=left.value(uri, localName, qName, attributes);
								Object r=right.value(uri, localName, qName, attributes);
								if ((l==null) || (r==null)) return false;
								double lval = toDouble(l);
								double rval = toDouble(r);
								return lval <= rval;
							}
							catch (NumberFormatException e)
							{
								return false;
							}
						}

						@Override
						public String toString()
						{
							return left.toString() + "<=" + right.toString();
						}
					});
				}
				else
				{
					i = parse(
						tokens, i + 1, 0, stack);
					final Predicat right = stack.pop();
					final Predicat left = stack.pop();
					stack.push(new Predicat()
					{
						public Object value(String uri, String localName,
								String qName, Attributes attributes)
						{
							try
							{
								Object l=left.value(uri, localName, qName, attributes);
								Object r=right.value(uri, localName, qName, attributes);
								if ((l==null) || (r==null)) return false;
								double lval = toDouble(l);
								double rval = toDouble(r);
								return lval < rval;
							}
							catch (NumberFormatException e)
							{
								return false;
							}
						}

						@Override
						public String toString()
						{
							return left.toString() + "<" + right.toString();
						}
					});
				}
			}
			else if (">".equals(token))
			{
				if (priomax < 1)
					return i - 1;
				if ("=".equals(tokens.get(i + 1)))
				{
					i = parse(
						tokens, i + 2, 0, stack);
					final Predicat right = stack.pop();
					final Predicat left = stack.pop();
					stack.push(new Predicat()
					{
						public Object value(String uri, String localName,
								String qName, Attributes attributes)
						{
							try
							{
								Object l=left.value(uri, localName, qName, attributes);
								Object r=right.value(uri, localName, qName, attributes);
								if ((l==null) || (r==null)) return false;
								double lval = toDouble(l);
								double rval = toDouble(r);
								return lval >= rval;
							}
							catch (NumberFormatException e)
							{
								return false;
							}
						}

						@Override
						public String toString()
						{
							return left.toString() + ">" + right.toString();
						}
					});
				}
				else
				{
					i = parse(
						tokens, i + 1, 0, stack);
					final Predicat right = stack.pop();
					final Predicat left = stack.pop();
					stack.push(new Predicat()
					{
						public Object value(String uri, String localName,
								String qName, Attributes attributes)
						{
							try
							{
								Object l=left.value(uri, localName, qName, attributes);
								Object r=right.value(uri, localName, qName, attributes);
								if ((l==null) || (r==null)) return false;
								double lval = toDouble(l);
								double rval = toDouble(r);
								return lval > rval;
							}
							catch (NumberFormatException e)
							{
								return false;
							}
						}

						@Override
						public String toString()
						{
							return left.toString() + ">" + right.toString();
						}
					});
				}
			}
			else if ("or".equals(token))
			{
				if (priomax < 2)
					return i - 1;
				i = parse(
					tokens, i + 1, 1, stack);
				final Predicat right = stack.pop();
				final Predicat left = stack.pop();
				stack.push(new Predicat()
				{
					public Object value(String uri, String localName,
							String qName, Attributes attributes)
					{
						return toBoolean(left.value(
							uri, localName, qName, attributes))
								|| toBoolean(right.value(
									uri, localName, qName, attributes));
					}

					@Override
					public String toString()
					{
						return left.toString() + " or " + right.toString();
					}
				});
			}
			else if ("and".equals(token))
			{
				if (priomax < 2)
					return i - 1;
				i = parse(
					tokens, i + 1, 1, stack);
				final Predicat right = stack.pop();
				final Predicat left = stack.pop();
				stack.push(new Predicat()
				{
					public Object value(String uri, String localName,
							String qName, Attributes attributes)
					{
						return toBoolean(left.value(
							uri, localName, qName, attributes))
								&& toBoolean(right.value(
									uri, localName, qName, attributes));
					}

					@Override
					public String toString()
					{
						return left.toString() + " and " + right.toString();
					}
				});
			}
		}
		return tokens.size();
	}

	/** The original xpath */
	CharSequence xpath_;

	/** Flag to say if the xpath is a full path or no */
	boolean full;
	
	/** An array of nodes, with predicate. */
	CharSequence[] nodes_; // node name

	/** An array of predicates. */
	Predicat[] predicat_; // filter

	/** An array of position filter for each nodes. */
	int[] pos_; // -1 for all

	/** King of matching result. */
	enum Find { Nothing, Element, ElementStart, Attr, AttrStart, Text, TextStart};
	
	/**
	 * Check if an event match the corresponding node in the XPath.
	 * 
	 * @param level The node index.
	 * @param start The start of analyse (for partial path)
	 * @param uri The URI event.
	 * @param localName The localname event.
	 * @param qName The qName event.
	 * @param attributes The attributes in the event.
	 * @return What is find.
	 */
	Find match(int level, int start,String uri, String localName, String qName,
			Attributes attributes)
	{
		Find ifFind=Find.Nothing;
		boolean all=false;
		int locallevel=level-start;
		if ((locallevel < nodes_.length) && qName.equals(nodes_[locallevel]))
		{
			ifFind=Find.Element;
			all=true;
		}
		else if ((!full) && (nodes_.length>0))
		{
			if (qName.equals(nodes_[1]))
			{
				ifFind=Find.ElementStart;
				start=level-1;
				locallevel=level-start;
			}
			else if (nodes_[1].charAt(0)=='@')
			{
				all=false;
				ifFind=Find.AttrStart;
				start=level-1;
				locallevel=level-start;
			}
		}
		if (ifFind!=Find.Nothing)
		{
			Predicat predicat = predicat_[locallevel];
			boolean rc = true;
			if (predicat != null)
				rc = toBoolean(predicat.value(uri, localName, qName, attributes));
			if (rc)
			{
				if (locallevel < nodes_.length)
				{
					if (nodes_[locallevel].charAt(0) == '@')
					{
						if (nodes_[locallevel].equals("@*") || attributes.getValue(nodes_[locallevel].toString().substring(1)) != null)
							return all ? Find.Attr : Find.AttrStart;
					}
					else if ("text()".equals(nodes_[locallevel]))
					{
						return all ? Find.Text : Find.TextStart;
					}
				}
				return ifFind;
			}
		}
			
		return Find.Nothing;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		return xpath_.toString();
	}
}
