package cf;

import util.debugger;

public class linenumbertableattr extends attr 
{
	private short line_number_table_length;
	private linenumber[] line_number_table;
	
	public linenumbertableattr(int type, short ani, int al, short lntl, linenumber[] lnt)
	{
		super(type, ani, al);
		line_number_table_length = lntl;
		line_number_table = lnt;
	}
	
	public short getlinenumbertablelength()
	{
		return line_number_table_length;
	}
	
	public linenumber[] getlinenumbertable()
	{
		return line_number_table;
	}
	
	public void showforsuper()
	{
		super.showforsuper();
		for(int i = 0; i < line_number_table_length; i++)
		{
			debugger.log("line: " + i);
			line_number_table[i].show();
		}
	}
}
