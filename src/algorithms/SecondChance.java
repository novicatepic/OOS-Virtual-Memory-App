package algorithms;

import java.util.ArrayList;
import java.util.HashMap;

public class SecondChance extends Algorithm {
    private final HashMap<Integer, Boolean> hashMap;
    private final HashMap<Integer, Boolean> dynamicBitSettings;

    public SecondChance(int numOfPages, int[] references, ArrayList<Integer> rBit) {
        super(numOfPages, references);
        hashMap = new HashMap<>();
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
            if(hashMap.get(reference)) {
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
            //dodao 3 linije ispod
            if(hashMap.containsKey(reference) && !dynamicBitSettings.get(reference)) {
                dynamicBitSettings.replace(reference, true);
            }
            boolean didRemove = false;
            int temp = newBoundary - 1;
            ArrayList<String> arrayList = new ArrayList<>();
            ArrayList<Integer> positions = new ArrayList<>();
            if(hashMap.get(Integer.parseInt(matrix[newBoundary-1][column])) &&
                    dynamicBitSettings.get(Integer.parseInt((matrix[newBoundary-1][column])))) {
                //dodao while petlju
                while((!"".equals(matrix[temp][column]) && matrix[temp][column] != null && !"PF".equals(matrix[temp][column])) && hashMap.get(Integer.parseInt(matrix[temp][column])) &&
                        dynamicBitSettings.get(Integer.parseInt((matrix[temp][column])))) {
                    arrayList.add(matrix[temp][column]);
                    positions.add(temp);
                    if(temp == newBoundary - 2) {
                        didRemove = true;
                    }
                    temp--;
                }

                if(arrayList.size() != numOfPages) {
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
                    if(didRemove) {
                        System.out.println("Element at " + 1 + " " + arrayList.get(1));
                        dynamicBitSettings.replace(Integer.parseInt(arrayList.get(1)), false);
                        for(int i = 1; i < arrayList.size(); i++) {
                            matrix[positions.get(i) + 1][column] = arrayList.get(i);
                        }
                    }

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
