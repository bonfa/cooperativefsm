package cooperativefsm;

/**
 *
 * @author Renato
 */
import java.util.Vector;


public class Simulazione {

    //public Fsm fsm1;
    //public Fsm fsm2;
    public Vector listaFsm;
    
    public Vector listaRelazioni;           //lista statica che comprende TUTTE le possibili coppie di transizioni
    public Vector listaRelazioniAbilitate;  //lista dinamica che varia a seconda dello stato corrente
    public StatoCorrente sCorrente;
    
    
    
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
