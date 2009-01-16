package cf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import util.debugger;


public class ClassFile 
{
	public int magic;
	public short minor_version;
	public short major_version;
	public short constant_pool_count;
	public cpinfo[] constant_pool;
	public short access_flags;
	public short this_class;
	public short super_class;
	public short interfaces_count;
	public short[] interfaces;
	public short fields_count;
	public fieldinfo[] fields;
	public short methods_count;
	public methodinfo[] methods;
	public short attributes_count;
	public attr[] attributes;
	
	public DataInputStream dis = null;
	public DataOutputStream dos = null;
	public constantpool cp = constantpool.getinstance();
	
	public ClassFile(String filename)
	{
		try
		{
			dis = new DataInputStream(new FileInputStream(filename));
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
		
	}
	public void parseFile()
	{
		parsefile();
	}
	
	public void writeFile(String filename)
	{
		try 
		{
			dos = new DataOutputStream(new FileOutputStream(filename));
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		writefile();
	}
	
	public void done()
	{
		try
		{
			if(dis != null)
				dis.close();
			if(dos != null)
				dos.close();
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}
	
	public int returnMagic()
	{
		return magic;
	}
	
	public void addClassPrefix(String prefix)
	{
		constantpool.getinstance().addclassprefix(prefix);
	}
	
	public void addClassSuffix(String suffix)
	{
		constantpool.getinstance().addclasssuffix(suffix);
	}
	
	private void parsefile()
	{
		attrreader ar = new attrreader(dis);
		cpinforeader cir = new cpinforeader(dis);
		
		try 
		{
			magic = dis.readInt();
			minor_version = dis.readShort();
			major_version = dis.readShort();
			constant_pool_count = dis.readShort();
			/*Read constant pool*/
			constant_pool = new cpinfo[constant_pool_count - 1];
			for(int i = 0; i < (constant_pool_count - 1); i++)
			{
				constant_pool[i] = cir.readcpinfo();
				/*Long and Double occupy two costant pool entries*/
				if((constant_pool[i].gettype() == constanttype.CONSTANT_Long) 
						|| (constant_pool[i].gettype() == constanttype.CONSTANT_Double))
					i++;
			}
			/*Set constant pool right after reading is done*/
			cp.setpool(constant_pool);
			access_flags = dis.readShort();
			this_class = dis.readShort();
			super_class = dis.readShort();
			/*Read interfaces*/
			interfaces_count = dis.readShort();
			interfaces = new short[interfaces_count];
			for(int i = 0; i < interfaces_count; i++)
			{
				interfaces[i] = dis.readShort();
			}
			/*Read fields*/
			fields_count = dis.readShort();
			fields = new fieldinfo[fields_count];
			for(int i = 0; i < fields_count; i++)
			{
				short af = dis.readShort();
				short ni = dis.readShort();
				short di = dis.readShort();
				short ac = dis.readShort();
				attr[] as = new attr[ac];
				for(int j = 0; j < ac; j++)
					as[j] = ar.readattr();
				fields[i] = new fieldinfo(af, ni, di, ac, as);
			}
			/*Read methods*/
			methods_count = dis.readShort();
			methods = new methodinfo[methods_count];
			for(int i = 0; i < methods_count; i++)
			{
				short af = dis.readShort();
				short ni = dis.readShort();
				short di = dis.readShort();
				short ac = dis.readShort();
				attr[] as = new attr[ac];
				for(int j = 0; j < ac; j++)
					as[j] = ar.readattr();
				methods[i] = new methodinfo(af, ni, di, ac, as);
			}
			/*read attributes*/
			attributes_count = dis.readShort();
			attributes = new attr[attributes_count];
			for(int i = 0; i < attributes_count; i++)
				attributes[i] = ar.readattr();
		}
		catch(Exception e)
		{
			System.out.println(e.toString() + " in parsefile");
			e.printStackTrace();
		}
		
	}
	
	private void writefile()
	{
		cpinfowriter ciw = new cpinfowriter(dos);
		attrwriter aw = new attrwriter(dos);
		
		try
		{
			dos.writeInt(magic);
			dos.writeShort(minor_version);
			dos.writeShort(major_version);
			/*Write back constant pool*/
			constant_pool_count = constantpool.getinstance().getpoolcount();
			Object[] object_pool = constantpool.getinstance().getpool();
			dos.writeShort(constant_pool_count);
			for(int i = 0; i < (constant_pool_count - 1); i++)
			{
				cpinfo curcpinfo = (cpinfo)object_pool[i];
				ciw.writecpinfo(curcpinfo);
				/*Long and Double occupy two costant pool entries*/
				if((curcpinfo.gettype() == constanttype.CONSTANT_Long) 
						|| (curcpinfo.gettype() == constanttype.CONSTANT_Double))
					i++;
			}
			dos.writeShort(access_flags);
			dos.writeShort(this_class);
			dos.writeShort(super_class);
			/*Write interfaces*/
			dos.writeShort(interfaces_count);
			for(int i = 0; i < interfaces_count; i++)
				 dos.writeShort(interfaces[i]);
			/*Write fields*/
			dos.writeShort(fields_count);
			for(int i = 0; i < fields_count; i++)
			{
				dos.writeShort(fields[i].getaccessflag());
				dos.writeShort(fields[i].getnameindex());
				dos.writeShort(fields[i].getdescriptorindex());
				dos.writeShort(fields[i].getattributescount());
				attr[] as = fields[i].getattributes();
				for(int j = 0; j < as.length; j++)
					aw.writeattr(as[j]);
			}
			/*Write methods*/
			dos.writeShort(methods_count);
			for(int i = 0; i < methods_count; i++)
			{
				dos.writeShort(methods[i].getaccessflag());
				dos.writeShort(methods[i].getnameindex());
				dos.writeShort(methods[i].getdescriptorindex());
				dos.writeShort(methods[i].getattributescount());
				attr[] as = methods[i].getattributes();
				for(int j = 0; j < as.length; j++)
					aw.writeattr(as[j]);
			}
			/*Write attributes*/
			dos.writeShort(attributes_count);
			for(int i = 0; i < attributes_count; i++)
				aw.writeattr(attributes[i]);
			
		}
		catch(Exception e)
		{
			System.out.println(e.toString() + " in writeback");
			e.printStackTrace();
		}
	}
	
	public cpinfo[] getconstantpool()
	{
		return constant_pool;
	}
	
	public void show()
	{
		/*Header Info*/
		debugger.log("Header Info:");
		debugger.log("------------------------------------");
		debugger.log("magic: " + debugger.hexstr(magic, 8));
		debugger.log("minor_version: " + minor_version);
		debugger.log("major_version: " + major_version);
		debugger.log("access_flags: " + access_flags + "{" + classaccessflag.getflagstr(access_flags) + "}");
		debugger.log("this_class: " + this_class + "{" + constantpool.getinstance().checkpool(this_class) + "}");
		debugger.log("super_class: " + super_class + "{" + constantpool.getinstance().checkpool(super_class) + "}");
		debugger.log("------------------------------------");
		debugger.log(" ");
		debugger.log("Constant Pool:");
		debugger.log("------------------------------------");
		for(int i = 0; i < (constant_pool_count - 1); i++)
		{
			debugger.log("Constant " + (i+1) + ":");
			cpinfo curcpinfo = constant_pool[i];
			switch(curcpinfo.gettype())
			{
				case constanttype.CONSTANT_Class:
					((classcpinfo)curcpinfo).show();
					break;
				case constanttype.CONSTANT_Fieldref:
				case constanttype.CONSTANT_Methodref:
				case constanttype.CONSTANT_InterfaceMethodref:
					((fieldrefcpinfo)curcpinfo).show();
					break;
				case constanttype.CONSTANT_String:
					((stringcpinfo)curcpinfo).show();
					break;
				case constanttype.CONSTANT_Integer:
				case constanttype.CONSTANT_Float:
					((integercpinfo)curcpinfo).show();
					break;
				case constanttype.CONSTANT_Long:
				case constanttype.CONSTANT_Double:
					((longcpinfo)curcpinfo).show();
					break;
				case constanttype.CONSTANT_NameAndType:
					((nameandtypecpinfo)curcpinfo).show();
					break;
				case constanttype.CONSTANT_Utf8:
					((utf8cpinfo)curcpinfo).show();
					break;
				default:
					
					break;
			}
			debugger.log("");
			/*Long and Double occupy two costant pool entries*/
			if((constant_pool[i].gettype() == constanttype.CONSTANT_Long) 
					|| (constant_pool[i].gettype() == constanttype.CONSTANT_Double))
				i++;
		}
		debugger.log("------------------------------------");
		debugger.log(" ");
		debugger.log("Interfaces:");
		debugger.log("------------------------------------");
		for(int i = 0; i < interfaces_count; i++)
			debugger.log("Interface: " + interfaces[i] + "{" + cp.checkpool(interfaces[i]) + "}");
		debugger.log("------------------------------------");
		debugger.log(" ");
		debugger.log("Fields:");
		debugger.log("------------------------------------");
		for(int i = 0; i < attributes_count; i++)
		{
			debugger.log("Filed: " + i);
			debugger.log("------------------");
			fields[i].show();
			debugger.log(" ");
		}
		debugger.log("------------------------------------");
		debugger.log("Methods:");
		debugger.log(" ");
		debugger.log("------------------------------------");
		for(int i = 0; i < methods_count; i++)
		{
			debugger.log("Method: " + i);
			debugger.log("------------------");
			methods[i].show();
			debugger.log(" ");
		}
		debugger.log("------------------------------------");
		debugger.log(" ");
		debugger.log("Attributes:");
		debugger.log("------------------------------------");
		for(int i = 0; i < attributes_count; i++)
		{
			debugger.log("Attribute: " + i);
			debugger.log("------------------");
			attributes[i].show();
			debugger.log(" ");
		}
		debugger.log("------------------------------------");
	}

	
}
