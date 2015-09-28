package ravensproject;

import java.util.Comparator;

public class ObjectDifferenceComparator implements Comparator<ObjectDifference> {
	@Override
	public int compare(ObjectDifference arg0, ObjectDifference arg1) {
		return arg0.difference - arg1.difference;
	}
}
