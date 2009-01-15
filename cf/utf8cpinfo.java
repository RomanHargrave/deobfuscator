package cf;

import cp.ClassPath;
import util.debugger;
import java.io.File;

public class utf8cpinfo extends cpinfo 
{
	private short length;
	private byte[] bytes;
	private String bytesstr;
	
	ClassPath cp = ClassPath.getInstance();
	
	public utf8cpinfo(byte tag, short l, byte[] bs)
	{
		super(tag);
		length = l;
		bytes = bs;
		try
		{
			bytesstr = new String(bytes, "UTF-8");
		}
		catch(Exception e)
		{
			
		}
	}
	
	public byte[] getbytes()
	{
		return bytes;
	}
	
	public short getlength()
	{
		return length;
	}
	
	public void addclassprefix(String prefix)
	{
		if(!cp.containsClass(bytesstr))
			return;
		String pn = cp.getPackageName(bytesstr);
		String cn = cp.getClassName(bytesstr);
		StringBuffer sb = new StringBuffer(pn);
		if(!pn.equals(""))
			sb.append(File.separator);
		sb.append(prefix);
		sb.append(cn);
		bytesstr = sb.toString();
		try
		{
			length += prefix.getBytes("UTF-8").length;
			bytes = bytesstr.getBytes("UTF-8");
		}
		catch(Exception e)
		{
			
		}
	}
	
	public void addclasssuffix(String suffix)
	{
		if(!cp.containsClass(bytesstr))
			return;
		StringBuffer sb = new StringBuffer(bytesstr);
		sb.append(suffix);
		bytesstr = sb.toString();
		try
		{
			length += suffix.getBytes("UTF-8").length;
			bytes = bytesstr.getBytes("UTF-8");
		}
		catch(Exception e)
		{
			
		}
	}
	
	public void show()
	{
		super.show();
		debugger.log("length: " + length);
		debugger.log("utf8inf: " + bytesstr);
	}
	
	public String toString()
	{
		return (super.toString() + "｛" + bytesstr + "｝");
	}
}
