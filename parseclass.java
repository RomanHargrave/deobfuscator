

import java.io.File;
import cf.ClassFile;

import util.debugger;

public class parseclass 
{
	static String class_file = null;
	static String output_file = null;
	static boolean verbose = false;
	static boolean help = false;
	
	public parseclass()
	{
		
	}
	
	public static void main(String[] args)
	{
		/*parse args*/
		parseargs(args);
		if(help)
		{
			help();
			return;
		}
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
				System.out.println(class_file + " not class file!");
				error();
				return;
			}
			File cf = new File(class_file);
			if(!cf.exists())
			{
				System.out.println(class_file + " not exists!");
				error();
				return;
			}
			if(verbose)
				System.out.println("Parsing " + class_file.toString());
			ClassFile classfile = new ClassFile(class_file);
			classfile.parseFile();
			classfile.show();
			classfile.done();
		}
		else
			error();
		
		
	}
	
	private static void parseargs(String[] args)
	{
		for(int i = 0; i < args.length; i++)
		{
			
			if(!args[i].startsWith("-"))
				class_file = args[i];
			else if(args[i].equals("-o"))
			{
				i++;
				if(i < args.length)
					output_file = args[i];
			}
			else if(args[i].equals("-v"))
				verbose = true;
			else if(args[i].equals("-h"))
				help = true;
		}
	}
	
	private static void error()
	{
		System.out.println("Input wrong");
		help();
	}
	
	private static void help()
	{
		System.out.println("parseclass v1.0.0 Copyright 2009 LXB (laixuebin@gmail.com)");
		System.out.println("Usage: java -jar parseclass <class_file> [options]");
		System.out.println("Options: -o   <path>  	Output file name");
		System.out.println("         -v             Processing with verbose");
		System.out.println("         -h             Show this help message");
		System.out.println("Visit http://code.google.com/p/deobfuscator/ for more information");
	}
}
