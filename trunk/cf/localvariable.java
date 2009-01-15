package cf;

import util.debugger;

public class localvariable 
{
	private short start_pc;
	private short length;
	private short name_index;
	private short descriptor_index;
	private short index;
	
	public localvariable(short sp, short l, short ni, short di, short i)
	{
		start_pc = sp;
		length = l;
		name_index = ni;
		descriptor_index = di;
		index = i;
	}
	
	public short getstartpc()
	{
		return start_pc;
	}
	
	public short getlength()
	{
		return length;
	}
	
	public short getnameindex()
	{
		return name_index;
	}
	
	public short getdescriptorindex()
	{
		return descriptor_index;
	}
	
	public short getindex()
	{
		return index;
	}
	
	public void show()
	{
		debugger.log("start_pc: " + start_pc + " ;length: " + length);
		debugger.log("name_index: " + name_index + "{" + constantpool.getinstance().checkpool(name_index) + "}");
		debugger.log("descriptor_index: " + descriptor_index + "{" + constantpool.getinstance().checkpool(descriptor_index) + "}");
		debugger.log("index: " + index);
	}
}
