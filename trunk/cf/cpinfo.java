package cf;

import util.debugger;

public class cpinfo 
{
	private byte tag;
	
	public cpinfo(byte tag)
	{
		this.tag = tag;
	}
	
	public byte gettype()
	{
		return tag;
	}
	
	public void addclassprefix(String prefix)
	{
		
	}
	
	public void addclasssuffix(String suffix)
	{
		
	}
	
	public void show()
	{
		debugger.log("Constant Type: " + toString());
	}
	
	public String toString()
	{
		String s;
		switch(tag)
		{
			case constanttype.CONSTANT_Class:
				s = "Class";
				break;
			case constanttype.CONSTANT_Fieldref:
				s = "Fieldref";
				break;
			case constanttype.CONSTANT_Methodref:
				s = "Methodref";
				break;
			case constanttype.CONSTANT_InterfaceMethodref:
				s = "InterfaceMethodref";
				break;
			case constanttype.CONSTANT_String:
				s = "String";
				break;
			case constanttype.CONSTANT_Integer:
				s = "Integer";
				break;
			case constanttype.CONSTANT_Float:
				s = "Float";
				break;
			case constanttype.CONSTANT_Long:
				s = "Long";
				break;
			case constanttype.CONSTANT_Double:
				s = "Double";
				break;
			case constanttype.CONSTANT_NameAndType:
				s = "NameAndType";
				break;
			case constanttype.CONSTANT_Utf8:
				s = "Utf8";
				break;
			default:
				s = "Null";
				break;
		}
		return s;
	}
}
