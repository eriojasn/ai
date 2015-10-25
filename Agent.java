package ravensproject;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

public class Agent {
    public Agent() { }

    public int Solve(RavensProblem problem) {
    	if (!problem.hasVerbal())
    		return -1;

		System.out.println("New problem " + problem.getName() + "...");

		if (problem.getName().contains("Problem B-"))
			return this.SolveTwoByTwo(problem);

		if (problem.getName().contains(("Problem C-")))
			return this.SolveThreeByThree(problem);

		return -1;
    }

	private int SolveThreeByThree(RavensProblem problem)
	{
		ProblemExtractor extractor = new ProblemExtractor(problem);
		HashMap<String, Set<String>> attributeValues = extractor.GetAllAttributeValuePairs();

		for (String attribute : attributeValues.keySet())
		{
			System.out.print("All possible values for " + attribute + ": ");
			Set<String> temp = attributeValues.get(attribute);

			for (String value : temp)
				System.out.print(value + ";");

			System.out.println();
		}

		return -1;
	}

	private int SolveTwoByTwo(RavensProblem problem)
	{
    	ProblemExtractor extractor = new ProblemExtractor(problem);
    	Set<String> allAttributes = extractor.GetAllAttributes();
    	ArrayList<RavensFigure> problemFigures =  extractor.GetProblemFigures();
		HashMap<String, Set<String>> attributeValues = extractor.GetAllAttributeValuePairs();

    	SymmetryChecker symmetryChecker = new SymmetryChecker(problemFigures);

    	Mapper horizontalMapper = new Mapper(problemFigures.get(0), problemFigures.get(1), allAttributes);
    	ArrayList<Pair<RavensObject, RavensObject>> horizontalMap = horizontalMapper.Map();
    	Mapper verticalMapper = new Mapper(problemFigures.get(0), problemFigures.get(2), allAttributes);
    	ArrayList<Pair<RavensObject, RavensObject>> verticalMap = verticalMapper.Map();

    	horizontalMapper.PrintMap();
    	verticalMapper.PrintMap();

    	ArrayList<Mapper> maps = new ArrayList<Mapper>();
    	maps.add(horizontalMapper);
    	maps.add(verticalMapper);
    	TwoMapMerger mapMerger = new TwoMapMerger(horizontalMapper, verticalMapper);
    	mapMerger.Merge();
    	mapMerger.PrintMergedMap();

    	ArrayList<RavensObject> solutionFigure = new ArrayList<RavensObject>();
    	for (ArrayList<RavensObject> row : mapMerger.mergedMap)
    	{
    		AttributeMerger attributeMerger = new AttributeMerger(row, allAttributes, symmetryChecker);
    		attributeMerger.Merge();
    		attributeMerger.PrintMergedAttributes();
    		MockRavensObject temp = new MockRavensObject("x" + 1);
    		temp.attributes = attributeMerger.mergedAttributes;
    		solutionFigure.add(temp);
    	}

    	ArrayList<RavensFigure> answerFigures = extractor.GetAnswerFigures();

    	ArrayList<Mapper> answerMaps = new ArrayList<Mapper>();
    	for (RavensFigure answerFigure : answerFigures)
    	{
    		Mapper answerMap = new Mapper(solutionFigure, answerFigure, allAttributes);
    		answerMap.Map();
    		answerMaps.add(answerMap);
    	}

    	Collections.sort(answerMaps, new MapperComparator());

        return Integer.parseInt(answerMaps.get(0).rightFigure.getName());
	}
}
