package cf;

import util.debugger;

public class innerclass 
{
	private short inner_class_info_index;
	private short outer_class_info_index;
	private short inner_name_index;
	private short inner_class_access_flags;
	
	public innerclass(short icii, short ocii, short ini, short icaf)
	{
		inner_class_info_index = icii;
		outer_class_info_index = ocii;
		inner_name_index = ini;
		inner_class_access_flags = icaf;
	}
	
	public short getinnerclassinfoindex()
	{
		return inner_class_info_index;
	}
	
	public short getouterclassinfoindex()
	{
		return outer_class_info_index;
	}
	
	public short getinnernameindex()
	{
		return inner_name_index;
	}
	
	public short getinnerclassaccessflags()
	{
		return inner_class_access_flags;
	}
	
	public void show()
	{
		debugger.log("inner_class_info_index: " + inner_class_info_index + 
				"{" + constantpool.getinstance().checkpool(inner_class_info_index) + "}");
		debugger.log("outer_class_info_index: " + outer_class_info_index + 
				"{" + constantpool.getinstance().checkpool(outer_class_info_index) + "}");
		debugger.log("inner_name_index: " + inner_name_index + 
				"{" + constantpool.getinstance().checkpool(inner_name_index) + "}");
		debugger.log("inner_class_access_flags: " + classaccessflag.getflagstr(inner_class_access_flags));
	}
	
	public String toString()
	{
		return super.toString();
	}
}
