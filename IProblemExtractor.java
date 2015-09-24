package ravensproject;

import java.util.ArrayList;

public interface IProblemExtractor extends IFigureExtractor {
	public ArrayList<RavensFigure> GetProblemFigures();
	public ArrayList<RavensFigure> GetAnswerFigures();
	public ArrayList<RavensFigure> GetAllFigures();
}
