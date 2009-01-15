package util;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.PrintStream;
import java.util.Date;
import java.text.SimpleDateFormat;

public class debugger 
{
	public final static int DEBUG_LEVEL_BYTES = 80;
	public final static int DEBUG_LEVEL_THREADS = 50;
	public final static int DEBUG_LEVEL_CALLSTACK = 40;
	public final static int DEBUG_LEVEL_STAMP = 30;
	public final static int DEBUG_LEVEL_INFO = 20;
	public final static int DEBUG_LEVEL_ERROR = 10;

	private final static String STR_INFO = "[info]  ";
	private final static String STR_ERR	= "[error] ";
	private final static String STR_STAMP = "[stamp] ";
	private final static String STR_BYTES = "[bytes]";
	private final static String STR_CALLSTACK = "[callstack]";
	private final static String STR_THREADS = "[threads]";
	private final static String STR_UNKNOWN = "[unknown]";

	
	private static debugger d = null;
	private static int currentlevel = DEBUG_LEVEL_CALLSTACK;
	private static int count = 0;

	
	private static PrintStream syspw;
	
	public debugger()
	{
		
		
	}

	public static debugger getInstance()
	{
		if(d == null)
			d = new debugger();

		return d;
	}

	public static void info(String s)
	{
		if(currentlevel >= DEBUG_LEVEL_INFO)
			log(DEBUG_LEVEL_INFO, s);
	}

	public static void error(String s)
	{
		if(currentlevel >= DEBUG_LEVEL_ERROR)
			log(DEBUG_LEVEL_ERROR, s);
	}

	public static void stamp()
	{
		if(currentlevel >= DEBUG_LEVEL_STAMP)
			log(DEBUG_LEVEL_STAMP, "");
	}

	public static void stamp(String s)
	{
		if(currentlevel >= DEBUG_LEVEL_STAMP)
			log(DEBUG_LEVEL_STAMP, s);
	}

	public static void callstack()
	{
		if(currentlevel >= DEBUG_LEVEL_CALLSTACK)
			log(DEBUG_LEVEL_CALLSTACK, null);
	}

	public static void threads()
	{
		if(currentlevel >= DEBUG_LEVEL_CALLSTACK)
			log(DEBUG_LEVEL_THREADS, null);
	}

	public static void dumpbytes(byte[] bytes)
	{
		dumpbytes(bytes, 0, bytes.length);
	}

	public static void dumpbytes(byte[] bytes, int offset, int len)
	{
		int byteslen = offset + len;
		int l = bytes.length;
		String strln;

		if(offset > l)
			return;
		else if(byteslen > l)
			byteslen = l;
		
		
		//log(DEBUG_LEVEL_BYTES, "Dump Bytes Start");
		for(int i = offset; i < byteslen; i+=16)
		{
			strln = hexstr(i, 8) + "  ";
			for(int j = 0; (j < 16)&&((i + j) < byteslen); j++)
				strln = strln + hexstr(bytes[i + j], 2) + " ";
			log(strln);
		}
		//log(DEBUG_LEVEL_BYTES, "Dump Bytes End");
	
	}
	
	public static String hexstr(int value, int strlen)
	{
		String istr = Integer.toHexString(value);
		int len = istr.length();

		if(len >= strlen)
			return istr.substring((len - strlen), len);
		else
		{
			for(int i = 0; i < (strlen - len); i++)
				istr = "0" + istr;
			return istr;
		}
	}
	
	public static void log(int level, String s)
	{

		createsyspw();
		String str;
		
		switch(level)
		{
			case DEBUG_LEVEL_INFO:
				str = STR_INFO;
				break;
			case DEBUG_LEVEL_ERROR:
				str = STR_ERR;
				break;
			case DEBUG_LEVEL_STAMP:
				str = STR_STAMP;
				break;
			case DEBUG_LEVEL_CALLSTACK:
				str = STR_CALLSTACK;
				showcallstack();
				break;
			case DEBUG_LEVEL_THREADS:
				str = STR_THREADS;
				listthreads();
				break;
			default:
				str = STR_UNKNOWN;
				break;
		}
		/*Just print now*/
		//count++;
		String countstr = (new SimpleDateFormat("HH:mm:ss.SSS")).format(new Date());
		syspw.print("[" + countstr + "]" + " -> " + str + " " + s + "\n");
	}

	public static void log(String s)
	{
		createsyspw();
		syspw.print(s + "\n");
	}

	public static void showcallstack()
	{
		new Throwable().printStackTrace(syspw);
	}
	
	public static void setpw(String filename)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(filename);
			syspw = new PrintStream(fos, true);
		}
		catch(Exception e)
		{
			System.out.println("Exception " + e + " happened!");
		}
	}
	
	public static void createsyspw()
	{
		if(syspw != null)
			return;
		setpw("javaparse.log");
	}

	public static void listthreads()
	{
		/*Get current root thread group*/
		ThreadGroup root = Thread.currentThread().getThreadGroup().getParent();
    	while (root.getParent() != null) 
    	{
        	root = root.getParent();
        }

        checkgroup(root, 0);
	}

	private static void checkgroup(ThreadGroup group, int level)
	{
        int numThreads = group.activeCount();
        Thread[] threads = new Thread[numThreads*2];
        numThreads = group.enumerate(threads, false);
    
        for (int i=0; i<numThreads; i++) 
        {
           syspw.println(threads[i].toString() + " in " + group.toString()); 
	    }
    
        int numGroups = group.activeGroupCount();
        ThreadGroup[] groups = new ThreadGroup[numGroups*2];
        numGroups = group.enumerate(groups, false);
    
        for (int i=0; i<numGroups; i++) {
            checkgroup(groups[i], level+1);
        }
	}
}
