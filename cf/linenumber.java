package cf;

import util.debugger;

public class linenumber 
{
	private short start_pc;
	private short line_number;
	
	public linenumber(short sp, short ln)
	{
		start_pc = sp;
		line_number = ln;
	}
	
	public short getstartpc()
	{
		return start_pc;
	}
	
	public short getlinenumber()
	{
		return line_number;
	}
	
	public void show()
	{
		debugger.log("start_pc: " + start_pc);
		debugger.log("line_number: " + line_number);
	}
}
