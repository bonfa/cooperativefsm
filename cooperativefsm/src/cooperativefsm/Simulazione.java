package cooperativefsm;

/**
 *
 * @author Renato
 */
import java.util.Vector;


public class Simulazione {

    public enum Relazione{
        SINCRONA,
        ASINCRONA,
        M_EX
    }

    //public Fsm fsm1;
    //public Fsm fsm2;
    private Vector listaFsm;
    
    private Vector listaRelazioni;           //lista statica che comprende TUTTE le possibili coppie di transizioni
    private Vector listaRelazioniAbilitate;  //lista dinamica che varia a seconda dello stato corrente
    private StatoCorrente sCorrente;
    private Relazione relazioniTransizioni[][];
    
    
    
    public Simulazione (Vector listaM, Vector listaRel, StatoCorrente sc)
        {
        listaFsm = listaM;
        listaRelazioni = listaRel;
        sCorrente = sc;
        }


    /**
     *
     * @return
     */
    public boolean eseguiIterazione ()
        {
        boolean fineProg = false;

        ///////
        
        return fineProg;
        }

}
