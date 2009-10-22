package cooperativefsm;

/**
 * Classe che eredita  dalla classe padre Input, e ne sovrascrive alcuni metodi in modo  da
 * specializzarli per la lettura da tastiera
 *
 * @author Renato
 */

import java.util.Vector;

public class InputTast extends Input
{  
     private final String RICH_STATI = "Inserisci il numero di stati della fsm ";
     private final String RICH_TRANS = "Vuoi inserire una nuova transizione? ";
     private final String INS_STATO   = "Inserire il numero dello stato ";
     private final String INS_RELAZ = "Vuoi inserire una nuova relazione tra transizioni? ";
     private final String TIPO_RELAZ = "TIPO DI RELAZIONE TRA LE TRANSIZIONI";
     private final String [] SCELTA_RELAZ  = {"Relazione sincrona","Relazione mutuamente esclusiva"};

     private final int min_stati = 2;

//     private Simulazione.Relazione relazioniTransizioni[][]; //Relazione è un tipo enum che definisce i tipi di relazione
//     private Vector<Fsm> listaFsm;
//     private StatoCorrente statoIniziale;
     
     
     /**
      * Costruttore specializzato per l'input da tastiera.
      */
     public InputTast ()
     {
         listaFsm = new Vector<Fsm> ();
     }

     public @Override Simulazione leggiSimulazione()
    {
        this.inizializzaListaFsm();
         
        for (int i = 0; i < listaFsm.size(); i++)                       //si inizializza ogni istanza di fsm nella listaFsm
        {                                                               //con i suoi stati e le sue transizioni
            Fsm appoggio = listaFsm.elementAt(i);

            int numStati = this.leggiNumStati(appoggio.getId());        //inserimento degli stati
            this.inizializzaStati(appoggio, numStati);

            this.inizializzaTrans(appoggio);                            //inserimento delle transizioni
        }
        
        statoIniziale = leggiStatoIniziale(listaFsm);                   //Lettura stato iniziale
        
        relazioniTransizioni = initRelazioni();                         //Inizializza l'array bidimensionale di relazioni
        while (ciSonoRelaz())
             imposta(relazioniTransizioni, listaFsm);

        
        System.out.println("Simulazione caricata correttamente!");
        return (new Simulazione(listaFsm, relazioniTransizioni, statoIniziale));    //L'interfaccia tra io e core è data da questa riga, è l'unico punto di incontro
           
   }
     
     
    /**
     *  Definisce e istanzia un certo numero (2 in questo caso) di Fsm per la simulazione
     */

    public void inizializzaListaFsm()
    {
        int numFsm = 2;
        for(int i = 0; i < numFsm; i++)
             {
             Fsm fsm = creaFsm (Integer.toString(i));
             listaFsm.add(fsm);
             }
    }
    
    
     public int leggiNumStati (String nome_fsm)
     {
         return Servizio.leggiIntConMinimo(RICH_STATI + nome_fsm + ": ", min_stati);
     }

     
    /**
    * Metodo che crea la lista degli stati di una fsm
    * 
    * @param Fsm di appoggio
    * @param Numero di stati
    */

     public void inizializzaStati (Fsm x, int numStat)
     {
            for (int i = 0; i < numStat; i++)
            {
            Stato s = new Stato (i);
            x.addStato(s);
            }
     }


     /**
      *
      * @param a: Stringa per il messaggio da visualizzare
      * @param max: è di fatto il numero totale di stati di una fsm
      * @return un nuovo stato che sicuramente appartiene alla fsm
      */
     public Stato leggiStatoConMax (String a, int max)
     {
         int id = Servizio.leggiInt(INS_STATO + a + ": ", 0, max);
         Stato s = new Stato (id);
         return s;
     }

     
     /**
      * Metodo che crea la lista delle transizioni di una fsm
      * @param La Fsm a cui vanno aggiunte un certo numero di transizioni
    */
     public void inizializzaTrans (Fsm x)
     {
        boolean continua = this.ciSonoTrans();   //metodo che verrà sovrascritto dalle sottoclassi
        int k=0;
        while (continua)
        {
            Stato sorgente = this.leggiStatoConMax("sorgente", x.getNumStati()-1);
            Stato destinazione = this.leggiStatoConMax("destinazione", x.getNumStati()-1);

            Transizione t = new Transizione (k,sorgente,destinazione);
            x.addTrans(t);
            k++;
            System.out.println("Transizione inserita correttamente!");
            continua = this.ciSonoTrans();
        }//while
     }


     public boolean ciSonoTrans ()
     {
        return Servizio.yesOrNo(RICH_TRANS);
     }


     public boolean ciSonoRelaz()
     {
        return Servizio.yesOrNo(INS_RELAZ);
     }



     /**
      *
      * @param la lista contenente le fsm
      * @return lo stato iniziale della simulazione
      *
      */
     public @Override StatoCorrente leggiStatoIniziale (Vector<Fsm> lista)
     {
        StatoCorrente s = new StatoCorrente ();
       
        Stato c1 = leggiStatoConMax("corrente della fsm " + lista.get(0).getId(), lista.get(0).getNumStati() - 1);
        Stato c2 = leggiStatoConMax("corrente della fsm " + lista.get(1).getId(), lista.get(1).getNumStati() - 1);

        s.setStati( c1, c2 );
        
        return s;
     }

    

    /**
     * Metodo che crea un'istanza di Fsm a partire dalla variabili globali che sono
     * già state inizializzate
     * @param nome: nome della fsm
     * @return una nuova istanza di fsm
     */

     public Fsm creaFsm (String nome)
     {
        Fsm macchina= new Fsm ( nome );
        return macchina;
     }
     
     
     

/**
     * Metodo che ricava le relazioni tra le transizioni delle 2 FSM
     * @return il vettore contenente le relazioni tra TUTTE le transizioni [...]
     */
     public  Simulazione.Relazione[][] initRelazioni ()
     {
            int n = listaFsm.elementAt(0).getTransizioni().size();//N° transizioni prima fsm
            int m = listaFsm.elementAt(1).getTransizioni().size();//..seconda
            Simulazione.Relazione relazioniTransizioni[][] = (new Simulazione.Relazione [n+1][m+1]);
                
            for (int i=0; i<n; i++)
            {
                for(int j=0; j<m; j++)
                    relazioniTransizioni[i][j] = relazioniTransizioni[i][j].ASINCRONA; //di default le transizioni sono asincrone tra loro,
                                                                                       //solo quelle sincrone e m-ex saranno specificate
            }
            
            return relazioniTransizioni;
     }


      /**
      * imposta una relazione tra due transizioni € a due fsm diverse
      * @param relaz: l'array bidimensionale contenente tutte le possibili relazioni tra transizioni
      * @param list: la lista di fsm in questione
      */

     public boolean imposta(Simulazione.Relazione[][] relaz, Vector<Fsm> list)
     {
        int t1 = Servizio.leggiInt("Transizione di riferimento della fsm 1", 0, list.get(0).getNumTr()- 1);
        int t2 = Servizio.leggiInt("Transizione di riferimento della fsm 2", 0, list.get(1).getNumTr() - 1);
          
        MyMenu sceltaTipo = new MyMenu ( TIPO_RELAZ, SCELTA_RELAZ);
        int sel = sceltaTipo.scegli();

        switch (sel)
            {
                case 1: (relaz[t1][t2]) = (relaz[t1][t2]).SINCRONA;
                case 2: (relaz[t1][t2]) = (relaz[t1][t2]).M_EX;
                break;
            }

        System.out.println("Relazione aggiunta correttamente!");
        return true;
     }


}
