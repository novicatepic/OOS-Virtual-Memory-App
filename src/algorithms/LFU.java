package algorithms;

import java.util.ArrayList;
import java.util.HashMap;

public class LFU extends Algorithm {
    private final int initialValue;
    private final int decrementValue;
    private final int incrementValue;
    //each reference is gonna have it's value in hashmap
    private final HashMap<String, Integer> hashMap;

    public LFU(int numOfPages, int[] references, int initialValue, int decrementValue, int incrementValue) {
        super(numOfPages, references);
        this.initialValue = initialValue;
        this.decrementValue = decrementValue;
        this.incrementValue = incrementValue;
        hashMap = new HashMap<>();
        //in the beggining, each element has initialValue in map
        for (int elem : references) {
            if (!hashMap.containsKey(String.valueOf(elem))) {
                hashMap.put(String.valueOf(elem), initialValue);
            }
        }
    }

    @Override
    void processNonFullColumn(int column, int reference) {
        if (!findIfElementAlreadyExistsInColumn(column, reference)) {
            hashMap.replace(String.valueOf(reference), initialValue);
            int position = findFirstEmptyPosition(column);
            //if there are no elements yet, put it in first position
            if (position == 2) {
                matrix[position][column] = String.valueOf(reference);
            } else {
                hashMap.replace(String.valueOf(reference), initialValue);
                //move each element down
                for (int i = position; i > 2; i--) {
                    matrix[i][column] = matrix[i - 1][column];
                    int oldValue = hashMap.get(matrix[i][column]);
                    oldValue -= decrementValue;
                    hashMap.replace(matrix[i][column], oldValue);
                }
                //put new element in first position
                matrix[2][column] = String.valueOf(reference);
                //sort references in matrix based on their value in hashmap
                String[] ref = sortForReal(column);
                int k = 0;
                //update matrix with sorted references
                for(int i = 2; i < ref.length + 2; i++) {
                    matrix[i][column] = ref[k++];
                }
            }
        } else {
            //if element exists, replacing values is needed
            //so i made a function for that
            int emptyPosition = findFirstEmptyPosition(column);
            replaceValues(emptyPosition, column, reference);
            String[] array = sortReferences(column, reference);
            int k = 0;
            //update matrix with sorted references
            for (int i = 2; i < emptyPosition; i++) {
                matrix[i][column] = array[k++];
            }

        }
    }

    @Override
    void processFullColumn(int column, int reference) {
        if (!findIfElementAlreadyExistsInColumn(column, reference)) {
            hashMap.replace(String.valueOf(reference), initialValue);
            //update values for elements
            replaceValues(newBoundary, column, reference);
            //if each element from current setup has higher value than initial value
            //don't do anything
            if(!checkIfEveryOtherIsBigger(column, reference)) {
                //update matrix, as before
                matrix[newBoundary - 1][column] = String.valueOf(reference);
                preSort(column);
                String[] array = sortForReal(column);

                int k = 0;
                for (int i = 2; i < newBoundary; i++) {
                    matrix[i][column] = array[k++];
                }
            }
        } else {
            //again, update values...
            replaceValues(newBoundary, column, reference);
            String[] array = sortReferences(column, reference);
            int k = 0;
            for (int i = 2; i < newBoundary; i++) {
                matrix[i][column] = array[k++];
            }
        }
    }

    //help function to check if every element in matrix is bigger than element that's supposed to be added
    //if every other element is bigger, welp, new element isn't gonna be added
    private boolean checkIfEveryOtherIsBigger(int column, int reference) {
        for(int i = 2; i < newBoundary; i++) {
            if(hashMap.get(String.valueOf(matrix[i][column])) <= hashMap.get(String.valueOf(reference))) {
                return false;
            }
        }
        return true;
    }

    //help function that adds increment value to existing element, if it appeared again
    //and calls sorting func
    private String[] sortReferences(int column, int reference) {
        int position = findPositionOfElement(reference, column);
        int newValue = hashMap.get(matrix[position][column]);
        newValue += incrementValue;
        hashMap.replace(matrix[position][column], newValue);
        return sortForReal(column);
    }

    //simple insertion sort func
    //sorts elements based on map values
    private String[] sortForReal(int column) {
        ArrayList<String> getElementsFromColumn = getElementsOfColumn(column);
        String[] array = new String[getElementsFromColumn.size()];
        getElementsFromColumn.toArray(array);

        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i; j < array.length; j++) {
                if (hashMap.get(array[i]) < hashMap.get(array[j])) {
                    String temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
        return array;
    }

    //simple sort help func
    private void preSort(int column) {
        int pos = newBoundary - 1;
        for(int i = newBoundary - 2; i >= 2; i--) {
            if(hashMap.get(matrix[pos][column]) >= hashMap.get(matrix[i][column])) {
                String temp = matrix[pos][column];
                matrix[pos][column] = matrix[i][column];
                matrix[i][column] = temp;
                pos--;
            }
            else {
                break;
            }
        }
    }

    //decrement value for other elements, help func
    private void replaceValues(int newBoundary, int column, int reference) {
        for (int i = 2; i < newBoundary; i++) {
            if (Integer.parseInt(matrix[i][column]) != reference) {
                int replaceValue = hashMap.get(matrix[i][column]);
                replaceValue -= decrementValue;
                hashMap.replace(matrix[i][column], replaceValue);
            }
        }
    }
}
