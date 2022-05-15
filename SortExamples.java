import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadLocalRandom;

public class SortExamples {

    public static void main(String[] args) {
        int[] array = new int[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = ThreadLocalRandom.current().nextInt();
        }
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

        System.arraycopy(array, 0, leftSubArray, 0, leftSubArray.length); //fills the left
        System.arraycopy(array, leftSubArray.length, rightSubArray, 0, rightSubArray.length); // fills the right

        var leftTask = new MergeSortThread(leftSubArray);
        var rightTask = new MergeSortThread(rightSubArray);

        leftTask.fork();
        rightTask.compute();
        leftTask.join();
        mergeSortedSubArr(leftSubArray, rightSubArray, array); // merge all in one
    }

    public static void mergeSortedSubArr(int[] leftSubArray, int[] rightSubArray, int[] result) {
        int cursorL = 0, cursorR = 0, cursorRes = 0;
        //it works while sub arrays not empty
        while (cursorL < leftSubArray.length && cursorR < rightSubArray.length) {//
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