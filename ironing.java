
import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import cf.ClassFile;
import cp.ClassPath;

public class ironing 
{
	static String class_path;
	static String output_path;
	static Vector sub_class_list;
	static boolean verbose = false;
	static boolean help = false;

	
	public ironing()
	{
		
	}
	
	public static void main(String[] args)
	{
		parseargs(args);
		if(help)
		{
			help();
			return;
		}
		if(class_path != null)
		{
			File cp = new File(class_path);
			sub_class_list = new Vector();
			if(!cp.exists())
			{
				error();
				//System.out.println(class_path);
				return;
			}
			class_path = cp.toString();
			ClassPath.getInstance().setPath(class_path);
			ClassPath.getInstance().updateClassList();
			/*Create sub class list*/
			File[] fs = cp.listFiles();
			sub_class_list.clear();
			for(int i = 0; i < fs.length; i++)
			{
				if(fs[i].isDirectory())
					addfile(fs[i]);
			}
		}
		else
		{
			help();
			return;
		}
		
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
		
		if(sub_class_list.size() == 0)
			return;
		
		Collection cfc = ClassPath.getInstance().getClassFiles();
		for(Iterator i = cfc.iterator(); i.hasNext(); )
		{
			File f = (File)i.next();
			processingfile(f);
			
		}
		
	
	}
	
	private static void processingfile(File f)
	{
		if(verbose)
			System.out.println("Parsing " + f.toString());
		ClassFile cf = new ClassFile(f.toString());
		cf.parseFile();
		for(Iterator i = sub_class_list.iterator(); i.hasNext();)
		{
			String cn = (String)i.next();
			String newcn = cn.replaceAll(File.separator, "_");
			cf.replaceClassName(cn, newcn);
		}
		StringBuffer sb = new StringBuffer(output_path);
		sb.append(File.separator);
		String pgn = ClassPath.getInstance().getPackageName(f);
		String cn = pgn + File.separator + ClassPath.getInstance().getClassName(f);
		if(sub_class_list.contains(cn))
		{
			String newcn = cn.replaceAll(File.separator, "_");
			sb.append(newcn);
		}
		else
			sb.append(ClassPath.getInstance().getClassName(f));
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
			
			if(!args[i].startsWith("-"))
				class_path = args[i];
			else if(args[i].equalsIgnoreCase("-op"))
			{
				i++;
				if(i < args.length)
					output_path = args[i];
			}
			else if(args[i].equals("-v"))
				verbose = true;
			else if(args[i].equals("-h"))
				help = true;
		}
	}
	
	private static void addfile(File f)
	{
		String path_name = ClassPath.getInstance().getpathname();
		File[] fs = f.listFiles();
		for(int i = 0; i < fs.length; i++)
		{
			if(fs[i].isDirectory())
				addfile(fs[i]);
			else
			{
				String fn = fs[i].toString();
				if(fn.endsWith(".class")
						&& fn.startsWith(path_name))
				{
					String cn = fn.substring(path_name.length() + 1, fn.indexOf(".class"));
					sub_class_list.add(cn);
				}
			}
		}
	}
	
	private static void error()
	{
		System.out.println("Input wrong");
		help();
	}
	
	private static void help()
	{
		System.out.println("ironing v1.0.0 Copyright 2009 LXB (laixuebin@gmail.com)");
		System.out.println("Usage: java -jar ironing.jar <class_path> [options]");
		System.out.println("Options: -op <output_path>    Output file name");
		System.out.println("         -v                   Processing with verbose");
		System.out.println("         -h                   Show this help message");
		System.out.println("Visit http://code.google.com/p/deobfuscator/ for more information");
	}
}
