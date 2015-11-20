package ravensproject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by eriojasn on 11/20/15.
 */
public class Rememberer {
    public ArrayList<Relationship> Remember(ArrayList<ArrayList<LiquidImage>> memory, ArrayList<LiquidImage> problemFigures)
    {
        ArrayList<Relationship> relationships = new ArrayList<>();
        int problemIndex = 0;
       for (ArrayList<LiquidImage> memoryProblem : memory) {
           for (LiquidImage memoryFigure : memoryProblem)
               for (LiquidImage problemFigure : problemFigures) {
                   Relationship temp = new Relationship(problemFigure, memoryFigure);
                   temp.problemNumber = problemIndex;
                   relationships.add(temp);
               }

           problemIndex++;
       }

        Collections.sort(relationships, new RelationshipComparator());

        return relationships;
    }
}
