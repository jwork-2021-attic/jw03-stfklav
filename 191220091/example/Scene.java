package example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.URL;

import example.classloader.SteganographyClassLoader;

public class Scene {

    public static void main(String[] args) throws Exception {

        Line line = new Line(7);
        line.put(Gourd.ONE, 6);
        line.put(Gourd.TWO, 3);
        line.put(Gourd.THREE, 1);
        line.put(Gourd.FOUR, 5);
        line.put(Gourd.FIVE, 2);
        line.put(Gourd.SIX, 4);
        line.put(Gourd.SEVEN, 0);

        Geezer theGeezer = Geezer.getTheGeezer();

        /*SteganographyClassLoader loader = new SteganographyClassLoader(
                new URL("https://cdn.njuics.cn/example.BubbleSorter.png"));

        Class c = loader.loadClass("example.BubbleSorter");*/

        /*SteganographyClassLoader loader = new SteganographyClassLoader(
                new URL("https://user-images.githubusercontent.com/80143498/136231706-d85b59ba-44bd-43ba-900b-209f233f6326.png"));
        Class c = loader.loadClass("example.SelectSorter");*/

       /*SteganographyClassLoader loader = new SteganographyClassLoader(
            new URL("file:///C:/Users/87389/Desktop/jw03-stfklav/191220091/example.QuickSorter.png"));
        Class c = loader.loadClass("example.QuickSorter");*/

       SteganographyClassLoader loader = new SteganographyClassLoader(
            new URL("file:///C:/Users/87389/Desktop/jw03-stfklav/191220091/S191220057.InsertSorter.png"));
        Class c = loader.loadClass("S191220057.InsertSorter");

        Sorter sorter = (Sorter) c.newInstance();

        theGeezer.setSorter(sorter);

        String log = theGeezer.lineUp(line);

        BufferedWriter writer;
        writer = new BufferedWriter(new FileWriter("selectResult.txt"));
        writer.write(log);
        writer.flush();
        writer.close();

    }

}
