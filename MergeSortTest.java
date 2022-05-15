import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
class MergeSortTest {
	@Test
	void test1() {
		MergeSort1 obj=new MergeSort1();
		int [] arr=new int[] {8,9,909,9}; 
		int [] sortedArray =obj.sortArray(arr);
		assertTrue(Arrays.equals(arr,sortedArray));
	}	
	@Test
	void test2() {
		MergeSort1 obj=new MergeSort1();
		int [] arr=new int[] {}; 
		int [] sortedArray =obj.sortArray(arr);
		assertTrue(Arrays.equals(arr,sortedArray));
	}
	@Test
	void test3() {
		MergeSort1 obj=new MergeSort1();
		int [] arr=new int[] {9};
		int [] sortedArray =obj.sortArray(arr);
		assertTrue(Arrays.equals(arr,sortedArray));
	}
	@Test
	void test4() {
		MergeSort1 obj=new MergeSort1();
		int [] arr=new int[] {9,9,9};
		int [] sortedArray =obj.sortArray(arr);
		assertTrue(Arrays.equals(arr,sortedArray));
	}
	@Test
	void test5() {
		MergeSort1 obj=new MergeSort1();
		int [] arr=new int[] {0,-9,8};
		int [] sortedArray =obj.sortArray(arr);
		assertTrue(Arrays.equals(arr,sortedArray));
	}

}
