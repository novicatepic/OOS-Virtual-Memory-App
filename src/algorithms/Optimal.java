package algorithms;

import java.util.ArrayList;

public class Optimal extends Algorithm {

    public Optimal() {}

    public Optimal(int numOfPages, int[] references) {
        super(numOfPages, references);
    }

    @Override
    void processNonFullColumn(int column, int reference) {
        if(!findIfElementAlreadyExistsInColumn(column, reference)) {
            processNonFullColumnIfElementDoesntExistFIFOLRUOptimal(column, reference);
        }
    }

    @Override
    void processFullColumn(int column, int reference) {
        if(!findIfElementAlreadyExistsInColumn(column, reference)) {
            String lookAhead = lookAhead(column);
            int position = findPositionOfElement(Integer.parseInt(lookAhead), column);
            matrix[position][column] = String.valueOf(reference);
        }
    }

    private String lookAhead(int column) {
        ArrayList<String> list = new ArrayList<String>();
        int finalArrayCounter = 0;
        ArrayList<String> elementsOfColumn = getElementsOfColumn(column);

        //DODAO column + 1
        for(int i = column + 1; i < references.length; i++) {
            if(finalArrayCounter < (numOfPages - 1) && elementsOfColumn.contains(String.valueOf(references[i]))) {
                list.add(String.valueOf(references[i]));
                finalArrayCounter++;
            }
        }

        //uklonio, bilo numOfPages - 1
        if(list.size() < (numOfPages)) {
            for(int i = newBoundary - 1; i >= 2; i--) {
                if(!list.contains(matrix[i][column])) {
                    return matrix[i][column];
                }
            }
        }

        for(String elem : elementsOfColumn) {
            if(!list.contains(elem)) {
                return elem;
            }
        }
        return null;
    }
}
