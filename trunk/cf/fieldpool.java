package cf;

import java.util.LinkedList;
import java.util.Arrays;
import java.util.Vector;
import java.util.Iterator;

public class fieldpool 
{
	LinkedList field_pool = null;
	Vector name_index_list = null;
	
	static fieldpool instance = null;
	
	public fieldpool()
	{
		
	}
	
	public static fieldpool getinstance()
	{
		if(instance == null)
			instance = new fieldpool();
		return instance;
	}
	
	
	public Object[] getfieldsasnameindex(short ni)
	{
		Vector fs = new Vector();
		
		for(Iterator i = field_pool.iterator(); i.hasNext();)
		{
			fieldinfo curmethodinfo = (fieldinfo)i.next();
			if(curmethodinfo.getnameindex() == ni)
				fs.add(curmethodinfo);
		}
		
		return fs.toArray();
	}
	
	public void replacenameindex(short oldindex, short newindex)
	{
		for(Iterator i = field_pool.iterator(); i.hasNext();)
		{
			fieldinfo curfieldinfo = (fieldinfo)i.next();
			if(curfieldinfo.getnameindex() == oldindex)
				curfieldinfo.setnameindex(newindex);
		}
	}
	
	public boolean containsnameindex(short nameindex)
	{
		for(Iterator i = field_pool.iterator(); i.hasNext();)
		{
			fieldinfo curfieldinfo = (fieldinfo)i.next();
			if(curfieldinfo.getnameindex() == nameindex)
				return true;
		}
		return false;
	}
	
	public void setpool(fieldinfo[] fp)
	{
		field_pool = new LinkedList(Arrays.asList(fp));
		/*build up name index list*/
		name_index_list = new Vector();
		for(Iterator i = field_pool.iterator(); i.hasNext();)
		{
			fieldinfo curfieldinfo = (fieldinfo)i.next();
			name_index_list.add(curfieldinfo.getnameindex());
		}
	}
	
	public Object[] getpool()
	{
		return field_pool.toArray();
	}
	
	public short getpoolcount()
	{
		return (short)field_pool.size();
	}
	
}
