## 253. Meeting Rooms II

### Question
Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei), find the minimum number of conference rooms required.

For example,
Given [[0, 30],[5, 10],[15, 20]],
return 2.

### Thinking:
* Method 1:
    1. Sort the intervals according to the starting time.
    2. Use a minHeap(pq) to save the endTime for all intervals according to the order of start time.
        * Add end time to the pq.
        * if cur start time < pq.peek() => means current start time is before first ending time, which means we must have a new room.
        * if cur start time >= pq.peek() => means we can use this room for the meeting, we poll out the the original period and add current period to the pq(Means we update the room with the new meeting).
    ```Java
    class Solution {
        public int minMeetingRooms(int[][] intervals) {
            if(intervals == null || intervals.length == 0) return 0;
            Arrays.sort(intervals, new Comparator<int[]>(){
                @Override
                public int compare(int[] a, int[] b){
                    return a[0] - b[0];
                }
            });
            PriorityQueue<Integer> pq = new PriorityQueue<>();
            int room = 0;
            for(int i = 0; i < intervals.length; i++){
                pq.offer(intervals[i][1]);
                if(intervals[i][0] < pq.peek())  room++;
                else{
                    pq.poll();
                }
            }
            return room;
        }
    }
    ```

* Method 2: Two pointer + sort
    * we sort start time and end time.
    * initialize the start and end index as 0.
    * if start time < end time, means we have a meeting in active, active++.
    * else active--.
    * We need to record the max number of the active room.
    ```Java
    class Solution {
        public int minMeetingRooms(int[][] intervals) {
            int len = intervals.length;
            int[] startTime = new int[len];
            int[] endTime = new int[len];
            int index = 0;
            for(int[] interval: intervals){
                startTime[index] = interval[0];
                endTime[index++] = interval[1];
            }
            Arrays.sort(startTime);
            Arrays.sort(endTime);
            int i = 0, j = 0;
            int activate = 0, max = 0;
            while(i < len && j < len){
                if(startTime[i] < endTime[j]){
                    activate++;
                    i++;
                }else{
                    activate--;
                    j++;
                }
                max = Math.max(max, activate);
            }
            return max;
        }
    }
    ```

### Amazon Seesion
* Method 1: Pq + sort
	```Java
	class Solution {
		public int minMeetingRooms(int[][] intervals) {
			if(intervals.length <= 1) return intervals.length;
			int result = 1;
			PriorityQueue<int[]> pq = new PriorityQueue<>((a, b)->{
				return a[1] - b[1];
			});
			Arrays.sort(intervals, (a, b)->{
				return a[0] != b[0] ? a[0] - b[0]: a[1] - b[1];
			});
			pq.offer(intervals[0]);
			for(int i = 1; i < intervals.length; i++){
				if(intervals[i][0] >= pq.peek()[1]){
					pq.poll();
				}
				pq.offer(intervals[i]);
				result = Math.max(result, pq.size());
			}
			return result;
		}
	}
	```