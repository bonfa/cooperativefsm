package cooperativefsm;

/**
 *
 * @author Alessandro Ferrari, Carlo Svanera, Luca Cominardi
 */
import java.util.*;


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

    private boolean numRelazioniSincroneUscentiIsSetted;
   
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
    
    public Simulazione (Vector<Fsm> _listaFsm, Relazione relazioni[][], StatoCorrente sc){
        listaFsm = _listaFsm;
        relazioniTransizioni = relazioni;
        statoCorrente = sc;
        transizioniAbilitate = new Vector<TransizioniAbilitate>();

        //Setto le transizioni uscenti
        ListIterator l_fsm = listaFsm.listIterator();
        Fsm fsm;

        while(l_fsm.hasNext()){
             fsm=(Fsm) l_fsm.next();
             fsm.setTransizioniUscentiStati();
        }

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

    //Non ho mai avuto il dono della sintesi, me lo dicono tutti
    public ReturnCodeIterazione setNumRelazioniSincroneTransizioniUscenti(){

        Vector<Transizione> trUsc1 = statoCorrente.getStatoCorrenteFSM1().getTransizioniUscenti();
        Vector<Transizione> trUsc2 = statoCorrente.getStatoCorrenteFSM2().getTransizioniUscenti();

        if(trUsc1 == null || trUsc2 == null){
            return ReturnCodeIterazione.OUTGOING_TRANSITION_NOT_SETTED;
        }

        ListIterator l_itr_tr1 = trUsc1.listIterator();
        ListIterator l_itr_tr2 = trUsc2.listIterator();

        Transizione t;

        //scorro tutte le transizioni della fsm1
        while(l_itr_tr1.hasNext()){
            t = (Transizione) l_itr_tr1.next();
            //trovo il numero di relazioni sincrone tra la transizione corrente
            //di fsm1 con le transizioni uscenti della fsm2
            t.setNumRelazioniSincroneStatoCorrente(relazioniTransizioni, trUsc2);
        }
        //scorro tutte le transizioni della fsm2
        while(l_itr_tr2.hasNext()){
            t = (Transizione) l_itr_tr2.next();
            //trovo il numero di relazioni sincrone tra la transizione corrente
            //di fsm2 con le transizioni uscenti della fsm1
            t.setNumRelazioniSincroneStatoCorrente(relazioniTransizioni, trUsc1);
        }

        numRelazioniSincroneUscentiIsSetted = true;

        return ReturnCodeIterazione.NO_ERROR;
    }

    /**
     * Imposta il vettore contenente le transizioni abilitate a seconda della stato della simulazione
     */
    private void setTransizioniAbilitate()
    {
        boolean isMutEx;

        Vector<Transizione> tr_usc1= statoCorrente.getStatoCorrenteFSM1().getTransizioniUscenti();
        Vector<Transizione> tr_usc2= statoCorrente.getStatoCorrenteFSM2().getTransizioniUscenti();

        ListIterator l_itr_tr_usc1 = tr_usc1.listIterator();
        ListIterator l_itr_tr_usc2 ;

        Transizione tr_1, tr_2, tr_sin_corrispondente;

        while(l_itr_tr_usc1.hasNext()){
            tr_1 = (Transizione) l_itr_tr_usc1.next();


            l_itr_tr_usc2 = tr_usc2.listIterator();

            //La transizione non è sincrona con nessuna transizione uscente
            //dallo stato corrente della fsm corrispondente, quindi può scattare
            //indipendentemente
            if(tr_1.getNumRelazioniSincroneStatoCorrente() == 0){
                transizioniAbilitate.add(new TransizioniAbilitate(tr_1,null));

                tr_2 = (Transizione) l_itr_tr_usc2.next();

                isMutEx = (relazioniTransizioni[tr_1.getId()][tr_2.getId()]==Simulazione.Relazione.M_EX);

                if((tr_2.getNumRelazioniSincroneStatoCorrente() == 0)&&(!isMutEx)){
                    transizioniAbilitate.add(new TransizioniAbilitate(tr_1,tr_2));
                }
            }

            //La transizione è sincrona con una sola transizione uscente dallo stato
            //corrente della fsm corrispondente
            if(tr_1.getNumRelazioniSincroneStatoCorrente() == 1){

                tr_sin_corrispondente = tr_1.getTransizioneSincronaCorrispondente();
                //Se anche la transizione con cui sono sincrono ha una sola transizione
                //sincrona nello stato corrente
                if(tr_sin_corrispondente.getNumRelazioniSincroneStatoCorrente()==1){
                    transizioniAbilitate.add(new TransizioniAbilitate(tr_1,tr_sin_corrispondente));
                }
            }

        }

        l_itr_tr_usc2 = tr_usc2.listIterator();

        while(l_itr_tr_usc2.hasNext()){
            tr_2 = (Transizione) l_itr_tr_usc2.next();
            if(tr_2.getNumRelazioniSincroneStatoCorrente()==0){
                transizioniAbilitate.add(new TransizioniAbilitate(null,tr_2));
            }
        }

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
        numRelazioniSincroneUscentiIsSetted=false;

        ReturnCodeIterazione risp = setNumRelazioniSincroneTransizioniUscenti();

        if(risp!=ReturnCodeIterazione.NO_ERROR){
            return risp;
        }

        setTransizioniAbilitate();

        return ReturnCodeIterazione.NO_ERROR;
    }

}
