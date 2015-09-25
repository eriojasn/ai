package ravensproject;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

public class Agent {
    public Agent() {
        
    }

    public int Solve(RavensProblem problem) {
    	/*The first step is to map each object to its corresponding object
    	 * in another square. Squares have to be adjacent to each other
    	 * and not diagonal to each other. Should find an algorithm but since
    	 * this is 2x2 going to hard code.
    	 */
    	System.out.println("New problem " + problem.getName() + "...");

    	ProblemExtractor extractor = new ProblemExtractor(problem);
    	Set<String> allAttributes = extractor.GetAllAttributes();
    	ArrayList<RavensFigure> problemFigures =  extractor.GetProblemFigures();
    	Mapper mapper = new Mapper(problemFigures.get(0), problemFigures.get(1), allAttributes);
    	ArrayList<Pair<RavensObject, RavensObject>> map = mapper.Map();
    	this.PrintOutMap(map, allAttributes);
    	
        return 1;
    }
    
    private void PrintOutMap(ArrayList<Pair<RavensObject, RavensObject>> map, Set<String> attributes)
    {
    	System.out.println("Printing out map...");
    	for (Pair<RavensObject, RavensObject> pair : map)
    	{
    		RavensObject left = pair.getLeft();
    		RavensObject right = pair.getRight();
    		
    		if (left != null)
    			System.out.print(left.getName());
    		else
    			System.out.print("null");
    		
    		System.out.print(" with ");
    		
    		if (right != null)
    			System.out.print(right.getName());
    		else
    			System.out.print("null");
    		
    		if (left != null && right != null)
    			System.out.print(" with a difference of " + (new ObjectDifference(pair.getLeft(), pair.getRight(), attributes)).difference);
    		
    		System.out.println();
    	}
    }
}
