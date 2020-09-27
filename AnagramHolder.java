import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Class used to create all possible permutations (of length 3 or more) given an original String
 */
public class AnagramHolder {
    private static HashSet<String> wordSet;
    private String original;
    private String word;
    private HashMap<Character, Integer> availableLetters;

    /**
     * 
     * @param original
     * @param word
     * @param availableLetters
     */
    public AnagramHolder(String original, String word, HashMap<Character, Integer> availableLetters) {
        this.original = original;
        this.word = word;
        this.availableLetters = availableLetters;
    }

    /**
     * Constructor used on the original String; should be used first
     * @param originalWord original String from which anagrams will be generated
     */
    public AnagramHolder(String originalWord) {
        this(originalWord, originalWord, null);
        wordSet = new HashSet<String>(factorial(original.length()));
    }

    /**
     * Constructor that creates AnagramHolder from which all others will be created; should be used second
     * @param originalHolder first instance of class which contains original String
     */
    public AnagramHolder(AnagramHolder originalHolder) {
        this(originalHolder.original, "", originalHolder.createAvailableLetters());
    }

    /**
     * Setter for availableLetters of this AnagramHolder
     * @param availableLetters updated HashMap of what letters are available
     */
    public void setAvailableLetters(HashMap<Character, Integer> availableLetters) {
        this.availableLetters = availableLetters;
    }

    /**
     * Getter for availableLetters of this AnagramHolder
     * @return the availableLetters of this AnagramHolder
     */
    public HashMap<Character, Integer> getAvailableLetters() {
        return this.availableLetters;
    }

    /**
     * Creates a HashMap that holds how many of each character is available in the String
     * @return availableLetters the availableLetters of this AnagramHolder
     */
    private HashMap<Character, Integer> createAvailableLetters() {
        int length = this.word.length();
        HashMap<Character, Integer> availableLetters = new HashMap<>(length);
        for (int i = 0; i < length; i++) {
            char ch = this.word.charAt(i);
            if (availableLetters.containsKey(ch)) {
                availableLetters.put(ch, availableLetters.get(ch) + 1);
            } else {
                availableLetters.put(ch, 1);
            }
        }
        return availableLetters;
    }

    /**
     * Makes a deep copy of the availableLetters field, so that each AnagramHolder has its own
     * @return a deep copy of availableLetters
     */
    private HashMap<Character, Integer> makeDeepCopy() {
        HashMap<Character, Integer> deepCopy = new HashMap<>(this.availableLetters.size());
        this.availableLetters.forEach((k,v) -> {
            char ch = k.charValue();
            int num = v.intValue();
            deepCopy.put(Character.valueOf(ch), Integer.valueOf(num));
        });
        return deepCopy;
    }

    /**
     * Returns the factorial of a number; used to initiate capacity of wordSet
     * @param n an integer
     * @return n! = n*(n-1)* ... * 2 * 1
     */
    private int factorial(int n) {
        int fact = n;
        for (int i = n -1; i >= 1; i--) {
            fact = fact * i;
        }
        return fact;
    }

    /**
     * Recursive method which utilizes the availableLetters field to see which characters can still be added to create more anagrams 
     */
    public void makeAnagrams() {
        this.availableLetters.forEach((k, v) -> {
            if (v != 0) {
                HashMap<Character, Integer> copyAvailableLetters = this.makeDeepCopy();
                copyAvailableLetters.put(k, v - 1);
                AnagramHolder ah = new AnagramHolder(this.original, this.word + k, copyAvailableLetters);
                ah.makeAnagrams();
                if (ah.word.length() > 2) {
                    wordSet.add(ah.word);
                }
            }
        });
    }

    @Override
    public String toString() {
        return this.word;
    }

    /**
     * Loop to print wordSet field once all anagrams are created
     */
    public void printWordSet() {
        Iterator<String> it = wordSet.iterator();
        System.out.print("WordSet: [ ");
        while (it.hasNext()) {
            System.out.print(it.next() + ", ");
        }
        System.out.print("]");
        System.out.println();
    }

    /**
     * Refines wordSet based on dictionary inputted; discards words that are not in the dictionary 
     * @param dictionary file containing words that are considered meaningful
     */
    public void createRefinedWordSet(HashSet<String> dictionary) {
        System.out.println("Refining...");
        wordSet.retainAll(dictionary);
    }
}