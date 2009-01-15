package cf;

import util.debugger;

public class classcpinfo extends cpinfo 
{
	private short name_index;
	
	public classcpinfo(byte tag, short ni)
	{
		super(tag);
		name_index = ni;
	}
	
	public short getnameindex()
	{
		return name_index;
	}
	
	public void addclassprefix(String prefix)
	{
		cpinfo curcpinfo = constantpool.getinstance().getcpinfo(name_index);
		if(curcpinfo.gettype() == constanttype.CONSTANT_Utf8)
			((utf8cpinfo)curcpinfo).addclassprefix(prefix);
	}
	
	public void addclasssuffix(String suffix)
	{
		cpinfo curcpinfo = constantpool.getinstance().getcpinfo(name_index);
		if(curcpinfo.gettype() == constanttype.CONSTANT_Utf8)
			((utf8cpinfo)curcpinfo).addclasssuffix(suffix);
	}
	
	public void show()
	{
		super.show();
		debugger.log("name_index: " + name_index + "{" + constantpool.getinstance().checkpool(name_index) + "}");
	}
	
	public String toString()
	{
		return (super.toString() + "(name_index:" + name_index + ")");
	}
}
