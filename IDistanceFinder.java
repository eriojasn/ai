package ravensproject;

public interface IDistanceFinder {
	public int FindObjectDistance(RavensObject o1, RavensObject o2);
	public int FindFigureDistance(RavensFigure f1, RavensFigure f2);
}