package algorithms;

import java.util.ArrayList;

public class Optimal extends Algorithm {

    public Optimal(int numOfPages, int[] references) {
        super(numOfPages, references);
    }

    @Override
    void processNonFullColumn(int column, int reference) {
        if(!findIfElementAlreadyExistsInColumn(column, reference)) {
            processNonFullColumnIfElementDoesntExistFIFOLRUOptimal(column, reference);
        }
    }

    //optimal algorithm only makes sense if it's new element coming up
    @Override
    void processFullColumn(int column, int reference) {
        if(!findIfElementAlreadyExistsInColumn(column, reference)) {
            //find which element to replace with help function
            String lookAhead = lookAhead(column);
            int position = findPositionOfElement(Integer.parseInt(lookAhead), column);
            matrix[position][column] = String.valueOf(reference);
        }
    }

    private String lookAhead(int column) {
        ArrayList<String> list = new ArrayList<>();
        int finalArrayCounter = 0;
        //find which elements are in this column
        ArrayList<String> elementsOfColumn = getElementsOfColumn(column);

        //look ahead and find elements in advance, put it into the list
        for(int i = column + 1; i < references.length; i++) {
            if(!list.contains(String.valueOf(references[i])) && finalArrayCounter < (numOfPages - 1) && elementsOfColumn.contains(String.valueOf(references[i]))) {
                list.add(String.valueOf(references[i]));
                finalArrayCounter++;
            }
        }

        //if we've completed the list, we'll return element that is found last
        //because that elements is miles away
        //an we'll remove it
        if(list.size() == numOfPages) {
            return list.get(list.size() - 1);
        }

        //if list is not full
        //we go from last element in column to the first element
        //and if list doesn't contain that element
        //we will remove that element
        if(list.size() < (numOfPages)) {
            for(int i = newBoundary - 1; i >= 2; i--) {
                if(!list.contains(matrix[i][column])) {
                    return matrix[i][column];
                }
            }
        }

        //does the same, dunno why i implemented this as well
        for(String elem : elementsOfColumn) {
            if(!list.contains(elem)) {
                return elem;
            }
        }
        return null;
    }
}
