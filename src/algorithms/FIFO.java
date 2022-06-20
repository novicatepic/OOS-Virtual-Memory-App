package algorithms;

public class FIFO extends Algorithm{

    public FIFO(int numOfPages, int[] references) {
        super(numOfPages, references);
    }

    //nothing special
    @Override
    void processNonFullColumn(int column, int reference) {
        if(!findIfElementAlreadyExistsInColumn(column, reference)) {
            processNonFullColumnIfElementDoesntExistFIFOLRUOptimal(column, reference);
        }
    }

    @Override
    void processFullColumn(int column, int reference) {
        if(!findIfElementAlreadyExistsInColumn(column, reference)) {
            processFullColumnIfElementDoesntExistFIFOLRU(column, reference);
        }
    }
}
