package cf;

import java.util.Vector;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Iterator;

import cp.ClassPath;

public class constantpool 
{
	public static constantpool instance = null;
	LinkedList constant_pool = null;
	LinkedList constant_add_list = null;
	Vector class_name_index_list;
	Vector field_name_index_list;
	Vector method_name_index_list;
	Vector field_name_index_list_from_removed_class;
	Vector method_name_index_list_from_removed_class;
	Vector processed_nt_index_list;
	Vector processed_name_index_list;
	
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
		updatefieldnameindexlistrc();
		updatemethodnameindexlistrc();
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
			if((curcpinfo.gettype() == constanttype.CONSTANT_Methodref)
				|| (curcpinfo.gettype() == constanttype.CONSTANT_InterfaceMethodref))
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
	
	public void updatemethodnameindexlistrc()
	{
		method_name_index_list_from_removed_class = new Vector();
		for(Iterator i = ClassPath.getInstance().getremovedclasslist().iterator(); i.hasNext();)
		{
			String cs = (String)i.next();
			for(Iterator j = constant_pool.iterator(); j.hasNext(); )
			{
				cpinfo curcpinfo = (cpinfo)j.next();
				if((curcpinfo.gettype() == constanttype.CONSTANT_Methodref)
					|| (curcpinfo.gettype() == constanttype.CONSTANT_InterfaceMethodref))
				{
					short index = ((fieldrefcpinfo)curcpinfo).getnameandtypeindex();
					cpinfo ntcpinfo = getcpinfo(index);
					short ni = ((nameandtypecpinfo)ntcpinfo).getnameindex();
					if((ntcpinfo.gettype() == constanttype.CONSTANT_NameAndType)
						&& (constantpool.getinstance().checkpool(ni).equals(cs)))
						method_name_index_list_from_removed_class.add(ni);
				}
				else if((curcpinfo.gettype() == constanttype.CONSTANT_Long) 
						|| (curcpinfo.gettype() == constanttype.CONSTANT_Double))
					j.next();
			}
		}
		
	}
	
	public boolean inmethodnameindexlistrc(short index)
	{
		return method_name_index_list_from_removed_class.contains(index);
	}
	
	public void updatefieldnameindexlistrc()
	{
		field_name_index_list_from_removed_class = new Vector();
		for(Iterator i = ClassPath.getInstance().getremovedclasslist().iterator(); i.hasNext();)
		{
			String cs = (String)i.next();
			for(Iterator j = constant_pool.iterator(); j.hasNext(); )
			{
				cpinfo curcpinfo = (cpinfo)j.next();
				if(curcpinfo.gettype() == constanttype.CONSTANT_Fieldref)
				{
					short index = ((fieldrefcpinfo)curcpinfo).getnameandtypeindex();
					cpinfo ntcpinfo = getcpinfo(index);
					short ni = ((nameandtypecpinfo)ntcpinfo).getnameindex();
					if((ntcpinfo.gettype() == constanttype.CONSTANT_NameAndType)
						&& (constantpool.getinstance().checkpool(ni).equals(cs)))
						field_name_index_list_from_removed_class.add(ni);
				}
				else if((curcpinfo.gettype() == constanttype.CONSTANT_Long) 
						|| (curcpinfo.gettype() == constanttype.CONSTANT_Double))
					j.next();
			}
		}
		
	}
	
	public boolean infieldnameindexlistrc(short index)
	{
		return field_name_index_list_from_removed_class.contains(index);
	}
	
	public void replacemethodnameindex(short oldindex, short newindex)
	{
		for(Iterator i = constant_pool.iterator(); i.hasNext();)
		{
			cpinfo curcpinfo = (cpinfo)i.next();
			if((curcpinfo.gettype() == constanttype.CONSTANT_Methodref)
				|| (curcpinfo.gettype() == constanttype.CONSTANT_InterfaceMethodref))
			{
				nameandtypecpinfo ntcpinfo = (nameandtypecpinfo)getcpinfo(((fieldrefcpinfo)curcpinfo).getnameandtypeindex());
				if(ntcpinfo.getnameindex() == oldindex)
					ntcpinfo.setnameindex(newindex);
			}
			else if((curcpinfo.gettype() == constanttype.CONSTANT_Long) 
					|| (curcpinfo.gettype() == constanttype.CONSTANT_Double))
				i.next();
		}
		
	}
	
	public void replacemethodnameindex(short oldindex, short newindex, short class_index)
	{
		for(Iterator i = constant_pool.iterator(); i.hasNext();)
		{
			cpinfo curcpinfo = (cpinfo)i.next();
			if((curcpinfo.gettype() == constanttype.CONSTANT_Methodref)
				|| (curcpinfo.gettype() == constanttype.CONSTANT_InterfaceMethodref)
				&& (((fieldrefcpinfo)curcpinfo).getclassindex() == class_index))
			{
				nameandtypecpinfo ntcpinfo = (nameandtypecpinfo)getcpinfo(((fieldrefcpinfo)curcpinfo).getnameandtypeindex());
				if(ntcpinfo.getnameindex() == oldindex)
					ntcpinfo.setnameindex(newindex);	
			}
			else if((curcpinfo.gettype() == constanttype.CONSTANT_Long) 
					|| (curcpinfo.gettype() == constanttype.CONSTANT_Double))
				i.next();
		}
		
	}
	
	public int getmethodntrefcount(short nati)
	{
		int count = 0; 
		for(Iterator i = constant_pool.iterator(); i.hasNext();)
		{
			cpinfo curcpinfo = (cpinfo)i.next();
			if(((curcpinfo.gettype() == constanttype.CONSTANT_Methodref)
				|| (curcpinfo.gettype() == constanttype.CONSTANT_InterfaceMethodref))
				&& ((fieldrefcpinfo)curcpinfo).getnameandtypeindex() == nati)
			{
				count++;
			}
			else if((curcpinfo.gettype() == constanttype.CONSTANT_Long) 
					|| (curcpinfo.gettype() == constanttype.CONSTANT_Double))
				i.next();
			
		}
		
		return count;
	}
	
	public void replacefieldnameindex(short oldindex, short newindex)
	{
		for(Iterator i = constant_pool.iterator(); i.hasNext();)
		{
			cpinfo curcpinfo = (cpinfo)i.next();
			if(curcpinfo.gettype() == constanttype.CONSTANT_Fieldref)
			{
				nameandtypecpinfo ntcpinfo = (nameandtypecpinfo)getcpinfo(((fieldrefcpinfo)curcpinfo).getnameandtypeindex());
				if(ntcpinfo.getnameindex() == oldindex)
					ntcpinfo.setnameindex(newindex);
			}
			else if((curcpinfo.gettype() == constanttype.CONSTANT_Long) 
					|| (curcpinfo.gettype() == constanttype.CONSTANT_Double))
				i.next();
		}
		
	}
	
	public short getutf8indexasstr(String str)
	{
		short count = 1;
		//System.out.println("Search: " + str);
		for(Iterator i = constant_pool.iterator(); i.hasNext(); count++)
		{
			cpinfo curcpinfo = (cpinfo)i.next();
			if(curcpinfo.gettype() == constanttype.CONSTANT_Utf8)
			{
				//System.out.println(" str" + count  + " is: " + ((utf8cpinfo)curcpinfo).getInfoString());
				if(((utf8cpinfo)curcpinfo).getInfoString().equalsIgnoreCase(str))
					return count;
			}
			else if((curcpinfo.gettype() == constanttype.CONSTANT_Long) 
					|| (curcpinfo.gettype() == constanttype.CONSTANT_Double))
				i.next();
		}
		if(constant_add_list != null)
		{
			for(Iterator i = constant_add_list.iterator(); i.hasNext(); count++)
			{
				cpinfo curcpinfo = (cpinfo)i.next();
				if(curcpinfo.gettype() == constanttype.CONSTANT_Utf8)
				{
					//System.out.println(" str" + count  + " is: " + ((utf8cpinfo)curcpinfo).getInfoString());
					
					if(((utf8cpinfo)curcpinfo).getInfoString().equalsIgnoreCase(str))
						return count;
				}
				else if((curcpinfo.gettype() == constanttype.CONSTANT_Long) 
						|| (curcpinfo.gettype() == constanttype.CONSTANT_Double))
					i.next();
			}
		}
		return 0;
	}
	
	public short getpoolcount()
	{
		return (short)(constant_pool.size() + 1);
	}
	
	public cpinfo getcpinfo(short index)
	{
		if(index <=  constant_pool.size())
			return (cpinfo)constant_pool.get(index - 1);
		else if(constant_add_list != null)
			return (cpinfo)constant_add_list.get(index - constant_pool.size() - 1);
		else
			return null;
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
			updatenameindexlist();
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
	
	public void addmethodprefix(String prefix)
	{
		for(Iterator i = constant_pool.iterator(); i.hasNext();)
		{
			cpinfo curcpinfo = (cpinfo)i.next();
			if((curcpinfo.gettype() == constanttype.CONSTANT_Methodref)
				|| (curcpinfo.gettype() == constanttype.CONSTANT_InterfaceMethodref))
				((fieldrefcpinfo)curcpinfo).addmethodprefix(prefix);
			else if((curcpinfo.gettype() == constanttype.CONSTANT_Long) 
					|| (curcpinfo.gettype() == constanttype.CONSTANT_Double))
				i.next();
		}
		/*Need to double check method pool because it can't be covered by constantpool processing for 100%*/
		String curcn = checkpool(fileinfo.getinstance().getthisclass());
		if(ClassPath.getInstance().containsClass(curcn))
		{
			Object[] oa = methodpool.getinstance().getpool();
			for(int i = 0; i < oa.length; i++)
			{
				methodinfo mi = (methodinfo)oa[i];
				mi.addmethodprefix(prefix);
			}
		}
		commitchange();
		clearprocessedntindexlist();
		clearprocessednameindexlist();
	}
	
	public void addmethodsuffix(String suffix)
	{
		for(Iterator i = constant_pool.iterator(); i.hasNext();)
		{
			cpinfo curcpinfo = (cpinfo)i.next();
			if((curcpinfo.gettype() == constanttype.CONSTANT_Methodref)
				|| (curcpinfo.gettype() == constanttype.CONSTANT_InterfaceMethodref))
				((fieldrefcpinfo)curcpinfo).addmethodsuffix(suffix);
			else if((curcpinfo.gettype() == constanttype.CONSTANT_Long) 
					|| (curcpinfo.gettype() == constanttype.CONSTANT_Double))
				i.next();
		}
		/*Need to double check method pool because it can't be covered by constantpool processing for 100%*/
		String curcn = checkpool(fileinfo.getinstance().getthisclass());
		if(ClassPath.getInstance().containsClass(curcn))
		{
			Object[] oa = methodpool.getinstance().getpool();
			for(int i = 0; i < oa.length; i++)
			{
				methodinfo mi = (methodinfo)oa[i];
				mi.addmethodsuffix(suffix);
			}
		}
		commitchange();
		clearprocessedntindexlist();
		clearprocessednameindexlist();
	}
	
	public void attachclassnameformethod()
	{
		for(Iterator i = constant_pool.iterator(); i.hasNext();)
		{
			cpinfo curcpinfo = (cpinfo)i.next();
			if((curcpinfo.gettype() == constanttype.CONSTANT_Methodref)
				|| (curcpinfo.gettype() == constanttype.CONSTANT_InterfaceMethodref))
				((fieldrefcpinfo)curcpinfo).attachclassnameformethod();
			else if((curcpinfo.gettype() == constanttype.CONSTANT_Long) 
					|| (curcpinfo.gettype() == constanttype.CONSTANT_Double))
				i.next();
		}
		/*Need to double check method pool because it can't be covered by constantpool processing for 100%*/
		String curcn = checkpool(fileinfo.getinstance().getthisclass());
		if(ClassPath.getInstance().containsClass(curcn))
		{
			Object[] oa = methodpool.getinstance().getpool();
			for(int i = 0; i < oa.length; i++)
			{
				methodinfo mi = (methodinfo)oa[i];
				mi.attachclassnameformethod();
			}
		}
		commitchange();
		clearprocessedntindexlist();
		clearprocessednameindexlist();
	}
	
	public void addfieldprefix(String prefix)
	{
		for(Iterator i = constant_pool.iterator(); i.hasNext();)
		{
			cpinfo curcpinfo = (cpinfo)i.next();
			if(curcpinfo.gettype() == constanttype.CONSTANT_Fieldref)
				((fieldrefcpinfo)curcpinfo).addfieldprefix(prefix);
			else if((curcpinfo.gettype() == constanttype.CONSTANT_Long) 
					|| (curcpinfo.gettype() == constanttype.CONSTANT_Double))
				i.next();
		}
		/*Need to double check field pool because it can't be covered by constantpool processing for 100%*/
		String curcn = checkpool(fileinfo.getinstance().getthisclass());
		if(ClassPath.getInstance().containsClass(curcn))
		{
			Object[] oa = fieldpool.getinstance().getpool();
			for(int i = 0; i < oa.length; i++)
			{
				fieldinfo fi = (fieldinfo)oa[i];
				fi.addfieldprefix(prefix);
			}
		}
		commitchange();
		clearprocessedntindexlist();
		clearprocessednameindexlist();
	}
	
	public void addfieldsuffix(String suffix)
	{
		for(Iterator i = constant_pool.iterator(); i.hasNext();)
		{
			cpinfo curcpinfo = (cpinfo)i.next();
			if(curcpinfo.gettype() == constanttype.CONSTANT_Fieldref)
				((fieldrefcpinfo)curcpinfo).addfieldsuffix(suffix);
			else if((curcpinfo.gettype() == constanttype.CONSTANT_Long) 
					|| (curcpinfo.gettype() == constanttype.CONSTANT_Double))
				i.next();
		}
		/*Need to double check field pool because it can't be covered by constantpool processing for 100%*/
		String curcn = checkpool(fileinfo.getinstance().getthisclass());
		if(ClassPath.getInstance().containsClass(curcn))
		{
			Object[] oa = fieldpool.getinstance().getpool();
			for(int i = 0; i < oa.length; i++)
			{
				fieldinfo fi = (fieldinfo)oa[i];
				fi.addfieldsuffix(suffix);
			}
		}
		commitchange();
		clearprocessedntindexlist();
		clearprocessednameindexlist();
	}
	
	public void addprocessedntindex(short nti)
	{
		if(processed_nt_index_list == null)
			processed_nt_index_list = new Vector();
		processed_nt_index_list.add(nti);
	}
	
	public boolean isprocessedntindex(short nti)
	{
		if(processed_nt_index_list == null)
			return false;
		else
			return processed_nt_index_list.contains(nti);
	}
	
	public void clearprocessedntindexlist()
	{
		processed_nt_index_list = null;
	}
	
	public void addprocessednameindex(short ni)
	{
		if(processed_name_index_list == null)
			processed_name_index_list = new Vector();
		processed_name_index_list.add(ni);
	}
	
	public boolean isprocessednameindex(short ni)
	{
		if(processed_name_index_list == null)
			return false;
		else
			return processed_name_index_list.contains(ni);
	}
	
	public void clearprocessednameindexlist()
	{
		processed_name_index_list = null;
	}
	
	public String checkpool(int index)
	{
		String s = null;
		String s1 = null;
		String s2 = null;
		
		if(constant_pool == null)
			return s;
		
		cpinfo curcpinfo;
		if(index <=  constant_pool.size())
			curcpinfo = (cpinfo)constant_pool.get(index -1);
		else if(constant_add_list != null)
			curcpinfo = (cpinfo)constant_add_list.get(index - constant_pool.size() - 1);
		else
			return null;
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
				s = s1 + "." +s2;
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
				s = s1 + " " + s2 ;
				break;
			case constanttype.CONSTANT_Utf8:
				s = ((utf8cpinfo)curcpinfo).getInfoString();
				break;
			default:
				s = "null";
				break;
		}
		
		return s;
	}
	
	
}