package ravensproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class Mapper implements IMapper {
	public RavensFigure leftFigure;
	public RavensFigure rightFigure;
	public ArrayList<Pair<RavensObject, RavensObject>> map;
	public int figureDifference;
	
	private Set<String> attributes;
	private FigureExtractor leftFigureExtractor;
	private FigureExtractor rightFigureExtractor;
	private ArrayList<RavensObject> leftObjects;
	private ArrayList<RavensObject> rightObjects;
	private ArrayList<ObjectDifference> differences;
	
	public Mapper(RavensFigure leftFigure, RavensFigure rightFigure, Set<String> attributes)
	{
		this.leftFigure = leftFigure;
		this.rightFigure = rightFigure;
		this.attributes = attributes;
		
		this.rightFigureExtractor = new FigureExtractor(this.rightFigure);
		this.map = new ArrayList<Pair<RavensObject, RavensObject>>();
		
		if (leftFigure.getName() != null)
		{
			this.leftFigureExtractor = new FigureExtractor(this.leftFigure);
			this.leftObjects = leftFigureExtractor.GetAllObjects();
		}

		this.rightObjects = rightFigureExtractor.GetAllObjects();
		this.differences = new ArrayList<ObjectDifference>();
	}
	
	public Mapper(ArrayList<RavensObject> leftObjects, RavensFigure rightFigure, Set<String> attributes)
	{
		this(new RavensFigure(null, null, null), rightFigure, attributes);
		this.leftObjects = leftObjects;
	}

	@Override
	public ArrayList<Pair<RavensObject, RavensObject>> Map() {
		this.map = new ArrayList<Pair<RavensObject, RavensObject>>();
		
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

		this.figureDifference = this.GetFigureDifference();
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
    		System.out.println(this.figureDifference);
    	}
	}
	
	private int GetFigureDifference()
	{
    	for (Pair<RavensObject, RavensObject> pair : map)
    	{
    		ObjectDifference temp;
    		temp = new ObjectDifference(pair.getLeft(), pair.getRight(), this.attributes);
    		
    		figureDifference += temp.difference;
    	}
    	
    	return figureDifference;
	}
}