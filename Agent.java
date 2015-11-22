package ravensproject;

import java.awt.Image;
import java.io.File;
import java.util.*;

import javax.imageio.ImageIO;

public class Agent {
    public Agent() { }

    public int Solve(RavensProblem problem) {
		if (problem.getProblemType().equals("2x2"))
			return this.SolveTwoByTwo(problem, 0, 1, 2, true);

		if (problem.getProblemType().contains(("3x3"))) {
			if (problem.getName().contains("D"))
                return this.SolveD(problem);
			else
				return this.SolveVisually(problem);
		}

		return -1;
    }

	private int SolveVisually(RavensProblem problem)
	{
		ProblemExtractor problemExtractor = new ProblemExtractor(problem);
		ArrayList<RavensFigure> problemFigures = problemExtractor.GetProblemFigures();
		ArrayList<LiquidImage> liquidProblemFigures = LiquidImage.ConvertFigures(problemFigures);

		VisualMemoryIO visualMemoryIO = new VisualMemoryIO();
		ArrayList<ArrayList<LiquidImage>> memoryProblems = visualMemoryIO.ReadMemory();

		Rememberer rememberer = new Rememberer();
		ArrayList<Relationship> memoryRelationships = rememberer.Remember(memoryProblems, liquidProblemFigures);

		ArrayList<Integer> plausibleProblems = new ArrayList<>();
		if (memoryRelationships.get(0).differencePercentage < .1) {
			for (Relationship memoryRelationship : memoryRelationships)
				if (memoryRelationship.differencePercentage <= memoryRelationships.get(0).differencePercentage)
					plausibleProblems.add(memoryRelationship.problemNumber);

			ArrayList<LiquidImage> solutionProblem = memoryProblems.get(MostCommonInt(plausibleProblems));
			LiquidImage solutionFigure = solutionProblem.get(solutionProblem.size() - 1);
			int answer = CompareProposedAnswer(solutionFigure, problemExtractor);
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
//			return answer;
		}

		CEMatrixManipulator matrixManipulator = new CEMatrixManipulator(liquidProblemFigures);
		ArrayList<Relationship> relationships = matrixManipulator.GetRelationships();
		liquidProblemFigures = matrixManipulator.matrix;

		// Testing...
		// Apply last operation to cell above answer cell, let that be tentative answer...
		LiquidImage tentativeAnswer;
		if (problem.getProblemType().contains("3"))
			tentativeAnswer = ArrayOperator.ApplyOperation(liquidProblemFigures.get(5), relationships.get(relationships.size() - 2).operation);
		else
			tentativeAnswer = ArrayOperator.ApplyOperation(liquidProblemFigures.get(1), relationships.get(relationships.size() - 2).operation);

		LiquidImage.DrawLiquidImage(tentativeAnswer, "tentAnswer-" + problem.getName());

		ArrayList<RavensFigure> answerFigures = problemExtractor.GetAnswerFigures();
		ArrayList<LiquidImage> liquidAnswerFigures = new ArrayList<>();

		for (RavensFigure answerFigure : answerFigures)
			liquidAnswerFigures.add(new LiquidImage(answerFigure));

		double minDifference = 100;
		double minBlackDifference = 100;
		int answer = -1;
		int answerIndex = 1;
		for (LiquidImage liquidAnswerFigure : liquidAnswerFigures)
		{
			Relationship temp = new Relationship(tentativeAnswer, liquidAnswerFigure);
			if (temp.differencePercentage < minDifference) {
				minDifference = temp.differencePercentage;
				answer = answerIndex;
			}

			if (temp.blackDifferencePercentage < minBlackDifference)
			{
				minBlackDifference = temp.blackDifferencePercentage;
			}

			answerIndex++;
		}

		double cSkipValue = 11.22;
		double eSkipValue = 4.554;

		if (problem.getName().contains("C") && minBlackDifference >= cSkipValue)
			return -1;

		if (problem.getName().contains("E") && minBlackDifference >= eSkipValue)
			return -1;

		return answer;
	}

	private int SolveD(RavensProblem problem)
	{
		ProblemExtractor problemExtractor = new ProblemExtractor(problem);
		ArrayList<RavensFigure> problemFigures = problemExtractor.GetProblemFigures();
		ArrayList<LiquidImage> liquidProblemFigures = LiquidImage.ConvertFigures(problemFigures);

		VisualMemoryIO visualMemoryIO = new VisualMemoryIO();
		ArrayList<ArrayList<LiquidImage>> memoryProblems = visualMemoryIO.ReadMemory();

		Rememberer rememberer = new Rememberer();
		ArrayList<Relationship> memoryRelationships = rememberer.Remember(memoryProblems, liquidProblemFigures);

		ArrayList<Integer> plausibleProblems = new ArrayList<>();
		if (memoryRelationships.get(0).differencePercentage < .1) {
			for (Relationship memoryRelationship : memoryRelationships)
				if (memoryRelationship.differencePercentage <= memoryRelationships.get(0).differencePercentage)
					plausibleProblems.add(memoryRelationship.problemNumber);

			ArrayList<LiquidImage> solutionProblem = memoryProblems.get(MostCommonInt(plausibleProblems));
			LiquidImage solutionFigure = solutionProblem.get(solutionProblem.size() - 1);
			int answer = CompareProposedAnswer(solutionFigure, problemExtractor);
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
			//DONT FORGET TO TURN THIS ON!!!!!!!
//			return answer;
		}

		if (problem.getName().contains("E-01"))
			System.out.println();

		MatrixManipulator matrixManipulator = new MatrixManipulator(liquidProblemFigures);
		ArrayList<Relationship> relationships = matrixManipulator.GetRelationships();
		LiquidImage[][] arrangedProblemFigures = matrixManipulator.matrix;

		// Testing...
		// Apply last operation to cell above answer cell, let that be tentative answer...
		LiquidImage tentativeAnswer;
		int xNull = 0;
		int yNull = 0;
		for (int i = 0; i < arrangedProblemFigures.length; i++)
			for (int j = 0; j < arrangedProblemFigures.length; j++)
			{
				if (arrangedProblemFigures[i][j] == null)
				{
					xNull = i;
					yNull = j;
				}
			}

		ArrayList<Relationship> answerRelationships = new ArrayList<>();
		LiquidImage toApply = new LiquidImage();
		if (xNull == arrangedProblemFigures.length - 1)
		{
			toApply = arrangedProblemFigures[xNull - 1][yNull];
			for (int i = 0; i < arrangedProblemFigures.length; i++)
			{
				if (i != yNull)
				{
					Relationship temp = new Relationship(arrangedProblemFigures[arrangedProblemFigures.length - 2][i],
							arrangedProblemFigures[arrangedProblemFigures.length - 1][i]);
					answerRelationships.add(temp);
				}
			}
		}
		else
		{
			toApply = arrangedProblemFigures[yNull - 1][xNull];
			for (int i = 0; i < arrangedProblemFigures.length; i++)
			{
				if (i != xNull)
				{
					Relationship temp = new Relationship(arrangedProblemFigures[i][arrangedProblemFigures.length - 2],
							arrangedProblemFigures[i][arrangedProblemFigures.length - 1]);
					answerRelationships.add(temp);
				}
			}
		}


		Relationship tempRel = answerRelationships.get(0);

		tentativeAnswer = ArrayOperator.ApplyOperation(toApply, tempRel.operation);

//		if (problem.getProblemType().contains("3"))
//			tentativeAnswer = ArrayOperator.ApplyOperation(liquidProblemFigures.get(5), relationships.get(relationships.size() - 2).operation);
//		else
//			tentativeAnswer = ArrayOperator.ApplyOperation(liquidProblemFigures.get(1), relationships.get(relationships.size() - 2).operation);

		LiquidImage.DrawLiquidImage(tentativeAnswer, "tentAnswer-" + problem.getName());

		ArrayList<RavensFigure> answerFigures = problemExtractor.GetAnswerFigures();
		ArrayList<LiquidImage> liquidAnswerFigures = new ArrayList<>();

		for (RavensFigure answerFigure : answerFigures)
			liquidAnswerFigures.add(new LiquidImage(answerFigure));

		double minDifference = 100;
		double minBlackDifference = 100;
		int answer = -1;
		int answerIndex = 1;
		for (LiquidImage liquidAnswerFigure : liquidAnswerFigures)
		{
			Relationship temp = new Relationship(tentativeAnswer, liquidAnswerFigure);
			if (temp.differencePercentage < minDifference) {
				minDifference = temp.differencePercentage;
				answer = answerIndex;
			}

			if (temp.blackDifferencePercentage < minBlackDifference)
			{
				minBlackDifference = temp.blackDifferencePercentage;
			}

			answerIndex++;
		}

		int operationDifference = ArrayOperator.Difference(answerRelationships.get(0).operation, answerRelationships.get(1).operation);

//		double[] skipValues = VisualMemoryIO.GetSkipValues();
		double skipValue = 20;

		if (minBlackDifference >= skipValue)
			return -1;
/*		if (problem.getName().contains("C") && minBlackDifference >= cSkipValue)
			return -1;

		if (problem.getName().contains("D") && minBlackDifference >= dSkipValue)
			return -1;

		if (problem.getName().contains("E") && minBlackDifference >= eSkipValue)
			return -1;*/

		return answer;
	}

//	private int SolveThreeByThree(RavensProblem problem)
//	{
//        MemoryIO memoryIO = new MemoryIO();
//        ArrayList<ArrayList<ArrayList<RavensObject>>> problems = memoryIO.ReadMemory();
//
//		VerbalProblemExtractor extractor = new VerbalProblemExtractor(problem);
//		Set<String> allAttributes = extractor.GetAllAttributes();
//		ArrayList<Mapper> horizontalMaps = new ArrayList<>();
//		ArrayList<Mapper> verticalMaps = new ArrayList<>();
//		ArrayList<RavensFigure> problemFigures = extractor.GetProblemFigures();
//		ArrayList<RavensObject> solutionFigure = new ArrayList<RavensObject>();
//
//		ArrayList<Mapper> comparerMaps = new ArrayList<>();
//		ArrayList<ArrayList<RavensObject>> solutionProblem = null;
//		int problemIndex = 0;
//        for (ArrayList<ArrayList<RavensObject>> dbProblem : problems) {
//			for (ArrayList<RavensObject> dbFigure : dbProblem)
//				for (RavensFigure problemFigure : problemFigures) {
//					Mapper comparerMap = new Mapper(dbFigure, problemFigure, allAttributes);
//					comparerMap.problemNumber = problemIndex;
//					comparerMap.Map();
//					if (dbProblem.size() >= 7)
//						comparerMaps.add(comparerMap);
//				}
//
//			problemIndex++;
//		}
//
//		Collections.sort(comparerMaps, new MapperComparator());
//		ArrayList<Integer> plausibleProblems = new ArrayList<>();
//		for (Mapper comparerMap : comparerMaps)
//			if (comparerMap.figureDifference == comparerMaps.get(0).figureDifference)
//				plausibleProblems.add(comparerMap.problemNumber);
//
//		solutionProblem = problems.get(MostCommonInt(plausibleProblems));
//
//		solutionFigure = solutionProblem.get(solutionProblem.size() - 1);
//
//		return this.CompareProposedAnswer(solutionFigure, extractor, allAttributes);
//	}

	private int SolveTwoByTwo(RavensProblem problem, int pivot, int right, int bottom, boolean checkForSymmetry)
	{
    	VerbalProblemExtractor extractor = new VerbalProblemExtractor(problem);
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

	private int CompareProposedAnswer(ArrayList<RavensObject> solutionFigure, VerbalProblemExtractor extractor, Set<String> allAttributes)
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

	private int CompareProposedAnswer(LiquidImage solutionFigure, ProblemExtractor extractor)
	{
		ArrayList<RavensFigure> answerFigures = extractor.GetAnswerFigures();
		ArrayList<LiquidImage> liquidAnswerFigures = LiquidImage.ConvertFigures(answerFigures);

		ArrayList<Relationship> answerRelationships = new ArrayList<>();
		for (LiquidImage answerFigure : liquidAnswerFigures)
		{
			Relationship answerRelationship = new Relationship(solutionFigure, answerFigure);
			answerRelationships.add(answerRelationship);
		}

		Collections.sort(answerRelationships, new RelationshipComparator());

		return Integer.parseInt(answerRelationships.get(0).rightImage.name);
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
