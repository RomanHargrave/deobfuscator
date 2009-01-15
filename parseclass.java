

import java.io.File;
import cf.ClassFile;

import util.debugger;

public class parseclass 
{
	static String class_file = null;
	static String output_file = null;
	
	public parseclass()
	{
		
	}
	
	public static void main(String[] args)
	{
		/*parse args*/
		parseargs(args);
		/*check output file*/
		if(output_file != null)
		{
			File of = new File(output_file);
			File pf = new File(of.getParent());
			if(!pf.exists())
				pf.mkdirs();
			debugger.setpw(of.toString());
		}
		/*Parse class file*/
		if(class_file != null)
		{
			if(!class_file.endsWith(".class"))
			{
				System.out.println(class_file + "not class file!");
				return;
			}
			File cf = new File(class_file);
			if(!cf.exists())
			{
				System.out.println(class_file + "not exists!");
				return;
			}
			
			ClassFile classfile = new ClassFile(class_file);
			classfile.parseFile();
			classfile.show();
			classfile.done();
		}
		else
			help();
		
		
	}
	
	private static void parseargs(String[] args)
	{
		for(int i = 0; i < args.length; i++)
		{
			if(args[i].equals("-f"))
			{
				i++;
				if(i < args.length)
					class_file = args[i];
			}
			else if(args[i].equals("-o"))
			{
				i++;
				if(i < args.length)
					output_file = args[i];
			}
		}
	}
	
	private static void help()
	{
		System.out.println("Input wrong");
	}
}
