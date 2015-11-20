package ravensproject;

import java.util.Comparator;

/**
 * Created by eriojasn on 11/20/15.
 */
public class RowRelationshipComparator implements Comparator<RowRelationship> {
    @Override
    public int compare(RowRelationship r1, RowRelationship r2) {
        return r1.combinedDifference - r2.combinedDifference;
    }
}
