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

		if (problem.getProblemType().equals("2x2"))
			return this.SolveTwoByTwo(problem, 0, 1, 2, true);

		if (problem.getProblemType().contains(("3x3")))
			return this.SolveThreeByThree(problem);

		return -1;
    }

	private int SolveThreeByThree(RavensProblem problem)
	{
        MemoryIO memoryIO = new MemoryIO();
        ArrayList<ArrayList<ArrayList<RavensObject>>> problems = memoryIO.ReadMemory();
		ProblemExtractor extractor = new ProblemExtractor(problem);
		Set<String> allAttributes = extractor.GetAllAttributes();
		ArrayList<Mapper> horizontalMaps = new ArrayList<>();
		ArrayList<Mapper> verticalMaps = new ArrayList<>();
		ArrayList<RavensFigure> problemFigures = extractor.GetProblemFigures();
		ArrayList<RavensObject> solutionFigure = new ArrayList<RavensObject>();

		boolean foundProblem = false;
		ArrayList<ArrayList<RavensObject>> solutionProblem = null;
        for (ArrayList<ArrayList<RavensObject>> dbProblem : problems) {
            for (ArrayList<RavensObject> dbFigure : dbProblem) {
                Mapper comparerMap = new Mapper(dbFigure, problemFigures.get(0), allAttributes);
                comparerMap.Map();
                int diff = comparerMap.figureDifference;

				if (diff == 0) {
					foundProblem = true;
					break;
				}
            }

			if (foundProblem) {
				solutionFigure = dbProblem.get(dbProblem.size() - 1);
				break;
			}
        }

		if (foundProblem)
			return this.CompareProposedAnswer(solutionFigure, extractor, allAttributes);
		else
			return this.SolveTwoByTwo(problem, 4, 5, 7, false);
	}

	private int SolveTwoByTwo(RavensProblem problem, int pivot, int right, int bottom, boolean checkForSymmetry)
	{
    	ProblemExtractor extractor = new ProblemExtractor(problem);
    	Set<String> allAttributes = extractor.GetAllAttributes();
    	ArrayList<RavensFigure> problemFigures =  extractor.GetProblemFigures();
		HashMap<String, Set<String>> attributeValues = extractor.GetAllAttributeValuePairs();

		SymmetryChecker symmetryChecker = null;
		if (checkForSymmetry)
			symmetryChecker = new SymmetryChecker(problemFigures);

    	Mapper horizontalMapper = new Mapper(problemFigures.get(pivot), problemFigures.get(right), allAttributes);
    	ArrayList<Pair<RavensObject, RavensObject>> horizontalMap = horizontalMapper.Map();
    	Mapper verticalMapper = new Mapper(problemFigures.get(pivot), problemFigures.get(bottom), allAttributes);
    	ArrayList<Pair<RavensObject, RavensObject>> verticalMap = verticalMapper.Map();

    	horizontalMapper.PrintMap();
    	verticalMapper.PrintMap();

    	TwoMapMerger mapMerger = new TwoMapMerger(horizontalMapper, verticalMapper);
    	mapMerger.Merge();
    	mapMerger.PrintMergedMap();

    	ArrayList<RavensObject> solutionFigure = new ArrayList<RavensObject>();
    	for (ArrayList<RavensObject> row : mapMerger.mergedMap)
    	{
			AttributeMerger attributeMerger = null;
			if (symmetryChecker != null)
    			attributeMerger = new AttributeMerger(row, allAttributes, symmetryChecker);
			else
				attributeMerger = new AttributeMerger(row, allAttributes);

    		attributeMerger.Merge();
    		attributeMerger.PrintMergedAttributes();
    		MockRavensObject temp = new MockRavensObject("x" + 1);
    		temp.attributes = attributeMerger.mergedAttributes;
    		solutionFigure.add(temp);
    	}

		return this.CompareProposedAnswer(solutionFigure, extractor, allAttributes);
	}

	private int CompareProposedAnswer(ArrayList<RavensObject> solutionFigure, ProblemExtractor extractor, Set<String> allAttributes)
	{
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
