package algorithms;

public class LRU extends Algorithm {

    public LRU(int numOfPages, int[] references) {
        super(numOfPages, references);
    }

    @Override
    void processNonFullColumn(int column, int reference) {
        if(!findIfElementAlreadyExistsInColumn(column, reference)) {
            processNonFullColumnIfElementDoesntExistFIFOLRUOptimal(column, reference);
        }
        else {
            helpForLRUProcessing(column, reference);
        }
    }

    @Override
    void processFullColumn(int column, int reference) {
        if(!findIfElementAlreadyExistsInColumn(column, reference)) {
            processFullColumnIfElementDoesntExistFIFOLRU(column, reference);
        }
        else {
            helpForLRUProcessing(column, reference);
        }
    }

    //in lru when we find a element that already exists
    //we put it at the top
    //and shift others from old position downwards
    private void helpForLRUProcessing(int column, int reference) {
        int position = findPositionOfElement(reference, column);
        for(int i = position; i > 2; i--) {
            matrix[i][column] = matrix[i-1][column];
        }
        matrix[2][column] = String.valueOf(reference);
    }
}
