package ravensproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by eriojasn on 11/19/15.
 */
public class MatrixManipulator {
    public ArrayList<ArrayList<LiquidImage>> rows;
    public ArrayList<ArrayList<LiquidImage>> columns;

    private ArrayList<SetRelationship> allPossibleRows;
    private ArrayList<SetRelationship> allPossibleColumns;
    public ArrayList<LiquidImage> matrix;
    private int N;
    public MatrixManipulator(ArrayList<LiquidImage> matrix)
    {
        this.allPossibleRows = new ArrayList<>();
        this.allPossibleColumns = new ArrayList<>();
        this.N = (int)Math.sqrt((double)matrix.size() + 1);
        this.columns = this.GetColumns(matrix);
        this.matrix = ArrangeMatrix(matrix);
        this.columns = this.GetColumns(this.matrix);
    }

    private ArrayList<LiquidImage> ArrangeMatrix(ArrayList<LiquidImage> matrix)
    {
        ArrayList<LiquidImage> arrangedMatrix = new ArrayList<>();

        // Get all possible set combinations
        FindPermutations(columns, new LiquidImage[columns.size()], 0, true);
        Collections.sort(allPossibleRows, new SetRelationshipComparator());

        for (SetRelationship row : allPossibleRows)
        {
            ArrayList<LiquidImage> tempRow = row.set;
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
        for (SetRelationship row : allPossibleRows)
            for (LiquidImage element : row.set)
                if (!arrangedMatrix.contains(element) && !missingElements.contains(element)) missingElements.add(element);

        for (LiquidImage missingElement : missingElements)
            arrangedMatrix.add(missingElement);

        this.rows = this.GetRows(arrangedMatrix);
        FindPermutations(rows, new LiquidImage[rows.size()], 0, false);
        Collections.sort(allPossibleColumns, new SetRelationshipComparator());

        LiquidImage[] tempArrangedMatrix = new LiquidImage[(N * N) - 1];
        int counter = 0;
        int index = 0;
        for (SetRelationship column : allPossibleColumns)
        {
            ArrayList<LiquidImage> tempColumn = column.set;
            boolean found = false;
            for (LiquidImage element : tempColumn)
                for (LiquidImage matrixElement : tempArrangedMatrix)
                    if (matrixElement != null && element.name.equals(matrixElement.name)) {
                        found = true;
                        break;
                    }

            if (!found) {
                for (LiquidImage element : tempColumn) {
                    tempArrangedMatrix[index] = element;
                    index += N;
                }
                counter++;
                index = counter;
            }
        }

        missingElements = new ArrayList<>();
        for (SetRelationship column : allPossibleColumns)
            for (LiquidImage element : column.set)
                if (!Arrays.asList(tempArrangedMatrix).contains(element) && !missingElements.contains(element)) missingElements.add(element);

        boolean added = false;
        for (LiquidImage missingElement : missingElements) {
            added = false;
            for (int i = 0; i < tempArrangedMatrix.length; i++) {
                if (added) continue;
                if (tempArrangedMatrix[i] == null) {
                    tempArrangedMatrix[i] = missingElement;
                    added = true;
                }
            }
        }

        arrangedMatrix = new ArrayList<>(Arrays.asList(tempArrangedMatrix));

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

    private void FindPermutations(ArrayList<ArrayList<LiquidImage>> set, LiquidImage[] combo, int index, boolean rows) {
       for (LiquidImage element : set.get(index)) {
           combo[index] = element;
           if (index != set.size() - 1) FindPermutations(set, combo, index + 1, rows);
           else {
               LiquidImage[] comboToBeAdded = new LiquidImage[set.size()];
               System.arraycopy(combo, 0, comboToBeAdded, 0, combo.length);
               SetRelationship temp = new SetRelationship(new ArrayList<LiquidImage>(Arrays.asList(combo)));
               if (rows)
                   allPossibleRows.add(temp);
               else
                   allPossibleColumns.add(temp);
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
