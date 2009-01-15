package cf;

import util.debugger;

public class sourcefileattr extends attr 
{
	private short sourcefile_index;
	
	public sourcefileattr(int type, short ani, int al, short si)
	{
		super(type, ani, al);
		sourcefile_index = si;
	}
	
	public short getsourcefileindex()
	{
		return sourcefile_index;
	}
	
	public void showforsuper()
	{
		super.showforsuper();
		debugger.log("sourcefile: " + sourcefile_index + "{" + constantpool.getinstance().checkpool(sourcefile_index) + "}");
	}
}
