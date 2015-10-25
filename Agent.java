package ravensproject;

import java.awt.Image;
import java.io.File;
import java.util.*;

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

		ArrayList<Mapper> comparerMaps = new ArrayList<>();
		ArrayList<ArrayList<RavensObject>> solutionProblem = null;
		int problemIndex = 0;
        for (ArrayList<ArrayList<RavensObject>> dbProblem : problems) {
			for (ArrayList<RavensObject> dbFigure : dbProblem)
				for (RavensFigure problemFigure : problemFigures) {
					Mapper comparerMap = new Mapper(dbFigure, problemFigure, allAttributes);
					comparerMap.problemNumber = problemIndex;
					comparerMap.Map();
					if (dbProblem.size() >= 7)
						comparerMaps.add(comparerMap);
				}

			problemIndex++;
		}

		Collections.sort(comparerMaps, new MapperComparator());
		ArrayList<Integer> plausibleProblems = new ArrayList<>();
		for (Mapper comparerMap : comparerMaps)
			if (comparerMap.figureDifference == comparerMaps.get(0).figureDifference)
				plausibleProblems.add(comparerMap.problemNumber);

		solutionProblem = problems.get(MostCommonInt(plausibleProblems));

		solutionFigure = solutionProblem.get(solutionProblem.size() - 1);

		return this.CompareProposedAnswer(solutionFigure, extractor, allAttributes);
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

	public static <T> T MostCommonInt(List<T> list) {
		Map<T, Integer> map = new HashMap<>();

		for (T t : list) {
			Integer val = map.get(t);
			map.put(t, val == null ? 1 : val + 1);
		}

		Map.Entry<T, Integer> max = null;

		for (Map.Entry<T, Integer> e : map.entrySet()) {
			if (max == null || e.getValue() > max.getValue())
				max = e;
		}

		return max.getKey();
	}
}
