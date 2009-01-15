package cf;

public class classaccessflag extends accessflag 
{
	public final static short ACC_SUPER = 0x0020;
	
	public classaccessflag()
	{
		super();
	}
		
	public static String getflagstr(short flag)
	{
		String s = accessflag.getflagstr(flag);
		StringBuffer sb = new StringBuffer(s);
		
		if((flag & ACC_SUPER) == ACC_SUPER)
			sb.append("super ");
		
		return sb.toString();
	}
}
