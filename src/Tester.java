import wordlocator.WordLocator;
import wordlocator.WordLocatorInterface;

import java.io.IOException;

public class Tester {
    public static void main(String[] args) throws IOException {

        WordLocatorInterface locator = new WordLocator("Input");

        System.out.println("count: " + locator.getWords().size());
        System.out.println(locator.getWords());
        System.out.println();

        System.out.println("count: " + locator.getFilePaths().size());
        System.out.println(locator.getFilePaths());
        System.out.println();

        System.out.println("count: " + locator.numOfOccurrencesInAllFiles("and"));
        locator.printOccurrencesInAllFiles("and");
        System.out.println();

        System.out.println("count: " + locator.numOfOccurrencesInFile("Input/Music/Beatles/Money.txt", "that's"));
        locator.printOccurrencesInFile("Input/Music/Beatles/Money.txt", "that's");
    }
}
