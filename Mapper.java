package ravensproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class Mapper implements IMapper {
	public RavensFigure leftFigure;
	public RavensFigure rightFigure;
	public ArrayList<Pair<RavensObject, RavensObject>> map;
	
	private Set<String> attributes;
	
	public Mapper(RavensFigure leftFigure, RavensFigure rightFigure, Set<String> attributes)
	{
		this.leftFigure = leftFigure;
		this.rightFigure = rightFigure;
		this.attributes = attributes;
		
		this.Map();
	}

	@Override
	public ArrayList<Pair<RavensObject, RavensObject>> Map() {
		FigureExtractor leftFigureExtractor = new FigureExtractor(this.leftFigure);
		FigureExtractor rightFigureExtractor = new FigureExtractor(this.rightFigure);
		this.map = new ArrayList<Pair<RavensObject, RavensObject>>();
		
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
				if (pair.getLeft().equals(difference.leftObject) || pair.getRight().equals(difference.rightObject))
					found = true;
			
			if(!found)
				map.add(new Pair<RavensObject, RavensObject>(difference.leftObject, difference.rightObject));
		}
		
		for (ObjectDifference difference : differences)
		{
			found = false;
			for (Pair<RavensObject, RavensObject> pair : map)
				if (pair.getLeft() != null && pair.getLeft().equals(difference.leftObject))
					found = true;
			
			if (!found)
				map.add(new Pair<RavensObject, RavensObject>(difference.leftObject, null));
			
			found = false;
			for (Pair<RavensObject, RavensObject> pair : map)
				if (pair.getRight() != null && pair.getRight().equals(difference.rightObject))
					found = true;
			
			if (!found)
				map.add(new Pair<RavensObject, RavensObject>(null, difference.rightObject));
		}

		//this.PrintMap();
		return map;
	}

	@Override
	public void PrintMap() {
    	System.out.println("Printing out map...");

    	for (Pair<RavensObject, RavensObject> pair : map)
    	{
    		RavensObject left = pair.getLeft();
    		RavensObject right = pair.getRight();
    		
    		if (left != null)
    			System.out.print(left.getName());
    		else
    			System.out.print("null");
    		
    		System.out.print(" <-> ");
    		
    		if (right != null)
    			System.out.print(right.getName());
    		else
    			System.out.print("null");
    		
    		System.out.println();

    		ObjectDifference temp;
    		if (left != null && right != null)
    			temp = new ObjectDifference(pair.getLeft(), pair.getRight(), this.attributes);
    	}
	}
}