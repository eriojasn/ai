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
	private HashMap<String, String> leftObjectAttributes;
	private HashMap<String, String> rightObjectAttributes;
	
	public ObjectDifference(RavensObject leftObject, RavensObject rightObject, Set<String> attributes)
	{
		this.leftObject = leftObject;
		this.rightObject = rightObject;
		this.attributes = attributes;
		this.difference = attributes.size();
		
		this.leftObjectAttributes = AttributeListFiller.FillAttributeList(leftObject, attributes);
		this.rightObjectAttributes = AttributeListFiller.FillAttributeList(rightObject, attributes);

		this.Compare();
	}
	
	public ObjectDifference(HashMap<String, String> leftAttributes, RavensObject rightObject, Set<String> attributes)
	{
		this.leftObjectAttributes = leftAttributes;
		this.rightObject = rightObject;
		this.attributes = attributes;
		this.difference = attributes.size();
		
		this.rightObjectAttributes = AttributeListFiller.FillAttributeList(rightObject, attributes);

		this.Compare();
	}

	@Override
	public int Compare() {
		this.replacementAttributes = new HashMap<String, String>();

		for	(String attribute : this.attributes)
			if (ObjectDifference.IsAttributeEqual(leftObjectAttributes, rightObjectAttributes, attribute))
				difference--;
			else if (rightObjectAttributes.get(attribute) != null)
				this.replacementAttributes.put(attribute, rightObjectAttributes.get(attribute));

		return difference;
	}
	
	public static boolean IsAttributeEqual(HashMap<String, String> leftObjectAttributes, HashMap<String, String> rightObjectAttributes, String attribute)
	{
		if (leftObjectAttributes.get(attribute) == rightObjectAttributes.get(attribute) ||
		((leftObjectAttributes.get(attribute) != null && rightObjectAttributes.get(attribute) != null) &&
				leftObjectAttributes.get(attribute).equals(rightObjectAttributes.get(attribute))))
			return true;
		else return false;
	}

	public void PrintDifference()
	{
		System.out.println(this.difference);
	}
}
