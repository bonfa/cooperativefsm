package cooperativefsm.io;

import cooperativefsm.logic.*;
import java.util.Vector;
/**
 * Classe specializzatat per la lettura si una simulazione da tastiera.
 * Eredita  dalla classe Input, e ne sovrascrive in particolarei metodi
 * astratti "leggiSimulazione()" e "leggiStatoIniziale()"
 * @author Carlo
 */

public class InputTast extends Input
{  
     private final String RICH_STATI = "Inserisci il numero di stati della fsm ";
     private final String RICH_TRANS = "Vuoi inserire una nuova transizione? ";
     private final String INS_STATO   = "Inserire il numero dello stato ";
     private final String INS_RELAZ = "Vuoi inserire una nuova relazione tra transizioni? ";
     private final String TIPO_RELAZ = "TIPO DI RELAZIONE TRA LE TRANSIZIONI";
     private final String INS_NOMETR   = "Inserire il nome della transizione: ";
     private final String INS_ST_CORR   = "Inserire lo stato corrente della fsm ";
     private final String INS_ID_TR = "Inserisci il numero di riferimento della transizione della fsm ";

     private final String [] SCELTA_RELAZ  = {"Relazione sincrona","Relazione mutuamente esclusiva"};
     private final int min_stati = 2;


     /**
      * Costruttore specializzato per l'input da tastiera.
      */
     public InputTast ()
     {
         listaFsm = new Vector<Fsm> ();
     }
     /**
      * Metodo fonadementale per effettuare l'input di una simulazione da tastiera
      * @return Un'istanza corretta di Simulazione
      */
     public @Override Simulazione leggiSimulazione()
    {
        this.inizializzaListaFsm();
         
        for (int i = 0; i < listaFsm.size(); i++)                       //si inizializza ogni istanza di fsm nella listaFsm
        {                                                               //con i suoi stati e le sue transizioni
            Fsm appoggio = listaFsm.elementAt(i);
            System.out.println("\n---------------------------------------");
            int numStati = this.leggiNumStati(appoggio.getNome());        //inserimento degli stati
            this.inizializzaStati(appoggio, numStati);

            this.inizializzaTrans(appoggio);                            //inserimento delle transizioni
        }
        
        statoIniziale = leggiStatoIniziale(listaFsm);                   //Lettura stato iniziale

        if(listaFsm.get(0).getNumTr() != 0  &&      //se entrambe le fsm hanno almeno una transizione si inizializza il vettore
           listaFsm.get(1).getNumTr() != 0)
        {
            relazioniTransizioni = initRelazioni();                         //Inizializza l'array bidimensionale di relazioni
            while (ciSonoRelaz())
                 imposta(relazioniTransizioni, listaFsm);
        }
        else
            System.out.println("Non puoi inserire relazioni tra transizioni perchè una della due FSM non ha transizioni.");

        System.out.println("Simulazione caricata correttamente!");
        return (new Simulazione(listaFsm, relazioniTransizioni, statoIniziale));    //L'interfaccia tra io e core è data da questa riga, è l'unico punto di incontro
   }
     
     
    /**
     *  Definisce e istanzia un certo numero (2 in questo caso) di Fsm per la simulazione
     */

    private void inizializzaListaFsm()
    {
        int numFsm = 2;
        for(int i = 0; i < numFsm; i++)
             listaFsm.add(creaFsm(i));
    }
    
    /**
     * Metodo che richiede il numero di stati di una FSM
     * @param nome_fsm
     * @return Il numero di stati da creare nella fsm
     */
     private int leggiNumStati (String nome_fsm)
     {
         return Servizio.leggiIntConMinimo(RICH_STATI + nome_fsm + ": ", min_stati);
     }

     
    /**
    * Metodo che crea la lista degli stati di una fsm
    * 
    * @param Fsm di appoggio
    * @param Numero di stati
    */
     private void inizializzaStati (Fsm x, int numStat)
     {
            for (int i = 0; i < numStat; i++)
            {
            Stato s = new Stato (i);
            x.addStato(s);
            }
     }


     
     /**
      * Metodo che restituisce lo stato di una fsm corrispondente all'id numerico inserito
      * Se l'id è coerente, lo stato esiste sicuramente, perchè gli id degli stati sono incrementali
      * @ensures id < max;
      * @param a: Stringa per il messaggio da visualizzare
      * @param x: La Fsm da cui estrarre lo stato
      * @return
      */
     private Stato leggiStatoConMax (String a, Fsm x)
     {
         int max = x.getNumStati()-1;
         int id = Servizio.leggiInt(INS_STATO + a , 0, max);
         return x.getStatoAt(id);
     }

     
     /**
      * Metodo che crea la lista delle transizioni di una fsm
      * @param La Fsm a cui vanno aggiunte un certo numero di transizioni
    */
     private void inizializzaTrans (Fsm x)
     {
        boolean continua = this.ciSonoTrans();   
        int k = 0; //rappresenta l'id della transizione, che corrisponde al numero progressivo con cui è inserita
        while (continua)
        {
            Stato sorgente = this.leggiStatoConMax("sorgente", x );
            Stato destinazione = this.leggiStatoConMax("destinazione", x );
            Transizione newTr = new Transizione (k,sorgente,destinazione);

            //controllo che non ci sia già una transizione tra questi stati!
            boolean giaIns = false;
            Vector<Transizione> trIns = x.getTransizioni();  //vettore con le transizioni attualmente inserite
            int numTrIns = trIns.size();        //numero delle transizioni già inserite

            for (int i=0; i<numTrIns; i++)
                if (trIns.get(i).equals(newTr))
                    giaIns = true;
  
            if(!giaIns)
            {
                newTr.setNome(Servizio.leggiStringaNonVuota(INS_NOMETR));
                x.addTrans(newTr);
                k++;    //incrementa l'indice per la prossima transizione che si potrà inserire
                    System.out.println("Transizione inserita correttamente!");
            }
            else
                System.out.println("Purtroppo è già presente una transizione tra questi due stati. " +
                        "\nTransizione non inserita!\n");

            continua = this.ciSonoTrans();
        }//while
     }

     /**
      * Chiede all'utente se vuole inserire ulteriori transizioni
      * @return Una variabile boolean che indica se si vogliono aggiungere altre transizioni
      */
     private boolean ciSonoTrans ()
     {
        return Servizio.yesOrNo(RICH_TRANS);
     }

     /**
      * Chiede all'utente se vuole inserire ulteriori relazioni tra transizioni
      * @return Una variabile boolean che indica se si vogliono aggiungere altre relazioni tra transizioni
      */
     private boolean ciSonoRelaz()
     {
        return Servizio.yesOrNo(INS_RELAZ);
     }



     /**
      * Legge da tastirera lo stato iniziale della simulazione
      * @ensures (\exist s1; 0 < ind1 < lista.get(0).getnumStati() -1; lista.getStatoAt(ind1) != null)
      * @param la lista contenente le fsm
      * @return lo stato iniziale della simulazione
      *
      */
     public @Override StatoCorrente leggiStatoIniziale (Vector<Fsm> lista)
     {
        StatoCorrente s = new StatoCorrente ();
 
        int ind1 = Servizio.leggiInt("\n" +INS_ST_CORR + lista.get(0).getId(), 0, lista.get(0).getNumStati() - 1);
        int ind2 = Servizio.leggiInt("\n" +INS_ST_CORR + lista.get(1).getId(), 0, lista.get(1).getNumStati() - 1);
        Stato s1 = lista.get(0).getStatoAt(ind1);
        Stato s2 = lista.get(1).getStatoAt(ind2);
        s.setStati( s1, s2 );
        
        return s;
     }


/**
 * Metodo che crea un'istanza di Fsm
 * @ensures macchina != null
 * @param id Identificatore numerico della Fsm
 * @return la nuova istanza di Fsm
 */
     private Fsm creaFsm (int id)
     {
        Fsm macchina = new Fsm (id);
        return macchina;
     }
     
     
     

    /**
     * Metodo che inizializza le relazioni tra tutte le transizioni delle 2 FSM come relazioni asincrone
     * @return il vettore contenente le relazioni tra TUTTE le transizioni
     */
     private  Simulazione.Relazione[][] initRelazioni ()
     {
            int n = listaFsm.elementAt(0).getNumTr();//N° transizioni prima fsm
            int m = listaFsm.elementAt(1).getNumTr();//..seconda
            relazioniTransizioni = new Simulazione.Relazione[n][m];
                
            for (int i=0; i < n; i++)
            {
                for(int j = 0; j < m; j++)
                    relazioniTransizioni[i][j] = relazioniTransizioni[i][j].ASINCRONA; //di default le transizioni sono asincrone tra loro,
                                                                                       //solo quelle sincrone e m-ex saranno specificate
            }
            
            return relazioniTransizioni;
     }


      /**
      * Imposta una relazione tra due transizioni appartenenti a due fsm diverse
      * @param relaz: l'array bidimensionale contenente tutte le possibili relazioni tra transizioni
      * @param list: la lista di fsm in questione
      */

     private void imposta(Simulazione.Relazione[][] relaz, Vector<Fsm> list)
     {  
        stampaTr(list.get(0));
        System.out.println("\n" + INS_ID_TR + "0: ");
        int t1 = Servizio.leggiInt(  "" , 0, list.get(0).getNumTr()- 1);

        stampaTr(list.get(1));
        System.out.println("\n" + INS_ID_TR + "1: ");
        
        int t2 = Servizio.leggiInt( "", 0, list.get(1).getNumTr() - 1);
          
        //ogni transizione deve avere una e una sola relazione con ognuna delle transizioni dell'altra fsm
        if(relazioniTransizioni[t1][t2] == Simulazione.Relazione.ASINCRONA) //cioè non è ancora presente una relazione tra le due transizioni in questione
        {
            MyMenu sceltaTipo = new MyMenu ( TIPO_RELAZ, SCELTA_RELAZ);
            int sel = sceltaTipo.scegli();

            switch (sel)
                {
                    case 1: (relaz[t1][t2]) = (relaz[t1][t2]).SINCRONA; break;
                    case 2: (relaz[t1][t2]) = (relaz[t1][t2]).M_EX; break;
                }

            System.out.println("Relazione aggiunta correttamente!");
        }
        else
        System.out.println("E'già presente una relazione tra queste transizioni.\nNon verrà fatta nessuna operazione.\n");
     }

     /**
      * Stampa a video tutte le transizioni di una Fsm
      * @param x Macchina a stati finiti
      */
    private void stampaTr (Fsm x)
    {
        System.out.println("\n---------------------------------------");
        System.out.println("\nLista delle Transizioni della FSM " + x.getId());
        for (int i = 0; i < x.getNumTr(); i++)
            System.out.println(x.getTransizioneAt(i).ToString());
    }
}
