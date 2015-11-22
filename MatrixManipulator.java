package ravensproject;

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

    private ArrayList<SetRelationship> allPossibleRows;
    private ArrayList<SetRelationship> allPossibleColumns;
    public LiquidImage[][] matrix;
    private int N;
    public MatrixManipulator(ArrayList<LiquidImage> matrix)
    {
        this.allPossibleRows = new ArrayList<>();
        this.allPossibleColumns = new ArrayList<>();
        this.N = (int)Math.sqrt((double)matrix.size() + 1);
        this.columns = this.GetColumns(matrix);
        this.matrix = ArrangeMatrix(matrix);
        this.columns = this.GetColumns(matrix);
    }

    private LiquidImage[][] ArrangeMatrix(ArrayList<LiquidImage> matrix)
    {
        ArrayList<LiquidImage> arrangedMatrix = new ArrayList<>();
        LiquidImage[][] arrangedMatrixArr = new LiquidImage[N][N];

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

        int arrayListCounter = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                if (arrayListCounter >= arrangedMatrix.size())
                {
                    arrangedMatrixArr[i][j] = null;
                    continue;
                }
                arrangedMatrixArr[i][j] = arrangedMatrix.get(arrayListCounter);
                arrayListCounter++;
            }

        // Using ArrayList instead of HashSet to preserve order
        ArrayList<LiquidImage> missingElements = new ArrayList<>();
        for (SetRelationship row : allPossibleRows)
            for (LiquidImage element : row.set)
                if (!arrangedMatrix.contains(element) && !missingElements.contains(element)) missingElements.add(element);

        ArrayList<ArrayList<LiquidImage>> arrangedMatrixRows = this.GetRows(arrangedMatrix);
        ArrayList<LiquidImage> lastRow = arrangedMatrixRows.get(arrangedMatrixRows.size() - 1);
        ArrayList<Relationship> missingRelationships = this.GetLastRow2MissingElementsRelationships(lastRow, missingElements);

        for (LiquidImage firstMissingElement : missingElements) {
            int minDifference = 9999999;
            int minIndex = 0;
            for (int i = 0; i < N; i++) {
                if (arrangedMatrixArr[N - 1][i] == null)
                {
                    int tempDifference = ArrayOperator.Difference(firstMissingElement.liquidImg, arrangedMatrixArr[N - 2][i].liquidImg);
                    if (tempDifference < minDifference)
                    {
                        minDifference = tempDifference;
                        minIndex = i;
                    }
                }
            }

            arrangedMatrixArr[N - 1][minIndex] = firstMissingElement;
        }

        arrangedMatrix = new ArrayList<>();
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            {
                if (arrangedMatrixArr[i][j] != null)
                   arrangedMatrix.add(arrangedMatrixArr[i][j]);
                else
                    arrangedMatrix.add(null);
            }

        this.rows = this.GetRows(arrangedMatrixArr); // DEBUG HERE
        FindPermutations(rows, new LiquidImage[rows.size()], 0, false);
        Collections.sort(allPossibleColumns, new SetRelationshipComparator());

        LiquidImage[][] secondArrangedMatrixArr = new LiquidImage[N][N];
        LiquidImage[] tempArrangedMatrix = new LiquidImage[(N * N) - 1];
        int index = 0;
        for (SetRelationship column : allPossibleColumns)
        {
            ArrayList<LiquidImage> tempColumn = column.set;
            boolean found = false;
            for (LiquidImage element : tempColumn)
                for (int i = 0; i < N; i ++)
                    for (int j = 0; j < N; j++)
                        if (secondArrangedMatrixArr[i][j] != null && element.name.equals(secondArrangedMatrixArr[i][j].name)) {
                            found = true;
                            break;
                        }

            if (!found) {
                int miniIndex = 0;
                for (LiquidImage element : tempColumn) {
                    secondArrangedMatrixArr[miniIndex][index] = element;
                    miniIndex++;
                }
                index++;
                if (index == N - 1) break;
            }
        }

        HashSet<LiquidImage> secondMissingElements = new HashSet<>();
        for (SetRelationship column : allPossibleColumns)
            for (LiquidImage element : column.set) {
                boolean found = false;
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        if (secondArrangedMatrixArr[i][j] == element) {
                            found = true;
                            break;
                        }
                    }
                    if (found) break;
                }
                if (!found)
                    secondMissingElements.add(element);
            }

        for (LiquidImage secondMissingElement : secondMissingElements) {
            int minDifference = 9999999;
            int minIndex = 0;
            for (int i = 0; i < N; i++) {
                if (secondArrangedMatrixArr[i][N - 1] == null)
                {
                    int tempDifference = ArrayOperator.Difference(secondMissingElement.liquidImg, secondArrangedMatrixArr[i][N - 2].liquidImg);
                    if (tempDifference < minDifference)
                    {
                        minDifference = tempDifference;
                        minIndex = i;
                    }
                }
            }

            secondArrangedMatrixArr[minIndex][N - 1] = secondMissingElement;
        }

        return secondArrangedMatrixArr;
    }

    private ArrayList<Relationship> GetLastRow2MissingElementsRelationships(ArrayList<LiquidImage> set, ArrayList<LiquidImage> missingElements)
    {
        ArrayList<Relationship> result = new ArrayList<>();

        for (LiquidImage setElement : set)
            for (LiquidImage missingElement : missingElements)
                result.add(new Relationship(setElement, missingElement));

        Collections.sort(result, new RelationshipComparator());

        return result;
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
            if (index == matrix.size()) break;
            rows.add(new ArrayList<LiquidImage>());
            for (int j = 0; j < N; j++)
            {
                rows.get(i).add(matrix.get(index));
                index++;
            }
        }

        return rows;
    }

    private ArrayList<ArrayList<LiquidImage>> GetRows(LiquidImage[][] matrix)
    {
        ArrayList<ArrayList<LiquidImage>> rows = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            rows.add(new ArrayList<LiquidImage>());
            for (int j = 0; j < N; j++) {
                if (matrix[i][j] != null)
                    rows.get(i).add(matrix[i][j]);
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
