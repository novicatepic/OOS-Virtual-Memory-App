package algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SecondChance extends Algorithm {
    private HashMap<Integer, Boolean> hashMap;
    private HashMap<Integer, Boolean> dynamicBitSettings;

    public  SecondChance() {
        super();
    }

    public SecondChance(int numOfPages, int[] references, ArrayList<Integer> rBit) {
        super(numOfPages, references);
        hashMap = new HashMap<Integer, Boolean>();
        dynamicBitSettings = new HashMap<>();
        for(int elem : references) {
            if(rBit.contains(elem) && !hashMap.containsKey(elem)) {
                hashMap.put(elem, true);
            }
            else if(!rBit.contains(elem) && !hashMap.containsKey(elem)){
                hashMap.put(elem, false);
            }
        }
        dynamicBitSettings.putAll(hashMap);
    }

    @Override
    void processNonFullColumn(int column, int reference) {
        if(findIfElementAlreadyExistsInColumn(column, reference)) {
            if(hashMap.get(reference)) {
                dynamicBitSettings.replace(reference, true);
            }
        }
        else {
            int position = findFirstEmptyPosition(column);
            if(hashMap.get(reference) != false) {
                dynamicBitSettings.replace(reference, true);
                for(int i = position; i > 2; i--) {
                    matrix[i][column] = matrix[i-1][column];
                }
                matrix[2][column] = String.valueOf(reference);
            }
            else {
                position = findFirstEmptyPosition(column);
                for(int i = position; i > 2; i--) {
                    matrix[i][column] = matrix[i-1][column];
                }
                matrix[2][column] = String.valueOf(reference);
            }
        }
    }

    @Override
    void processFullColumn(int column, int reference) {
        if(findIfElementAlreadyExistsInColumn(column, reference)) {
            if(hashMap.get(reference) && !dynamicBitSettings.get(reference)) {
                dynamicBitSettings.replace(reference, true);
            }
        }
        else {
            if(hashMap.get(Integer.parseInt(matrix[newBoundary-1][column])) &&
                    dynamicBitSettings.get(Integer.parseInt((matrix[newBoundary-1][column])))) {
                if(numOfPages > 1) {
                    String first = matrix[3][column];
                }
                String element = matrix[newBoundary-1][column];
                dynamicBitSettings.replace(Integer.parseInt(element), false);
                for(int i = (newBoundary-1); i > 2; i--) {
                    matrix[i][column] = matrix[i-1][column];
                }

                if(numOfPages != 1) {
                    matrix[2][column] = String.valueOf(reference);
                }
                else {
                    matrix[2][column] = element;
                }

                for(int i = (newBoundary-1); i > 3; i--) {
                    matrix[i][column] = matrix[i-1][column];
                }
                if(numOfPages > 1) {
                    matrix[3][column] = element;
                }

            }
            else {
                for(int i = newBoundary - 1; i > 2; i--) {
                    matrix[i][column] = matrix[i - 1][column];
                }
                matrix[2][column] = String.valueOf(reference);
            }
        }
    }
}
