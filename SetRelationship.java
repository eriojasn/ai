package ravensproject;

import java.util.ArrayList;

/**
 * Created by eriojasn on 11/20/15.
 */
public class SetRelationship {
    public int combinedDifference;
    ArrayList<LiquidImage> set;
    public String name;

    public SetRelationship(ArrayList<LiquidImage> set)
    {
        this.set = set;
        this.name = this.set.get(0).name;
        for (int i = 0; i < set.size() - 1; i++)
        {
            int runner = i + 1;
            combinedDifference += ArrayOperator.Difference(this.set.get(i).liquidImg, this.set.get(runner).liquidImg);
            this.name += this.set.get(runner).name;
        }
    }
}
