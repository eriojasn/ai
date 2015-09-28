package ravensproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class AttributeMerger implements IAttributeMerger {
	public HashMap<String, String> mergedAttributes;

	private ArrayList<RavensObject> objectsToMerge;
	private Set<String> attributes;

	public AttributeMerger(ArrayList<RavensObject> objectsToMerge, Set<String> attributes)
	{
		this.objectsToMerge = objectsToMerge;
		this.attributes = attributes;
	}

	@Override
	public HashMap<String, String> Merge() {
		mergedAttributes = new HashMap<String, String>();
		ArrayList<HashMap<String, String>> objectAttributesToMerge = new ArrayList<HashMap<String, String>>();
		
		for (RavensObject objectToMerge : objectsToMerge)
			objectAttributesToMerge.add(this.FillAttributeList(objectToMerge));
		
		for (String attribute : attributes)
			mergedAttributes.put(attribute, objectAttributesToMerge.get(0).get(attribute));

		for (int i = 1; i < objectsToMerge.size(); i++)
			for (String attribute : attributes)
				if (!ObjectDifference.IsAttributeEqual(objectAttributesToMerge.get(0), objectAttributesToMerge.get(i), attribute))
					mergedAttributes.put(attribute, objectAttributesToMerge.get(i).get(attribute));
		
		return mergedAttributes;
	}
	
	public void PrintMergedAttributes()
	{
		System.out.println("Printing merged attributes...");
		
		for (String key : mergedAttributes.keySet())
			System.out.println(key + ":" + mergedAttributes.get(key));
	}
	
	private HashMap<String, String> FillAttributeList(RavensObject ravensObject)
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
