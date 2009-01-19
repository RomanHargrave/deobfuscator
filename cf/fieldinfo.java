package cf;

import util.debugger;

public class fieldinfo 
{
	private short access_flag;
	private short name_index;
	private short descriptor_index;
	private short attributes_count;
	private attr[] attributes;
	
	public fieldinfo(short af, short ni, short di, short ac, attr[] as)
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
	
	/*this addfieldprefix must be based on constantpool scan and process for addfieldprefix is done*/
	public void addfieldprefix(String prefix)
	{
		short ni = this.getnameindex();
		cpinfo nxtcpinfo = constantpool.getinstance().getcpinfo(ni);
		if(nxtcpinfo.gettype() != constanttype.CONSTANT_Utf8)
			return;
		short index = constantpool.getinstance().getutf8indexasstr(prefix + ((utf8cpinfo)nxtcpinfo).getInfoString());
		//System.out.println("Searched index is: " + index);
		if(index != 0)
		{
			this.setnameindex(index);
			return;
		}
		if(constantpool.getinstance().inclassnameindexlist(ni)
				|| constantpool.getinstance().inmethodnameindexlist(ni)
				|| methodpool.getinstance().containsnameindex(ni))
		{
			utf8cpinfo ucpinfo = new utf8cpinfo(nxtcpinfo.gettype(), ((utf8cpinfo)nxtcpinfo).getlength(), ((utf8cpinfo)nxtcpinfo).getbytes());
			ucpinfo.addfieldprefix(prefix);
			index = constantpool.getinstance().addcpinfo(ucpinfo);
			this.setnameindex(index);
			//constantpool.getinstance().addprocessednameindex(index);
		}
		else
			((utf8cpinfo)nxtcpinfo).addfieldprefix(prefix);
		//constantpool.getinstance().addprocessednameindex(ni);
	}
	
	/*this addfieldsuffix must be based on constantpool scan and process for addfieldsuffix is done*/
	public void addfieldsuffix(String suffix)
	{
		short ni = this.getnameindex();
		cpinfo nxtcpinfo = constantpool.getinstance().getcpinfo(ni);
		if(nxtcpinfo.gettype() != constanttype.CONSTANT_Utf8)
			return;
		short index = constantpool.getinstance().getutf8indexasstr(((utf8cpinfo)nxtcpinfo).getInfoString() + suffix);
		if(index != 0)
		{
			this.setnameindex(index);
			return;
		}
		if(constantpool.getinstance().inclassnameindexlist(ni)
				|| constantpool.getinstance().inmethodnameindexlist(ni)
				|| methodpool.getinstance().containsnameindex(ni))
		{
			utf8cpinfo ucpinfo = new utf8cpinfo(nxtcpinfo.gettype(), ((utf8cpinfo)nxtcpinfo).getlength(), ((utf8cpinfo)nxtcpinfo).getbytes());
			ucpinfo.addfieldsuffix(suffix);
			index = constantpool.getinstance().addcpinfo(ucpinfo);
			this.setnameindex(index);
			//constantpool.getinstance().addprocessednameindex(index);
		}
		else
			((utf8cpinfo)nxtcpinfo).addfieldsuffix(suffix);
		//constantpool.getinstance().addprocessednameindex(ni);
	}
	
	public void show()
	{
		debugger.log("acces_flag: " + access_flag + "{" + fieldaccessflag.getflagstr(access_flag) + "}");
		debugger.log("name_index: " + name_index + "{" + constantpool.getinstance().checkpool(name_index) +"}");
		debugger.log("descriptor_index: " + descriptor_index + "{" + constantpool.getinstance().checkpool(descriptor_index) + "}");
		for(int i = 0; i < attributes_count; i++)
		{
			debugger.log("attribute: " + i);
			attributes[i].show();
		}
	}
	
}