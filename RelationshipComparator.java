package ravensproject;

import java.util.Comparator;

/**
 * Created by eriojasn on 11/20/15.
 */
public class RelationshipComparator implements Comparator<Relationship> {
    @Override
    public int compare(Relationship r1, Relationship r2) {
        return r1.difference - r2.difference;
    }
}
