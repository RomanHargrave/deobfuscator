package cf;

import java.io.DataOutputStream;
import java.io.OutputStream;

public class attrwriter 
{
	private DataOutputStream dos;
	
	public attrwriter(OutputStream os)
	{
		dos = (DataOutputStream)os;
	}
	
	public void writeattr(attr a)
	{
		try
		{
			dos.writeShort(a.getattributenameindex());
			dos.writeInt(a.getattributelength());
			switch(a.gettype())
			{
				case attrtype.ATTR_CONSTANT:
					dos.writeShort(((constantvalueattr)a).getconstantvalueindex());
					break;
				case attrtype.ATTR_CODE:
					dos.writeShort(((codeattr)a).getmaxstack());
					dos.writeShort(((codeattr)a).getmaxlocals());
					dos.writeInt(((codeattr)a).getcodelength());
					dos.write(((codeattr)a).getcode());
					/*read exception table*/
					dos.writeShort(((codeattr)a).getexceptiontablelength());
					exceptiontable[] et = ((codeattr)a).getexceptiontable();
					for(int i = 0; i < et.length; i++)
					{
						dos.writeShort(et[i].getstartpc());
						dos.writeShort(et[i].getendpc());
						dos.writeShort(et[i].gethandlerpc());
						dos.writeShort(et[i].getcatchtype());
					}
					dos.writeShort(((codeattr)a).getattributescount());
					attr[] as = ((codeattr)a).getattributes();
					attrwriter aw = new attrwriter(dos);
					for(int i = 0; i < as.length; i++)
						aw.writeattr(as[i]);
					break;
				case attrtype.ATTR_EXCEPTIONS:
					dos.writeShort(((exceptionsattr)a).getnumberofexceptions());
					short[] eit = ((exceptionsattr)a).getexcetpionindextable();
					for(int i = 0; i < eit.length; i++)
						dos.writeShort(eit[i]);
					break;
				case attrtype.ATTR_INNERCLASSES:
					dos.writeShort(((innerclassesattr)a).getnumberofclasses());
					innerclass[] ics = ((innerclassesattr)a).getinnerclasses();
					for(int i = 0; i < ics.length; i++)
					{
						dos.writeShort(ics[i].getinnerclassinfoindex());
						dos.writeShort(ics[i].getouterclassinfoindex());
						dos.writeShort(ics[i].getinnernameindex());
						dos.writeShort(ics[i].getinnerclassaccessflags());
					}
					break;
				case attrtype.ATTR_SYNTHETIC:
					break;
				case attrtype.ATTR_SOURCEFILE:
					dos.writeShort(((sourcefileattr)a).getsourcefileindex());
					break;
				case attrtype.ATTR_LINENUMTBL:
					dos.writeShort(((linenumbertableattr)a).getlinenumbertablelength());
					linenumber[] lnt = ((linenumbertableattr)a).getlinenumbertable();
					for(int i = 0; i < lnt.length; i++)
					{
						dos.writeShort(lnt[i].getstartpc());
						dos.writeShort(lnt[i].getlinenumber());
					}
					break;
				case attrtype.ATTR_LOCALVARTBL:
					dos.writeShort(((localvariabletableattr)a).getlocalvariabletablelength());
					localvariable[] lvt = ((localvariabletableattr)a).getlocalvariabletable();
					for(int i = 0; i < lvt.length; i++)
					{
						dos.writeShort(lvt[i].getstartpc());
						dos.writeShort(lvt[i].getlength());
						dos.writeShort(lvt[i].getnameindex());
						dos.writeShort(lvt[i].getdescriptorindex());
						dos.writeShort(lvt[i].getindex());
					}
					break;
				case attrtype.ATTR_DEPRECATED:
					break;
				case attrtype.ATTR_NULL:
					break;
			}
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
	
}
