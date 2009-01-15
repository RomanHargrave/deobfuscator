package cf;

import java.io.DataInputStream;
import java.io.InputStream;

public class attrreader 
{	
	DataInputStream dis;
		
	public attrreader(InputStream is)
	{
		dis = (DataInputStream)is;
	}
	
	public attr readattr()
	{
		attr curattr = null;
		
		try 
		{
			short ani = dis.readShort();
			int al = dis.readInt();
			int curtype = attrtype.checktype(ani);
			switch(curtype)
			{
				case attrtype.ATTR_CONSTANT:
					short ci = dis.readShort();
					curattr = new constantvalueattr(curtype, ani, al, ci);
					break;
				case attrtype.ATTR_CODE:
					short ms = dis.readShort();
					short ml = dis.readShort();
					int cl = dis.readInt();
					byte[] c = new byte[cl];
					dis.read(c);
					/*read exception table*/
					short etl = dis.readShort();
					exceptiontable[] et = new exceptiontable[etl];
					for(int i = 0; i < etl; i++)
					{
						short sp = dis.readShort();
						short ep = dis.readShort();
						short hp = dis.readShort();
						short ct = dis.readShort();
						et[i] = new exceptiontable(sp, ep, hp, ct);
					}
					short ac = dis.readShort();
					attr[] as = new attr[ac];
					attrreader ar = new attrreader(dis);
					for(int i = 0; i < ac; i++)
						as[i] = ar.readattr();
					curattr = new codeattr(curtype, ani, al, ms, ml, cl, c, etl, et, ac, as);
					break;
				case attrtype.ATTR_EXCEPTIONS:
					short noe = dis.readShort();
					short[] eit = new short[noe];
					for(int i = 0; i < noe; i++)
						eit[i] = dis.readShort();
					curattr = new exceptionsattr(curtype, ani, al, noe, eit);
					break;
				case attrtype.ATTR_INNERCLASSES:
					short noc = dis.readShort();
					innerclass[] ics = new innerclass[noc];
					for(int i = 0; i < noc; i++)
					{
						short icii = dis.readShort();
						short ocii = dis.readShort();
						short ini = dis.readShort();
						short icaf = dis.readShort();
						ics[i] = new innerclass(icii, ocii, ini, icaf);
					}
					curattr = new innerclassesattr(curtype, ani, al, noc, ics);
					break;
				case attrtype.ATTR_SYNTHETIC:
					curattr = new syntheticattr(curtype, ani, al);	
					break;
				case attrtype.ATTR_SOURCEFILE:
					short si = dis.readShort();
					curattr = new sourcefileattr(curtype, ani, al, si);
					break;
				case attrtype.ATTR_LINENUMTBL:
					short lntl = dis.readShort();
					linenumber[] lnt = new linenumber[lntl];
					for(int i = 0; i < lntl; i++)
					{
						short sp = dis.readShort();
						short ln = dis.readShort();
						lnt[i] = new linenumber(sp, ln);
					}
					curattr = new linenumbertableattr(curtype, ani, al, lntl, lnt);
					break;
				case attrtype.ATTR_LOCALVARTBL:
					short lvtl = dis.readShort();
					localvariable[] lvt = new localvariable[lvtl];
					for(int i = 0; i < lvtl; i++)
					{
						short sp = dis.readShort();
						short l = dis.readShort();
						short ni = dis.readShort();
						short di = dis.readShort();
						short index = dis.readShort();
						lvt[i] = new localvariable(sp, l, ni, di, index);
					}
					curattr = new localvariabletableattr(curtype, ani, al, lvtl, lvt);
					break;
				case attrtype.ATTR_DEPRECATED:
					curattr = new deprecatedattr(curtype, ani, al);
					break;
				case attrtype.ATTR_NULL:
					curattr = null;
					break;
			}
	
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
			curattr = null;
		}
		
		return curattr;
	}
	
	
	
}
