import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class arrayHW {

    public static<E> boolean unique(List<E> list) { //list of anything with type E
      for(int i =0; i < list.size(); i++){ //Iterate
          for (int j = i+1; j < list.size(); j++){ //Iterates through all folowing numbers after 'i'
              if(list.get(i).equals(list.get(j))) { //compares
                  return false;
              }
          }
      }
        return true;
    }
    public static List<Integer> allMultiples(List<Integer> list, int x){ //list of any integer
//if the user input(x) is a multiple of any of the indexes, return a list of the multiples
            List<Integer> result = new ArrayList<>();

            for(int i =0; i < list.size(); i++){
                if(list.get(i)%x == 0){ //if the indiced number is taken by the modulus of x and equals 0
                    result.add(list.get(i)); //add the current integer to 'result' if it is a multiple
                }
           }return result;
    }
    public static List<String> allStringsOfSize(List<String> list, int length){ //If an item in the list is the size of
        //integer that is entered, return the index words that are the size of that number(meaning remove the words that aren't that size)
        List<String> result = new ArrayList<>();

        for(String i : list){
            if(i.length() == length){ //if the size of the indiced string is == to inputted length, add it to new list
                result.add(i);// add the current string that is 'i' into the new list called 'result'
            }
        }return result;
    }

    public static <E extends Comparable<E>> boolean isPermutation(List<E> first, List<E> second) {

        List<E> sortA = new ArrayList<>();
        List<E> sortB = new ArrayList<>();

        sortA.addAll(first); // add everything from orignal list into new list
        sortB.addAll(second);

        Collections.sort(sortA); // sort both list in ASCII order
        Collections.sort(sortB);

        return sortA.equals(sortB); //compare both list for accuracy
    }


    public static List<String> stringsToListOFWords(String input){ //Takes in a string and converts it into a list of words
        List<String> result = new ArrayList<>();

        String[] words = input.split("\\s+"); // Split the string into seperate words

        for(String word : words){ //Iterate through string and put the current indice into word
        if(!word.isEmpty()){ //if that indice is not empty
            result.add("\"" + word + "\""); // add that word to words with quotes seperating them
        }
       }return result;
    }


    public static void removeAllInstances(List<Integer> list, int x) {
        for (int i = list.size() - 1; i >= 0; i--) { // Iterate backward to avoid shifting issues
            if (list.get(i).equals(x)) { // Use .equals() to compare i and x with eachother
                list.remove(i); //then remove the i indice that is equal to x
            }
        }
    }



    public static void main(String[] args) {

        // unique
        List<Integer> uniqueList = List.of(1, 2, 3, 4, 5);
        List<Integer> nonUniqueList = List.of(1, 2, 3, 2, 4);
        System.out.println(unique(uniqueList)); // Expected: true
        System.out.println(unique(nonUniqueList)); // Expected: false
//allMultiples
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        int multiple = 2;
        System.out.println(allMultiples(numbers, multiple)); // Expected: [2, 4, 6, 8, 10]

//allStringsOfSize
        List<String> strings = List.of("apple", "banana", "kiwi", "grape", "pear");
        int length = 5;
        System.out.println(allStringsOfSize(strings, length)); // Expected: [apple, grape]

    //isPermutation
        List<Integer> list1 = List.of(1, 2, 3);
        List<Integer> list2 = List.of(3, 2, 1);
        System.out.println(isPermutation(list1, list2)); // Expected: true

    // stringsToListOFWords
        String input = "Hello, world! This is a test.";
        System.out.println(stringsToListOFWords(input)); // Expected: [Hello,, world!, This, is, a, test.]

    //removeAllInstances
        List<Integer> list = new ArrayList<>(List.of(1, 4, 5, 6, 5, 5, 2));
        removeAllInstances(list, 5);
        System.out.println(list);


    }}
