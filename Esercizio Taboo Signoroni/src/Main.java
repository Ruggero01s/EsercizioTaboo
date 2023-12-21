import java.util.*;


public class Main {

    //parametri
    public static final int MAX_ITERATIONS = 300;
    public static final int LOCAL_MAX_ITERATIONS = 30;
    public static final int TABOO_TENURE = 4;


    //dati del problema ordinati in deadline crescente (il più stringente prima)
    public static final List<Integer> ID = new ArrayList<>(Arrays.asList(4, 1, 2, 3, 5, 6));
    public static final List<Integer> W = new ArrayList<>(Arrays.asList(1, 1, 1, 1, 1, 1));
    public static final List<Integer> P = new ArrayList<>(Arrays.asList(2, 6, 4, 8, 10, 3));
    public static final List<Integer> D = new ArrayList<>(Arrays.asList(8, 9, 12, 15, 20, 22));

    public static List<Job> globalBestOrder = new ArrayList<>();
    public static int globalBestOut;

    public static void main(String[] args) {
        ArrayList<Job> jobs = new ArrayList<>();

        for(int i=0; i<W.size(); i++)       //inizializzazione lista dei jobs
            jobs.add(new Job( ID.get(i), W.get(i),P.get(i),D.get(i),0));

        globalBestOrder = new ArrayList<>(jobs);        //inizializzazione ottimo globale
        globalBestOut = computeOptimumValue(globalBestOrder);     //inizializzazione valore ottimo globale
        List<Job> localBestOrder = new ArrayList<>(jobs);       //inizializzazione ottimo locale
        int localBestOut = computeOptimumValue(localBestOrder);            //inizializzazione valore ottimo locale
        List<Job> currentOrder = new ArrayList<>(jobs);         //inizializzazione soluzione corrente
        boolean firstOfBatch = false;
        int i;
        int c=0;
        for (i = 0; i < MAX_ITERATIONS; i++) {
            if (firstOfBatch){
                c=0;
                Collections.shuffle(currentOrder);      //diversificazione
                firstOfBatch=false;
            }
            bubbleDown(currentOrder);       // mossa
            int out = computeOptimumValue(currentOrder);
            if (out < localBestOut) {       // confronta la soluzione ottenuta con il local e global best e li aggiorna se necessario
                localBestOut = out;
                if (out < globalBestOut)
                {
                    globalBestOut = out;
                    globalBestOrder = new ArrayList<>(currentOrder);
                }
            }
            updateTaboo(jobs); //abbassa la durata dei taboo di 1
            /*if (i%10==0) //feedback printout
                printOut(currentOrder, computeOptimumValue(currentOrder), i); */
            if(c == LOCAL_MAX_ITERATIONS /4 && localBestOut>globalBestOut) // se entro tot iterazioni non si trova un valore
                firstOfBatch=true;                                         // migliore dell'ottimo globale si diversifica con shuffle
            if(c == LOCAL_MAX_ITERATIONS)                            //ogni tot diversifica comunque per esplorare di più le soluzioni
                firstOfBatch=true;
            c++;
        }
        printGlobalOut(i);  // print finale
    }

    /**
     * Calcola il valore della funzione obiettivo data una sequenza di jobs
     * @param jobs la sequenza di cui calcolare il valore obiettivo
     * @return
     */
    public static int computeOptimumValue(List<Job> jobs) {
        int C = 0;
        int out = 0;
        for (Job j : jobs) {
            C += j.getP();
            int value = C - j.getD();
            if (value > 0) {
                out += j.getW() * value;
            }
        }
        return out;
    }

    public static void printOut(List<Job> jobs, int out, int i) {
        String sequence="";
        for (Job j : jobs) {
            sequence += j.getId() + " ";
        }
        System.out.print("\nSolution explored at " + (i) + " iterations: " + sequence + "Value: " + out);
    }

    public static void printGlobalOut(int i) {
        String sequence="";
        for (Job j : globalBestOrder) {
            sequence += j.getId() + " ";
        }
        System.out.print("\nBest solution at " + (i) + " iterations: " + sequence + "Value: " + globalBestOut);
    }

    /**
     * data una lista porta indietro di un posto il job finito più in anticipo
     * @param jobs la sequenza data
     */
    public static void bubbleDown(List<Job> jobs)
    {
        int indexOfBest = jobs.indexOf(findFastest(jobs));
        int newId=0;
        for (int i=1; i<jobs.size()-indexOfBest; i++) { //cerca la prima posizione libera da taboo in cui spostare indietro il più veloce
            Job jobToSwap = jobs.get(indexOfBest + i);
            if (jobToSwap.getTaboo() <= 0) {
                newId = indexOfBest + i;
                Collections.swap(jobs, indexOfBest, newId);
                break;
            }
        }
        jobs.get(newId).setTaboo(TABOO_TENURE);   //quello che è stato portato indietro non può essere portato indietro di nuovo per n mosse
    }

    /**
     * trova il job che è più in anticipo rispetto all sua deadline
     * @param jobs
     * @return
     */
    public static Job findFastest(List<Job> jobs) {
        int c = 0;
        int bestValue = 0;
        Job best = null;
        for (Job j : jobs) {
            c += j.getP();
            int calc = c - j.getD();
            if (calc < bestValue) {
                bestValue = j.getW() * calc;
                best = j;
            }
        }
        return best;
    }

    /**
     * aggiorna tutti i taboo a taboo-1
     * @param jobs
     */
    public static void updateTaboo(List<Job> jobs) {
        for (Job j : jobs) {
            j.setTaboo(j.getTaboo()-1);
        }
    }
}