
import cf.ClassFile;
import cp.ClassPath;
import java.io.File;
import java.util.Collection;
import java.util.Iterator;

public class deobfuscator 
{
	static String class_prefix = null;
	static String class_suffix = null;
	static String class_path = null;
	static String output_path = null;
	
	public deobfuscator()
	{
		
	}
	
	public static void main(String[] args)
	{
		/*parse args*/
		parseargs(args);
		/*Create class path list*/
		if(class_path != null)
		{
			File cp = new File(class_path);
			if(!cp.exists())
			{
				help();
				System.out.println(class_path);
				return;
			}
			class_path = cp.toString();
			ClassPath.getInstance().setPath(class_path);
			ClassPath.getInstance().updateClassList();
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
		
		if(class_prefix != null)
		{
			Collection cfc = ClassPath.getInstance().getClassFiles();
			for(Iterator i = cfc.iterator(); i.hasNext(); )
			{
				File f = (File)i.next();
				System.out.println(f.toString());
				ClassFile cf = new ClassFile(f.toString());
				cf.parseFile();
				cf.addClassPrefix(class_prefix);
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
				sb.append(class_prefix);
				sb.append(f.getName());
				System.out.println(sb.toString());
				cf.writeFile(sb.toString());
				cf.done();
			}
		}
		
		if(class_suffix != null)
		{
			Collection cfc = ClassPath.getInstance().getClassFiles();
			for(Iterator i = cfc.iterator(); i.hasNext(); )
			{
				File f = (File)i.next();
				System.out.println(f.toString());
				ClassFile cf = new ClassFile(f.toString());
				cf.parseFile();
				cf.addClassSuffix(class_suffix);
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
				sb.append(ClassPath.getInstance().getClassName(f));
				sb.append(class_suffix);
				sb.append(".class");
				cf.writeFile(sb.toString());
				System.out.println(sb.toString());
				cf.done();
			}
		}
	}
	
	private static void parseargs(String[] args)
	{
		for(int i = 0; i < args.length; i++)
		{
			/*To check option mode*/
			if(args[i].equalsIgnoreCase("-cp"))
			{
				i++;
				if(i < args.length)
					class_path = args[i];
			}
			else if(args[i].equalsIgnoreCase("-op"))
			{
				i++;
				if(i < args.length)
					output_path = args[i];
			}
			else if(args[i].equalsIgnoreCase("-classprefix"))
			{
				i++;
				if(i < args.length)
					class_prefix = args[i];
			}
			else if(args[i].equalsIgnoreCase("-classsuffix"))
			{
				i++;
				if(i < args.length)
					class_suffix = args[i];
			}
		}
	}
	
	private static void help()
	{
		System.out.println("Input wrong");
	}
}
