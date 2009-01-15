package cf;

import java.io.DataInputStream;
import java.io.InputStream;

import util.debugger;

public class cpinforeader 
{
	private DataInputStream dis; 
	
	
		
	public cpinforeader(InputStream is)
	{
		dis = (DataInputStream)is;
		
	}
	
	public cpinfo readcpinfo()
	{
		cpinfo curcpinfo;
		try
		{
			byte type = dis.readByte();
			//debugger.log("type is: " + type);
			switch(type)
			{
				case constanttype.CONSTANT_Class:
					short ni = dis.readShort();
					curcpinfo = new classcpinfo(type, ni);
					//((classcpinfo)curcpinfo).show();
					break;
				case constanttype.CONSTANT_Fieldref:
				case constanttype.CONSTANT_Methodref:
				case constanttype.CONSTANT_InterfaceMethodref:
					short ci = dis.readShort();
					short nati = dis.readShort();
					curcpinfo = new fieldrefcpinfo(type, ci, nati);
					//((fieldrefcpinfo)curcpinfo).show();
					break;
				case constanttype.CONSTANT_String:
					short si = dis.readShort();
					curcpinfo = new stringcpinfo(type, si);
					//((stringcpinfo)curcpinfo).show();
					break;
				case constanttype.CONSTANT_Integer:
				case constanttype.CONSTANT_Float:
					//dis.skipBytes(4);
					//int bs = 0;
					int bs = dis.readInt();
					curcpinfo = new integercpinfo(type, bs);
					//((integercpinfo)curcpinfo).show();
					break;
				case constanttype.CONSTANT_Long:
				case constanttype.CONSTANT_Double:
					//dis.skipBytes(8);
					//int hbs = 0;
					//int lbs = 0;
					int hbs = dis.readInt();
					int lbs = dis.readInt();
					curcpinfo = new longcpinfo(type, hbs, lbs);
					//((longcpinfo)curcpinfo).show();
					break;
				case constanttype.CONSTANT_NameAndType:
					short i = dis.readShort();
					short di = dis.readShort();
					curcpinfo = new nameandtypecpinfo(type, i, di);
					//((nameandtypecpinfo)curcpinfo).show();
					break;
				case constanttype.CONSTANT_Utf8:
					short l = dis.readShort();
					//dis.skipBytes(l);
					byte[] bytes = new byte[l];
					int readres = dis.read(bytes);
					if(readres == -1)
						debugger.error("Get to file end");
					else if(readres < l)
						debugger.error("Didn't read complete string");
					curcpinfo = new utf8cpinfo(type, l, bytes);
					//((utf8cpinfo)curcpinfo).show();
					break;
				default:
					debugger.log("null");
					curcpinfo = null;
					break;
			}
		
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
			curcpinfo = null;
		}
		return curcpinfo;
	}
	
	
	
}
