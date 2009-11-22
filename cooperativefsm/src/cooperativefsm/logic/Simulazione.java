
package cooperativefsm.logic;

/**
 *
 * @author Alessandro Ferrari, Carlo Svanera, Luca Cominardi
 */
import cooperativefsm.*;
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

    /*
     *
     * Il costruttore provvede a settare le fsm, le relazioni tra transizione e
     * lo stato corrente della simulazione.
     *
     * @param Vector<Fsm> _listaFsm
     * @param Relazione relazioni[][]
     * @param StatoCorrente sc
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
     * 
     * @return la lista delle fsm della simulazione
     *
     * @see  Fsm
     */

    public Vector<Fsm> getListaFsm()
    {
        return listaFsm;
    }

    /**
     *
     * @return lo StatoCorrente della simulazione
     *
     * @see StatoCorrente
     */

    public StatoCorrente getStatoCorrente() {
        return statoCorrente;
    }

    /**
     *
     * @return
     */

    public Vector<TransizioniAbilitate> getTransizioniAbilitate() {
        return transizioniAbilitate;
    }

    /**
     *
     * Questo metodo serve per settare l'attributo
     * numRelazioniSincroneStatoCorrente di Transizione che ad ogni
     * iterazione della simulazione indica per ognuna delle transizioni uscente
     * dallo stato corrente il numero di relazioni sincrone che  la transizione
     * ha con le transizioni uscenti dallo stato corrente della fsm
     * complementare. Ovviamente questo metodo deve essere richiamato ad ogni
     * iterazione, per ogni transizione uscente. Se eventualmente la transizione
     * ha una sola relazione sincrona con una transizione uscente, la funzione
     * setta l'attributo transizioneSincronaCorrispondente con il riferimento
     * della transizione sincrona corrispondente. In caso contrario, il valore
     * contenuto nell'attributo non è da considerarsi sensato.
     *
     * @param idFsm
     * @param tr
     * @param transUscFsmCorrispondente
     *
     * @see Fsm, Transizione, Relazione, Stato
     */

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

    /**
     * Si preoccupa di chiamare il metodo
     * setNumRelazioniSincroneStatoCorrentePerTransizione per ogni transizione
     * uscente dallo stato corrente.
     *
     * @return un codice che indica se l'iterazione si è svolta correttamente o meno
     */
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
     * Questo metodo si preoccuppa di settare la lista delle transizioni
     * abilitate allo scatto nell'iterazione corrente, seguendo l'algoritmo
     * descritto nel diagramma degli stati annesso alla documentazione.
     * Al termine dell'esecuzione di questo metodo avremo un Vector contenente
     * le istanze di TransizioniAbilitate, ognuna delle quali specifica lo stato
     * successivo della simulazione.
     *
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
     * @param TransizioniAbilitate t
     * @return Il nuovo Stato corrente della simulazione
     */
    public StatoCorrente scatta(TransizioniAbilitate t)
    {
        Stato s1,s2;
        
        if(t.getTransizioneFSM1() != null){
            s1=t.getTransizioneFSM1().getStato2();
        }else{
            s1=statoCorrente.getStatoCorrenteFSM1();
        }

        if(t.getTransizioneFSM2() != null){
            s2=t.getTransizioneFSM2().getStato2();
        }else{
            s2=statoCorrente.getStatoCorrenteFSM2();
        }



        statoCorrente.setStati(s1, s2);

        return statoCorrente;
    }

    /**
     *  Si occupa di effettuare i compiti che deve svolgere il sistema in uno
     *  stp.
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
