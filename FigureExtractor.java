package ravensproject;

import java.util.ArrayList;
import java.util.Set;

public class FigureExtractor implements IFigureExtractor {
	private RavensFigure figure;
	
	public FigureExtractor(RavensFigure figure)
	{
		this.figure = figure;
	}

	@Override
	public Set<String> GetAllAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<RavensObject> GetAllObjects() {
		ArrayList<RavensObject> allObjects = new ArrayList<RavensObject>(figure.getObjects().values());

		return allObjects;
	}

}
