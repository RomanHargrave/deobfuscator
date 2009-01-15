package cf;

public class methodaccessflag extends accessflag 
{
	public final static short ACC_SYNCHRONIZED = 0x0020;
	
	public methodaccessflag()
	{
		super();
	}
	
	public static String getflagstr(short flag)
	{
		String s = accessflag.getflagstr(flag);
		StringBuffer sb = new StringBuffer(s);
		if((flag & ACC_SYNCHRONIZED) == ACC_SYNCHRONIZED)
			sb.append("synchronized ");
		
		return sb.toString();
	}
}
