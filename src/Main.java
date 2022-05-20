import algorithms.*;
import exceptions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws NonExistingElementException {
        int[] references = new int[]{1, 2, 3, 4, 2, 1, 5, 6, 2, 1, 2, 3, 7, 6, 3, 2, 1, 2, 3, 6};
        //int[] references = new int[]{2, 1, 2, 4};
        FIFO fifo = new FIFO(3, references);
        fifo.implementAlgorithm();

        System.out.println("========================");
        System.out.println("========================");
        System.out.println("========================");

        LRU lru = new LRU(3, references);
        lru.implementAlgorithm();

        System.out.println("========================");
        System.out.println("========================");
        System.out.println("========================");

        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(2);
        SecondChance secondChance = new SecondChance(3, references, arrayList);
        secondChance.implementAlgorithm();

        System.out.println("========================");
        System.out.println("========================");
        System.out.println("========================");

        Optimal optimal = new Optimal(3, references);
        optimal.implementAlgorithm();

        System.out.println("========================");
        System.out.println("========================");
        System.out.println("========================");

        LFU lfu = new LFU(3, references, 10, 1, 4);
        lfu.implementAlgorithm();

        System.out.println("========================");
        System.out.println("========================");
        System.out.println("========================");

        /*Scanner scan = new Scanner(System.in);


        try {
            System.out.println("Input num of pages: ");
            int numOfPages = scan.nextInt();
            System.out.println("Input number of references: ");
            int numOfReferences = scan.nextInt();
            if(numOfPages < 0 || numOfReferences < 0) {
                throw new NegativeNumberException();
            }
            int[] references = new int[numOfReferences];
            for(int i = 0; i < numOfReferences; i++) {
                System.out.println("Enter " + (i + 1) + ". reference: ");
                int reference = scan.nextInt();
                references[i] = reference;
            }
            System.out.println("Algorithms to work with: \n" +
                    "1-FIFO\n2-LRU\n3-Second Chance\n4-LFU\n5-Optimal algorithm");
            System.out.println("Enter --exit to exit application!");
            String userInput = "";
            do {
                userInput = scan.nextLine();
                if(userInput == "1") {
                    FIFO fifo = new FIFO(numOfPages, references);
                    fifo.implementAlgorithm();
                }
                else if(userInput == "2") {
                    LRU lru = new LRU(numOfPages, references);
                    lru.implementAlgorithm();
                }
                else if(userInput == "3") {
                    ArrayList<Integer> referencesList = new ArrayList<>();
                    for(int elem : references) {
                        referencesList.add(elem);
                    }
                    ArrayList<Integer> functionList = new ArrayList<>();
                    System.out.println("Enter which value(s) you want to have R bit: ");
                    System.out.println("close to exit input");
                    String extraInput;
                    do {
                        extraInput = scan.nextLine();
                        if(referencesList.contains(Integer.parseInt(extraInput))) {
                            functionList.add(Integer.parseInt(extraInput));
                        }
                        else {
                            throw new NonExistingElementException();
                        }
                    } while(extraInput != "--close");
                    SecondChance secondChance = new SecondChance(numOfPages, references,referencesList );
                }
                else if(userInput == "4") {
                    System.out.println("Input initial value: ");
                    int initialValue = scan.nextInt();
                    System.out.println("Input decrement value: ");
                    int decrementValue = scan.nextInt();
                    System.out.println("Input increment value: ");
                    int incrementValue = scan.nextInt();
                    LFU lfu = new LFU(numOfPages, references, initialValue, decrementValue, incrementValue);
                    lfu.implementAlgorithm();
                }
                else if(userInput == "5") {
                    Optimal optimal = new Optimal(numOfPages, references);
                    optimal.implementAlgorithm();
                }
                else if(userInput == "--exit") {
                    System.out.println("Exiting app!");
                }
                else {
                    System.out.println("Invalid option, try again!");
                }
            } while(!"--exit".equals(userInput));
        }
        catch(NegativeNumberException exc) {
            System.out.println(exc.getMessage());
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            scan.close();
        }*/
    }
}
