package cf;

import java.io.File;
import cp.ClassPath;
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
	
	public void setnameandtypeindex(short nati)
	{
		name_and_type_index = nati; 
	}
	
	public void addmethodprefix(String prefix)
	{
		String cs = constantpool.getinstance().checkpool(class_index);
		/*if it is a class out of current classpath, then return*/
		if(!ClassPath.getInstance().containsClass(cs)
			|| constantpool.getinstance().isprocessedntindex(name_and_type_index))
			return;
		short ni; 
		cpinfo curcpinfo = constantpool.getinstance().getcpinfo(name_and_type_index);
		if(curcpinfo.gettype() != constanttype.CONSTANT_NameAndType)
			return;
		ni = ((nameandtypecpinfo)curcpinfo).getnameindex();
		cpinfo nxtcpinfo = constantpool.getinstance().getcpinfo(ni);
		if((nxtcpinfo.gettype() != constanttype.CONSTANT_Utf8)
			|| constantpool.getinstance().checkpool(ni).equals("<init>"))
			return;
		constantpool.getinstance().addprocessedntindex(name_and_type_index);
		short index = constantpool.getinstance().getutf8indexasstr(prefix + ((utf8cpinfo)nxtcpinfo).getInfoString());
		if(index != 0)
		{
			((nameandtypecpinfo)curcpinfo).setnameindex(index);
			return;
		}
		if(constantpool.getinstance().inclassnameindexlist(ni)
				|| constantpool.getinstance().infieldnameindexlist(ni)
				|| fieldpool.getinstance().containsnameindex(ni)
				|| constantpool.getinstance().inmethodnameindexlistrc(ni))
		{
			utf8cpinfo ucpinfo = new utf8cpinfo(nxtcpinfo.gettype(), ((utf8cpinfo)nxtcpinfo).getlength(), ((utf8cpinfo)nxtcpinfo).getbytes());
			ucpinfo.addmethodprefix(prefix);
			index = constantpool.getinstance().addcpinfo(ucpinfo);
			((nameandtypecpinfo)curcpinfo).setnameindex(index);
			//constantpool.getinstance().replacemethodnameindex(ni, index);
			//methodpool.getinstance().replacenameindex(ni, index);
			//constantpool.getinstance().addprocessednameindex(index);
		}
		else if(!methodpool.getinstance().containsnameindex(ni))
			((utf8cpinfo)nxtcpinfo).addmethodprefix(prefix);
		constantpool.getinstance().addprocessedntindex(name_and_type_index);
		//constantpool.getinstance().addprocessednameindex(ni);
	}
	
	public void addmethodsuffix(String suffix)
	{
		String cs = constantpool.getinstance().checkpool(class_index);
		/*if it is a class out of current classpath, then return*/
		if(!ClassPath.getInstance().containsClass(cs)
				|| constantpool.getinstance().isprocessedntindex(name_and_type_index))
			return;
		short ni; 
		cpinfo curcpinfo = constantpool.getinstance().getcpinfo(name_and_type_index);
		if(curcpinfo.gettype() != constanttype.CONSTANT_NameAndType)
			return;
		ni = ((nameandtypecpinfo)curcpinfo).getnameindex();
		cpinfo nxtcpinfo = constantpool.getinstance().getcpinfo(ni);
		if((nxtcpinfo.gettype() != constanttype.CONSTANT_Utf8)
			|| constantpool.getinstance().checkpool(ni).equals("<init>"))
			return;
		constantpool.getinstance().addprocessedntindex(name_and_type_index);
		short index = constantpool.getinstance().getutf8indexasstr(((utf8cpinfo)nxtcpinfo).getInfoString() + suffix);
		if(index != 0)
		{
			((nameandtypecpinfo)curcpinfo).setnameindex(index);
			return;
		}
		if(constantpool.getinstance().inclassnameindexlist(ni)
				|| constantpool.getinstance().infieldnameindexlist(ni)
				|| fieldpool.getinstance().containsnameindex(ni)
				|| constantpool.getinstance().inmethodnameindexlistrc(ni))
		{
			utf8cpinfo ucpinfo = new utf8cpinfo(nxtcpinfo.gettype(), ((utf8cpinfo)nxtcpinfo).getlength(), ((utf8cpinfo)nxtcpinfo).getbytes());
			ucpinfo.addmethodsuffix(suffix);
			index = constantpool.getinstance().addcpinfo(ucpinfo);
			((nameandtypecpinfo)curcpinfo).setnameindex(index);
			//constantpool.getinstance().replacemethodnameindex(ni, index);
			//methodpool.getinstance().replacenameindex(ni, index);
			//constantpool.getinstance().addprocessednameindex(index);
		}
		else if(!methodpool.getinstance().containsnameindex(ni))
			((utf8cpinfo)nxtcpinfo).addmethodsuffix(suffix);
		//constantpool.getinstance().addprocessednameindex(ni);
	}
	
	public void attachclassnameformethod()
	{
		String cs = constantpool.getinstance().checkpool(class_index);
		/*Skip for methods of those classes, not included in classpath*/
		if(!ClassPath.getInstance().containsClass(cs))
			return;
		short ni;
		cpinfo curcpinfo = constantpool.getinstance().getcpinfo(name_and_type_index);
		ni = ((nameandtypecpinfo)curcpinfo).getnameindex();
		//System.out.println(cs + "_" + constantpool.getinstance().checkpool(ni));
		/*Skip error constant type, processed name index, constructor*/
		if(curcpinfo.gettype() != constanttype.CONSTANT_NameAndType
			|| constantpool.getinstance().checkpool(ni).equals("<init>"))
			return;
		/*those methodref entries with same name_and_type_index must come from different class, 
		 * then create a new name and type entry for it*/
		if(constantpool.getinstance().isprocessedntindex(name_and_type_index))
		{
			curcpinfo = (cpinfo) new nameandtypecpinfo(curcpinfo.gettype(), 
					((nameandtypecpinfo)curcpinfo).getnameindex(), ((nameandtypecpinfo)curcpinfo).getdescriptorindex());
			short nati = constantpool.getinstance().addcpinfo(curcpinfo);
			this.setnameandtypeindex(nati);
		}
		cpinfo nxtcpinfo = constantpool.getinstance().getcpinfo(ni);
		if(nxtcpinfo.gettype() != constanttype.CONSTANT_Utf8)
			return;
		StringBuffer sb = new StringBuffer(cs.replaceAll(File.separator, "_"));
		sb.append("_");
		constantpool.getinstance().addprocessedntindex(name_and_type_index);
		short index = constantpool.getinstance().getutf8indexasstr(sb.toString() + ((utf8cpinfo)nxtcpinfo).getInfoString());
		if(index != 0)
		{
			((nameandtypecpinfo)curcpinfo).setnameindex(index);
			return;
		}
		/*If the specified name idnex, had also been used as class name | field name | current class's method, 
		 * then we need to create a new utf8 entry for it */
		utf8cpinfo ucpinfo = new utf8cpinfo(nxtcpinfo.gettype(), ((utf8cpinfo)nxtcpinfo).getlength(), ((utf8cpinfo)nxtcpinfo).getbytes());
		ucpinfo.addfieldprefix(sb.toString());
		index = constantpool.getinstance().addcpinfo(ucpinfo);
		((nameandtypecpinfo)curcpinfo).setnameindex(index);
		//methodpool.getinstance().replacenameindex(ni, index, class_index);
		
	}
	
	public void addfieldprefix(String prefix)
	{
		String cs = constantpool.getinstance().checkpool(class_index);
		/*if it is a class out of current classpath, then return*/
		if(!ClassPath.getInstance().containsClass(cs)
				|| constantpool.getinstance().isprocessedntindex(name_and_type_index))
			return;
		short ni; 
		cpinfo curcpinfo = constantpool.getinstance().getcpinfo(name_and_type_index);
		if(curcpinfo.gettype() != constanttype.CONSTANT_NameAndType)
			return;
		ni = ((nameandtypecpinfo)curcpinfo).getnameindex();
		//System.out.println(cs + "_" + constantpool.getinstance().checkpool(ni));
		cpinfo nxtcpinfo = constantpool.getinstance().getcpinfo(ni);
		if(nxtcpinfo.gettype() != constanttype.CONSTANT_Utf8)
			return;
		constantpool.getinstance().addprocessedntindex(name_and_type_index);
		short index = constantpool.getinstance().getutf8indexasstr(prefix + ((utf8cpinfo)nxtcpinfo).getInfoString());
		if(index != 0)
		{
			((nameandtypecpinfo)curcpinfo).setnameindex(index);
			return;
		}
		if(constantpool.getinstance().inclassnameindexlist(ni)
				|| constantpool.getinstance().inmethodnameindexlist(ni)
				|| methodpool.getinstance().containsnameindex(ni)
				|| constantpool.getinstance().infieldnameindexlistrc(ni))
		{
			utf8cpinfo ucpinfo = new utf8cpinfo(nxtcpinfo.gettype(), ((utf8cpinfo)nxtcpinfo).getlength(), ((utf8cpinfo)nxtcpinfo).getbytes());
			ucpinfo.addfieldprefix(prefix);
			index = constantpool.getinstance().addcpinfo(ucpinfo);
			((nameandtypecpinfo)curcpinfo).setnameindex(index);
			//constantpool.getinstance().replacefieldnameindex(ni, index);
			//fieldpool.getinstance().replacenameindex(ni, index);
			//constantpool.getinstance().addprocessednameindex(index);
		}
		else if(!fieldpool.getinstance().containsnameindex(ni))
		{
			//System.out.println(prefix + constantpool.getinstance().checkpool(ni));
			((utf8cpinfo)nxtcpinfo).addfieldprefix(prefix);
		}
		//constantpool.getinstance().addprocessednameindex(ni);
	}
	
	public void addfieldsuffix(String suffix)
	{
		String cs = constantpool.getinstance().checkpool(class_index);
		/*if it is a class out of current classpath, then return*/
		if(!ClassPath.getInstance().containsClass(cs)
			|| constantpool.getinstance().isprocessedntindex(name_and_type_index))
			return;
		short ni; 
		cpinfo curcpinfo = constantpool.getinstance().getcpinfo(name_and_type_index);
		if(curcpinfo.gettype() != constanttype.CONSTANT_NameAndType)
			return;
		ni = ((nameandtypecpinfo)curcpinfo).getnameindex();
		cpinfo nxtcpinfo = constantpool.getinstance().getcpinfo(ni);
		if(nxtcpinfo.gettype() != constanttype.CONSTANT_Utf8)
			return;
		constantpool.getinstance().addprocessedntindex(name_and_type_index);
		short index = constantpool.getinstance().getutf8indexasstr(((utf8cpinfo)nxtcpinfo).getInfoString() + suffix);
		if(index != 0)
		{
			((nameandtypecpinfo)curcpinfo).setnameindex(index);
			return;
		}
		if(constantpool.getinstance().inclassnameindexlist(ni)
				|| constantpool.getinstance().inmethodnameindexlist(ni)
				|| methodpool.getinstance().containsnameindex(ni)
				|| constantpool.getinstance().infieldnameindexlistrc(ni))
		{
			utf8cpinfo ucpinfo = new utf8cpinfo(nxtcpinfo.gettype(), ((utf8cpinfo)nxtcpinfo).getlength(), ((utf8cpinfo)nxtcpinfo).getbytes());
			ucpinfo.addfieldsuffix(suffix);
			index = constantpool.getinstance().addcpinfo(ucpinfo);
			((nameandtypecpinfo)curcpinfo).setnameindex(index);
			//constantpool.getinstance().replacefieldnameindex(ni, index);
			//fieldpool.getinstance().replacenameindex(ni, index);
			//constantpool.getinstance().addprocessednameindex(index);
		}
		else if(!fieldpool.getinstance().containsnameindex(ni))
			((utf8cpinfo)nxtcpinfo).addfieldsuffix(suffix);
		
		//constantpool.getinstance().addprocessednameindex(ni);
	}
	
	public void show()
	{
		super.show();
		debugger.log("class_index: " + class_index + "{" + constantpool.getinstance().checkpool(class_index) + "}");
		debugger.log("name_and_type_index: " + name_and_type_index  + "{" + constantpool.getinstance().checkpool(name_and_type_index) + "}");
	}
	
	public String toString()
	{
		return (super.toString() + "{" + constantpool.getinstance().checkpool(class_index)
				+ "." + constantpool.getinstance().checkpool(name_and_type_index) + "}");
	}
}
