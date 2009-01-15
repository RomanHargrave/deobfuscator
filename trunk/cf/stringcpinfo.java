package cf;

import util.debugger;

public class stringcpinfo extends cpinfo 
{
	private short string_index;
	
	public stringcpinfo(byte tag, short si)
	{
		super(tag);
		string_index = si;
	}
	public short getstringindex()
	{
		return string_index;
	}
	
	public void show()
	{
		super.show();
		debugger.log("string_index: " + string_index);
	}
	
	public String toString()
	{
		return (super.toString() + "(string_index:" + string_index + ")");
	}
}
