import java.io.*;
import java.util.Scanner;
import java.util.HashSet;

public class AnagramsDriver {
    /**
     * Creates dictionary object of words that are considered meaningful
     * @param fileName name of dictionary file
     * @return a HashSet object that contains all "meaningful" words
     * @throws FileNotFoundException thrown when the dictionary file is not in the same directory as this file
     */
    public static HashSet<String> createDictionary(String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        int counter = 0;
        Scanner scan = new Scanner(f);
        System.out.println("Counting number of elements in Dictionary...");
        while (scan.hasNextLine()) {
            counter++;
            scan.nextLine();
        }
        HashSet<String> dictionary = new HashSet<>(counter);
        scan = new Scanner(f);
        System.out.println("Creating Dictionary...");
        while (scan.hasNextLine()) {
            String word = scan.nextLine();
            dictionary.add(word);
        }
        System.out.println("Dictionary created!");
        return dictionary;
    }

    /**
     * Creates dictionary object of words that are considered meaningful when number of lines in file is known
     * @param fileName name of dictionary file
     * @param numLines number of lines/words in the dictionary file
     * @return a HashSet object that contains all "meaningful" words
     * @throws FileNotFoundException thrown when the dictionary file is not in the same directory as this file
     */
    public static HashSet<String> createKnownDictionary(String fileName, int numLines) throws FileNotFoundException {
        File f = new File(fileName);
        Scanner scan = new Scanner(f);
        HashSet<String> dictionary = new HashSet<>(numLines);
        System.out.println("Creating Dictionary...");
        while (scan.hasNextLine()) {
            String word = scan.nextLine();
            dictionary.add(word.toLowerCase());
        }
        System.out.println("Dictionary created!");
        return dictionary;
    }
    /**
     * Method in which AnagramHolder instances are created
     * @param args String of characters from which anagrams will be created
     */
    public static void main(String[] args) {
        try {
            final long startTime = System.currentTimeMillis();
            HashSet<String> dictionary = createDictionary("BigDictionary.txt");
            AnagramHolder original = new AnagramHolder(args[0]);
            AnagramHolder ah = new AnagramHolder(original);
            ah.makeAnagrams();
            ah.createRefinedWordSet(dictionary);
            ah.printWordSet();
            final long endTime = System.currentTimeMillis();
            System.out.println("Running time was: " +  (endTime - startTime)/(1000) + " seconds.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}