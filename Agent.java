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
    	ProblemExtractor extractor = new ProblemExtractor(problem);
    	Set<String> allAttributes = extractor.GetAllAttributes();
    	ArrayList<RavensFigure> problemFigures =  extractor.GetProblemFigures();
    	Mapper mapper = new Mapper(problemFigures.get(0), problemFigures.get(1), allAttributes);
    	mapper.Map();
    	
        return 1;
    }
}
