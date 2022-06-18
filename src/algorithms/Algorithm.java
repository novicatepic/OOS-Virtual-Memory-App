package algorithms;

import java.util.ArrayList;

abstract public class Algorithm {
    int numOfPages;
    int numOfReferences;
    int[] references;
    String[][] matrix;
    int newBoundary;

    public Algorithm() {
        super();
    }

    public Algorithm(int numOfPages, int[] references) {
        this.numOfPages = numOfPages;
        this.references = references;
        this.numOfReferences = references.length;
        int matrixRows = numOfPages + 2;
        newBoundary = numOfPages + 2;
        this.matrix = new String[matrixRows][numOfReferences];
        for(int i = 0; i < numOfReferences; i++) {
            matrix[0][i] = String.valueOf(references[i]);
        }
    }

    abstract void processNonFullColumn(int column, int reference);
    public void implementAlgorithm() {
        initFirstRow();
        for(int i = 0; i < numOfReferences; i++) {
            if(i > 0) {
                rewriteOldColumn(i);
            }

            if(isPageFault(i, matrix[0][i])) {
                matrix[1][i] = "PF";
            }

            if(isColumnFull(i)) {
                processFullColumn(i, references[i]);
            }
            else {
                processNonFullColumn(i, references[i]);
            }
        }
        printMatrix();
        efficiency();
    }
    abstract void processFullColumn(int column, int reference);

    void initFirstRow() {
       for(int i = 0; i < references.length; i++) {
           matrix[0][i] = String.valueOf(references[i]);
       }
    }

    boolean isPageFault(int column, String element) {
        int  counter = 0;
        boolean isNullInColumn = false;
        for(int i = 2; i < newBoundary; i++) {
            if(matrix[i][column] == null) {
                isNullInColumn = true;
            }
            else {
                if(element.equals(matrix[i][column])) {
                    return false;
                }
                counter++;
            }
        }
        if(counter == numOfPages) {
            return true;
        }

        return isNullInColumn;
    }

    boolean isColumnFull(int column) {
        for(int i = 2; i < newBoundary; i++) {
            if(matrix[i][column] == null) {
                return false;
            }
        }
        return true;
    }

    int findPositionOfElement(int element, int column) {
        for(int i = 2; i < newBoundary; i++) {
            if(String.valueOf(element).equals(matrix[i][column])) {
                return i;
            }
        }
        return -1;
    }

    void rewriteOldColumn(int newColumn) {
        for(int i = 2; i < newBoundary; i++) {
            matrix[i][newColumn] = matrix[i][newColumn - 1];
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
                    System.out.printf("%4s", matrix[i][j]);
                }
                else {
                    String emptyElem = "/";
                    System.out.printf("%4s", emptyElem);
                }
            }
            System.out.println();
        }
    }

    void efficiency() {
        int pageFaults = countNumOfPageFaults();
        double percentage = countPfPercentage();
        System.out.println("Algorithm efficiency: PF = " + pageFaults);
        System.out.println(" => pf = " + pageFaults + " / " + numOfReferences + " = " + percentage + "%");
    }

    void processNonFullColumnIfElementDoesntExistFIFOLRUOptimal(int column, int reference) {
        int firstPosition = findFirstEmptyPosition(column);
        if(firstPosition == 2) {
            matrix[firstPosition][column] = String.valueOf(reference);
        }
        else {
            for(int i = firstPosition; i > 2; i--) {
                matrix[i][column] = matrix[i-1][column];
            }
            matrix[2][column] = String.valueOf(reference);
        }
    }

    void processFullColumnIfElementDoesntExistFIFOLRU(int column, int reference) {
        for(int i = newBoundary - 1; i >= 2; i--) {
            matrix[i][column] = matrix[i-1][column];
        }
        matrix[2][column] = String.valueOf(reference);
    }

    ArrayList<String> getElementsOfColumn(int column) {
        ArrayList<String> list = new ArrayList<>();
        for(int i = 2; i < newBoundary; i++) {
            if(matrix[i][column] != null) {
                list.add(String.valueOf(matrix[i][column]));
            }
        }

        return list;
    }

}
