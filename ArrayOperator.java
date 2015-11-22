package ravensproject;

/**
 * Created by eriojasn on 11/19/15.
 */
public class ArrayOperator {
    public static boolean[] ORBooleanArray (boolean[] a, boolean[] b)
    {
        boolean[] result = new boolean[a.length];

        int index = 0;
        for (boolean aBool : a)
        {
            if (aBool == b[index])
                result[index] = false;
            else
                result[index] = true;

            index++;
        }

        return result;
    }

    public static int Difference (boolean[] a, boolean[] b)
    {
        int difference = 0;

        int index = 0;
        for (boolean aBool : a) {
            if (aBool != b[index])
                difference++;

            index++;
        }

        return difference;
    }

    public static int BlackDifference (boolean[] a, boolean[] b)
    {
        int difference = 0;

        int index = 0;
        for (boolean aBool : a) {
            if (aBool != b[index] && aBool)
                difference++;

            index++;
        }

        return difference;
    }

    public static LiquidImage ApplyOperation (LiquidImage operated, boolean[] operation)
    {
        LiquidImage newImage = new LiquidImage();
        int index = 0;
        boolean[] operatedArray = operated.liquidImg;
        for (boolean op : operation)
        {
            if (op)
                operatedArray[index] = !(operatedArray[index]);

            index++;
        }

        newImage.liquidImg = operatedArray;

        return newImage;
    }
}
