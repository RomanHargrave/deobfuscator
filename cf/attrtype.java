package cf;

public class attrtype 
{
	public final static int ATTR_CONSTANT = 1;
	public final static int ATTR_CODE = 2;
	public final static int ATTR_EXCEPTIONS = 3;
	public final static int ATTR_INNERCLASSES = 4;
	public final static int ATTR_SYNTHETIC = 5;
	public final static int ATTR_SOURCEFILE = 6;
	public final static int ATTR_LINENUMTBL = 7;
	public final static int ATTR_LOCALVARTBL = 8;
	public final static int ATTR_DEPRECATED = 9;
	public final static int ATTR_NULL = 0;
	
	public attrtype()
	{
		
	}
	
	public static int checktype(short ani)
	{
		cpinfo curcpinfo = constantpool.getinstance().getcpinfo(ani);
		if(curcpinfo == null)
			return ATTR_NULL; 
		if(curcpinfo.gettype() != constanttype.CONSTANT_Utf8)
			return ATTR_NULL;
		utf8cpinfo curutf8cpinfo = (utf8cpinfo)curcpinfo;
		String typestring = null;
		try 
		{
			 typestring = new String(curutf8cpinfo.getbytes(), "UTF-8");
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
		//System.out.println(typestring);
		if(typestring.compareToIgnoreCase("ConstantValue") == 0)
		{
			return ATTR_CONSTANT;
		}
		else if(typestring.compareToIgnoreCase("Code") == 0)
		{
			return ATTR_CODE;
		}
		else if(typestring.compareToIgnoreCase("Exceptions") == 0)
		{
			return ATTR_EXCEPTIONS;
		}
		else if(typestring.compareToIgnoreCase("InnerClasses") == 0)
		{
			return ATTR_INNERCLASSES;
		}
		else if(typestring.compareToIgnoreCase("Synthetic") == 0)
		{
			return ATTR_SYNTHETIC;
		}
		else if(typestring.compareToIgnoreCase("SourceFile") == 0)
		{
			return ATTR_SOURCEFILE;
		}
		else if(typestring.compareToIgnoreCase("LineNumberTable") == 0)
		{
			return ATTR_LINENUMTBL;
		}
		else if(typestring.compareToIgnoreCase("LocalVariableTable") == 0)
		{
			return ATTR_LOCALVARTBL;
		}
		else if(typestring.compareToIgnoreCase("Deprecated") == 0)
		{
			return ATTR_DEPRECATED;
		}
		
		return ATTR_NULL;
	}
}
