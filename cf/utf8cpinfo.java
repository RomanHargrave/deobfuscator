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
		setbytesstr(sb.toString());
	}
	
	public void addclasssuffix(String suffix)
	{
		if(!cp.containsClass(bytesstr))
			return;
		StringBuffer sb = new StringBuffer(bytesstr);
		sb.append(suffix);
		setbytesstr(sb.toString());
	}
	
	private void addprefix(String prefix)
	{
		StringBuffer sb = new StringBuffer(prefix);
		sb.append(bytesstr);
		setbytesstr(sb.toString());
	}
	
	private void addsuffix(String suffix)
	{
		StringBuffer sb = new StringBuffer(bytesstr);
		sb.append(suffix);
		setbytesstr(sb.toString());
	}
	
	public void addmethodprefix(String prefix)
	{
		addprefix(prefix);
	}
	
	public void addmethodsuffix(String suffix)
	{
		addsuffix(suffix);
	}
	
	public void addfieldprefix(String prefix)
	{
		addprefix(prefix);
	}
	
	public void addfieldsuffix(String suffix)
	{
		addsuffix(suffix);
	}
	
	public void setbytesstr(String bs)
	{
		bytesstr = bs;
		try
		{
			bytes = bytesstr.getBytes("UTF-8");
			length = (short)bytes.length;
		}
		catch(Exception e)
		{
			
		}
	}
	
	public String getInfoString()
	{
		return bytesstr;
	}
	
	public void show()
	{
		super.show();
		debugger.log("length: " + length);
		debugger.log("utf8inf: " + bytesstr);
	}
	
	public String toString()
	{
		return (super.toString() + "{" + bytesstr + "}");
	}
}
