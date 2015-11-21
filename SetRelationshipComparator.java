package ravensproject;

import java.util.Comparator;

/**
 * Created by eriojasn on 11/20/15.
 */
public class SetRelationshipComparator implements Comparator<SetRelationship> {
    @Override
    public int compare(SetRelationship r1, SetRelationship r2) {
        return r1.combinedDifference - r2.combinedDifference;
    }
}
