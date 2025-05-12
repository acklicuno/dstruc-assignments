import java.io.*;
import java.util.*;

//Declare variables for comparisons, swaps and runtime
public class Sorting {

    static class SortStats {
        int comparisons = 0;
        int swaps = 0;
        long runtime = 0;
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter numbers:");
        String[] input = scanner.nextLine().split(" ");
        //string to int array
        int[] original = Arrays.stream(input).mapToInt(Integer::parseInt).toArray();

        FileWriter writer = new FileWriter("sorting_results.csv");
        writer.write("Algorithm,Comparisons,Swaps,Runtime(ns)\n");

        // Insertion Sort
        int[] insertionArray = Arrays.copyOf(original, original.length);
        SortStats insertionStats = new SortStats(); //Creating stats object
        insertionSort(insertionArray, insertionStats);
        //This is who you write it to a csv
        writer.write(String.format("Insertion Sort,%d,%d,%d\n", insertionStats.comparisons, insertionStats.swaps, insertionStats.runtime));

        // Quicksort
        int[] quickArray = Arrays.copyOf(original, original.length);
        SortStats quickStats = new SortStats();//stat obnject
        long start = System.nanoTime(); //timer
        quickSort(quickArray, 0, quickArray.length - 1, quickStats);
        quickStats.runtime = System.nanoTime() - start;
        writer.write(String.format("Quicksort,%d,%d,%d\n", quickStats.comparisons, quickStats.swaps, quickStats.runtime));

        // Merge Sort
        int[] mergeArray = Arrays.copyOf(original, original.length);
        SortStats mergeStats = new SortStats();//stat object
        long mStart = System.nanoTime(); //timer
        //run
        mergeSort(mergeArray, 0, mergeArray.length - 1, mergeStats);
        mergeStats.runtime = System.nanoTime() - mStart;
        writer.write(String.format("Merge Sort,%d,%d,%d\n", mergeStats.comparisons, mergeStats.swaps, mergeStats.runtime));
        // how to close the file
        writer.close();
        System.out.println("Results written to sorting_results.csv");
    }

    static void insertionSort(int[] arr, SortStats stats) {
        long start = System.nanoTime();
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0) {
                stats.comparisons++;
                if (arr[j] > key) {//if out of order shift the element, count shifts as the swaps and inc
                    arr[j + 1] = arr[j];
                    stats.swaps++;
                    j--;
                } else {//if in order
                    break;
                }
            }
            arr[j + 1] = key;//Insert
        }
        stats.runtime = System.nanoTime() - start;
    }

    static void quickSort(int[] arr, int low, int high, SortStats stats) {
        if (low < high) {
            int pi = partition(arr, low, high, stats);
            //left and right arrays
            quickSort(arr, low, pi - 1, stats);
            quickSort(arr, pi + 1, high, stats);
        }
    }

    static int partition(int[] arr, int low, int high, SortStats stats) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            stats.comparisons++;
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
                stats.swaps++;
            }
        }
        swap(arr, i + 1, high);//final pivot swap
        stats.swaps++;
        return i + 1;//return pivot
    }

    static void mergeSort(int[] arr, int left, int right, SortStats stats) {
        if (left < right) {
            //Find the middle, set the right and left halves, then merge
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid, stats);
            mergeSort(arr, mid + 1, right, stats);
            merge(arr, left, mid, right, stats);
        }
    }

    static void merge(int[] arr, int left, int mid, int right, SortStats stats) {
        //Set the size of both sides of the left and right
        int n1 = mid - left + 1;
        int n2 = right - mid;
        //Set the left and right subbarrays
        int[] L = Arrays.copyOfRange(arr, left, mid + 1);
        int[] R = Arrays.copyOfRange(arr, mid + 1, right + 1);
        //set pointer
        //Count comparisons
        //Copy from left
        //Copy from right
        //Count the swwaps
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            stats.comparisons++;
            if (L[i] <= R[j]) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
                stats.swaps++;  // treat placing from R into L as a swap
            }
        }
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
