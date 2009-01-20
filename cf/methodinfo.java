package cf;

import java.io.File;

import cp.ClassPath;
import util.debugger;

public class methodinfo 
{
	private short access_flag;
	private short name_index;
	private short descriptor_index;
	private short attributes_count;
	private attr[] attributes;
	
	public methodinfo(short af, short ni, short di, short ac, attr[] as)
	{
		access_flag = af;
		name_index = ni;
		descriptor_index = di;
		attributes_count = ac;
		attributes = as;
	}
	
	public short getaccessflag()
	{
		return access_flag;
	}
	
	public short getnameindex()
	{
		return name_index;
	}
	
	public void setnameindex(short ni)
	{
		name_index = ni;
	}
	
	public short getdescriptorindex()
	{
		return descriptor_index;
	}
	
	public short getattributescount()
	{
		return attributes_count;
	}
	
	public attr[] getattributes()
	{
		return attributes;
	}
	
	/*this addmethodprefix must be based on constantpool scan and process for addmethodprefix is done*/
	public void addmethodprefix(String prefix)
	{
		short ni = this.getnameindex();
		if(constantpool.getinstance().checkpool(ni).equals("<init>")
				|| constantpool.getinstance().checkpool(ni).equals("<clinit>"))
			return;
		cpinfo nxtcpinfo = constantpool.getinstance().getcpinfo(ni);
		if(nxtcpinfo.gettype() != constanttype.CONSTANT_Utf8)
			return;
		short index = constantpool.getinstance().getutf8indexasstr(prefix + ((utf8cpinfo)nxtcpinfo).getInfoString());
		if(index != 0)
		{
			this.setnameindex(index);
			return;
		}
		if(constantpool.getinstance().inclassnameindexlist(ni)
				|| constantpool.getinstance().infieldnameindexlist(ni)
				|| fieldpool.getinstance().containsnameindex(ni)
				|| methodpool.getinstance().containsdescriptorindex(ni)
				|| fieldpool.getinstance().containsdescriptorindex(ni))
		{
			utf8cpinfo ucpinfo = new utf8cpinfo(nxtcpinfo.gettype(), ((utf8cpinfo)nxtcpinfo).getlength(), ((utf8cpinfo)nxtcpinfo).getbytes());
			ucpinfo.addmethodprefix(prefix);
			index = constantpool.getinstance().addcpinfo(ucpinfo);
			this.setnameindex(index);
			//constantpool.getinstance().addprocessednameindex(index);
		}
		else
			((utf8cpinfo)nxtcpinfo).addmethodprefix(prefix);
		//constantpool.getinstance().addprocessednameindex(ni);
	}
	
	/*this addmethodprefix must be based on constantpool scan and process for addmethodsuffix is done*/
	public void addmethodsuffix(String suffix)
	{
		short ni = this.getnameindex();
		if (constantpool.getinstance().checkpool(ni).equals("<init>")
				|| constantpool.getinstance().checkpool(ni).equals("<clinit>"))
			return;
		cpinfo nxtcpinfo = constantpool.getinstance().getcpinfo(ni);
		if(nxtcpinfo.gettype() != constanttype.CONSTANT_Utf8)
			return;
		short index = constantpool.getinstance().getutf8indexasstr(((utf8cpinfo)nxtcpinfo).getInfoString() + suffix);
		if(index != 0)
		{
			this.setnameindex(index);
			return;
		}
			utf8cpinfo ucpinfo = new utf8cpinfo(nxtcpinfo.gettype(), ((utf8cpinfo)nxtcpinfo).getlength(), ((utf8cpinfo)nxtcpinfo).getbytes());
			ucpinfo.addmethodsuffix(suffix);
			index = constantpool.getinstance().addcpinfo(ucpinfo);
			this.setnameindex(index);
	}
	
	public void attachclassnameformethod()
	{
		constantpool cp = constantpool.getinstance();
		short ni = this.getnameindex();
		if(cp.checkpool(ni).equals("<init>")
				 || cp.checkpool(ni).equals("<clinit>"))
			return;
		cpinfo nxtcpinfo = cp.getcpinfo(ni);
		if(nxtcpinfo.gettype() != constanttype.CONSTANT_Utf8)
			return;
		String cs = cp.checkpool(fileinfo.getinstance().getthisclass());
		//System.out.println(cs);
		StringBuffer sb = new StringBuffer(cs.replaceAll(File.separator, "_"));
		sb.append("_");
		short index;
		index = cp.getutf8indexasstr(sb.toString() + ((utf8cpinfo)nxtcpinfo).getInfoString());
		//System.out.println(sb.toString() + ((utf8cpinfo)nxtcpinfo).getInfoString() + "=== index is " + index);
		if(index != 0)
		{
			this.setnameindex(index);
			return;
		}
		utf8cpinfo ucpinfo = new utf8cpinfo(nxtcpinfo.gettype(), ((utf8cpinfo)nxtcpinfo).getlength(), ((utf8cpinfo)nxtcpinfo).getbytes());
		ucpinfo.addmethodprefix(sb.toString());
		index = cp.addcpinfo(ucpinfo);
		this.setnameindex(index);
		cp.addprocessednameindex(ni);
	}
	
	public void show()
	{
		debugger.log("access_flag: " + access_flag + "{" + methodaccessflag.getflagstr(access_flag) + "}");
		debugger.log("name_index: " + name_index + "{" + constantpool.getinstance().checkpool(name_index) + "}");
		debugger.log("descriptor_index: " + descriptor_index + "{" + constantpool.getinstance().checkpool(descriptor_index) + "}");
		for(int i = 0; i < attributes_count; i++)
		{
			debugger.log("attribute: " + i);
			debugger.log("------------");
			attributes[i].show();
		}
	}
}
