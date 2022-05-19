package algorithms;

public class FIFO extends  Algorithm{

    public FIFO() {
        super();
    }

    public FIFO(int numOfPages, int[] references) {
        this.numOfPages = numOfPages;
        this.references = references;
        this.numOfReferences = references.length;
        int matrixRows = numOfPages + 2;
        int matrixColumns = numOfReferences;
        newBoundary = numOfPages + 2;
        this.matrix = new String[matrixRows][numOfReferences];
        for(int i = 0; i < numOfReferences; i++) {
            matrix[0][i] = String.valueOf(references[i]);
        }
    }

    @Override
    public void implementAlgorithm() {
        initFirstRow();
        for(int i = 0; i < numOfReferences; i++) {
            if(isPageFault(i, matrix[0][i])) {
                matrix[1][i] = "PF";

                if(isColumnFull(i)) {
                    processFullColumn(i, references[i]);
                }
                else {
                    processNonFullColumn(i, references[i]);
                }
            }
        }

        int pageFaults = countNumOfPageFaults();
        double percentage = countPfPercentage();
        System.out.println("Algorithm efficiency: PF = " + pageFaults);
        System.out.println(" => pf = " + pageFaults + " / " + numOfReferences + " = " + percentage + "%");
    }
}
