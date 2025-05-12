package src;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {
    public static void main(String[] args) {
        IndexTree index = new IndexTree();//Words are stored in index

        try {
            File file = new File("pg100.txt");
            Scanner scan = new Scanner(file);
            int lineNumber = 0;

            while (scan.hasNextLine()) {
                lineNumber++;
                String line = scan.nextLine();
                String[] words = line.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");//It is explained to remove punctuation and converts it all to lowercase for accuracy

                for (String word : words) {//add to inde
                    if (!word.isEmpty()) {
                        index.add(word, lineNumber);
                    }
                }
            }
            scan.close();//REmember to close scnner
        } catch (FileNotFoundException e) { //Safeproof
            System.out.println("File not found!");
            return;
        }

        // Print out the index
        index.printIndex();
    }
}
