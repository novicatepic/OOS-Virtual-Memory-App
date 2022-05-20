package algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class LFU extends Algorithm {
    private int initialValue;
    private int decrementValue;
    private int incrementValue;
    private HashMap<String, Integer> hashMap;

    public LFU() {
        super();
    }

    public LFU(int numOfPages, int[] references, int initialValue, int decrementValue, int incrementValue) {
        super(numOfPages, references);
        this.initialValue = initialValue;
        this.decrementValue = decrementValue;
        this.incrementValue = incrementValue;
        hashMap = new HashMap<>();
        for (int elem : references) {
            if (!hashMap.containsKey(elem)) {
                hashMap.put(String.valueOf(elem), initialValue);
            }
        }
    }

    @Override
    void processNonFullColumn(int column, int reference) {
        if (!findIfElementAlreadyExistsInColumn(column, reference)) {
            hashMap.replace(String.valueOf(reference), initialValue);
            int position = findFirstEmptyPosition(column);
            if (position == 2) {
                matrix[position][column] = String.valueOf(reference);
            } else {
                for (int i = position; i > 2; i--) {
                    matrix[i][column] = matrix[i - 1][column];
                    int oldValue = hashMap.get(matrix[i][column]);
                    oldValue -= decrementValue;
                    hashMap.replace(matrix[i][column], oldValue);
                }
                matrix[2][column] = String.valueOf(reference);
            }
        } else {
            int emptyPosition = findFirstEmptyPosition(column);
            replaceValues(emptyPosition, column, reference);
            String[] array = sortReferences(column, reference);
            int k = 0;
            for (int i = 2; i < emptyPosition; i++) {
                matrix[i][column] = array[k++];
            }

        }
    }

    private String[] sortReferences(int column, int reference) {
        int position = findPositionOfElement(reference, column);
        int newValue = hashMap.get(matrix[position][column]);
        newValue += incrementValue;
        hashMap.replace(matrix[position][column], newValue);
        return sortForReal(column);
    }

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

    @Override
    void processFullColumn(int column, int reference) {

        if (!findIfElementAlreadyExistsInColumn(column, reference)) {
            hashMap.replace(String.valueOf(reference), initialValue);
            replaceValues(newBoundary, column, reference);
            matrix[newBoundary - 1][column] = String.valueOf(reference);
            preSort(column);
            String[] array = sortForReal(column);

            int k = 0;
            for (int i = 2; i < newBoundary; i++) {
                matrix[i][column] = array[k++];
            }
        } else {
            replaceValues(newBoundary, column, reference);
            String[] array = sortReferences(column, reference);
            int k = 0;
            for (int i = 2; i < newBoundary; i++) {
                matrix[i][column] = array[k++];
            }
        }
    }

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
