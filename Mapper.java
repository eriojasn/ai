package ravensproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class Mapper implements IMapper {
	public RavensFigure leftFigure;
	public RavensFigure rightFigure;
	public ArrayList<Pair<RavensObject, RavensObject>> map;
	
	private Set<String> attributes;
	private FigureExtractor leftFigureExtractor;
	private FigureExtractor rightFigureExtractor;
	
	public Mapper(RavensFigure leftFigure, RavensFigure rightFigure, Set<String> attributes)
	{
		this.leftFigure = leftFigure;
		this.rightFigure = rightFigure;
		this.leftFigureExtractor = new FigureExtractor(this.leftFigure);
		this.rightFigureExtractor = new FigureExtractor(this.rightFigure);
		this.attributes = attributes;
	}

	@Override
	public ArrayList<Pair<RavensObject, RavensObject>> Map() {
		map = new ArrayList<Pair<RavensObject, RavensObject>>();
		
		ArrayList<RavensObject> leftObjects = leftFigureExtractor.GetAllObjects();
		ArrayList<RavensObject> rightObjects = rightFigureExtractor.GetAllObjects();
		ArrayList<ObjectDifference> differences = new ArrayList<ObjectDifference>();
		
		for (RavensObject leftObject : leftObjects)
			for (RavensObject rightObject : rightObjects)
				differences.add(new ObjectDifference(leftObject, rightObject, attributes));
		
		Collections.sort(differences, new ObjectDifferenceComparator());
		
		boolean found;
		for (ObjectDifference difference : differences)
		{
			found = false;
			for (Pair<RavensObject, RavensObject> pair : map)
				if (pair.getLeft().equals(difference.compared1) || pair.getRight().equals(difference.compared2))
					found = true;
			
			if(!found)
				map.add(new Pair<RavensObject, RavensObject>(difference.compared1, difference.compared2));
		}
		
		for (ObjectDifference difference : differences)
		{
			found = false;
			for (Pair<RavensObject, RavensObject> pair : map)
				if (pair.getLeft() != null && pair.getLeft().equals(difference.compared1))
					found = true;
			
			if (!found)
				map.add(new Pair<RavensObject, RavensObject>(difference.compared1, null));
			
			found = false;
			for (Pair<RavensObject, RavensObject> pair : map)
				if (pair.getRight() != null && pair.getRight().equals(difference.compared2))
					found = true;
			
			if (!found)
				map.add(new Pair<RavensObject, RavensObject>(null, difference.compared2));
		}

		return map;
	}
}