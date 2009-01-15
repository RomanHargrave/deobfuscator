package cf;

import util.debugger;

public class longcpinfo extends cpinfo 
{
	private int high_bytes;
	private int low_bytes;
	
	public longcpinfo(byte tag, int hbs, int lbs)
	{
		super(tag);
		high_bytes = hbs;
		low_bytes = lbs;
	}
	
	public int gethighbytes()
	{
		return high_bytes;
	}
	
	public int getlowbytes()
	{
		return low_bytes;
	}
	
	public void show()
	{
		super.show();
		debugger.log("high_bytes: " + debugger.hexstr(high_bytes, 8));
		debugger.log("low_bytes: " + debugger.hexstr(low_bytes, 8));
	}
	
	public String toString()
	{
		return (super.toString() + "(" + debugger.hexstr(high_bytes, 8) + ":" + debugger.hexstr(low_bytes, 8) + ")");
	}
}
