package cooperativefsm;

/**
 *
 * @author Alessandro Ferrari, Carlo Svanera, Luca Cominardi
 */
import java.util.Vector;
import java.lang.*;


public abstract class  Input {



    //Per i nomi degli attributi fai riferimento ai diagrammi uml che ho fatto

    //Questi attributi sono in realtà variabili di appoggio che servono ai metodi (astatti) ... SERVONO????
    //private Vector<Stato>       listaS;
    //private Vector<Transizione> listaT;
    
    //La documentazione su come deve essere  relazioniTransizioni è presente nelle mail
    //Se non è ancora chiara contattami
    
    private Simulazione.Relazione relazioniTransizioni[][]; //Relazione è un tipo enum che definisce i tipi di relazione
    private Vector<Fsm> listaFsm;
    
    
    
    /**
     * Metodo che inizializza una vera e propria sessione di simulazione
     * La chiamata è comune sia per istanze di IputTast sia di InputXML,
     * mentre i metodi che devono specializzarsi sono sovrascritti nelle sottoclassi
     */

    public Simulazione leggiSimulazione()
    {
         this.inizializzaListaFsm();

         
         for (int i = 0; i < listaFsm.size(); i++)      //si inizializza ogni istanza di fsm nella listaFsm 
         {                                              //con i suoi stati e le sue transizioni
            Fsm appoggio = listaFsm.elementAt(i);

            int numStati = this.leggiNumStati();        //inserimento degli stati
            this.inizializzaStati(appoggio, numStati);

            this.inizializzaTrans(appoggio);            //inserimento delle transizioni
         }

         StatoCorrente statoIniziale = leggiStatoIniziale(listaFsm);    //Lettura stato iniziale
         relazioniTransizioni = leggiNumRelazioni();                    //Lettura relazioni
         
         while (ciSonoRelaz())
             imposta(relazioniTransizioni);
                                  
                     if(!simulazioneCorretta() )        //Controllo dei vincoli di correttezza della simulazione
                     {
                           System.out.println("I dati inseriti non sono validi!");;
                     }

         
         return (new Simulazione(listaFsm, relazioniTransizioni, statoIniziale));       //L'interfaccia tra io e core è data da questa riga, è l'unico punto di incontro
   }
    
    
    
    //questi metodi in teoria saranno mai invocati perchè non sono mai create istanze della clase Input;
    //saranno le loro versioni sovrascritte nelle varie sottoclassi a funzionare
    
    public abstract int leggiNumStati();
    
    public abstract boolean ciSonoTrans();

    public abstract Stato leggiStato(String a);

       /**
      * Metodo che imposta lo stato iniziale e imposta lo stato corrente
      *  lo stato deve € al vettore macch1.listaStati --->controllo!
      * @return
      */
        
     public abstract StatoCorrente leggiStatoIniziale(Vector<Fsm> list);
     
     public abstract void imposta(Simulazione.Relazione[][] relaz);
     
     public abstract boolean ciSonoRelaz();
             
        
    //fine metodi abstract
    


    public Vector<Fsm> getListaFsm ()
    {
        return listaFsm;
    }




    /**
     * Metodo indipendente dal tipo di input, verrà chiamato da entrambe le sottoclassi.
     * Definisce e istanzia un certo numero (2 in questo caso) di Fsm per la simulazione
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
    


    /**
    * Metodo che crea la lista degli stati di una fsm
    * 
    * @param Serve per identificare la fsm nel file xml, oppure è il nome assegnatole da tastiera
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
      * Metodo che crea la lista delle transizioni di una fsm
      * 
      * @param La Fsm a cui va aggiunta una transizione
      * @param Stato sorgente della transizione
      * @param Stato destinazione della transizione
    */
     public void inizializzaTrans (Fsm x)
     {
         
           
         boolean continua = this.ciSonoTrans();   //metodo che verrà sovrascritto dalle sottoclassi

            while (continua)
            {
                Stato sorgente = this.leggiStato("sorgente");
                Stato destinazione = this.leggiStato("destinazione");
                //Controllo per verificare che gli stati € alla fsm [...]       <--------------TODO------------------!!!!!
                
                Transizione t = new Transizione (sorgente,destinazione);
                x.addTrans(t);

                continua = this.ciSonoTrans();
            }//while
            

     }

     
     
     
     /*
      * Metodo che crea un'istanza di Fsm a partire dalla variabili globali che sono
      * già state inizializzate
      * @return la nuova istanza di Fsm
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
     public  Simulazione.Relazione[][] leggiNumRelazioni ()
     {
            int n = listaFsm.elementAt(0).getTransizioni().size();//N° transizioni prima fsm
            int m = listaFsm.elementAt(1).getTransizioni().size();//..seconda
            Simulazione.Relazione relazioniTransizioni[][] = (new Simulazione.Relazione [n+1][m+1]);
                
            for (int i=0; i<n+1; i++)
            {
                for(int j=0; j<m+1; j++)
                    relazioniTransizioni[i][j] = relazioniTransizioni[i][j].ASINCRONA; //di default le transizioni sono asincrone tra loro,
                                                                                       //solo quelle sincrone e m-ex saranno specificate
            }
            
            return relazioniTransizioni;
     }
       
        
        
        
    /**
     * Metodo che controlla la correttezza delle informazione relative alla simulazione
     * che sono state introdotte/lette
     *
     */
    public boolean simulazioneCorretta ()
        {
            boolean correct = true;
            
            //TODO
            
            return correct;
        }
    
        
}
