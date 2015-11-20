package ravensproject;
import java.io.*;
import java.util.*;

/**
 * Created by eriojasn on 11/20/15.
 */
public class VisualMemoryIO {
    private static final String PROBLEM_DELIM = "p";
    private static final String FIGURE_DELIM = "f";
    private static final String SOLUTION_DELIM = "f";
    private static final String FILE_NAME = "visualmemory.txt";

    public VisualMemoryIO() { }

    public void Record(RavensProblem problem)
    {
        BufferedWriter bufferedWriter = null;

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(FILE_NAME, true));

            bufferedWriter.write(PROBLEM_DELIM);

            ProblemExtractor extractor = new ProblemExtractor(problem);
            ArrayList<RavensFigure> problemFigures = extractor.GetProblemFigures();
            for (RavensFigure problemFigure : problemFigures)
            {
                bufferedWriter.write(FIGURE_DELIM);

                this.WriteRavensFigure(bufferedWriter, problemFigure);

                bufferedWriter.write(FIGURE_DELIM);
            }

            int answer = problem.checkAnswer(-1);
            problem.getCorrect();
            RavensFigure answerFigure = problem.getFigures().get(Integer.toString(answer));

            bufferedWriter.write(SOLUTION_DELIM);

            this.WriteRavensFigure(bufferedWriter, answerFigure);

            bufferedWriter.write(SOLUTION_DELIM);
        }
        catch (Exception e) { e.printStackTrace(); }
        finally {
            try {
                bufferedWriter.write(PROBLEM_DELIM);

                bufferedWriter.close(); }
            catch (Exception e) { }
        }
    }

    private void WriteRavensFigure(BufferedWriter bufferedWriter, RavensFigure figure)
    {
        LiquidImage temp = new LiquidImage(figure);

        for (boolean pixel : temp.liquidImg)
        {
            try {
                if (pixel)
                    bufferedWriter.write("1");
                else
                    bufferedWriter.write("0");
            }
            catch (Exception e) { e.printStackTrace(); }
        }
    }

    private List<String> RemoveNulls(String[] array)
    {
        List<String> list = new ArrayList<String>(Arrays.asList(array));
        list.removeAll(Arrays.asList("", null));

        return list;
    }

    public ArrayList<ArrayList<LiquidImage>> ReadMemory()
    {
        BufferedReader bufferedReader = null;
        ArrayList<ArrayList<LiquidImage>> allProblems = new ArrayList<>();

        String filePath = FILE_NAME;
        File file = new File(filePath);
        if (!file.exists())
           filePath = "ravensproject/" + filePath;
        file = new File(filePath);
        if (!file.exists())
            filePath = "../" + FILE_NAME;
        file = new File(filePath);
        if(!file.exists())
            filePath = "../ravensproject/" + FILE_NAME;

        try
        {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            String memory = bufferedReader.readLine();
            List<String> problems = this.RemoveNulls(memory.split(PROBLEM_DELIM));

            for (String problem : problems)
            {
                ArrayList<LiquidImage> allFigures = new ArrayList<>();
                List<String> figures = this.RemoveNulls(problem.split(FIGURE_DELIM));

                for (String figure : figures)
                {
                    boolean[] pixels = new boolean[figure.length()];
                    LiquidImage temp = new LiquidImage();
                    char[] figureChars = figure.toCharArray();
                    int index = 0;
                    for (char figureChar : figureChars) {
                        if (figureChar == '1')
                            pixels[index] = true;
                        else
                            pixels[index] = false;
                        index++;
                    }
                    temp.liquidImg = pixels;
                    allFigures.add(temp);
//                    LiquidImage.DrawLiquidImage(temp, Double.toString(Math.random()));
                }

                allProblems.add(allFigures);
            }
        }
        catch (Exception e) { }

        return allProblems;
    }
}
