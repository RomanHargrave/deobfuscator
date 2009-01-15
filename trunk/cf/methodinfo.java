package cf;

import util.debugger;

public class methodinfo 
{
	private short access_flag;
	private short name_index;
	private short descriptor_index;
	private short attributes_count;
	private attr[] attributes;
	
	public methodinfo(short af, short ni, short di, short ac, attr[] as)
	{
		access_flag = af;
		name_index = ni;
		descriptor_index = di;
		attributes_count = ac;
		attributes = as;
	}
	
	public short getaccessflag()
	{
		return access_flag;
	}
	
	public short getnameindex()
	{
		return name_index;
	}
	
	public short getdescriptorindex()
	{
		return descriptor_index;
	}
	
	public short getattributescount()
	{
		return attributes_count;
	}
	
	public attr[] getattributes()
	{
		return attributes;
	}
	
	public void show()
	{
		debugger.log("access_flag: " + access_flag + "{" + methodaccessflag.getflagstr(access_flag) + "}");
		debugger.log("name_index: " + name_index + "{" + constantpool.getinstance().checkpool(name_index) + "}");
		debugger.log("descriptor_index: " + descriptor_index + "{" + constantpool.getinstance().checkpool(descriptor_index) + "}");
		for(int i = 0; i < attributes_count; i++)
		{
			debugger.log("attribute: " + i);
			debugger.log("------------");
			attributes[i].show();
		}
	}
}
