package algorithms;

import java.util.ArrayList;
import java.util.HashMap;

public class SecondChance extends Algorithm {
    //put r bit values in hashmap
    private final HashMap<Integer, Boolean> hashMap;
    //dynamic hashmap is usefull because it allows us to set/remove rBit
    //we can set it again because it's remembered in hashMap, and hashMap is not changed in the algorithm
    private final HashMap<Integer, Boolean> dynamicBitSettings;

    public SecondChance(int numOfPages, int[] references, ArrayList<Integer> rBit) {
        super(numOfPages, references);
        hashMap = new HashMap<>();
        dynamicBitSettings = new HashMap<>();
        //init both maps
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
                //restore r-bit if it was lost
                dynamicBitSettings.replace(reference, true);
            }
        }
        else {
            //there is not gonna be a problem because column is not full
            //so i can just move elements downwards and put new element on the top
            //if it has possibility to have a r-bit, i'm gonna update it
            int position = findFirstEmptyPosition(column);
            if(hashMap.get(reference)) {
                dynamicBitSettings.replace(reference, true);
                for(int i = position; i > 2; i--) {
                    matrix[i][column] = matrix[i-1][column];
                }
                matrix[2][column] = String.valueOf(reference);
            }
            else {
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
            //restore r-bit
            if(hashMap.get(reference) && !dynamicBitSettings.get(reference)) {
                dynamicBitSettings.replace(reference, true);
            }
        }
        else {
            //restore r-bit
            if(hashMap.containsKey(reference) && !dynamicBitSettings.get(reference)) {
                dynamicBitSettings.replace(reference, true);
            }

            //if last element had r-bit
            if(hashMap.get(Integer.parseInt(matrix[newBoundary-1][column])) &&
                    dynamicBitSettings.get(Integer.parseInt((matrix[newBoundary-1][column])))) {
                //didRemove allows me to check if i had two r-bits in last two positions, when i have multiple r-bits
                //if i had it like that, i can't just throw away that second r-bit
                //it has to stay saved
                boolean didRemove = false;
                int temp = newBoundary - 1;
                ArrayList<String> arrayList = new ArrayList<>();
                ArrayList<Integer> positions = new ArrayList<>();
                //find all elements that have r-bit
                //case when every r-bit is in the column covered
                while((!"".equals(matrix[temp][column]) && matrix[temp][column] != null && !"PF".equals(matrix[temp][column])) && hashMap.get(Integer.parseInt(matrix[temp][column])) &&
                        dynamicBitSettings.get(Integer.parseInt((matrix[temp][column])))) {
                    arrayList.add(matrix[temp][column]);
                    positions.add(temp);
                    if(temp == newBoundary - 2) {
                        didRemove = true;
                    }
                    temp--;
                }

                //if i had every r-bit in all elements in the column
                //only last element is updated
                if(arrayList.size() != numOfPages) {
                    String element = matrix[newBoundary-1][column];
                    dynamicBitSettings.replace(Integer.parseInt(element), false);
                    //move elements, so i can put a new element in there
                    for(int i = (newBoundary-1); i > 2; i--) {
                        matrix[i][column] = matrix[i-1][column];
                    }

                    //put new element in first position of matrix
                    if(numOfPages != 1) {
                        matrix[2][column] = String.valueOf(reference);
                    }
                    else {
                        matrix[2][column] = element;
                    }

                    for(int i = (newBoundary-1); i > 3; i--) {
                        matrix[i][column] = matrix[i-1][column];
                    }

                    //put r-bit element just below new element
                    if(numOfPages > 1) {
                        matrix[3][column] = element;
                    }
                    //every r-bit element has to stay saved
                    //so it's gonna be like that
                    //and restore last element whose r-bit is removed to the last position
                    if(didRemove) {
                        dynamicBitSettings.replace(Integer.parseInt(arrayList.get(1)), false);
                        for(int i = 1; i < arrayList.size(); i++) {
                            matrix[positions.get(i) + 1][column] = arrayList.get(i);
                        }
                    }

                }
                //remove r-bit from last element, it stayed saved, but need to update it
                else {
                    dynamicBitSettings.replace(Integer.parseInt(arrayList.get(0)), false);
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
