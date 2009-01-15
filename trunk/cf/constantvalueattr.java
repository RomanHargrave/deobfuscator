package cf;

import util.debugger; 

public class constantvalueattr extends attr 
{
	private short constantvalue_index;
	
	public constantvalueattr(int type, short ani, int al, short ci)
	{
		super(type, ani, al);
		constantvalue_index = ci;
	}
	
	public short getconstantvalueindex()
	{
		return constantvalue_index;
	}
	
	public void showforsuper()
	{
		super.showforsuper();
		debugger.log("constantvalue_index: " + constantvalue_index + "{" + 
				constantpool.getinstance().checkpool(constantvalue_index)+"}");
	}
	
	public String toString()
	{
		return super.toString();
	}
}
