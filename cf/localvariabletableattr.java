package cf;

import util.debugger;

public class localvariabletableattr extends attr 
{
	private short local_variable_table_length;
	private localvariable[] local_variable_table;
	
	public localvariabletableattr(int type, short ani, int al, short lvtl, localvariable[] lvt)
	{
		super(type, ani, al);
		local_variable_table_length = lvtl;
		local_variable_table = lvt;
	}
	
	public short getlocalvariabletablelength()
	{
		return local_variable_table_length;
	}
	
	public localvariable[] getlocalvariabletable()
	{
		return local_variable_table;
	}
	
	public void showforsuper()
	{
		super.showforsuper();
		for(int i = 0; i < local_variable_table_length; i++)
		{
			debugger.log("local variable: " + i);
			local_variable_table[i].show();
		}
	}
}
