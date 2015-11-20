package ravensproject;

/**
 * Created by eriojasn on 11/19/15.
 */
public class Relationship {
    public LiquidImage leftImage, rightImage, negativeImage;
    public boolean[] operation;
    public String name;

    public Relationship(LiquidImage leftImage, LiquidImage rightImage)
    {
        this.leftImage = leftImage;
        this.rightImage = rightImage;
        operation = ArrayOperator.ORBooleanArray(leftImage.liquidImg, rightImage.liquidImg);
        this.negativeImage = new LiquidImage();
        this.name = leftImage.name + "-" + rightImage.name;
        negativeImage.name = this.name;
        negativeImage.liquidImg = operation;
    }
}
