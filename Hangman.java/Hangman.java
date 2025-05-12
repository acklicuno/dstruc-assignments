import java.util.*;
import java.io.*;

public class Hangman {

    public static Map<Integer, List<String>> readDictionary(String filename) {
        Map<Integer, List<String>> dictionaryMap = new HashMap<>();

        try (Scanner scanner = new Scanner(new File(filename))) {
            // Read each word from the  file
            while (scanner.hasNext()) {
                String word = scanner.next().toUpperCase();
                int len = word.length();

                // If the map doesn't have this length yet, add a new list
                dictionaryMap.putIfAbsent(len, new ArrayList<>());
                // Add the word to the correct list based on length
                dictionaryMap.get(len).add(word);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Dictionary file not found.");
        }

        return dictionaryMap;
    }


    public static void game(List<String> wordList, int numGuesses) {
        Scanner input = new Scanner(System.in);
        Set<String> guessedLetters = new HashSet<>(); // stores letters
        String currentState = "_".repeat(wordList.get(0).length()); // Word display
        List<String> currentWords = new ArrayList<>(wordList); // Start with all words of said length

        // Loop continues until user runs out of guesses or wins
        while (numGuesses > 0) {
            // Display current game status
            System.out.println("Current word: " + spacedOut(currentState));
            System.out.println("Guessed letters: " + guessedLetters);
            System.out.println("Remaining guesses: " + numGuesses);
            System.out.println("Remaining words: " + currentWords.size()); // Useful for debugging

            // Get a valid letter guess from user
            String guess;
            while (true) {
                System.out.print("Enter your guess: ");
                guess = input.next().toUpperCase();

                // Input validation
                if (guess.length() != 1 || !Character.isLetter(guess.charAt(0))) {
                    System.out.println("Invalid input. Enter a single letter.");
                } else if (guessedLetters.contains(guess)) {
                    System.out.println("You Guessed That Already.");
                } else {
                    break; // Valid guess
                }
            }

            guessedLetters.add(guess); // Add to guessed letters

            // Build word families (pattern-to-wordlist mapping)
            Map<String, List<String>> wordFamilies = genWordFamilies(currentWords, guessedLetters);

            // Choose the family with the most words (the "cheating" part)
            String bestPattern = getBestFamily(wordFamilies);

            // Update word list to only include words from the best (largest) family
            currentWords = wordFamilies.get(bestPattern);

            // If the new pattern didn't reveal the guessed letter, it's a wrong guess
            if (bestPattern.equals(currentState)) {
                System.out.println("That is not in the word");
                numGuesses--;
            } else {
                System.out.println("Good guess");
                currentState = bestPattern; // Update current word pattern
            }

            // If all letters guessed, set up a win condition
            if (!currentState.contains("_")) {
                System.out.println("Correct. The word is " + currentState);
                return;
            }
        }

        // pick a random word from remaining possibilities if theyt get it wrong
        String finalWord = currentWords.get(new Random().nextInt(currentWords.size()));
        System.out.println("You lose. The word is " + finalWord);
    }


     //Generates word families by matching each word in the list to a pattern.

    public static Map<String, List<String>> genWordFamilies(List<String> wordList, Set<String> guessed) {
        Map<String, List<String>> families = new HashMap<>();

        for (String word : wordList) {
            StringBuilder pattern = new StringBuilder();

            // For each letter in the word, decide if it's revealed or hidden
            for (int i = 0; i < word.length(); i++) {
                String letter = String.valueOf(word.charAt(i));
                // If the letter has been guessed, reveal it; otherwise, use underscore
                pattern.append(guessed.contains(letter) ? letter : "_");
            }

            String key = pattern.toString(); // Final pattern string for this word
            families.putIfAbsent(key, new ArrayList<>()); // Add new list if needed
            families.get(key).add(word); // Add word to its matching pattern list
        }

        return families; // Returns a map: pattern -> list of words matching that pattern
    }

    /**
     * Finds and returns the pattern (key) with the most words (largest family).
     *  it always picks the biggest group
     */
    public static String getBestFamily(Map<String, List<String>> wordFamilies) {
        String bestPattern = "";
        int maxSize = 0;

        for (Map.Entry<String, List<String>> entry : wordFamilies.entrySet()) {
            if (entry.getValue().size() > maxSize) {
                bestPattern = entry.getKey();
                maxSize = entry.getValue().size();
            }
        }

        return bestPattern; // Pattern that allows for the most remaining words
    }


    public static String spacedOut(String word) {
        return word.replaceAll("", " ").trim();
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<Integer, List<String>> dictionary = readDictionary("words.txt");

        while (true) {
            // Ask user to choose a word length
            System.out.print("Enter word length: ");
            int wordLength = scanner.nextInt();

            // Check if there are words of that length
            if (!dictionary.containsKey(wordLength)) {
                System.out.println("No words of that length. Try again.");
                continue;
            }

            System.out.print("Enter number of allowed wrong guesses: ");
            int numGuesses = scanner.nextInt();
            // Get all words of the chosen length and start the game
            List<String> wordList = dictionary.get(wordLength);
            game(wordList, numGuesses);

            // Prompt to play again
            System.out.print("Play again? (y/n): ");
            String again = scanner.next().toLowerCase();
            if (!again.equals("y")) break;
        }
    }
}
