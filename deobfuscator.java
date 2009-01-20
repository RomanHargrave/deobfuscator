
import cf.ClassFile;
import cp.ClassPath;
import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

public class deobfuscator 
{
	static String class_prefix = null;
	static String class_suffix = null;
	static String method_prefix = null;
	static String method_suffix = null;
	static String field_prefix = null;
	static String field_suffix = null;
	static String class_path = null;
	static String output_path = null;
	static boolean attach_classname_for_method = false;
	static Vector exclude_list;
	static Vector exclude_file_list;
	static boolean verbose = false;
	static boolean help = false;
	
	public deobfuscator()
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
		/*Create class path list*/
		if(class_path != null)
		{
			File cp = new File(class_path);
			if(!cp.exists())
			{
				error();
				//System.out.println(class_path);
				return;
			}
			class_path = cp.toString();
			ClassPath.getInstance().setPath(class_path);
			ClassPath.getInstance().updateClassList();
			if(exclude_list != null)
			{
				for(Iterator i = exclude_list.iterator(); i.hasNext();)
				{
					String fstr = (String)i.next();
					File exfile = new File(fstr);
					if(!exfile.exists())
						exfile = new File(class_path + File.separator + fstr);
					//System.out.println(exfile.toString());
					exclude_file_list = new Vector();
					if(exfile.exists()
							&& (ClassPath.getInstance().containsClass(exfile)))
					{
						ClassPath.getInstance().removeClass(exfile);
						exclude_file_list.add(exfile);
					}
				}
			}
		}
		else
		{
			help();
			return;
		}
		
		
		/**/
		if(output_path == null)
		{
			File op = new File(class_path + "/deobfustrating");
			op.mkdirs();
			output_path = op.toString();
		}
		else
		{
			File op = new File(output_path);
			if(!op.exists())
			{
				System.out.println(output_path + " not exists, create it!");
				op.mkdirs();
			}
			output_path = op.toString();
		}
		
		if(class_prefix != null
			|| class_suffix != null
			|| method_prefix != null
			|| method_suffix != null
			|| field_prefix != null
			|| field_suffix != null
			|| attach_classname_for_method)
		{
			Collection cfc = ClassPath.getInstance().getClassFiles();
			for(Iterator i = cfc.iterator(); i.hasNext(); )
			{
				File f = (File)i.next();
				processingfile(f);
			}
			if(exclude_file_list != null)
			{
				for(Iterator i = exclude_file_list.iterator(); i.hasNext();)
				{
					File f = (File)i.next();
					processingfile(f);
				}
			}
			
		}
	
	}
	
	private static void processingfile(File f)
	{
		if(verbose)
			System.out.println("Parsing " + f.toString());
		ClassFile cf = new ClassFile(f.toString());
		cf.parseFile();
		if(class_prefix != null)
			cf.addClassPrefix(class_prefix);
		if(class_suffix != null)
			cf.addClassSuffix(class_suffix);
		if(method_prefix != null)
			cf.addMethodPrefix(method_prefix);
		if(method_suffix != null)
			cf.addMethodSuffix(method_suffix);
		if(attach_classname_for_method)
			cf.attacheClassNameForMethod();
		if(field_prefix != null)
			cf.addFieldPrefix(field_prefix);
		if(field_suffix != null)
			cf.addFieldSuffix(field_suffix);
		StringBuffer sb = new StringBuffer(output_path);
		sb.append(File.separator);
		String pgn = ClassPath.getInstance().getPackageName(f);
		if(!pgn.equals(""))
		{
			sb.append(pgn);
			File pgf = new File(sb.toString());
			if(!pgf.exists())
				pgf.mkdirs();
			sb.append(File.separatorChar);
		}
		if(class_prefix != null)
			sb.append(class_prefix);
		sb.append(ClassPath.getInstance().getClassName(f));
		if(class_suffix != null)
			sb.append(class_suffix);
		sb.append(".class");
		if(verbose)
			System.out.println("Generating " +sb.toString());
		cf.writeFile(sb.toString());
		cf.done();
	}
	
	private static void parseargs(String[] args)
	{
		for(int i = 0; i < args.length; i++)
		{
			/*To check option mode*/
			if(!args[i].startsWith("-"))
			{
				class_path = args[i];
			}
			else if(args[i].equalsIgnoreCase("-op"))
			{
				i++;
				if(i < args.length)
					output_path = args[i];
			}
			else if(args[i].equalsIgnoreCase("-cpx"))
			{
				i++;
				if(i < args.length)
					class_prefix = args[i];
			}
			else if(args[i].equalsIgnoreCase("-csx"))
			{
				i++;
				if(i < args.length)
					class_suffix = args[i];
			}
			else if(args[i].equalsIgnoreCase("-mpx"))
			{
				i++;
				if(i < args.length)
					method_prefix = args[i];
			}
			else if(args[i].equalsIgnoreCase("-msx"))
			{
				i++;
				if(i < args.length)
					method_suffix = args[i];
			}
			else if(args[i].equalsIgnoreCase("-fpx"))
			{
				i++;
				if(i < args.length)
					field_prefix = args[i];
			}
			else if(args[i].equalsIgnoreCase("-fsx"))
			{
				i++;
				if(i < args.length)
					field_suffix = args[i];
			}
			else if(args[i].equalsIgnoreCase("-acfm"))
			{
				attach_classname_for_method = true;
			}
			else if(args[i].equalsIgnoreCase("-ex"))
			{
				i++;
				exclude_list = new Vector();
				while(i < args.length && !args[i].startsWith("-"))
				{
					exclude_list.add(args[i]);
					i++;
				}
				if(i < args.length)
					i--;
			}
			else if(args[i].equalsIgnoreCase("-v"))
				verbose = true;
			else if(args[i].equalsIgnoreCase("-h"))
				help = true;
		}
	}
	
	private static void error()
	{
		System.out.println("Input wrong");
	}
	
	private static void help()
	{
		System.out.println("deobfuscator v1.0.0 Copyright 2009 LXB (laixuebin@gmail.com)");
		System.out.println("Usage: java -jar deobfuscator <class_path> [options]");
		System.out.println("Options: -op  <path>  	Processing output path");
		System.out.println("         -cpx <prefix> 	Class prefix");
		System.out.println("         -csx <suffix> 	Class suffix");
		System.out.println("         -mpx <prefix> 	Method prefix");
		System.out.println("         -msx <suffix> 	Method suffix");
		System.out.println("         -acfm          Attach full class name as prefix for each method");
		System.out.println("         -fpx <prefix> 	Field prefix");
		System.out.println("         -fsx <suffix> 	Field prefix");
		System.out.println("         -ex  <files> 	Specify files to be excluded for processing");
		System.out.println("         -v             Processing with verbose");
		System.out.println("         -h             Show this help message");
		System.out.println("Visit http://code.google.com/p/deobfuscator/ for more information");
	}
}
