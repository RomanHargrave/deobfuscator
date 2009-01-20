package cf;

import util.debugger;

public class integercpinfo extends cpinfo 
{
	private int bytes;
	public integercpinfo(byte tag, int bs)
	{
		super(tag);
		bytes = bs;
	}
	
	public int getbytes()
	{
		return bytes;
	}
	
	public void show()
	{
		super.show();
		debugger.log("bytes: " + debugger.hexstr(bytes, 8));
	}
	
	public String toString()
	{
		return (super.toString() + "(" + debugger.hexstr(bytes, 8) + ")");
	}
}
