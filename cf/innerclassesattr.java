package cf;

import util.debugger;

public class innerclassesattr extends attr 
{
	private short number_of_classes;
	private innerclass[] innerclasses;
	
	public innerclassesattr(int type, short ani, int al, short noc, innerclass[] ics)
	{
		super(type, ani, al);
		number_of_classes = noc;
		innerclasses = ics;
	}
	
	public short getnumberofclasses()
	{
		return number_of_classes;
	}
	
	public innerclass[] getinnerclasses()
	{
		return innerclasses;
	}
	
	public void showforsuper()
	{
		super.showforsuper();
		for(int i = 0; i < number_of_classes; i++)
		{
			debugger.log("inner classes: " + i);
			innerclasses[i].show();
		}
	}
	
	
}
