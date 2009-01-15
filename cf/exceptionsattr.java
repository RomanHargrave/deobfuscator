package cf;

import util.debugger;

public class exceptionsattr extends attr 
{
	private short number_of_exceptions;
	private short[] exception_index_table;
	
	public exceptionsattr(int type, short ani, int al, short noe, short[] eit)
	{
		super(type, ani, al);
		number_of_exceptions = noe;
		exception_index_table = eit;
	}
	public short getnumberofexceptions()
	{
		return number_of_exceptions;
	}
	
	public short[] getexcetpionindextable()
	{
		return exception_index_table;
	}
	
	public void showforsuper()
	{
		super.showforsuper();
		for(int i = 0; i < number_of_exceptions; i++)
		{
			debugger.log("Exception: " + exception_index_table[i] + "{" + 
					constantpool.getinstance().checkpool(exception_index_table[i]) + "}");
		}
	}
	
	public String toString()
	{
		return super.toString();
	}
}
