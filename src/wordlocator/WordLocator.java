package wordlocator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class WordLocator implements WordLocatorInterface {

    private TreeSet<String> allWords;
    private TreeSet<String> allFilePaths;
    private ArrayList<Location> locations;
    private TreeMap<String,ArrayList<Location>> wordLocation;


    private void processFile(String file) throws IOException {
        File fileToProcess = new File(file);
        if(fileToProcess.isFile()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int ch;
            int line = 1, column = 0;
            String word = "";
            do {
                ch = reader.read();
                column++;
                if(WordLocatorInterface.isInWord((char)ch)) {
                    word += (char)ch;
                } else if(ch == '\n'){
                    line++;
                    column = 0;
                } else {
                    if (word.length() > 0) {
                        Location location = new Location(fileToProcess.getPath(), line, column-word.length());
                        locations.add(location);
                        allWords.add(word.toLowerCase());

                        ArrayList<Location> uniqueLocations = wordLocation.get(word.toLowerCase());
                        if(uniqueLocations == null) {
                            uniqueLocations = new ArrayList<>();
                        }
                        uniqueLocations.add(location);
                        wordLocation.put(word.toLowerCase(), uniqueLocations);

                        word = "";
                    }
                }
            } while (ch != -1);

            reader.close();
        }
    }


    private void traverse(File parent) {
        if(parent.isDirectory()) {
            File[] children = parent.listFiles();
            for (File child : children) {
                if(child.isFile()) {
                    allFilePaths.add(child.getPath());
                }
                traverse(child);
            }
        }
    }


    public WordLocator(String parentDir) throws IOException {
        allWords = new TreeSet<>();
        allFilePaths = new TreeSet<>();
        locations = new ArrayList<>();
        wordLocation = new TreeMap<>();

        File parentDirectory = new File(parentDir);
        traverse(parentDirectory);

        for(String path: allFilePaths) {
            processFile(path);
        }
    }


    public TreeSet<String> getWords() {
        return allWords;
    }


    public TreeSet<String> getFilePaths() {
        return allFilePaths;
    }


    public int numOfOccurrencesInAllFiles(String word) {
        int count = 0;

        for(Map.Entry<String, ArrayList<Location>> wordLocationMap : wordLocation.entrySet()) {
            String wordInMap = wordLocationMap.getKey();
            ArrayList<Location> locationsInMap = wordLocationMap.getValue();
            for(Location location: locationsInMap) {
                if(wordInMap.equals(word)) {
                    count++;
                }
            }
        }

        return count;
    }


    public void printOccurrencesInAllFiles(String word) {
        for(Map.Entry<String, ArrayList<Location>> wordLocationsMap : wordLocation.entrySet()) {
            String wordInMap = wordLocationsMap.getKey();
            ArrayList<Location> locationsArray = wordLocationsMap.getValue();
            for(Location location: locationsArray) {
                if(wordInMap.equals(word)) {
                    System.out.println(location);
                }
            }
        }
    }


    public int numOfOccurrencesInFile(String filepath, String word) {
        int count = 0;

        for(Map.Entry<String, ArrayList<Location>> wordLocationMap : wordLocation.entrySet()) {
            String wordInMap = wordLocationMap.getKey();
            ArrayList<Location> locationsInMap = wordLocationMap.getValue();
            for(Location location: locationsInMap) {
                if(wordInMap.equals(word) && location.getFilepath().equals(filepath)) {
                    count++;
                }
            }
        }

        return count;
    }


    public void printOccurrencesInFile(String filepath, String word) {
        for(Map.Entry<String, ArrayList<Location>> wordLocationsMap : wordLocation.entrySet()) {
            String wordInMap = wordLocationsMap.getKey();
            ArrayList<Location> locationsArray = wordLocationsMap.getValue();
            for(Location location: locationsArray) {
                if(wordInMap.equals(word) && location.getFilepath().equals(filepath)) {
                    System.out.println(location);
                }
            }
        }
    }
}
