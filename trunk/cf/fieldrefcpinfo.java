package cf;

import util.debugger;

public class fieldrefcpinfo extends cpinfo 
{
	private short class_index;
	private short name_and_type_index;
	
	public fieldrefcpinfo(byte tag, short ci, short nati)
	{
		super(tag);
		class_index = ci;
		name_and_type_index = nati;
	}
	
	public short getclassindex()
	{
		return class_index;
	}
	
	public short getnameandtypeindex()
	{
		return name_and_type_index;
	}
	
	public void show()
	{
		super.show();
		debugger.log("class_index: " + class_index);
		debugger.log("name_and_type_index: " + name_and_type_index);
	}
	
	public String toString()
	{
		return (super.toString() + "(class_index:" + class_index + "|" + "name_and_type_index:" + name_and_type_index + ")");
	}
}
