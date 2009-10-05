package cooperativefsm;

/**
 *
 * @author Alessandro Ferrari, Carlo Svanera, Luca Cominardi
 */
import java.util.Vector;


public class Simulazione {

    public enum Relazione{
        SINCRONA,
        ASINCRONA,
        M_EX
    }

    private Vector<Fsm> listaFsm;
    private Vector<TransizioniAbilitate> transizioniAbilitate;  //lista dinamica che varia a seconda dello stato corrente
    private StatoCorrente statoCorrente;
    private Relazione relazioniTransizioni[][];
    
    /**
     *
     * That constructor provide to create a simulation by settings the params
     * passed from the IO classes.
     *
     * @param listaFSM
     * @param relazioni
     * @param sc
     *
     * @see Input,InputXML,InputTast
     */
    
    public Simulazione (Vector<Fsm> listaFSM, Relazione relazioni[][], StatoCorrente sc)
    {
        listaFsm = listaFSM;
        relazioniTransizioni = relazioni;
        statoCorrente = sc;
    }

    /**
     * 
     * This method fill the list of TransizioniAbilitate starting from
     *  a set of Relazioni that interest outgoing Transizioni from the current
     *  state.
     * 
     */

    private void setTransizioniAbilitate(){
        //TODO: vedi macchina a stati finiti uml
    }

    private StatoCorrente scatta(TransizioniAbilitate t){
        StatoCorrente prossimoStato = new StatoCorrente();

        //TODO

        return prossimoStato;
    }

    /**
     *This method perform the simultion step.
     * 
     */
    public boolean eseguiIterazione ()
    {
        //TODO

        return true;
    }

}
