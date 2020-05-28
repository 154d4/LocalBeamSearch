package main;

public abstract class LocalSearch {

	public static int localSearch(int[][] matrix, int column, int row) {
		int max = 0;
		
		// Define that matrix[row][column]
		// For example 4x4 matrix:
		// [x, x, x, x],
		// [x, x, x, x],
		// [x, x, x, x],
		// [x, x, x, x].
		// Notation: x represents non-considered position, o represents considered.

		if ((row == 0) && (column == 0)) {
			// Named as corner1
			// e.g. 4x4 matrix -> data[0][0]
			// [o, x, x, x],
			// [x, x, x, x],
			// [x, x, x, x],
			// [x, x, x, x].

			int columnPlusOne = matrix[row][column + 1];
			int rowPlusOne = matrix[row + 1][column];

			if (findMax(columnPlusOne, rowPlusOne) > matrix[row][column]) {

				if (columnPlusOne == findMax(columnPlusOne, rowPlusOne))

					localSearch(matrix, column + 1, row);
				else
					localSearch(matrix, column, row + 1);
			}
			if (matrix[row][column] >= max)
				max = matrix[row][column];
			return max;

		} else if ((row == 0) && (column == (matrix[0].length - 1))) {
			// Named as corner2
			// e.g. 4x4 matrix -> data[0][3]
			// [x, x, x, o],
			// [x, x, x, x],
			// [x, x, x, x],
			// [x, x, x, x].

			int dataCoulumnMinusOne = matrix[row][column - 1];
			int rowPlusOne = matrix[row + 1][column];

			if (findMax(dataCoulumnMinusOne, rowPlusOne) > matrix[row][column]) {
				if (dataCoulumnMinusOne == findMax(dataCoulumnMinusOne, rowPlusOne))
					localSearch(matrix, column - 1, row);
				else
					localSearch(matrix, column, row + 1);
			}

			if (matrix[row][column] >= max)
				max = matrix[row][column];
			return max;

		} else if ((row == (matrix.length - 1)) && (column == 0)) {
			// Named as corner3
			// e.g. 4x4 matrix -> data[3][0]
			// [x, x, x, x],
			// [x, x, x, x],
			// [x, x, x, x],
			// [o, x, x, x].

			int columnPlusOne = matrix[row][column + 1];
			int rowMinusOne = matrix[row - 1][column];

			if (findMax(columnPlusOne, rowMinusOne) > matrix[row][column]) {
				if (columnPlusOne == findMax(columnPlusOne, rowMinusOne))
					localSearch(matrix, column + 1, row);
				else
					localSearch(matrix, column, row - 1);
			}

			if (matrix[row][column] >= max)
				max = matrix[row][column];
			return max;
		} else if ((row == (matrix.length - 1)) && (column == (matrix[matrix.length - 1].length) - 1)) {

			// Named as corner4
			// e.g. 4x4 matrix -> data[3][3]
			// [x, x, x, x],
			// [x, x, x, x],
			// [x, x, x, x],
			// [x, x, x, o].

			int columnMinusOne = matrix[row][column - 1];
			int rowMinusOne = matrix[row - 1][column];

			if (findMax(columnMinusOne, rowMinusOne) > matrix[row][column]) {

				if (columnMinusOne == findMax(columnMinusOne, rowMinusOne)) {

					localSearch(matrix, column - 1, row);
				} else {

					localSearch(matrix, column, row - 1);
				}
			}

			if (matrix[row][column] >= max)
				max = matrix[row][column];
			return max;

		}

		// Next, we consider on rim of matrix which not be the corner.

		else if (row == 0) {

			// Named as rim1
			// e.g. 4x4 matrix -> data[0][1], data[0][2]
			// [x, o, o, x],
			// [x, x, x, x],
			// [x, x, x, x],
			// [x, x, x, x].

			int rowPlusOne = matrix[row + 1][column];
			int columnPlusOne = matrix[row][column + 1];
			int columnMinusOne = matrix[row][column - 1];

			if ((findMax(findMax(rowPlusOne, columnPlusOne), columnMinusOne)) > matrix[row][column]) {
				if (rowPlusOne == (findMax(findMax(rowPlusOne, columnPlusOne), columnMinusOne))) {
					localSearch(matrix, column, row + 1);
				} else if (columnPlusOne == (findMax(findMax(rowPlusOne, columnPlusOne), columnMinusOne))) {
					localSearch(matrix, column + 1, row);
				} else if (columnMinusOne == (findMax(findMax(rowPlusOne, columnPlusOne), columnMinusOne))) {
					localSearch(matrix, column - 1, row);
				}
			} else {

				if (matrix[row][column] >= max)
					max = matrix[row][column];
				return max;
			}

		} else if (row == (matrix.length - 1)) {

			// Named as rim2
			// e.g. 4x4 matrix -> data[3][1], data[3][2]
			// [x, x, x, x],
			// [x, x, x, x],
			// [x, x, x, x],
			// [x, o, o, x].

			int rowMinusOne = matrix[row - 1][column];
			int columnplusOne = matrix[row][column + 1];
			int columnMinusOne = matrix[row][column - 1];

			if ((findMax(findMax(rowMinusOne, columnplusOne), columnMinusOne)) > matrix[row][column]) {
				if (rowMinusOne == (findMax(findMax(rowMinusOne, columnplusOne), columnMinusOne))) {

					localSearch(matrix, column, row - 1);

				} else if (columnplusOne == (findMax(findMax(rowMinusOne, columnplusOne), columnMinusOne))) {

					localSearch(matrix, column + 1, row);
				} else if (columnMinusOne == (findMax(findMax(rowMinusOne, columnplusOne), columnMinusOne))) {

					localSearch(matrix, column - 1, row);
				}
			} else {

				if (matrix[row][column] >= max)
					max = matrix[row][column];
				return max;
			}
			// ----------------------------------
		} else if (column == 0) {

			// Named as rim3
			// e.g. 4x4 matrix -> data[1][0], data[2][0]
			// [x, x, x, x],
			// [o, x, x, x],
			// [o, x, x, x],
			// [x, x, x, x].

			int rowPlusOne = matrix[row + 1][column];
			int rowMinusOne = matrix[row - 1][column];
			int columnPlusOne = matrix[row][column + 1];

			if ((findMax(findMax(rowMinusOne, columnPlusOne), rowPlusOne)) > matrix[row][column]) {

				if (rowMinusOne == (findMax(findMax(rowMinusOne, columnPlusOne), rowPlusOne))) {
					localSearch(matrix, column, row - 1);
				} else if (columnPlusOne == (findMax(findMax(rowMinusOne, columnPlusOne), rowPlusOne))) {
					localSearch(matrix, column + 1, row);
				} else if (rowPlusOne == (findMax(findMax(rowMinusOne, columnPlusOne), rowPlusOne))) {
					localSearch(matrix, column, row + 1);
				}
			} else {
				if (matrix[row][column] >= max)
					max = matrix[row][column];
				return max;
			}
		} else if (column == matrix[0].length - 1) {

			// Named as rim4
			// e.g. 4x4 matrix -> data[1][3], data[2][3]
			// [x, x, x, x],
			// [x, x, x, o],
			// [x, x, x, o],
			// [x, x, x, x].

			int rowPlusOne = matrix[row + 1][column];
			int rowMinusOne = matrix[row - 1][column];
			int columnMinusOne = matrix[row][column - 1];

			if ((findMax(findMax(rowMinusOne, columnMinusOne), rowPlusOne)) > matrix[row][column]) {

				if (rowMinusOne == (findMax(findMax(rowMinusOne, columnMinusOne), rowPlusOne))) {
					localSearch(matrix, column, row - 1);
				} else if (columnMinusOne == (findMax(findMax(rowMinusOne, columnMinusOne), rowPlusOne))) {
					localSearch(matrix, column - 1, row);
				} else if (rowPlusOne == (findMax(findMax(rowMinusOne, columnMinusOne), rowPlusOne))) {
					localSearch(matrix, column, row + 1);
				}
			} else {

				if (matrix[row][column] >= max)
					max = matrix[row][column];

				return max;
			}
		}

		// And this is a normal case which is a middle position.

		else {

			// Named as normal
			// e.g. 4x4 matrix
			// [x, x, x, x],
			// [x, o, o, x],
			// [x, o, o, x],
			// [x, x, x, x].

			int rowplusOne = matrix[row + 1][column];
			int rowMinusOne = matrix[row - 1][column];
			int columnPlusOne = matrix[row][column + 1];
			int columnMinusOne = matrix[row][column - 1];

			if ((findMax((findMax(findMax(rowplusOne, rowMinusOne), columnPlusOne)),
					columnMinusOne)) > matrix[row][column]) {

				if (rowMinusOne == findMax((findMax(findMax(rowplusOne, rowMinusOne), columnPlusOne)),
						columnMinusOne)) {
					localSearch(matrix, column, row - 1);
				} else if (columnMinusOne == findMax((findMax(findMax(rowplusOne, rowMinusOne), columnPlusOne)),
						columnMinusOne)) {
					localSearch(matrix, column - 1, row);
				} else if (rowplusOne == findMax((findMax(findMax(rowplusOne, rowMinusOne), columnPlusOne)),
						columnMinusOne)) {
					localSearch(matrix, column, row + 1);
				} else if (columnPlusOne == findMax((findMax(findMax(rowplusOne, rowMinusOne), columnPlusOne)),
						columnMinusOne)) {
					localSearch(matrix, column + 1, row);
				}

			} else {
				if (matrix[row][column] >= max)
					max = matrix[row][column];
				return max;
			}
		}

		return max;
	}

	// Compare function in order to find the maximum value.
	public static int findMax(int integer1, int integer2) {
		if (integer1 > integer2)
			return integer1;
		else
			return integer2;
	}
}
