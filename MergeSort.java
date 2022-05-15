import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.Scanner;
import java.util.Arrays;
import java.time.Duration;
import java.time.Instant;
import java.lang.Comparable;

public class MergeSort{

    public static Comparable[] selectionsort(Comparable[] arr) {

        for (int i = 0; i < arr.length; i++) {
            int index = i;
            for (int j = i + 1; j < arr.length; j++)
                if (arr[j].compareTo(arr[index]) < 0) {
                	//System.out.println((int)arr[j].compareTo(arr[index]));
                    index = j;// searching for lowest index
                }
            
            if (index != i) {
            	System.out.println(Arrays.toString(arr));
                Comparable temp = arr[i];
                arr[i] = arr[index];
                arr[index] = temp;
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Please input the length of the array : ");
        int n = sc.nextInt();
        int[] array = new int[n];
        Integer[] array1 = new Integer[n];

        System.out.print("Do you want to generate the random elements automatically (if yes click y else n): ");
        char opt = sc.next().charAt(0);
        if (opt == 'y') {
            // create SIZE random integers between 0 and 999
            java.util.Random rand = new java.util.Random();
            for (int i = 0; i < n; ++i) {
                array[i] = rand.nextInt(1000);
                array1[i] = rand.nextInt(1000);
            }
            System.out.print("The original array: ");
            System.out.println(Arrays.toString(array));
        } else if (opt == 'n') {
            System.out.println("Please input the array elements: ");
            try {
                for (int i = 0; i < n; ++i) {
                    array[i] = sc.nextInt();
                    array1[i] = sc.nextInt();
                }
            } catch (Exception e) {
                System.out.println("Please give the Integer value only");
            }

        } else {
            System.out.println("Error: invalid input!");
            System.exit(1);
        }
        sc.close();

        printArray(array);
        Instant inst1 = Instant.now();

        if (n <= 100) {
            Instant selection_inst1 = Instant.now();

            System.out.println("\nselection sort called");
            Comparable[] arr = selectionsort(array1);
            System.out.println(Arrays.toString(arr));
            Instant selection_inst2 = Instant.now();
            System.out.println(
                    "selection Elapsed Time: " + Duration.between(selection_inst1, selection_inst2).toString());

        } else {
            // Instant selection_inst1 = Instant.now();
            // System.out.println("\nselection sort called");
            // selectionSort(array);
            // Instant selection_inst2 = Instant.now();
            // System.out.println(
            // "selection Elapsed Time: " + Duration.between(selection_inst1,
            // selection_inst2).toString());

            ForkJoinPool.commonPool().invoke(new MergeSortThread(array));
            System.out.println(ForkJoinPool.commonPool().getParallelism());
            System.out.println(
                    " Before invoking number of active thread :" +
                            ForkJoinPool.commonPool().getActiveThreadCount());

            System.out.printf("Main: parallelism is %d \n",
                    ForkJoinPool.commonPool().getParallelism());
            System.out.printf("Main: getActiveThreadCount is %d \n",
                    ForkJoinPool.commonPool().getActiveThreadCount());
            System.out.printf("Main: getQueuedTaskCount is %d \n",
                    ForkJoinPool.commonPool().getQueuedTaskCount());
            System.out.printf("Main: getStealCount is %d \n",
                    ForkJoinPool.commonPool().getStealCount());
        }
        // System.out.printf("Main: parallelism outside is %d \n",
        // ForkJoinPool.commonPool().getParallelism());
        // System.out.printf("Main: getActiveThreadCount outside is %d \n",
        // ForkJoinPool.commonPool().getActiveThreadCount());
        // System.out.printf("Main: getQueuedTaskCount outside is %d \n",
        // ForkJoinPool.commonPool().getQueuedTaskCount());
        // System.out.printf("Main: getStealCount outside is %d \n",
        // ForkJoinPool.commonPool().getStealCount());
        // System.out.println("\nSorted array is: ");
        Instant inst2 = Instant.now();
        System.out.println("Elapsed Time: " + Duration.between(inst1,
                inst2).toString());
        printArray(array);

    }

    private static void printArray(int[] array) {

        System.out.println();
        for (int element : array) {
            System.out.print(element + " ");
        }
    }
    
}

class MergeSortThread extends RecursiveAction {
    private int[] array;
    private int arrayLength;

    public MergeSortThread(int[] array) {
        this.array = array;
        this.arrayLength = array.length;
    }

    @Override
    protected void compute() {
        if (arrayLength < 2) { // exit condition
            return;
        }
        int[] leftSubArray = new int[arrayLength / 2]; // left subarray
        int[] rightSubArray = new int[arrayLength - arrayLength / 2]; // right subarray

        System.arraycopy(array, 0, leftSubArray, 0, leftSubArray.length); // fills the left
        System.arraycopy(array, leftSubArray.length, rightSubArray, 0, rightSubArray.length); // fills the right

        MergeSortThread leftTask = new MergeSortThread(leftSubArray);
        MergeSortThread rightTask = new MergeSortThread(rightSubArray);

        leftTask.fork();
        rightTask.compute();
        leftTask.join();
        // leftTask.fork();
        // rightTask.fork();
        // leftTask.join();
        // rightTask.join();
        mergeSortedSubArr(leftSubArray, rightSubArray, array); // merge all in one
    }

    public static void mergeSortedSubArr(int[] leftSubArray, int[] rightSubArray, int[] result) {
        int cursorL = 0, cursorR = 0, cursorRes = 0;
        // it works while sub arrays not empty
        while (cursorL < leftSubArray.length && cursorR < rightSubArray.length) {//
            if (leftSubArray[cursorL] <= rightSubArray[cursorR]) {
                result[cursorRes++] = leftSubArray[cursorL++];
            } else {
                result[cursorRes++] = rightSubArray[cursorR++];
            }
        }
        // it works if right sub array is empty, but left isn't yet
        while (cursorL < leftSubArray.length) {
            result[cursorRes++] = leftSubArray[cursorL++];
        }
        // otherwise
        while (cursorR < rightSubArray.length) {
            result[cursorRes++] = rightSubArray[cursorR++];
        }
    }

}