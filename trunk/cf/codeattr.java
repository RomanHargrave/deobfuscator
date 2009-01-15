package cf;

import util.debugger;

public class codeattr extends attr
{
	private short max_stack;
	private short max_locals;
	private int code_length;
	private byte[] code;
	private short exception_table_length;
	private exceptiontable[] exception_table;
	private short attributes_count;
	private attr[] attributes;
	
	public codeattr(int type, short ani, int al, short ms, short ml, int cl, byte[] c, short etl, exceptiontable[] et, short ac, attr[] as)
	{
		super(type, ani, al);
		max_stack = ms;
		max_locals = ml;
		code_length = cl;
		code = c;
		exception_table_length = etl;
		exception_table = et;
		attributes_count = ac;
		attributes = as;
	}
	public short getmaxstack()
	{
		return max_stack;
	}
	
	public short getmaxlocals()
	{
		return max_locals;
	}
	
	public int getcodelength()
	{
		return code_length;
	}
	
	public byte[] getcode()
	{
		return code;
	}
	
	public short getexceptiontablelength()
	{
		return exception_table_length;
	}
	
	public exceptiontable[] getexceptiontable()
	{
		return exception_table;
	}
	
	public short getattributescount()
	{
		return attributes_count;
	}
	
	public attr[] getattributes()
	{
		return attributes;
	}
	
	public void showforsuper()
	{
		super.showforsuper();		
		debugger.log("max_stack: " + max_stack);
		debugger.log("max_locals: " + max_locals);
		debugger.log("code_length: " + code_length);
		debugger.log("code:");
		debugger.dumpbytes(code);
		debugger.log("exception_table_length: " + exception_table_length);
		for(int i = 0; i < exception_table_length; i++)
		{
			debugger.log("exception: " + i);
			debugger.log("------");
			exceptiontable et = exception_table[i];
			et.show();
			debugger.log(" ");
		}
		for(int i = 0; i < attributes_count; i ++)
		{
			debugger.log("attribute: " + i);
			debugger.log("------");
			attributes[i].show();
			debugger.log("");
		}
	}
	
	public String toString()
	{
		return super.toString();
	}
}
