package ravensproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class AttributeMerger implements IAttributeMerger {
	public HashMap<String, String> mergedAttributes;

	private ArrayList<RavensObject> objectsToMerge;
	private Set<String> attributes;
	private SymmetryChecker symmetryChecker;

	public AttributeMerger(ArrayList<RavensObject> objectsToMerge, Set<String> attributes, SymmetryChecker symmetryChecker)
	{
		this.objectsToMerge = objectsToMerge;
		this.attributes = attributes;
		this.symmetryChecker = symmetryChecker;
	}

	@Override
	public HashMap<String, String> Merge() {
		mergedAttributes = new HashMap<String, String>();
		ArrayList<HashMap<String, String>> objectAttributesToMerge = new ArrayList<HashMap<String, String>>();
		
		for (RavensObject objectToMerge : objectsToMerge)
			objectAttributesToMerge.add(AttributeListFiller.FillAttributeList(objectToMerge, attributes));
		
		for (String attribute : attributes)
			mergedAttributes.put(attribute, objectAttributesToMerge.get(0).get(attribute));

		for (int i = 1; i < objectsToMerge.size(); i++)
			for (String attribute : attributes)
				if (!ObjectDifference.IsAttributeEqual(objectAttributesToMerge.get(0), objectAttributesToMerge.get(i), attribute))
					mergedAttributes.put(attribute, objectAttributesToMerge.get(i).get(attribute));
		
		//CHECK FOR SPECIAL CASE
		
		if (symmetryChecker.symmetricAngle >= 0)
			mergedAttributes.put(SymmetryChecker.ANGLE_KEY, Integer.toString(symmetryChecker.symmetricAngle));
		
		if (symmetryChecker.symmetricPosition != null)
			mergedAttributes.put(SymmetryChecker.ALIGNMENT_KEY, symmetryChecker.symmetricPosition);
		
		return mergedAttributes;
	}
	
	public void PrintMergedAttributes()
	{
		System.out.println("Printing merged attributes...");
		
		for (String key : mergedAttributes.keySet())
			System.out.println(key + ":" + mergedAttributes.get(key));
	}
}
