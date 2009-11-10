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
    public Relazione relazioniTransizioni[][];
   
    /**
     *
     * That constructor provide to create a simulation by settings the params
     * passed from the IO classes.
     *
     * @param _listaFsm
     * @param relazioni
     * @param sc
     *
     * @see Input,InputXML,InputTast
     */
    
    public Simulazione (Vector<Fsm> _listaFsm, Relazione relazioni[][], StatoCorrente sc)
    {
        listaFsm = _listaFsm;
        relazioniTransizioni = relazioni;
        statoCorrente = sc;
        transizioniAbilitate = new Vector<TransizioniAbilitate>();
    }

    /**
     * 
     * This method fill the list of TransizioniAbilitate starting from
     *  a set of Relazioni that interest outgoing Transizioni from the current
     *  state.
     * 
     * @return 
     */

    public Vector<Fsm> getListaFsm()
    {
        return listaFsm;
    }

    /**
     * Imposta il vettore contenente le transizioni abilitate a seconda della stato della simulazione
     */
    private void setTransizioniAbilitate()
    {
        //TODO: vedi macchina a stati finiti uml
    }

    
    /**
     * Esegue lo scatto della simulazione.
     * @param t
     * @return Il nuovo Stato corrente della simulazione
     */
    private StatoCorrente scatta(TransizioniAbilitate t)
    {
        StatoCorrente prossimoStato = new StatoCorrente();

        //TODO

        return prossimoStato;
    }

    /**
     *This method perform the simultion step.
     * 
     * @return un boolean che rappresenta la volontà di proseguire nella simulazione
     */
    public ReturnCodeIterazione eseguiIterazione ()
    {
        return ReturnCodeIterazione.NO_ERROR;
    }

}
