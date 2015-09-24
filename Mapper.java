package ravensproject;

import java.util.ArrayList;
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
		//this.Map();
	}

	@Override
	public ArrayList<Pair<RavensObject, RavensObject>> Map() {
		map = new ArrayList<Pair<RavensObject,RavensObject>>();
		
		ArrayList<RavensObject> leftObjects = leftFigureExtractor.GetAllObjects();
		ArrayList<RavensObject> rightObjects = rightFigureExtractor.GetAllObjects();
		ArrayList<ObjectDifference> differences = new ArrayList<ObjectDifference>();
		
		for (RavensObject leftObject : leftObjects)
			for (RavensObject rightObject : rightObjects)
				differences.add(new ObjectDifference(leftObject, rightObject, attributes));

		return map;
	}
}