package ravensproject;

/**
 * Created by eriojasn on 11/19/15.
 */
public class Relationship {
    public LiquidImage leftImage, rightImage, negativeImage;
    public boolean[] operation;
    public String name;
    public int difference, blackDifference;
    public double differencePercentage, blackDifferencePercentage;
    public int problemNumber;

    public Relationship(LiquidImage leftImage, LiquidImage rightImage)
    {
        this.leftImage = leftImage;
        this.rightImage = rightImage;
        operation = ArrayOperator.ORBooleanArray(leftImage.liquidImg, rightImage.liquidImg);
        this.negativeImage = new LiquidImage();
        this.name = leftImage.name + "-" + rightImage.name;
        negativeImage.name = this.name;
        negativeImage.liquidImg = operation;
        this.difference = ArrayOperator.Difference(leftImage.liquidImg, rightImage.liquidImg);
        this.differencePercentage = (double)difference / (double)leftImage.liquidImg.length * 100;
        LiquidImage.GetBlackPixels(leftImage);
        LiquidImage.GetBlackPixels(rightImage);
        this.blackDifference = ArrayOperator.BlackDifference(leftImage.liquidImg, rightImage.liquidImg);
        this.blackDifferencePercentage = (double)blackDifference / (double) leftImage.blackPixels * 100;
    }
}
