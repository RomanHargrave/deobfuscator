package cf;

import util.debugger;

public class exceptiontable 
{
	private short start_pc;
	private short end_pc;
	private short handler_pc;
	private short catch_type;
	
	public exceptiontable(short sp, short ep, short hp, short ct)
	{
		start_pc = sp;
		end_pc = ep;
		handler_pc = hp;
		catch_type = ct;
	}
	
	public short getstartpc()
	{
		return start_pc;
	}
	
	public short getendpc()
	{
		return end_pc;
	}
	
	public short gethandlerpc()
	{
		return handler_pc;
	}
	
	public short getcatchtype()
	{
		return catch_type;
	}
	
	public void show()
	{
		debugger.log("start_pc: " + start_pc);
		debugger.log("end_pc: " + end_pc);
		debugger.log("handler_pc: " + handler_pc);
		debugger.log("catch_type: " + catch_type);
	}
	
	public String toString()
	{
		return ("start_pc:" + start_pc + "|end_pc:" + end_pc + "|handler_pc:" + handler_pc + "|catch_type:" + catch_type);
	}
	
}
