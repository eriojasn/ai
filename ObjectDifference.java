package ravensproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class ObjectDifference implements IDifference {
	public RavensObject leftObject;
	public RavensObject rightObject;
	public HashMap<String, String> replacementAttributes;
	public int difference;

	private Set<String> attributes;
	
	public ObjectDifference(RavensObject compared1, RavensObject compared2, Set<String> attributes)
	{
		this.leftObject = compared1;
		this.rightObject = compared2;
		this.attributes = attributes;
		
		this.Compare();
	}

	@Override
	public int Compare() {
		this.replacementAttributes = new HashMap<String, String>();
		this.difference = attributes.size();

		HashMap<String, String> leftObjectAttributes = leftObject.getAttributes();
		HashMap<String, String> rightObjectAttributes = rightObject.getAttributes();

		for	(String attribute : this.attributes)
			if (leftObjectAttributes.get(attribute) == rightObjectAttributes.get(attribute) ||
			((leftObjectAttributes.get(attribute) != null && rightObjectAttributes.get(attribute) != null) &&
					leftObjectAttributes.get(attribute).equals(rightObjectAttributes.get(attribute))))
				difference--;
			else if (rightObjectAttributes.get(attribute) != null)
				this.replacementAttributes.put(attribute, rightObjectAttributes.get(attribute));

		return difference;
	}
	
	public void PrintDifference()
	{
		System.out.println();
		System.out.println("Printing out difference...");
		System.out.println("The difference between RavensObject " + leftObject.getName() 
			+ " and RavensObject " + rightObject.getName() + " is " + difference);
		
		for (String attribute : this.replacementAttributes.keySet())
			System.out.println(attribute + " becomes " + this.replacementAttributes.get(attribute));
	}
}
