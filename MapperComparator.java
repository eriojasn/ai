package ravensproject;

import java.util.Comparator;

public class MapperComparator implements Comparator<Mapper> {
	@Override
	public int compare(Mapper o1, Mapper o2) {
		return o1.figureDifference - o2.figureDifference;
	}
}
