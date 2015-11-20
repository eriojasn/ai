package ravensproject;

import java.util.Comparator;

public class RavensFigureComparator implements Comparator<RavensFigure> {
    @Override
    public int compare(RavensFigure r1, RavensFigure r2) {
        return r1.getName().compareTo(r2.getName());
    }
}
