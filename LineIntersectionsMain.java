import java.util.Arrays;
import java.util.Scanner;

public class LineIntersectionsMain {
  public static void main(String[] args) {
    Scanner kb = new Scanner(System.in);
    int numOfInstances = Integer.parseInt(kb.nextLine());
    for (int m = 0; m < numOfInstances; m++) {
      int numOfLines = Integer.parseInt(kb.nextLine());
      Pair[] lines = new Pair[numOfLines];
      for (int j = 0; j < numOfLines; j++) {
        lines[j] = new Pair();
        lines[j].start = Integer.parseInt(kb.nextLine());
      }
      for (int j = 0; j < numOfLines; j++) {
        lines[j].end = Integer.parseInt(kb.nextLine());
      }
      Arrays.sort(lines, (p1, p2) -> (p1.start - p2.start));
      // the items in the array are now sorted according to The top line, now, if we just use the
      // inversion counter from before and the top lines order as indicies we should be fine
      Long[] ends = Arrays.stream(lines).map(e -> (long) (e.end)).toArray(size -> new Long[size]);
      System.out.println(modifiedMergeSort(ends, 0, ends.length - 1));
    }
    kb.close();
  }

  /**
   * this uses the merge sort with the new addition of counting the inversions
   * 
   * @param nums
   * @return
   */
  public static long modifiedMergeSort(Long[] nums, int start, int end) {
    if (start - end == 1 || start - end == 0) {
      return 0;
    }

    long firstHalf = modifiedMergeSort(nums, start, (start + end) / 2);
    long secondHalf = modifiedMergeSort(nums, (start + end) / 2 + 1, end);
    long mergedCount = mergeCount(nums, start, end);
    return firstHalf + secondHalf + mergedCount;

  }

  /**
   * 
   * @param nums
   * @param start
   * @param end
   * @return
   */
  private static long mergeCount(Long[] nums, int start, int end) {
    Long[] newList = new Long[end - start + 1];
    int nextIndex = 0;
    Long count = 0l;
    int ListAPointer = start;
    int ListBPointer = (start + end) / 2 + 1;
    while (ListAPointer < (start + end) / 2 + 1 || ListBPointer < end + 1) {
      // this is the popping of the min from each of them with preference to list A
      if (ListAPointer < (start + end) / 2 + 1
          && (ListBPointer >= end + 1 || nums[ListAPointer] <= nums[ListBPointer])) {
        newList[nextIndex] = nums[ListAPointer];
        ListAPointer++;
        // System.out.println("popped from A " + ListAPointer + " " + ListBPointer + " " + count);
      } else {
        newList[nextIndex] = nums[ListBPointer];
        ListBPointer++;
        count += (start + end) / 2 + 1 - ListAPointer;
        // System.out.println("popped from B " + ListAPointer + " " + ListBPointer + " " + count);
      }
      nextIndex++;
    }
    // now we have to reassign the values in num start to end to be what is in new List
    int newListIndex = 0;
    for (int i = start; i <= end; i++) {
      nums[i] = newList[newListIndex];
      newListIndex++;
    }
    // System.out.print("List: ");
    /*
     * for (int i = 0; i < nums.length; i++) { System.out.print(nums[i] + " "); }
     * System.out.println();
     */
    return count;
  }
}
