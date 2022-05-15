import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.Scanner;
import java.util.Arrays;

public class MergeSort1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Please input the length of the array : ");
		int n = sc.nextInt();
        int[] array = new int[n];

		System.out.print("Do you want to generate the random elements automatically (if yes click y else n): ");
		char opt = sc.next().charAt(0);
		if (opt == 'y') {
			// create SIZE random integers between 0 and 999
			java.util.Random rand = new java.util.Random();
			for (int i = 0; i < n; ++ i) 
				array[i] = rand.nextInt(1000);
			System.out.print("The original array: ");
			System.out.println(Arrays.toString(array));
		} else if (opt == 'n') {
				System.out.println("Please input the array elements: ");
			try{
			for (int i = 0; i < n; ++ i)
				array[i] = sc.nextInt();      
			}
			catch(Exception e){
				System.out.println("Please give the Integer value only");
			}
			
		} 
		else {
			System.out.println("Error: invalid input!");
			System.exit(1);
		}
		sc.close();

        printArray(array);
        ForkJoinPool.commonPool().invoke(new MergeSortThread(array));
        printArray(array);
    }

    private static void printArray(int[] array) {

        System.out.println();
        for (int element : array) {
            System.out.print(element + " ");
        }
    }
}

// RecursiveAction class is a subclass of Fork-Join API and doesn't return a value
class MergeSortThread extends RecursiveAction {
    private int[] array;
    private int arrayLength;

    //constructor for MergeSortThread because we are passing an array
    public MergeSortThread(int[] array) {
        this.array = array;
        this.arrayLength = array.length;
    }

    @Override
    protected void compute() {
        if (arrayLength < 2) { // base condition
            return;
        }

        int midpoint = array.length/2;

        int[] leftSubArray = new int[midpoint]; // creating left subarray from 0 upto midpoint
        int[] rightSubArray = new int[arrayLength - midpoint]; //creating right subarray from midpoint upto arraylength

        System.arraycopy(array, 0, leftSubArray, 0, leftSubArray.length); //copy the values to left subarray
        System.arraycopy(array, leftSubArray.length, rightSubArray, 0, rightSubArray.length); //copy the values to right subarray

        var leftTask = new MergeSortThread(leftSubArray); //called recursively
        var rightTask = new MergeSortThread(rightSubArray); //called recursively

        leftTask.fork();
        rightTask.compute(); //called automatically for RecursiveAction
        leftTask.join();
        mergeSortedSubArr(leftSubArray, rightSubArray, array); //thread for merging subarrays in single array
    }

    public static void mergeSortedSubArr(int[] leftSubArray, int[] rightSubArray, int[] result) {
   
        //we need to keep track of current indexes of  left subarray, right subarray and the result array, 
       //they will be used while comparing and merging the elements

        int cursorL = 0, cursorR = 0, cursorRes = 0;

        //it works while sub arrays not empty
        while (cursorL < leftSubArray.length && cursorR < rightSubArray.length) {
            // compare the elements of left and right subarrays
            if (leftSubArray[cursorL] <= rightSubArray[cursorR]) {
                result[cursorRes++] = leftSubArray[cursorL++];
            } else {
                result[cursorRes++] = rightSubArray[cursorR++];
            }
        }

        //it works if right sub array is empty, but left isn't yet
        while (cursorL < leftSubArray.length) {
            result[cursorRes++] = leftSubArray[cursorL++];
        }

        //otherwise
        while (cursorR < rightSubArray.length) {
            result[cursorRes++] = rightSubArray[cursorR++];
        }
    }
}