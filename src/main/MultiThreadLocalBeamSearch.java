package main;

// Import library for writing CSV
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MultiThreadLocalBeamSearch extends LocalSearch {

	public static void main(String[] args) throws InterruptedException, IOException {

		// ------------Generate Matrix 10000x10000 data-------------
		int matrixSize = 10000; // matrix size
		int previousRandomValue = 0; 
		int randomRange, randomMax, randomMin, randomValue;
		int[][] matrix = new int[matrixSize][matrixSize];
		
		// Randomly generate the matrix with smoothly slope (+- 100)
		for (int coloumn = 0; coloumn < matrixSize; coloumn++)
			for (int row = 0; row < matrixSize; row++) {
				randomMax = previousRandomValue + 100;
				randomMin = previousRandomValue - 100;
				randomRange = randomMax - randomMin + 1;
				randomValue = (int) (Math.random() * randomRange) + randomMin;
				matrix[coloumn][row] = randomValue;
				previousRandomValue = randomValue;
			}

		// ------------Write generated matrix into CSV file-------------
		int[] array = new int[matrixSize * matrixSize];
		int temp = 0;
		
		// Reshape matrix to vector array
		for (int coloumn = 0; coloumn < matrixSize; coloumn++)
			for (int row = 0; row < matrixSize; row++) {
				array[temp] = matrix[row][coloumn];
				temp++;
			}
		// Define file name
		BufferedWriter br = new BufferedWriter(new FileWriter("genaratedMatrix.csv"));
		StringBuilder sb = new StringBuilder();
		
		// Write CSV
		for (int element : array) {
			sb.append(element);
			sb.append(",");
		}

		br.write(sb.toString());
		br.close();

		// ------------Generate random (x,y) 10,000 points----------
		// Randomly initialize row and column
		int genaratingCount = 0;
		int m = 0, n = 0;
		int[] randomX = new int[matrixSize], randomY = new int[matrixSize];

		while (genaratingCount != matrixSize) {

			m = (int) (Math.random() * 100);
			n = (int) (Math.random() * 100);

			randomX[genaratingCount] = m;
			randomY[genaratingCount] = n;

			genaratingCount++;
		}

		// ------------Start SEQUENTIAL Calculation----------------
		int sequentialCount = 0;
		int moveCount = 0;
		long startTime = System.currentTimeMillis();
		int localMax = 0;
		int max = -100000;
		
		// Loop for 10k -> find max value
		while (sequentialCount != matrixSize) {
			localMax = localSearch(matrix, randomX[sequentialCount], randomY[sequentialCount]);
			if (max < localMax) {
				max = localMax;
				moveCount++;
				System.out.println("moveCount: " + moveCount + ", max: " + max);
			}

			sequentialCount++;
		}
		long duration = System.currentTimeMillis() - startTime;
		System.out.println("sequential: " + duration + " ms");

		// ------------Start PARALLEL Calculation---------------
		
		// Using 4 threads
		int nThread = 4;
		
		// Allocate the X, Y array
		int[] ranx1 = new int[matrixSize / nThread];
		int[] rany1 = new int[matrixSize / nThread];
		int[] ranx2 = new int[matrixSize / nThread];
		int[] rany2 = new int[matrixSize / nThread];
		int[] ranx3 = new int[matrixSize / nThread];
		int[] rany3 = new int[matrixSize / nThread];
		int[] ranx4 = new int[matrixSize / nThread];
		int[] rany4 = new int[matrixSize / nThread];
		
		// Define the start, stop point
		int[] chunkStart = new int[nThread];
		int[] chuckStop = new int[nThread];

		// Thread 1 start 0 to 2499
		// Thread 2 start 2500 to 4999
		// Thread 3 start 5000 to 7499
		// Thread 4 start 7500 to 9999
		for (int i = 0; i < nThread; i++) {
			if (i == 0) {
				chunkStart[i] = 0;
				chuckStop[i] = matrixSize / nThread * (i + 1);

			} else {
				chunkStart[i] = chuckStop[i - 1];
				chuckStop[i] = matrixSize / nThread * (i + 1);
			}
		}
		
		// Separate initial array for each thread
		temp = 0;
		for (int i = chunkStart[0]; i < chuckStop[0]; i++) {
			ranx1[temp] = randomX[i];
			rany1[temp] = randomY[i];
			temp++;
		}

		temp = 0;
		for (int i = chunkStart[1]; i < chuckStop[1]; i++) {
			ranx2[temp] = randomX[i];
			rany2[temp] = randomY[i];
			temp++;
		}

		temp = 0;
		for (int i = chunkStart[2]; i < chuckStop[2]; i++) {
			ranx3[temp] = randomX[i];
			rany3[temp] = randomY[i];
			temp++;
		}

		temp = 0;
		for (int i = chunkStart[3]; i < chuckStop[3]; i++) {
			ranx4[temp] = randomX[i];
			rany4[temp] = randomY[i];
			temp++;
		}
		
		// Define tasks
		Task task1 = new Task("thread-1", matrix, ranx1, rany1);
		Task task2 = new Task("thread-2", matrix, ranx2, rany2);
		Task task3 = new Task("thread-3", matrix, ranx3, rany3);
		Task task4 = new Task("thread-4", matrix, ranx4, rany4);

		// ------------Start PARALLEL Calculation---------------
		
		// Define threads
		Thread t1 = new Thread(task1);
		Thread t2 = new Thread(task2);
		Thread t3 = new Thread(task3);
		Thread t4 = new Thread(task4);

		long startTimeParallel = System.currentTimeMillis();
		t1.start();
		t2.start();
		t3.start();
		t4.start();

		t1.join();
		t2.join();
		t3.join();
		t4.join();

		long parallelDuration = System.currentTimeMillis() - startTimeParallel;

		System.out.println("Parallel: " + parallelDuration + " ms");
		System.out.println("Max of Thread 1: " + task1.getMax());
		System.out.println("Max of Thread 2: " + task2.getMax());
		System.out.println("Max of Thread 3: " + task3.getMax());
		System.out.println("Max of Thread 4: " + task4.getMax());
		int result = findMax(findMax(findMax(task1.getMax(), task2.getMax()), task3.getMax()), task4.getMax());
		System.out.println("Parallel answer: " + result);

	}
}
