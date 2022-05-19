package algorithms;

abstract public class Algorithm {
    int numOfPages;
    int numOfReferences;
    int[] references;
    String[][] matrix;
    int newBoundary;

    void initFirstRow() {
       for(int i = 0; i < references.length; i++) {
           matrix[0][i] = String.valueOf(references[i]);
       }
    }

    boolean isPageFault(int column, String element) {
        int  counter = 0;
        for(int i = 2; i < newBoundary; i++) {
            if(matrix[i][column] == null) {
                return true;
            }
            else {
                if(element.equals(matrix[i][column])) {
                    return false;
                }
            }
        }
        return true;
    }

    boolean isColumnFull(int column) {
        int  counter = 0;
        for(int i = 2; i < newBoundary; i++) {
            if(matrix[i][column] == null) {
                return false;
            }
        }
        return true;
    }

    void processFullColumn(int column, int reference) {
        if(!findIfElementAlreadyExistsInColumn(column, reference)) {
            for(int i = newBoundary; i >= 3; i--) {
                matrix[i][column] = matrix[i-1][column];
            }
            matrix[2][column] = String.valueOf(reference);
        }
    }

    int findFirstEmptyPosition(int column) {
        for(int i = 2; i < newBoundary; i++) {
            if(matrix[i][column] == null) {
                return i;
            }
        }
        return -1;
    }

    boolean findIfElementAlreadyExistsInColumn(int column, int element) {
        for(int i = 2; i < newBoundary; i++) {
            if(matrix[i][column] != null && matrix[i][column].equals(String.valueOf(element))) {
                return true;
            }
        }
        return false;
    }

    void processNonFullColumn(int column, int reference) {
        if(!findIfElementAlreadyExistsInColumn(column, reference)) {
            int firstPosition = findFirstEmptyPosition(column);
            matrix[firstPosition][column] = String.valueOf(reference);
        }

    }

    int countNumOfPageFaults() {
        int numOfPageFaults = 0;
        for(int i = 0; i < numOfReferences; i++) {
            if("PF".equals(matrix[1][i])) {
                numOfPageFaults++;
            }
        }
        return numOfPageFaults;
    }

    double countPfPercentage() {
        int numOfFaults = countNumOfPageFaults();
        double percentage = numOfFaults / (double)numOfReferences;
        percentage *= 100;
        return percentage;
    }

    public void printMatrix() {
        for(int i = 0; i < newBoundary; i++) {
            for(int j = 0; j < references.length; j++) {
                if(matrix[i][j] != null) {
                    System.out.print(matrix[i][j] + " ");
                }
                else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }
    }

    abstract void implementAlgorithm();
}
