package algorithms;

public class LRU extends Algorithm {

    public LRU() {
        super();
    }

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

    private void helpForLRUProcessing(int column, int reference) {
        int position = findPositionOfElement(reference, column);
        for(int i = position; i > 2; i--) {
            matrix[i][column] = matrix[i-1][column];
        }
        matrix[2][column] = String.valueOf(reference);
    }
}
