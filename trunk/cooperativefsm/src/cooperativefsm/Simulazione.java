
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
     * Il costruttore provvede a settare le fsm, le relazioni tra transizione
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

    public StatoCorrente getStatoCorrente() {
        return statoCorrente;
    }

    public Vector<TransizioniAbilitate> getTransizioniAbilitate() {
        return transizioniAbilitate;
    }

    private void setNumRelazioniSincroneStatoCorrentePerTransizione( int idFsm, Transizione tr, Vector<Transizione> transUscFsmCorrispondente){

        int count=0;
        Transizione tr_corr;
        ListIterator l_itr_tr = transUscFsmCorrispondente.listIterator();

        //Scorro la lista di transizioni uscenti per verificare quelle con cui ho una relazione sincrona
        while(l_itr_tr.hasNext()){
            tr_corr = (Transizione) l_itr_tr.next();
            if(idFsm==0){
                    if(relazioniTransizioni[tr.getId()][tr_corr.getId()] == Simulazione.Relazione.SINCRONA){
                    count++;
                    //Il valore sarà sensato solo se al termine count sarà uguale ad 1
                    tr.setTransizioneSincronaCorrispondente( tr_corr );
                }
            }else{
                if(relazioniTransizioni[tr_corr.getId()][tr.getId()] == Simulazione.Relazione.SINCRONA){
                    count++;
                    //Il valore sarà sensato solo se al termine count sarà uguale ad 1
                    tr.setTransizioneSincronaCorrispondente( tr_corr );
                }
            }
        }

       tr.setNumRelazioniSincroneStatoCorrente(count);
    }

    //Non ho mai avuto il dono della sintesi, me lo dicono tutti
    private ReturnCodeIterazione setNumRelazioniSincroneTransizioniUscenti(){

        Vector<Transizione> trUsc1 = statoCorrente.getStatoCorrenteFSM1().getTransizioniUscenti();
        Vector<Transizione> trUsc2 = statoCorrente.getStatoCorrenteFSM2().getTransizioniUscenti();

        int id1 = listaFsm.elementAt(0).getId();
        int id2 = listaFsm.elementAt(1).getId();

        if(trUsc1 == null || trUsc2 == null){
            return ReturnCodeIterazione.NUM_SYNC_REL_NOT_SETTED;
        }

        ListIterator l_itr_tr1 = trUsc1.listIterator();
        ListIterator l_itr_tr2 = trUsc2.listIterator();

        Transizione t;

        //scorro tutte le transizioni della fsm1
        while(l_itr_tr1.hasNext()){
            t = (Transizione) l_itr_tr1.next();
            //trovo il numero di relazioni sincrone tra la transizione corrente
            //di fsm1 con le transizioni uscenti della fsm2
            setNumRelazioniSincroneStatoCorrentePerTransizione(id1,t, trUsc2);
        }
        //scorro tutte le transizioni della fsm2
        while(l_itr_tr2.hasNext()){
            t = (Transizione) l_itr_tr2.next();
            //trovo il numero di relazioni sincrone tra la transizione corrente
            //di fsm2 con le transizioni uscenti della fsm1
            setNumRelazioniSincroneStatoCorrentePerTransizione(id2,t, trUsc1);
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

                while(l_itr_tr_usc2.hasNext()){
                    tr_2 = (Transizione) l_itr_tr_usc2.next();

                    isMutEx = (relazioniTransizioni[tr_1.getId()][tr_2.getId()]==Simulazione.Relazione.M_EX);

                    if((tr_2.getNumRelazioniSincroneStatoCorrente() == 0)&&(!isMutEx)){
                        transizioniAbilitate.add(new TransizioniAbilitate(tr_1,tr_2));
                    }
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
    public StatoCorrente scatta(TransizioniAbilitate t)
    {
        StatoCorrente prossimoStato = new StatoCorrente();

        prossimoStato.setStati(t.getTransizioneFSM1().getStato2(), t.getTransizioneFSM2().getStato2());

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

        transizioniAbilitate = new Vector<TransizioniAbilitate>();

        ReturnCodeIterazione risp = setNumRelazioniSincroneTransizioniUscenti();

        if(risp!=ReturnCodeIterazione.NO_ERROR){
            return risp;
        }

        setTransizioniAbilitate();

        return ReturnCodeIterazione.NO_ERROR;
    }

    public String ToString()
    {
        String s = "";
        for(int i=0; i<listaFsm.size(); i++)
        {
            s+=listaFsm.get(i).ToString();
        }
        s+="\nRelazioni tra transizioni:\n" +
                "Fsm1\tFsm2\tTipo\n";
        for(int i=0; i<relazioniTransizioni.length; i++)
            for(int j=0; j<relazioniTransizioni[0].length; j++)
                s+="id: " + i + "\tid: " + j + "\t" + relazioniTransizioni[i][j]+"\n";
        return s;
    }
}
