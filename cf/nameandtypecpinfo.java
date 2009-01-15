package cf;

import util.debugger;

public class nameandtypecpinfo extends cpinfo 
{
	private short name_index;
	private short descriptor_index;
	
	public nameandtypecpinfo(byte tag, short ni, short di)
	{
		super(tag);
		name_index = ni;
		descriptor_index = di;
	}
	
	public short getnameindex()
	{
		return name_index;
	}
	
	public short getdescriptorindex()
	{
		return descriptor_index;
	}
	
	public void show()
	{
		super.show();
		debugger.log("name_index: " + name_index + "{" + constantpool.getinstance().checkpool(name_index) + "}" );
		debugger.log("descriptor_index: " + descriptor_index + "{" + constantpool.getinstance().checkpool(descriptor_index) + "}" );
	}
}
