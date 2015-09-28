package ravensproject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ProblemExtractor implements IExtractor {
	private RavensProblem problem;

	public ProblemExtractor(RavensProblem problem)
	{
		this.problem = problem;
	}
	
	public ArrayList<RavensFigure> GetAnswerFigures()
	{
		ArrayList<RavensFigure> allFigures = this.GetAllFigures();
		ArrayList<RavensFigure> answerFigures = new ArrayList<RavensFigure>();
		
		for (RavensFigure figure : allFigures)
			if (Character.isDigit(figure.getName().charAt(0)))
				answerFigures.add(figure);
		
		return answerFigures;
	}
	
	public ArrayList<RavensFigure> GetProblemFigures()
	{
		ArrayList<RavensFigure> allFigures = this.GetAllFigures();
		ArrayList<RavensFigure> problemFigures = new ArrayList<RavensFigure>();
		
		for (RavensFigure figure : allFigures)
			if (Character.isAlphabetic(figure.getName().charAt(0)))
				problemFigures.add(figure);

		return problemFigures;
	}

	public ArrayList<RavensFigure> GetAllFigures()
	{
		ArrayList<RavensFigure> allFigures = new ArrayList<RavensFigure>(problem.getFigures().values());
		
		return allFigures;
	}
	
	public ArrayList<RavensObject> GetAllObjects()
	{
		ArrayList<RavensObject> allObjects = new ArrayList<RavensObject>();
		
		for (RavensFigure figure : this.GetAllFigures())
			for (RavensObject object : figure.getObjects().values())
				allObjects.add(object);
		
		return allObjects;
	}
	
	public Set<String> GetAllAttributes()
	{
		Set<String> allAttributes = new HashSet<String>();
		
		for (RavensObject object : this.GetAllObjects())
			for (String attribute : object.getAttributes().keySet())
				allAttributes.add(attribute);
		
		return allAttributes;
	}

	@Override
	public Set<String> GetAllAttributeValues() {
		// TODO Auto-generated method stub
		return null;
	}
}
