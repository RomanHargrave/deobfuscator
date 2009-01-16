package cf;

import util.debugger;

public class classcpinfo extends cpinfo 
{
	private short name_index;
	
	public classcpinfo(byte tag, short ni)
	{
		super(tag);
		name_index = ni;
	}
	
	public short getnameindex()
	{
		return name_index;
	}
	
	public void addclassprefix(String prefix)
	{
		constantpool cp = constantpool.getinstance();
		cpinfo curcpinfo = cp.getcpinfo(name_index);
		if(curcpinfo.gettype() != constanttype.CONSTANT_Utf8)
			return;
		if(cp.infieldnameindexlist(name_index)
				|| cp.ininterfacenameindexlist(name_index)
				|| cp.inmethodnameindexlist(name_index))
			 
		{
			/*Create a new utf8info for this class for duplicated name */
			utf8cpinfo ucpinfo = new utf8cpinfo(curcpinfo.gettype(), ((utf8cpinfo)curcpinfo).getlength(), ((utf8cpinfo)curcpinfo).getbytes());
			ucpinfo.addclassprefix(prefix);
			name_index = constantpool.getinstance().addcpinfo(ucpinfo);
		}
		else 
			((utf8cpinfo)curcpinfo).addclassprefix(prefix);
	}
	
	public void addclasssuffix(String suffix)
	{
		constantpool cp = constantpool.getinstance();
		cpinfo curcpinfo = cp.getcpinfo(name_index);
		if(curcpinfo.gettype() != constanttype.CONSTANT_Utf8)
			return;
		if(cp.infieldnameindexlist(name_index)
				|| cp.ininterfacenameindexlist(name_index)
				|| cp.inmethodnameindexlist(name_index))
			 
		{
			/*Create a new utf8info for this class for duplicated name */
			utf8cpinfo ucpinfo = new utf8cpinfo(curcpinfo.gettype(), ((utf8cpinfo)curcpinfo).getlength(), ((utf8cpinfo)curcpinfo).getbytes());
			ucpinfo.addclasssuffix(suffix);
			name_index = cp.addcpinfo(ucpinfo);
		}
		else 
			((utf8cpinfo)curcpinfo).addclasssuffix(suffix);
	}
	
	public void show()
	{
		super.show();
		debugger.log("name_index: " + name_index + "{" + constantpool.getinstance().checkpool(name_index) + "}");
	}
	
	public String toString()
	{
		return (super.toString() + "(name_index:" + name_index + ")");
	}
	
}
