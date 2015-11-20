package ravensproject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by eriojasn on 11/19/15.
 */
public class LiquidImage {
    public boolean[] liquidImg;
    public int blackPixels;
    public String name;

    private BufferedImage img;
    static private final int blueThreshold = 200;

    public LiquidImage() { }

    public LiquidImage(RavensFigure figure)
    {
        try {
            img = ImageIO.read(new File(figure.getVisual()));
        } catch(Exception ex) {}

        liquidImg = new boolean[img.getWidth() * img.getHeight()];
        LiquefyImage();
    }

    public static void DrawLiquidImage(LiquidImage liquidImage, String name)
    {
        int N = (int)Math.sqrt((double)liquidImage.liquidImg.length);
        BufferedImage img = new BufferedImage(N, N, BufferedImage.TYPE_INT_RGB );
        int index = 0;
        for ( int x = 0; x < N; x++ ) {
            for ( int y = 0; y < N; y++ ) {
                if (liquidImage.liquidImg[index])
                    img.setRGB(x, y, Color.BLACK.getRGB() );
                else
                    img.setRGB(x, y, Color.WHITE.getRGB());

                index++;
            }
        }

        try {
            File outputfile = new File(name + ".png");
            ImageIO.write(img, "png", outputfile);
        }
        catch (Exception e) { }
    }

    public static ArrayList<LiquidImage> ConvertFigures (ArrayList<RavensFigure> figures)
    {
        ArrayList<LiquidImage> liquidFigures = new ArrayList<>();

        for (RavensFigure figure : figures) {
            LiquidImage temp = new LiquidImage(figure);
            temp.name = figure.getName();
            liquidFigures.add(temp);
        }

        return liquidFigures;
    }

    private void LiquefyImage()
    {
        if (img == null) return;

        int index = 0;
        for(int i = 0 ; i < img.getWidth() ; i++)
            for(int j = 0 ; j < img.getHeight() ; j++) {
                if ((img.getRGB(i, j) & 0xff) < blueThreshold) {
                    liquidImg[index] = true;
                    blackPixels++;
                }
                index++;
            }
    }
}