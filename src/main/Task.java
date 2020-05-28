package main;

public class Task extends LocalSearch implements Runnable {

	private int[][] matrix;
	private String name;
	private int count, temp;
	private int max = 0;
	private int localmax = 0;
	private int[] randomArrayX;
	private int[] randomArrayY;

	public Task(String name, int[][] matrix, int[] randomArrayX, int[] randomArrayY) {
		this.name = name;
		this.matrix = matrix;
		this.randomArrayX = randomArrayX;
		this.randomArrayY = randomArrayY;
	}

	public void run() {
		count = 0;
		temp = 0;
		int moveCount = 0;
		// Loop for 2500
		while (count != matrix[0].length/4) {
			
			//Find max
			localmax = localSearch(matrix, randomArrayX[count], randomArrayY[count]);
			
			// Found max value
			if (localmax > this.max) {
				this.max = localmax;
				System.out.println("Thread: "+ this.name +", moveCount: " + moveCount + ", max: " + this.max);
				moveCount++;
			// Count how many time doesn't improve
			} else if (localmax == this.max)
				temp++;
			count++;
			// If the max doesn't improve for 100 loops -> loop break 
			if (temp == 100) {
				System.out.println("Thread break!");
				count = 2500;
			}
		}
	}

	// Recall max value function
	public int getMax() {
		return this.max;
	}
}
