package ravensproject;

import java.util.ArrayList;
import java.util.HashMap;

public class TwoMapMerger implements IMapMerger {
	public ArrayList<ArrayList<RavensObject>> mergedMap;

	private Mapper leftMap;
	private Mapper rightMap;

	public TwoMapMerger(Mapper leftMap, Mapper rightMap)
	{
		this.leftMap = leftMap;
		this.rightMap = rightMap;
	}

	@Override
	public ArrayList<ArrayList<RavensObject>> Merge() {
		mergedMap = new ArrayList<ArrayList<RavensObject>>();
		ArrayList<Pair<RavensObject, RavensObject>> rowsToAdd = new ArrayList<Pair<RavensObject, RavensObject>>();

		//Add first map.
		for (Pair<RavensObject, RavensObject> pair : leftMap.map)
			this.AddPair(pair);
		
		//Merge second map.
		for (Pair<RavensObject, RavensObject> pair : rightMap.map)
			for(ArrayList<RavensObject> row : mergedMap)
				switch (SearchForPairInRow(pair, row)) {
					case 'L':
						row.add(pair.getRight());
						break;
					case 'R':
						row.add(pair.getLeft());
						break;
					case 'N':
						rowsToAdd.add(pair);
						break;
				}

		for (Pair<RavensObject, RavensObject> pair : rowsToAdd)
		{
			if (!this.SearchForPairInMap(pair, mergedMap))
				this.AddPair(pair);
		}

		return mergedMap;
	}
	
	public void PrintMergedMap()
	{
    	for (ArrayList<RavensObject> row : this.mergedMap)
    	{
//    		for (RavensObject object : set)
//    			if (object != null)
//    				System.out.print(object.getName() + " ");
//    			else
//    				System.out.print("null ");

//    		System.out.println();
    	}
	}
	
	private void AddPair(Pair<RavensObject, RavensObject> pair)
	{
		ArrayList<RavensObject> newRow = new ArrayList<RavensObject>();
		
		newRow.add(pair.getLeft());
		newRow.add(pair.getRight());
		
		mergedMap.add(newRow);
	}
	
	private boolean SearchForPairInMap(Pair<RavensObject, RavensObject> pair, ArrayList<ArrayList<RavensObject>> map)
	{
		for (ArrayList<RavensObject> row : map)
		{
			char found = SearchForPairInRow(pair, row);
			
			if (found == 'L' || found == 'R')
				return true;
		}
		
		return false;
	}
	
	private char SearchForPairInRow(Pair<RavensObject, RavensObject> pair, ArrayList<RavensObject> row)
	{
		for (RavensObject object : row)
			if (object != null)
				if (pair.getLeft() != null && pair.getLeft().getName() == object.getName())
					return 'L';
				else if (pair.getRight() != null && pair.getRight().getName() == object.getName())
					return 'R';

		return 'N';
	}
}
