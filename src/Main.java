import algorithms.FIFO;
import exceptions.NegativeNumberException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);


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

                }
                else if(userInput == "3") {

                }
                else if(userInput == "4") {

                }
                else if(userInput == "5") {

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
        }

    }
}
