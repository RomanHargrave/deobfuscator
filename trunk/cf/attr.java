package cf;

import util.debugger;

public class attr 
{
	protected short attribute_name_index;
	protected int attribute_length;
	protected int type;
	
	public attr(int type, short ani, int al)
	{
		this.attribute_name_index = ani;
		this.attribute_length = al;
		this.type = type;
	}
	
	public int gettype()
	{
		return type;
	}
	
	public short getattributenameindex()
	{
		return attribute_name_index;
	}
	
	public int getattributelength()
	{
		return attribute_length;
	}
		
	public void show()
	{
		switch(type)
		{	
			case attrtype.ATTR_CONSTANT:
				((constantvalueattr)this).showforsuper();
				break;
			case attrtype.ATTR_CODE:
				((codeattr)this).showforsuper();
				break;
			case attrtype.ATTR_EXCEPTIONS:
				((exceptionsattr)this).showforsuper();
				break;
			case attrtype.ATTR_INNERCLASSES:
				((innerclassesattr)this).showforsuper();
				break;
			case attrtype.ATTR_SYNTHETIC:
				((syntheticattr)this).showforsuper();	
				break;
			case attrtype.ATTR_SOURCEFILE:
				((sourcefileattr)this).showforsuper();
				break;
			case attrtype.ATTR_LINENUMTBL:
				((linenumbertableattr)this).showforsuper();
				break;
			case attrtype.ATTR_LOCALVARTBL:
				((localvariabletableattr)this).showforsuper();
				break;
			case attrtype.ATTR_DEPRECATED:
				((deprecatedattr)this).showforsuper();
				break;
			case attrtype.ATTR_NULL:
				
				break;
		}
	}
	
	public void showforsuper()
	{
		debugger.log("Attribute: " + toString());
	}
	
	public String toString()
	{
		String s = null;
		
		switch(type)
		{	
			case attrtype.ATTR_CONSTANT:
				s = "ConstantValue";
				break;
			case attrtype.ATTR_CODE:
				s = "Code";
				break;
			case attrtype.ATTR_EXCEPTIONS:
				s = "Exceptions";
				break;
			case attrtype.ATTR_INNERCLASSES:
				s = "InnerClasses";
				break;
			case attrtype.ATTR_SYNTHETIC:
				s = "Synthetic";	
				break;
			case attrtype.ATTR_SOURCEFILE:
				s = "SourceFile";
				break;
			case attrtype.ATTR_LINENUMTBL:
				s = "LineNumberTable";
				break;
			case attrtype.ATTR_LOCALVARTBL:
				s = "LocalVariableTable";
				break;
			case attrtype.ATTR_DEPRECATED:
				s = "Deprecated";
				break;
			case attrtype.ATTR_NULL:
				s = "Null";
				break;
		}
		
		return s;
	}
}
