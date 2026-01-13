package software.aoc.day10.b.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import software.aoc.day10.b.model.Machine;

public class EquationSolver {

    private static final double EPSILON = 1e-4;

    public long solveMinPresses(Machine machine) {
        int numRows = machine.joltageTargets().size();
        int numCols = machine.buttons().size();

        double[][] matrix = new double[numRows][numCols + 1];

        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                List<Integer> affectedCounters = machine.buttons().get(c);
                matrix[r][c] = affectedCounters.contains(r) ? 1.0 : 0.0;
            }
            matrix[r][numCols] = machine.joltageTargets().get(r);
        }

        rref(matrix, numRows, numCols);

        List<Integer> freeVars = new ArrayList<>();
        int[] pivotRowForCol = new int[numCols];
        Arrays.fill(pivotRowForCol, -1);

        for (int c = 0; c < numCols; c++) {
            int pivotRow = -1;
            for (int r = 0; r < numRows; r++) {
                if (Math.abs(matrix[r][c] - 1.0) < EPSILON) {
                    boolean isLeading = true;
                    for (int prev = 0; prev < c; prev++) {
                        if (Math.abs(matrix[r][prev]) > EPSILON) {
                            isLeading = false;
                            break;
                        }
                    }
                    if (isLeading) {
                        pivotRow = r;
                        break;
                    }
                }
            }
            if (pivotRow != -1) {
                pivotRowForCol[c] = pivotRow;
            } else {
                freeVars.add(c);
            }
        }

        long maxSafeBound = 0;
        for (int t : machine.joltageTargets()) {
            maxSafeBound = Math.max(maxSafeBound, t);
        }
        maxSafeBound += 5;

        long result = solveRecursive(freeVars, 0, new long[numCols], matrix, pivotRowForCol, numRows, maxSafeBound);

        return result == Long.MAX_VALUE ? 0 : result;
    }

    private long solveRecursive(List<Integer> freeVars, int idx, long[] assignment,
            double[][] matrix, int[] pivotRowForCol, int numRows, long maxBound) {

        if (idx == freeVars.size()) {
            long currentSum = 0;

            for (int c = 0; c < assignment.length; c++) {
                int r = pivotRowForCol[c];
                if (r != -1) {
                    double val = matrix[r][assignment.length];
                    for (int freeIdx : freeVars) {
                        val -= matrix[r][freeIdx] * assignment[freeIdx];
                    }

                    if (val < -EPSILON || Math.abs(val - Math.round(val)) > EPSILON)
                        return Long.MAX_VALUE;
                    assignment[c] = Math.round(val);
                } else if (!freeVars.contains(c)) {
                    assignment[c] = 0;
                }
                currentSum += assignment[c];
            }

            for (int r = 0; r < numRows; r++) {
                double sum = 0;
                for (int c = 0; c < assignment.length; c++) {
                    sum += matrix[r][c] * assignment[c];
                }
                if (Math.abs(sum - matrix[r][assignment.length]) > EPSILON)
                    return Long.MAX_VALUE;
            }

            return currentSum;
        }

        int freeVarCol = freeVars.get(idx);
        long minTotal = Long.MAX_VALUE;

        for (long val = 0; val <= maxBound; val++) {
            assignment[freeVarCol] = val;
            long res = solveRecursive(freeVars, idx + 1, assignment, matrix, pivotRowForCol, numRows, maxBound);
            if (res < minTotal)
                minTotal = res;
        }

        return minTotal;
    }

    private void rref(double[][] matrix, int numRows, int numCols) {
        int pivotRow = 0;
        for (int col = 0; col < numCols && pivotRow < numRows; col++) {
            int maxRow = pivotRow;
            for (int i = pivotRow + 1; i < numRows; i++) {
                if (Math.abs(matrix[i][col]) > Math.abs(matrix[maxRow][col]))
                    maxRow = i;
            }
            if (Math.abs(matrix[maxRow][col]) < EPSILON)
                continue;

            double[] temp = matrix[pivotRow];
            matrix[pivotRow] = matrix[maxRow];
            matrix[maxRow] = temp;

            double pivotVal = matrix[pivotRow][col];
            for (int j = col; j <= numCols; j++)
                matrix[pivotRow][j] /= pivotVal;

            for (int i = 0; i < numRows; i++) {
                if (i != pivotRow) {
                    double factor = matrix[i][col];
                    if (Math.abs(factor) > EPSILON) {
                        for (int j = col; j <= numCols; j++)
                            matrix[i][j] -= factor * matrix[pivotRow][j];
                    }
                }
            }
            pivotRow++;
        }
    }
}
