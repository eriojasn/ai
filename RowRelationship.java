package ravensproject;

import java.util.ArrayList;

/**
 * Created by eriojasn on 11/20/15.
 */
public class RowRelationship {
    public int combinedDifference;
    ArrayList<LiquidImage> row;
    public String name;

    public RowRelationship (ArrayList<LiquidImage> row)
    {
        this.row = row;
        this.name = this.row.get(0).name;
        for (int i = 0; i < row.size() - 1; i++)
        {
            int runner = i + 1;
            combinedDifference += ArrayOperator.Difference(this.row.get(i).liquidImg, this.row.get(runner).liquidImg);
            this.name += this.row.get(runner).name;
        }
    }
}
