package cf;

import java.util.LinkedList;
import java.util.Arrays;
import java.util.Vector;
import java.util.Iterator;

public class methodpool 
{
	LinkedList method_pool = null;
	Vector name_index_list = null;
	
	static methodpool instance = null;
	
	public methodpool()
	{
		
	}
	
	public static methodpool getinstance()
	{
		if(instance == null)
			instance = new methodpool();
		return instance;
	}
	
	
	public Object[] getmethodsasnameindex(short ni)
	{
		Vector ms = new Vector();
		
		for(Iterator i = method_pool.iterator(); i.hasNext();)
		{
			methodinfo curmethodinfo = (methodinfo)i.next();
			if(curmethodinfo.getnameindex() == ni)
				ms.add(curmethodinfo);
		}
		
		return ms.toArray();
	}
	
	public void replacenameindex(short oldindex, short newindex)
	{
		for(Iterator i = method_pool.iterator(); i.hasNext();)
		{
			methodinfo curmethodinfo = (methodinfo)i.next();
			if(curmethodinfo.getnameindex() == oldindex)
				curmethodinfo.setnameindex(newindex);
		}
	}
	
	public void replacenameindex(short oldindex, short newindex, short class_index)
	{
		if(class_index != fileinfo.getinstance().getthisclass())
			return;
		for(Iterator i = method_pool.iterator(); i.hasNext();)
		{
			methodinfo curmethodinfo = (methodinfo)i.next();
			if(curmethodinfo.getnameindex() == oldindex)
				curmethodinfo.setnameindex(newindex);
		}
	}
	
	public boolean containsnameindex(short nameindex)
	{
		for(Iterator i = method_pool.iterator(); i.hasNext();)
		{
			methodinfo curmethodinfo = (methodinfo)i.next();
			if(curmethodinfo.getnameindex() == nameindex)
				return true;
		}
		
		return false;
	}
	
	public boolean containsdescriptorindex(short index)
	{
		for(Iterator i = method_pool.iterator(); i.hasNext();)
		{
			methodinfo curmethodinfo = (methodinfo)i.next();
			if(curmethodinfo.getdescriptorindex() == index)
				return true;
		}
		return false;
	}
	
	public void setpool(methodinfo[] mp)
	{
		method_pool = new LinkedList(Arrays.asList(mp));
		/*build up name index list*/
		name_index_list = new Vector();
		for(Iterator i = method_pool.iterator(); i.hasNext();)
		{
			methodinfo curmethodinfo = (methodinfo)i.next();
			name_index_list.add(curmethodinfo.getnameindex());
		}
	}
	
	public Object[] getpool()
	{
		return method_pool.toArray();
	}
	
	public short getpoolcount()
	{
		return (short)method_pool.size();
	}
}
