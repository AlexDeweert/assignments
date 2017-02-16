/* 
   Assignment 2
   Student: Alex L. Deweert
   ID: V00855767
   University of Victoria 
   CSC 225-B03
   Professor V. Srinivasan
*/

import java.util.Scanner;
import java.util.Vector;
import java.util.Arrays;
import java.io.File;

/*
   A HeapSort class that sorts an array of integers
   using a heap style ADT to organize the data
*/
public class HeapSort{
        public static final int max_values = 1000000;
        static int heapSize = 0;

        /*
           Heapsort constructor that creates a new array of the same
           size as the array passed in to be sorted. The minHeap
           is populated with the elements of the array which are
           automatically sorted, and then removed one at a time
           from the top of the minHeap back into the original array.
        */
        public static void HeapSort(int[] A){
                
                int[] minHeap = new int[ A.length + 1 ];
                
                for( int i = 0; i < A.length; i++ ) {
                        insert( minHeap, A[i] );
                }
                
                for( int i = heapSize, j = 0; i > 0; i--, j++ ) {
                        A[j] = deleteMin( minHeap );
                }
        }


        /*
           Insert an integer into the minheap
        */
        public static void insert( int[] minHeap, int val ) {
        
                heapSize++;
                //Heap is empty
                if( heapSize == 0 ) {
                        minHeap[1] = val; //is the root
                }

                //Heap is size 1 or greater
                else {
                        //Add to end of array and bubble up as required
                        minHeap[ heapSize ] = val;
                        bubbleUp( minHeap );
                }

        }

        /*
           Delete the minimum integer from the top
           of the heap.
        */
        public static int deleteMin( int[] minHeap ) {

                int returnVal = 0;

                //Heap is size 1 (just the root node)
                if( heapSize == 1 ) {
                        returnVal = minHeap[1];
                        heapSize--;
                }

                //Heap is empty
                else if( heapSize == 0) {
                        System.out.println("Error, heap is empty");
                }

                else { //Heap size is > 1?
                        returnVal = minHeap[1];
                        int rootKey = minHeap[1];
                        int lastKey = minHeap[heapSize];
                        int lastIndex = heapSize;
                        swap( rootKey, 1, lastKey, lastIndex, minHeap);
                        heapSize--;
                        trickleDown( minHeap );
                }
                
                return returnVal;
        }

        /*
           In the case where a small value is out of place,
           this value will "bubble up" into the correct position
           into the tree by comparing and swapping values as 
           necessry.
        */
        public static void bubbleUp( int[] minHeap ) {

                boolean done = false;
                int curr = heapSize; //start at last internal node
                while( !done ) {

                        int parentIndex = curr/2;
                        int parentKey = minHeap[parentIndex];
                        int childKey = minHeap[curr];
                        if( parentKey > childKey && curr != 1 ) {
                                swap( parentKey, parentIndex, childKey, curr, minHeap );
                                curr = parentIndex;
                        }
                        else {
                                done = true;
                        }
                }
        }

        /*
           Exchange a prents and child  key within the heap
        */
        public static void swap( int parentKey, int parentIndex, int childKey, int childIndex, int[] minHeap  ) {
                minHeap[ childIndex ] = parentKey;
                minHeap[ parentIndex ] = childKey;
        }

        /*
           In the case where a large value is out of place,
           this value will "trickle down" into the correct
           position in the tree by comparing and swapping
           as necessary.
        */
        public static void trickleDown ( int[] minHeap  ) {
                
                int curr = 1;//start at root
                int smallerIndex = 0;
                boolean done = false;


                while( !done ) {
                        
                        //If current node has left and right children
                        if( hasLeftChild( minHeap, curr ) && hasRightChild( minHeap, curr  ) ) {
                                smallerIndex = getSmallerNodeIndex( minHeap, curr );

                                //Only do the swap if the smaller of the two children is smaller
                                //than the current parent node
                                if( minHeap[curr] <=  minHeap[smallerIndex] ) {
                                        done = true;
                                }
                                else {
                                        swap( minHeap[curr], curr, minHeap[ smallerIndex ], smallerIndex, minHeap );
                                        curr = smallerIndex;
                                }
                        }

                        //Just has left child
                        else if( hasLeftChild( minHeap, curr ) ) {
                                
                                if( minHeap[curr] <= minHeap[ curr*2 ] ) {
                                        done = true;
                                }
                                else {
                                        swap( minHeap[curr], curr, minHeap[ curr*2 ], curr*2, minHeap );
                                }
                        }

                        //No children so at the bottom, done
                        else {
                                done = true; 
                        }


                }


        }

        /*
           Returns the index of the smaller of two child nodes.
        */
        public static int getSmallerNodeIndex ( int[] minHeap, int parentIndex ) {
                if( minHeap[ parentIndex*2 ] < minHeap[ parentIndex*2 +1 ] ) {
                        return parentIndex*2; //Left child is smaller
                }
                else {
                        return parentIndex*2+1; //Right child is smaller
                }
        }

        /*
           Returns true or false if the parent node has a left child
        */
        public static boolean hasLeftChild( int[] minHeap, int parentIndex ) {
                if( parentIndex * 2 <= heapSize) {
                        return true;
                }
                return false;
        }

        /*
           Returns true or false if the parent node has a right child
        */
        public static boolean hasRightChild( int[] minHeap, int parentIndex ) {
                if( parentIndex * 2 + 1 <= heapSize ) {
                        return true;
                }
                return false;
        }

        /*
           Utility method not really required.
           Prints the array similar to built in "Arrays.toString(arr)"
        */
        public static void printArray( int[] minHeap ) {

                System.out.printf("\nminHeap contents: ");

                if( heapSize == 0 ) {
                        System.out.println("[]");
                }

                else {
                        for( int i = 1; i <= heapSize; i++ ) {
                
                                if( i == 1 ) {

                                        if( heapSize == 1 ) {
                                                System.out.printf("[%d]\n", minHeap[i]);
                                         }
                                        else {
                                                System.out.printf("[%d", minHeap[i]);
                                        }
                                }
                                else if( i == heapSize ) {
                                        System.out.printf(", %d]\n", minHeap[i]);
                                }
                                else {
                                        System.out.printf(", %d", minHeap[i]);
                                }
                        }
                }

        }


        /* main()
           Contains code to test the HeapSort function. Nothing in this function 
           will be marked. You are free to change the provided code to test your 
           implementation, but only the contents of the HeapSort() function above 
           will be considered during marking.
        */
        public static void main(String[] args){
                Scanner s;
                if (args.length > 0){
                        try{
                                s = new Scanner(new File(args[0]));
                        } catch(java.io.FileNotFoundException e){
                                System.out.printf("Unable to open %s\n",args[0]);
                                return;
                        }
                        System.out.printf("Reading input values from %s.\n",args[0]);
                }else{
                        s = new Scanner(System.in);
                        System.out.printf("Enter a list of non-negative integers. Enter a negative value to end the list.\n");
                }
                Vector<Integer> inputVector = new Vector<Integer>();

                int v;
                while(s.hasNextInt() && (v = s.nextInt()) >= 0)
                        inputVector.add(v);

                int[] array = new int[inputVector.size()];

                for (int i = 0; i < array.length; i++)
                        array[i] = inputVector.get(i);

                System.out.printf("Read %d values.\n",array.length);


                long startTime = System.currentTimeMillis();

                HeapSort(array);

                long endTime = System.currentTimeMillis();

                double totalTimeSeconds = (endTime-startTime)/1000.0;

                //Don't print out the values if there are more than 100 of them
                if (array.length <= 100){
                        System.out.println("Sorted values:");
                        for (int i = 0; i < array.length; i++)
                                System.out.printf("%d ",array[i]);
                        System.out.println();
                }

                //Check whether the sort was successful
                boolean isSorted = true;
                for (int i = 0; i < array.length-1; i++)
                        if (array[i] > array[i+1])
                                isSorted = false;

                System.out.printf("Array %s sorted.\n",isSorted? "is":"is not");
                System.out.printf("Total Time (seconds): %.2f\n",totalTimeSeconds);
        }
}
