package ravensproject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Extractor implements IExtractor {
	private RavensProblem problem;

	public Extractor(RavensProblem problem)
	{
		this.problem = problem;
	}

	@Override
	public Object Extract(Level level) 
	{
		switch (level) {
			case FIGURES:
				return this.GetAllFigures();

			case OBJECTS:
				return this.GetAllObjects();

			case ATTRIBUTES:
				return this.GetAllAttributes();
		}


		return null;
	}
	
	private ArrayList<RavensFigure> GetAllFigures()
	{
		ArrayList<RavensFigure> allFigures = new ArrayList<RavensFigure>(problem.getFigures().values());
		
		return allFigures;
	}
	
	private ArrayList<RavensObject> GetAllObjects()
	{
		ArrayList<RavensObject> allObjects = new ArrayList<RavensObject>();
		
		for (RavensFigure figure : this.GetAllFigures())
			for (RavensObject object : figure.getObjects().values())
				allObjects.add(object);
		
		return allObjects;
	}
	
	private Set<String> GetAllAttributes()
	{
		Set<String> allAttributes = new HashSet<String>();
		
		for (RavensObject object : this.GetAllObjects())
			for (String attribute : object.getAttributes().keySet())
				allAttributes.add(attribute);
		
		return allAttributes;
	}
}
