package cf;

import java.util.Vector;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Iterator;

public class constantpool 
{
	public static constantpool instance = null;
	LinkedList constant_pool = null;
	LinkedList constant_add_list = null;
	Vector class_name_index_list;
	Vector field_name_index_list;
	Vector method_name_index_list;
	Vector interface_name_index_list;
	
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
		constant_pool = new LinkedList(Arrays.asList(cp));
		updatenameindexlist();
	}
	
	public Object[] getpool()
	{
		return constant_pool.toArray();
	}
	
	public void updatenameindexlist()
	{
		updateclassnameindexlist();
		updatefieldnameindexlist();
		updatemethodnameindexlist();
		updateinterfacenameindexlist();
	}
	
	public void updateclassnameindexlist()
	{
		class_name_index_list = new Vector();
		for(Iterator i = constant_pool.iterator(); i.hasNext(); )
		{
			cpinfo curcpinfo = (cpinfo)i.next();
			if(curcpinfo.gettype() == constanttype.CONSTANT_Class)
				class_name_index_list.add(((classcpinfo)curcpinfo).getnameindex());
			else if((curcpinfo.gettype() == constanttype.CONSTANT_Long) 
					|| (curcpinfo.gettype() == constanttype.CONSTANT_Double))
				i.next();
		}
	}
	
	public boolean inclassnameindexlist(short index)
	{
		return class_name_index_list.contains(index);
	}
	
	public void updatefieldnameindexlist()
	{
		field_name_index_list = new Vector();
		for(Iterator i = constant_pool.iterator(); i.hasNext();)
		{
			cpinfo curcpinfo = (cpinfo)i.next();
			if(curcpinfo.gettype() == constanttype.CONSTANT_Fieldref)
			{
				short index = ((fieldrefcpinfo)curcpinfo).getnameandtypeindex();
				cpinfo ntcpinfo = getcpinfo(index);
				if(ntcpinfo.gettype() == constanttype.CONSTANT_NameAndType)
					field_name_index_list.add(((nameandtypecpinfo)ntcpinfo).getnameindex());
			}
			else if((curcpinfo.gettype() == constanttype.CONSTANT_Long) 
					|| (curcpinfo.gettype() == constanttype.CONSTANT_Double))
				i.next();
		}
	}
	
	public boolean infieldnameindexlist(short index)
	{
		return field_name_index_list.contains(index);
	}
	
	public void updatemethodnameindexlist()
	{
		method_name_index_list = new Vector();
		for(Iterator i = constant_pool.iterator(); i.hasNext(); )
		{
			cpinfo curcpinfo = (cpinfo)i.next();
			if(curcpinfo.gettype() == constanttype.CONSTANT_Fieldref)
			{
				short index = ((fieldrefcpinfo)curcpinfo).getnameandtypeindex();
				cpinfo ntcpinfo = getcpinfo(index);
				if(ntcpinfo.gettype() == constanttype.CONSTANT_NameAndType)
					method_name_index_list.add(((nameandtypecpinfo)ntcpinfo).getnameindex());
			}
			else if((curcpinfo.gettype() == constanttype.CONSTANT_Long) 
					|| (curcpinfo.gettype() == constanttype.CONSTANT_Double))
				i.next();
		}
	}
	
	public boolean inmethodnameindexlist(short index)
	{
		return method_name_index_list.contains(index);
	}
	
	public void updateinterfacenameindexlist()
	{
		interface_name_index_list = new Vector();
		for(Iterator i = constant_pool.iterator(); i.hasNext();)
		{
			cpinfo curcpinfo = (cpinfo)i.next();
			if(curcpinfo.gettype() == constanttype.CONSTANT_Fieldref)
			{
				short index = ((fieldrefcpinfo)curcpinfo).getnameandtypeindex();
				cpinfo ntcpinfo = getcpinfo(index);
				if(ntcpinfo.gettype() == constanttype.CONSTANT_NameAndType)
					interface_name_index_list.add(((nameandtypecpinfo)ntcpinfo).getnameindex());
			}
			else if((curcpinfo.gettype() == constanttype.CONSTANT_Long) 
					|| (curcpinfo.gettype() == constanttype.CONSTANT_Double))
				i.next();
		}
	}
	
	public boolean ininterfacenameindexlist(short index)
	{
		return interface_name_index_list.contains(index);
	}
	
	public short getpoolcount()
	{
		return (short)(constant_pool.size() + 1);
	}
	
	public cpinfo getcpinfo(short index)
	{
		if(constant_pool.size() <= (index -1))
			return null;
		return (cpinfo)constant_pool.get(index - 1);
	}
	
	/*When this function, need to call commitchange to add the added note into reals constant pool*/
	public short addcpinfo(cpinfo ci)
	{
		if(constant_add_list == null)
			constant_add_list = new LinkedList();
		constant_add_list.add(ci);
		/*return constant pool as it's index*/
		return (short)(constant_pool.size() + constant_add_list.size());
	}
	
	private void commitchange()
	{
		if(constant_add_list != null)
		{
			constant_pool.addAll(constant_add_list);
			constant_add_list = null;
			updateclassnameindexlist();
		}
	}
	
	public void addclassprefix(String prefix)
	{
		for(Iterator i = constant_pool.iterator() ; i.hasNext();)
		{
			cpinfo curcpinfo = (cpinfo)i.next();
			if(curcpinfo.gettype() == constanttype.CONSTANT_Class)
				((classcpinfo)curcpinfo).addclassprefix(prefix);
			/*Long and Double occupy two costant pool entries*/
			else if((curcpinfo.gettype() == constanttype.CONSTANT_Long) 
					|| (curcpinfo.gettype() == constanttype.CONSTANT_Double))
				i.next();
		}
		/*commitmodification*/
		commitchange();
	}
	
	public void addclasssuffix(String suffix)
	{
		for(Iterator i = constant_pool.iterator(); i.hasNext();)
		{
			cpinfo curcpinfo = (cpinfo)i.next();
			if(curcpinfo.gettype() == constanttype.CONSTANT_Class)
				((classcpinfo)curcpinfo).addclasssuffix(suffix);
			else if((curcpinfo.gettype() == constanttype.CONSTANT_Long) 
					|| (curcpinfo.gettype() == constanttype.CONSTANT_Double))
				i.next();
		}
		/*commitmodification*/
		commitchange();
	}
	
	public String checkpool(int index)
	{
		String s = null;
		String s1 = null;
		String s2 = null;
		
		if(constant_pool == null)
			return s;
		
		cpinfo curcpinfo = (cpinfo)constant_pool.get(index -1);
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
