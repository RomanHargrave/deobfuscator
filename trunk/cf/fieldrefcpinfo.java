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
			|| constantpool.getinstance().checkpool(ni).equals("<init>")
			|| constantpool.getinstance().checkpool(ni).equals("<clinit>"))
			return;
		constantpool.getinstance().addprocessedntindex(name_and_type_index);
		short index = constantpool.getinstance().getutf8indexasstr(prefix + ((utf8cpinfo)nxtcpinfo).getInfoString());
		if(index != 0)
		{
			((nameandtypecpinfo)curcpinfo).setnameindex(index);
			return;
		}
		utf8cpinfo ucpinfo = new utf8cpinfo(nxtcpinfo.gettype(), ((utf8cpinfo)nxtcpinfo).getlength(), ((utf8cpinfo)nxtcpinfo).getbytes());
		ucpinfo.addmethodprefix(prefix);
		index = constantpool.getinstance().addcpinfo(ucpinfo);
		((nameandtypecpinfo)curcpinfo).setnameindex(index);
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
			|| constantpool.getinstance().checkpool(ni).equals("<init>")
			|| constantpool.getinstance().checkpool(ni).equals("<clinit>"))
			return;
		constantpool.getinstance().addprocessedntindex(name_and_type_index);
		short index = constantpool.getinstance().getutf8indexasstr(((utf8cpinfo)nxtcpinfo).getInfoString() + suffix);
		if(index != 0)
		{
			((nameandtypecpinfo)curcpinfo).setnameindex(index);
			return;
		}
		utf8cpinfo ucpinfo = new utf8cpinfo(nxtcpinfo.gettype(), ((utf8cpinfo)nxtcpinfo).getlength(), ((utf8cpinfo)nxtcpinfo).getbytes());
		ucpinfo.addmethodsuffix(suffix);
		index = constantpool.getinstance().addcpinfo(ucpinfo);
		((nameandtypecpinfo)curcpinfo).setnameindex(index);
	}
	
	public void attachclassnameformethod()
	{
		constantpool cp = constantpool.getinstance();
		String cs = cp.checkpool(class_index);
		/*Skip for methods of those classes, not included in classpath*/
		if(!ClassPath.getInstance().containsClass(cs))
			return;
		StringBuffer sb = new StringBuffer(cs.replaceAll(File.separator, "_"));
		sb.append("_");
		cpinfo curcpinfo = cp.getcpinfo(name_and_type_index);
		short ni = ((nameandtypecpinfo)curcpinfo).getnameindex();
		short ti = ((nameandtypecpinfo)curcpinfo).getdescriptorindex();
		String nstr = sb.toString() + cp.checkpool(ni);
		String tstr = cp.checkpool(ti);
		//System.out.println(nstr);
		/*Skip error constant type, processed name index, constructor*/
		if(curcpinfo.gettype() != constanttype.CONSTANT_NameAndType
			|| cp.checkpool(ni).equals("<init>")
			|| cp.checkpool(ni).equals("<clinit>"))
			return;
		short nati = cp.getntindexasstr(nstr, tstr);
		//System.out.println("nstr:tstr->" + nstr + ":" + tstr + " nati is " + nati);
		if(nati == 0)
		{
			curcpinfo = (cpinfo) new nameandtypecpinfo(curcpinfo.gettype(), ni, ti);
			nati = cp.addcpinfo(curcpinfo);
		}
		this.setnameandtypeindex(nati);
		cpinfo nxtcpinfo = cp.getcpinfo(ni);
		if(nxtcpinfo.gettype() != constanttype.CONSTANT_Utf8)
			return;
		short index = cp.getutf8indexasstr(nstr);
		//System.out.println("nstr->" + nstr +  " index is " + index);
		if(index != 0)
		{
			//System.out.println("{" + cp.checkpool(index) +"}");
			((nameandtypecpinfo)curcpinfo).setnameindex(index);
			return;
		}
		/*If the specified name idnex, had also been used as class name | field name | current class's method, 
		 * then we need to create a new utf8 entry for it */
		utf8cpinfo ucpinfo = new utf8cpinfo(nxtcpinfo.gettype(), ((utf8cpinfo)nxtcpinfo).getlength(), ((utf8cpinfo)nxtcpinfo).getbytes());
		ucpinfo.addmethodprefix(sb.toString());
		index = cp.addcpinfo(ucpinfo);
		((nameandtypecpinfo)curcpinfo).setnameindex(index);
		//methodpool.getinstance().replacenameindex(ni, index, class_index);
		
	}
	
	public void addfieldprefix(String prefix)
	{
		constantpool cp = constantpool.getinstance();
		String cs = cp.checkpool(class_index);
		/*if it is a class out of current classpath, then return*/
		if(!ClassPath.getInstance().containsClass(cs)
				|| cp.isprocessedntindex(name_and_type_index))
			return;
		cpinfo curcpinfo = cp.getcpinfo(name_and_type_index);
		if(curcpinfo.gettype() != constanttype.CONSTANT_NameAndType)
			return;
		short ni = ((nameandtypecpinfo)curcpinfo).getnameindex();
		//System.out.println(prefix + constantpool.getinstance().checkpool(ni));
		cpinfo nxtcpinfo = cp.getcpinfo(ni);
		if(nxtcpinfo.gettype() != constanttype.CONSTANT_Utf8)
			return;
		cp.addprocessedntindex(name_and_type_index);
		short index = cp.getutf8indexasstr(prefix + ((utf8cpinfo)nxtcpinfo).getInfoString());
		//System.out.println(prefix + ((utf8cpinfo)nxtcpinfo).getInfoString() + " index is " + index);
		if(index != 0)
		{
			((nameandtypecpinfo)curcpinfo).setnameindex(index);
			return;
		}
		utf8cpinfo ucpinfo = new utf8cpinfo(nxtcpinfo.gettype(), ((utf8cpinfo)nxtcpinfo).getlength(), ((utf8cpinfo)nxtcpinfo).getbytes());
		ucpinfo.addfieldprefix(prefix);
		index = cp.addcpinfo(ucpinfo);
		((nameandtypecpinfo)curcpinfo).setnameindex(index);
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
		utf8cpinfo ucpinfo = new utf8cpinfo(nxtcpinfo.gettype(), ((utf8cpinfo)nxtcpinfo).getlength(), ((utf8cpinfo)nxtcpinfo).getbytes());
		ucpinfo.addfieldsuffix(suffix);
		index = constantpool.getinstance().addcpinfo(ucpinfo);
		((nameandtypecpinfo)curcpinfo).setnameindex(index);
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
