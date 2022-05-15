import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class QuickSortTest {

	@Test
	void test1() {
		
		int [] arr=new int[] {8,9,909,9};
		QuickSort obj=new QuickSort(0,arr.length,arr);
		int [] sortedArray =obj.sortArray(arr,arr.length);
		assertTrue(Arrays.equals(arr,sortedArray));
	}
	
	@Test
	void test2() {
		int [] arr=new int[] {};
		QuickSort obj=new QuickSort(0,arr.length,arr);
		int [] sortedArray =obj.sortArray(arr,arr.length);
		assertTrue(Arrays.equals(arr,sortedArray));
	}
	
	@Test
	void test3() {
	
		int [] arr=new int[] {9};
		QuickSort obj=new QuickSort(0,arr.length,arr);
		int [] sortedArray =obj.sortArray(arr,arr.length);
		assertTrue(Arrays.equals(arr,sortedArray));
	}
	
	@Test
	void test4() {

		int [] arr=new int[] {9,9,9};
		QuickSort obj=new QuickSort(0,arr.length,arr);
		int [] sortedArray =obj.sortArray(arr,arr.length);
		assertTrue(Arrays.equals(arr,sortedArray));
	}
	
	@Test
	void test5() {
		
		int [] arr=new int[] {0,-9,8};
		QuickSort obj=new QuickSort(0,arr.length,arr);
		int [] sortedArray =obj.sortArray(arr,arr.length);
		assertTrue(Arrays.equals(arr,sortedArray));
	}
	
}
