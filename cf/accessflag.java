package cf;


public class accessflag 
{
	public final static short ACC_PUBLIC = 0x0001;
	public final static short ACC_PRIVATE = 0x0002;
	public final static short ACC_PROTECTED = 0x0004;
	public final static short ACC_STATIC = 0x0008;
	public final static short ACC_FINAL = 0x0010;
	public final static short ACC_VOLATILE = 0x0040;
	public final static short ACC_TRANSIENT = 0x0080;
	public final static short ACC_NATIVE = 0x0100;
	public final static short ACC_INTERFACE = 0x0200;
	public final static short ACC_ABSTRACT = 0x0400;
	public final static short ACC_STRICT = 0x0800;
	
	public accessflag()
	{
		
	}
		
	public static String getflagstr(short flag)
	{
		StringBuffer sb = new StringBuffer();
		
		if((flag & ACC_PUBLIC) == ACC_PUBLIC)
			sb.append("public ");
		if((flag & ACC_PRIVATE) == ACC_PRIVATE)
			sb.append("private ");
		if((flag & ACC_PROTECTED) == ACC_PROTECTED)
			sb.append("protected ");
		if((flag & ACC_STATIC) == ACC_STATIC)
			sb.append("static ");
		if((flag & ACC_FINAL) == ACC_FINAL)
			sb.append("final ");
		if((flag & ACC_VOLATILE) == ACC_VOLATILE)
			sb.append("volatile ");
		if((flag & ACC_TRANSIENT) == ACC_TRANSIENT)
			sb.append("transient ");
		if((flag & ACC_NATIVE) == ACC_NATIVE)
			sb.append("native ");
		if((flag & ACC_INTERFACE) == ACC_INTERFACE)
			sb.append("interface ");
		if((flag & ACC_ABSTRACT) == ACC_ABSTRACT)
			sb.append("abstract ");
		if((flag & ACC_STRICT) == ACC_STRICT)
			sb.append("strict ");
		
		return sb.toString();
	}
}
