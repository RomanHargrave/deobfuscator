package cf;

import java.util.Vector;

public class constantpool 
{
	public static constantpool instance = null;
	cpinfo[] constant_pool = null;
	Vector class_name_index_list;
	Vector 
	
	public constantpool()
	{
		
	}
	
	public static constantpool getinstance()
	{
		if(instance == null)
			instance = new constantpool();
		return instance;
	}
	
	public void setpool(cpinfo[] cp)
	{
		constant_pool = cp;
	}
	
	public void updatenameindexlist()
	{
		
	}
	
	public int getpoollength()
	{
		return constant_pool.length;
	}
	
	public cpinfo getcpinfo(short index)
	{
		if(constant_pool.length <= (index -1))
			return null;
		return constant_pool[index - 1];
	}
	
	public void addclassprefix(String prefix)
	{
		for(int i = 0; i < constant_pool.length; i ++)
		{
			if(constant_pool[i].gettype() == constanttype.CONSTANT_Class)
				((classcpinfo)constant_pool[i]).addclassprefix(prefix);
			/*Long and Double occupy two costant pool entries*/
			if((constant_pool[i].gettype() == constanttype.CONSTANT_Long) 
					|| (constant_pool[i].gettype() == constanttype.CONSTANT_Double))
				i++;
		}
	}
	
	public void addclasssuffix(String suffix)
	{
		for(int i = 0; i < constant_pool.length; i ++)
		{
			if(constant_pool[i].gettype() == constanttype.CONSTANT_Class)
				((classcpinfo)constant_pool[i]).addclasssuffix(suffix);
			if((constant_pool[i].gettype() == constanttype.CONSTANT_Long) 
					|| (constant_pool[i].gettype() == constanttype.CONSTANT_Double))
				i++;
		}
	}
	
	public String checkpool(int index)
	{
		String s = null;
		String s1 = null;
		String s2 = null;
		
		if(constant_pool == null)
			return s;
		
		cpinfo curcpinfo = constant_pool[(index - 1)];
		switch(curcpinfo.gettype())
		{
			case constanttype.CONSTANT_Class:
				s =  checkpool(((classcpinfo)curcpinfo).getnameindex());
				break;
			case constanttype.CONSTANT_Fieldref:
			case constanttype.CONSTANT_Methodref:
			case constanttype.CONSTANT_InterfaceMethodref:
				s1 = checkpool(((fieldrefcpinfo)curcpinfo).getclassindex());
				s2 = checkpool(((fieldrefcpinfo)curcpinfo).getnameandtypeindex());
				s = "(" + s1 + ")" + "(" + s2 + ")";
				break;
			case constanttype.CONSTANT_String:
				s = checkpool(((stringcpinfo)curcpinfo).getstringindex());
				break;
			case constanttype.CONSTANT_Integer:
			case constanttype.CONSTANT_Float:
				s = ((integercpinfo)curcpinfo).toString();
				break;
			case constanttype.CONSTANT_Long:
			case constanttype.CONSTANT_Double:
				s = ((longcpinfo)curcpinfo).toString();
				break;
			case constanttype.CONSTANT_NameAndType:
				s1 = checkpool(((nameandtypecpinfo)curcpinfo).getnameindex());
				s2 = checkpool(((nameandtypecpinfo)curcpinfo).getdescriptorindex());
				s = "(" + s1 + ")" + "(" + s2 + ")";
				break;
			case constanttype.CONSTANT_Utf8:
				s = ((utf8cpinfo)curcpinfo).toString();
				break;
			default:
				s = "null";
				break;
		}
		
		return s;
	}
	
	
}
