package ravensproject;

import java.io.*;
import java.util.*;

/**
 * Created by eriojasn on 10/25/15.
 */
public class MemoryIO {
    private static final String PROBLEM_DELIM = "<p>";
    private static final String FIGURE_DELIM = "<f>";
    private static final String OBJECT_DELIM = "<o>";
    private static final String ATTRIBUTE_DELIM = "<a>";
    private static final String SOLUTION_DELIM = "<f>";
    private static final String FILE_NAME = "memory.txt";

    public MemoryIO() { }

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

                for (RavensObject object : problemFigure.getObjects().values())
                {
                    bufferedWriter.write(OBJECT_DELIM);

                    for (String attribute : object.getAttributes().keySet())
                    {
                        bufferedWriter.write(ATTRIBUTE_DELIM);

                        bufferedWriter.write(attribute + ":" + object.getAttributes().get(attribute));

                        bufferedWriter.write(ATTRIBUTE_DELIM);
                    }

                    bufferedWriter.write(OBJECT_DELIM);
                }

                bufferedWriter.write(FIGURE_DELIM);
            }

            int answer = problem.checkAnswer(-1);
            problem.getCorrect();
            RavensFigure answerFigure = problem.getFigures().get(Integer.toString(answer));

            bufferedWriter.write(SOLUTION_DELIM);
            for (RavensObject object : answerFigure.getObjects().values())
            {
                bufferedWriter.write(OBJECT_DELIM);

                for (String attribute : object.getAttributes().keySet())
                {
                    bufferedWriter.write(ATTRIBUTE_DELIM);

                    bufferedWriter.write(attribute + ":" + object.getAttributes().get(attribute));

                    bufferedWriter.write(ATTRIBUTE_DELIM);
                }

                bufferedWriter.write(OBJECT_DELIM);
            }
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

    private List<String> RemoveNulls(String[] array)
    {
        List<String> list = new ArrayList<String>(Arrays.asList(array));
        list.removeAll(Arrays.asList("", null));

        return list;
    }

    public ArrayList<ArrayList<ArrayList<RavensObject>>> ReadMemory()
    {
        BufferedReader bufferedReader = null;
        ArrayList<ArrayList<ArrayList<RavensObject>>> allProblems = new ArrayList<>();

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
                ArrayList<ArrayList<RavensObject>> allFigures = new ArrayList<>();
                List<String> figures = this.RemoveNulls(problem.split(FIGURE_DELIM));

                for (String figure : figures)
                {
                    ArrayList<RavensObject> allObjects = new ArrayList<>();
                    List<String> objects = this.RemoveNulls(figure.split(OBJECT_DELIM));

                    for (String object : objects)
                    {
                        List<String> attributes = this.RemoveNulls(object.split(ATTRIBUTE_DELIM));
                        String uuid = UUID.randomUUID().toString();
                        MockRavensObject ravensObject = new MockRavensObject(uuid);
                        HashMap<String, String> attributeValues = new HashMap<>();

                        for (String attribute : attributes)
                        {
                            String[] keyValue = attribute.split(":");
                            attributeValues.put(keyValue[0], keyValue[1]);
                        }

                        ravensObject.attributes = attributeValues;
                        allObjects.add(ravensObject);
                    }

                    allFigures.add(allObjects);
                }

                allProblems.add(allFigures);
            }
        }
        catch (Exception e) { }

        return allProblems;
    }
}
