package cp;

import java.io.File;
import java.util.HashMap;
import java.util.Collection;
import java.util.Vector;
//import util.debugger;


public class ClassPath 
{
	File class_path;
	HashMap class_list;
	Vector removed_class_list;
	String path_name;
	
	public static ClassPath cp = null;
	
	public ClassPath()
	{
		class_list = new HashMap();
		removed_class_list = new Vector();
	}
	
	public static ClassPath getInstance()
	{
		if(cp == null)
			cp = new ClassPath();
		return cp;
	}
	public boolean containsClass(String classfullname)
	{
		return class_list.containsKey(classfullname);
	}
	
	public boolean containsClass(File classfile)
	{
		String filename = classfile.toString();
		if(!filename.endsWith(".class"))
			return false;
		String classfn = filename.substring(path_name.length() + 1, filename.indexOf(".class"));
		//System.out.println(classfn);
		return containsClass(classfn);
	}
	
	public void removeClass(String classfullname)
	{
		class_list.remove(classfullname);
	}
	
	public void removeClass(File classfile)
	{
		String filename = classfile.toString();
		if(!filename.endsWith(".class"))
			return;
		String classfn = filename.substring(path_name.length() + 1, filename.indexOf(".class"));
		if(containsClass(classfn))
		{
			removed_class_list.add(classfn);
			removeClass(classfn);
		}
	}
	
	public Vector getremovedclasslist()
	{
		return removed_class_list;
	}
	
	public String getPackageName(String classfullname)
	{
		if(!containsClass(classfullname))
			return null;
		File f = (File)class_list.get(classfullname);
		return getPackageName(f);
	}
	
	public String getPackageName(File f)
	{
		String parentname = f.getParent();
		if(!parentname.equals(path_name))
			return parentname.substring(path_name.length() + 1, parentname.length());
		else
			return "";
	}
	
	public Collection getClassFiles()
	{
		return class_list.values();
	}
	
	public String getClassName(String classfullname)
	{
		if(!containsClass(classfullname))
			return null;
		File f = (File)class_list.get(classfullname);
		return getClassName(f);
	}
	
	public String getClassName(File f)
	{
		String filename = f.getName();
		return filename.substring(0, filename.indexOf(".class"));
	}
	
	public void setPath(String pathname)
	{
		class_path = new File(pathname);
		path_name = class_path.toString();
	}
	
	public String getpathname()
	{
		return path_name;
	}
	
	public void updateClassList()
	{
		class_list.clear();
		addfile(class_path);
	}
	
	private void addfile(File f)
	{
		File[] fs = f.listFiles();
		for(int i = 0; i < fs.length; i++)
		{
			//debugger.log(fs[i].toString());
			//debugger.log(fs[i].getName());
			//debugger.log(fs[i].getParent());
			if(fs[i].isDirectory())
				addfile(fs[i]);
			else
			{
				String fn = fs[i].toString();
				if(fn.endsWith(".class")
						&& fn.startsWith(path_name))
				{
					String cn = fn.substring(path_name.length() + 1, fn.indexOf(".class"));
					class_list.put(cn, fs[i]);
				}
			}
		}
	}
	
	public void show()
	{
		/*File[] fs = class_path.listFiles();
		debugger.log(class_path.toString());
		for(int i = 0; i < fs.length; i++)
			debugger.log(fs[i].getPath());*/
		
	}
	
}
