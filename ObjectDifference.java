package ravensproject;

import java.util.HashMap;
import java.util.Set;

public class ObjectDifference implements IDifference {
	public RavensObject compared1;
	public RavensObject compared2;
	public int difference;
	private Set<String> attributes;
	
	public ObjectDifference(RavensObject compared1, RavensObject compared2, Set<String> attributes)
	{
		this.compared1 = compared1;
		this.compared2 = compared2;
		this.attributes = attributes;
		this.difference = attributes.size();
		this.Compare();
	}

	@Override
	public int Compare() {
		HashMap<String, String> compared1Attributes = compared1.getAttributes();
		HashMap<String, String> compared2Attributes = compared2.getAttributes();

		for	(String attribute : this.attributes)
			if(compared1Attributes.get(attribute) == compared2Attributes.get(attribute) ||
			((compared1Attributes.get(attribute) != null && compared2Attributes.get(attribute) != null) &&
					compared1Attributes.get(attribute).equals(compared2Attributes.get(attribute))))
				difference--;

		System.out.println("The difference between RavensObject " + compared1.getName() 
				+ " and RavensObject " + compared2.getName() + " is " + difference);
		
		return difference;
	}
}
