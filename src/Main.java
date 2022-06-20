import algorithms.*;
import exceptions.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*//int[] references = new int[]{8, 6, 4, 2, 3, 7, 4, 6, 3, 3, 5, 2, 3, 2, 5, 6, 1, 3, 7, 3};
        //int[] references = new int[]{1, 2, 3, 4, 2, 1, 5, 6, 2, 1, 2, 3, 7, 6, 3, 2, 1, 2, 3, 6};
        //int[] references = new int[]{6, 8, 1, 6, 5, 1, 2, 4, 7, 1, 2, 3, 4, 5, 2, 4, 3, 1, 5, 7};
        int[] references = new int[]{3, 1, 3, 5, 8, 7, 6, 2, 1, 6, 7, 8, 6, 3, 7, 3, 1, 2, 3, 6};
        //int[] references = new int[]{1, 1, 4, 4, 4, 7, 6, 2, 1, 6, 7, 8, 6, 3, 7, 3, 1, 2, 3, 6};
        //int[] references = new int[]{2, 4, 3, 1, 5, 7, 6, 8, 1, 6, 5, 6, 2, 4, 7, 1, 2, 3, 4, 5};
        //int[] references = new int[]{6, 5, 6, 2, 4, 7, 1, 2, 3, 4, 5, 2, 4, 3, 1, 5, 7, 6, 8, 1};
        //int[] references = new int[]{4, 5, 2, 4, 3, 1, 5, 7, 6, 8, 1, 2, 3, 6, 2, 4, 7, 1, 2, 3};
        //int[] references = new int[]{6, 8, 1, 6, 5, 1, 2, 4, 7, 1, 2, 3, 4, 5, 2, 4, 3, 1, 5, 7};
        //ne radi, ipak radi
        //int[] references = new int[]{2, 3, 6, 2, 4, 7, 1, 2, 3, 4, 5, 2, 4, 3, 1, 5, 7, 6, 8, 1};
        //int[] references = new int[]{2, 7, 3, 1, 1, 1, 3, 7, 6, 3, 4, 8, 4, 2, 8, 8, 8, 7, 4, 2};
        //int[] references = new int[]{7, 6, 8, 1, 2, 4, 5, 2, 4, 3, 1, 5, 3, 6, 2, 4, 7, 1, 2, 3};
        FIFO fifo = new FIFO(4, references);
        fifo.implementAlgorithm();

        System.out.println("========================");
        System.out.println("========================");
        System.out.println("========================");

        LRU lru = new LRU(4, references);
        lru.implementAlgorithm();

        System.out.println("========================");
        System.out.println("========================");
        System.out.println("========================");

        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        //arrayList.add(3);
        SecondChance secondChance = new SecondChance(3, references, arrayList);
        secondChance.implementAlgorithm();

        System.out.println("========================");
        System.out.println("========================");
        System.out.println("========================");

        Optimal optimal = new Optimal(4, references);
        optimal.implementAlgorithm();

        System.out.println("========================");
        System.out.println("========================");
        System.out.println("========================");

        LFU lfu = new LFU(3, references, 10, 1, 4);
        lfu.implementAlgorithm();

        System.out.println("========================");
        System.out.println("========================");
        System.out.println("========================");*/

        Scanner scan = new Scanner(System.in);


        try {
            System.out.println("Input num of pages: ");
            int numOfPages = scan.nextInt();
            System.out.println("Input number of references: ");
            int numOfReferences = scan.nextInt();
            if(numOfPages < 2 || numOfReferences < 0) {
                throw new InvalidNumberException();
            }
            int[] references = new int[numOfReferences];
            for(int i = 0; i < numOfReferences; i++) {
                System.out.println("Enter " + (i + 1) + ". reference: ");
                int reference = scan.nextInt();
                references[i] = reference;
            }
            int errorCounter = 0;
            String userInput;
            do {
                System.out.println("Algorithms to work with: \n" +
                        "1-FIFO\n2-LRU\n3-Second Chance\n4-LFU\n5-Optimal algorithm");
                System.out.println("Enter --exit to exit application!");
                userInput = scan.nextLine();
                if(userInput.equals("1")) {
                    FIFO fifo = new FIFO(numOfPages, references);
                    fifo.implementAlgorithm();
                }
                else if(userInput.equals("2")) {
                    LRU lru = new LRU(numOfPages, references);
                    lru.implementAlgorithm();
                }
                else if(userInput.equals("3")) {
                    ArrayList<Integer> referencesList = new ArrayList<>();
                    for(int elem : references) {
                        referencesList.add(elem);
                    }
                    ArrayList<Integer> functionList = new ArrayList<>();
                    System.out.println("Enter which value(s) you want to have R bit: ");
                    System.out.println("--close to exit input");
                    String extraInput;
                    do {
                        extraInput = scan.nextLine();
                        if(!extraInput.equals("--close")) {
                            if(referencesList.contains(Integer.parseInt(extraInput))) {
                                functionList.add(Integer.parseInt(extraInput));
                            }
                            else {
                                throw new NonExistingElementException();
                            }
                        }
                    } while(!extraInput.equals("--close"));

                    SecondChance secondChance = new SecondChance(numOfPages, references, functionList);
                    secondChance.implementAlgorithm();
                }
                else if(userInput.equals("4")) {
                    System.out.println("Input initial value: ");
                    int initialValue = scan.nextInt();
                    System.out.println("Input decrement value: ");
                    int decrementValue = scan.nextInt();
                    System.out.println("Input increment value: ");
                    int incrementValue = scan.nextInt();
                    LFU lfu = new LFU(numOfPages, references, initialValue, decrementValue, incrementValue);
                    lfu.implementAlgorithm();
                }
                else if(userInput.equals("5")) {
                    Optimal optimal = new Optimal(numOfPages, references);
                    optimal.implementAlgorithm();
                }
                else if(errorCounter == 0) {
                    errorCounter++;
                    //do nothing, just to avoid unnecessary printing!
                }
                else if(userInput.equals("--exit")) {
                    System.out.println("Exiting app!");
                }
                else {
                    System.out.println("Invalid option, try again!");
                }
            } while(!"--exit".equals(userInput));
        }
        catch(InvalidNumberException exc) {
            System.out.println(exc.getMessage());
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            scan.close();
        }
    }
}
