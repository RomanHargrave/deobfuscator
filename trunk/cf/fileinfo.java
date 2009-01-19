package cf;

public class fileinfo 
{
	static fileinfo instance = null;
	int magic;
	short minor_version;
	short major_version;
	short access_flags;
	short this_class;
	short super_class;
	
	public fileinfo()
	{
		
	}
	
	public void setinfo(int m, short mnv, short mjv, short af, short tc, short sc)
	{
		magic = m;
		minor_version = mnv;
		major_version = mjv;
		access_flags = af;
		this_class = tc;
		super_class = sc;
	}
	
	public static fileinfo getinstance()
	{
		if(instance == null)
			instance = new fileinfo();
		return instance;
	}
	
	public short getthisclass()
	{
		return this_class;
	}
}
