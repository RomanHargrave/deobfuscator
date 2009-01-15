package cf;

import java.io.DataOutputStream;
import java.io.OutputStream;


public class cpinfowriter 
{
	DataOutputStream dos;
	
	public cpinfowriter(OutputStream os)
	{
		dos = (DataOutputStream)os;
	}
	
	public void writecpinfo(cpinfo ci)
	{
		try
		{
			dos.writeByte(ci.gettype());
			switch(ci.gettype())
			{
				case constanttype.CONSTANT_Class:
					dos.writeShort(((classcpinfo)ci).getnameindex());
					break;
				case constanttype.CONSTANT_Fieldref:
				case constanttype.CONSTANT_Methodref:
				case constanttype.CONSTANT_InterfaceMethodref:
					dos.writeShort(((fieldrefcpinfo)ci).getclassindex());
					dos.writeShort(((fieldrefcpinfo)ci).getnameandtypeindex());				
					break;
				case constanttype.CONSTANT_String:
					dos.writeShort(((stringcpinfo)ci).getstringindex());
					break;
				case constanttype.CONSTANT_Integer:
				case constanttype.CONSTANT_Float:
					dos.writeInt(((integercpinfo)ci).getbytes());
					break;
				case constanttype.CONSTANT_Long:
				case constanttype.CONSTANT_Double:
					dos.writeInt(((longcpinfo)ci).gethighbytes());
					dos.writeInt(((longcpinfo)ci).getlowbytes());
					break;
				case constanttype.CONSTANT_NameAndType:
					dos.writeShort(((nameandtypecpinfo)ci).getnameindex());
					dos.writeShort(((nameandtypecpinfo)ci).getdescriptorindex());
					break;
				case constanttype.CONSTANT_Utf8:
					dos.writeShort(((utf8cpinfo)ci).getlength());
					dos.write(((utf8cpinfo)ci).getbytes());
					break;
				default:
					
					break;
			
			}
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
}
