package ravensproject;

import java.util.HashMap;
import java.util.Set;

public class AttributeListFiller {
	public static HashMap<String, String> FillAttributeList(RavensObject ravensObject, Set<String> attributes)
	{
		HashMap<String, String> attributeList = new HashMap<String, String>();
		
		for (String attribute : attributes)
			attributeList.put(attribute, null);
		
		if (ravensObject == null)
			return attributeList;
		
		for (String attribute : attributes)
			attributeList.put(attribute, ravensObject.getAttributes().get(attribute));
		
		return attributeList;
	}
}
