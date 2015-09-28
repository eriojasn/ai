package ravensproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class SymmetryChecker implements ISymmetryChecker {
	public static final String ALIGNMENT_KEY = "alignment";
	public static final String ANGLE_KEY = "angle";
	private static final String[] FOUR_CORNERS = { "bottom-right", "bottom-left", "top-right", "top-left" };
	
	public String symmetricPosition;
	public boolean angularSymmetry;
	public int symmetricAngle;

	private ArrayList<RavensFigure> problemFigures;
	
	public SymmetryChecker(ArrayList<RavensFigure> problemFigures)
	{
		this.problemFigures = problemFigures;
		this.CheckSymmetry();
	}

	@Override
	public void CheckSymmetry() {
		symmetricPosition = this.CheckPositionalSymmetry();
		symmetricAngle = this.CheckAngularSymmetry();
	}
	
	private String CheckPositionalSymmetry()
	{
		ArrayList<String> alignments = new ArrayList<String>();
		ArrayList<String> alreadyContained = new ArrayList<String>();
		HashMap<String, Boolean> missingAlignment = new HashMap<String, Boolean>();
		
		for (int i = 0; i < FOUR_CORNERS.length; i++)
			missingAlignment.put(FOUR_CORNERS[i], false);

		for (RavensFigure problemFigure : problemFigures)
		{
			FigureExtractor figureExtractor = new FigureExtractor(problemFigure);
			ArrayList<RavensObject> problemObjects = figureExtractor.GetAllObjects();
			alignments.add(problemObjects.get(0).getAttributes().get(ALIGNMENT_KEY));
		}

		for (int i = 0; i < FOUR_CORNERS.length; i++)
			for (String alignment : alignments)
				if (alignment != null && alignment.equals(FOUR_CORNERS[i]) && !alreadyContained.contains(alignment))
				{
					alreadyContained.add(alignment);
					missingAlignment.put(FOUR_CORNERS[i], true);
				}
		
		if (alreadyContained.size() == 3)
			for (String key : missingAlignment.keySet())
				if (!missingAlignment.get(key))
					return key;
		
		return null;
	}
	
	private int CheckAngularSymmetry()
	{
		ArrayList<Integer> angles = new ArrayList<Integer>();
		int chance = 0;
		int missingAngle = 0;
		
		for (RavensFigure problemFigure : problemFigures)
		{
			FigureExtractor figureExtractor = new FigureExtractor(problemFigure);
			ArrayList<RavensObject> problemObjects = figureExtractor.GetAllObjects();
			if (problemObjects.get(0).getAttributes().get(ANGLE_KEY) != null)
				angles.add(Integer.parseInt(problemObjects.get(0).getAttributes().get(ANGLE_KEY)));
		}
		
		if (angles.size() == 0) return -1;
		Collections.sort(angles);
		int comparedAngle = angles.get(0);
		
		for (int i = 0; i < angles.size(); i++) 
		{
			if(comparedAngle == angles.get(i) && chance < 2)
				comparedAngle += 90;
			else
			{
				chance++;
				if (chance >= 2) return -1;
				missingAngle = comparedAngle;
				i--;
				comparedAngle += 90;
			}
		}
		
		return missingAngle;
	}
}
