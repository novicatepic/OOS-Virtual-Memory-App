package algorithms;

import java.util.ArrayList;

abstract public class Algorithm {
    int numOfPages;
    int numOfReferences;
    int[] references;
    String[][] matrix;
    //last row
    int newBoundary;

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
        //put references in first row
        initFirstRow();
        for(int i = 0; i < numOfReferences; i++) {
            if(i > 0) {
                //put old elements in new column and then proceed to do whatever algorithm does
                rewriteOldColumn(i);
            }

            //check if it's new element, if it is then page fault is set
            if(isPageFault(i, matrix[0][i])) {
                matrix[1][i] = "PF";
            }

            //for each algorithm, we process full and non-full column, my implementation
            if(isColumnFull(i)) {
                processFullColumn(i, references[i]);
            }
            else {
                processNonFullColumn(i, references[i]);
            }
        }
        //after we finish algorithm, we print full matrix and count efficiency
        printMatrix();
        efficiency();
    }

    //each algorithm is gonna have it's own implementation of processing full column
    abstract void processFullColumn(int column, int reference);

    //initializing first row with references
    void initFirstRow() {
       for(int i = 0; i < references.length; i++) {
           matrix[0][i] = String.valueOf(references[i]);
       }
    }

    //simple function to check if there is page fault
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

    //help function, name says it all
    boolean isColumnFull(int column) {
        for(int i = 2; i < newBoundary; i++) {
            if(matrix[i][column] == null) {
                return false;
            }
        }
        return true;
    }

    //find pos of element, useful help function
    int findPositionOfElement(int element, int column) {
        for(int i = 2; i < newBoundary; i++) {
            if(String.valueOf(element).equals(matrix[i][column])) {
                return i;
            }
        }
        return -1;
    }

    //rewrite everything from old column before updating it in the next step
    void rewriteOldColumn(int newColumn) {
        for(int i = 2; i < newBoundary; i++) {
            matrix[i][newColumn] = matrix[i][newColumn - 1];
        }
    }

    //find first empty position in non-full column
    int findFirstEmptyPosition(int column) {
        for(int i = 2; i < newBoundary; i++) {
            if(matrix[i][column] == null) {
                return i;
            }
        }
        return -1;
    }

    //help function for page fault and algorithms
    boolean findIfElementAlreadyExistsInColumn(int column, int element) {
        for(int i = 2; i < newBoundary; i++) {
            if(matrix[i][column] != null && matrix[i][column].equals(String.valueOf(element))) {
                return true;
            }
        }
        return false;
    }

    //in the end, we have to get algorithm efficiency
    int countNumOfPageFaults() {
        int numOfPageFaults = 0;
        for(int i = 0; i < numOfReferences; i++) {
            if("PF".equals(matrix[1][i])) {
                numOfPageFaults++;
            }
        }
        return numOfPageFaults;
    }

    //as we've learned, quick simple function
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

    //final efficiency count
    void efficiency() {
        int pageFaults = countNumOfPageFaults();
        double percentage = countPfPercentage();
        System.out.println("Algorithm efficiency: PF = " + pageFaults);
        System.out.println(" => pf = " + pageFaults + " / " + numOfReferences + " = " + percentage + "%");
    }

    //help functions for fifo, lru, optimal
    //probably should've implemented it in some other class or something, since other two algorithms
    //don't need these function
    //we just move elements
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

    //shift elements downwards
    void processFullColumnIfElementDoesntExistFIFOLRU(int column, int reference) {
        for(int i = newBoundary - 1; i >= 2; i--) {
            matrix[i][column] = matrix[i-1][column];
        }
        matrix[2][column] = String.valueOf(reference);
    }

    //find elements in column, especially useful for optimal algorithm
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
