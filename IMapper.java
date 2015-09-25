package ravensproject;

import java.util.ArrayList;

/*Compares two RavensFigures and returns a list of pairs with the equivalent
*objects.
*/

public interface IMapper {
	public ArrayList<Pair<RavensObject, RavensObject>> Map();
	public void PrintMap();
}
