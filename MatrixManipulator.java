package ravensproject;

import com.sun.rowset.internal.Row;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by eriojasn on 11/19/15.
 */
public class MatrixManipulator {
    public ArrayList<ArrayList<LiquidImage>> rows;
    public ArrayList<ArrayList<LiquidImage>> columns;

    private ArrayList<RowRelationship> allPossibleRows;
    public ArrayList<LiquidImage> matrix;
    private int N;
    public MatrixManipulator(ArrayList<LiquidImage> matrix)
    {
        this.allPossibleRows = new ArrayList<>();
        this.N = (int)Math.sqrt((double)matrix.size() + 1);
        this.columns = this.GetColumns(matrix);
        this.matrix = ArrangeMatrix(matrix);
        this.rows = this.GetRows(this.matrix);
        this.columns = this.GetColumns(this.matrix);
    }

    private ArrayList<LiquidImage> ArrangeMatrix(ArrayList<LiquidImage> matrix)
    {
        ArrayList<LiquidImage> arrangedMatrix = new ArrayList<>();

        FindPermutations(columns, new LiquidImage[columns.size()], 0);
        Collections.sort(allPossibleRows, new RowRelationshipComparator());

        for (RowRelationship row : allPossibleRows)
        {
            ArrayList<LiquidImage> tempRow = row.row;
            boolean found = false;
            for (LiquidImage element : tempRow)
                for (LiquidImage matrixElement: arrangedMatrix)
                    if (element.name.equals(matrixElement.name)) { found = true; break; }

            if (!found)
                for (LiquidImage element : tempRow)
                    arrangedMatrix.add(element);
        }

        // Using ArrayList instead of HashSet to preserve order
        ArrayList<LiquidImage> missingElements = new ArrayList<>();
        for (RowRelationship row : allPossibleRows)
            for (LiquidImage element : row.row)
                if (!arrangedMatrix.contains(element) && !missingElements.contains(element)) missingElements.add(element);

        for (LiquidImage missingElement : missingElements)
            arrangedMatrix.add(missingElement);

        return arrangedMatrix;
    }

    public ArrayList<Relationship> GetRelationships()
    {
        ArrayList<Relationship> relationships = new ArrayList<>();

        // First rows
        for (ArrayList<LiquidImage> row : rows) {
            int index = 0;
            for (LiquidImage element : row) {
                if (index == row.size() - 1) break;
                relationships.add(new Relationship(element, row.get(index + 1)));
                index++;
            }
        }

        // First columns
        for (ArrayList<LiquidImage> column : columns) {
            int index = 0;
            for (LiquidImage element : column) {
                if (index == column.size() - 1) break;
                relationships.add(new Relationship(element, column.get(index + 1)));
                index++;
            }
        }

        return relationships;
    }

    private void FindPermutations(ArrayList<ArrayList<LiquidImage>> columns, LiquidImage[] row, int column) {
       for (LiquidImage element : columns.get(column)) {
           row[column] = element;
           if (column != columns.size() - 1) FindPermutations(columns, row, column + 1);
           else {
               LiquidImage[] rowToBeAdded = new LiquidImage[columns.size()];
               System.arraycopy(row, 0, rowToBeAdded, 0, row.length);
               RowRelationship temp = new RowRelationship(new ArrayList<LiquidImage>(Arrays.asList(row)));
               allPossibleRows.add(temp);
           }
       }
    }

    private ArrayList<ArrayList<LiquidImage>> GetRows(ArrayList<LiquidImage> matrix)
    {
        ArrayList<ArrayList<LiquidImage>> rows = new ArrayList<>();

        int index = 0;
        for (int i = 0; i < N; i++)
        {
            rows.add(new ArrayList<LiquidImage>());
            for (int j = 0; j < N; j++)
            {
                if (index == (N * N - 1)) break;
                rows.get(i).add(matrix.get(index));
                index++;
            }
        }

        return rows;
    }

    private ArrayList<ArrayList<LiquidImage>> GetColumns(ArrayList<LiquidImage> matrix)
    {
        ArrayList<ArrayList<LiquidImage>> columns = new ArrayList<>();

        int index;
        for (int i = 0; i < N; i++)
        {
            index = i;
            columns.add(new ArrayList<LiquidImage>());
            for (int j = 0; j < N; j++)
            {
                if (index == (N * N - 1)) break;
                columns.get(i).add(matrix.get(index));
                index += N;
            }
        }

        return columns;
    }
}
