package cooperativefsm;

/**
 *
 * @author Alessandro Ferrari, Carlo Svanera, Luca Cominardi
 */
import java.util.Vector;
import java.lang.*;


public class Input {

    //Attributi private, metodi public, information hiding ;), per accederci
    //utilizza i get e i set

    //Ricordati il specificatore del tipo di collection per i vector,
    //tipo Vector<nome_classe>

    //Per i nomi degli attributi fai riferimento ai diagrammi uml che ho fatto

    //Questi attributi sono in realtà variabili di appoggio che servono ai metodi (astatti)
    private Vector<Stato> listaS;
    private Vector<Transizione> listaT;
    //La documentazione su come deve essere  relazioniTransizioni è presente nelle mail
    //Se non è ancora chiara contattami
    private Simulazione.Relazione relazioniTransizioni[][]; //Relazione è un tipo enum che definisce i tipi di relazione
    private Vector<Fsm> listaFSM;
    
    
    /*
     Metodo che inizializza una vera e propria sessione di simulazione
     
     */
    public Simulazione leggiSimulazione()
            {
             int numFsm=2;
             int i = 0;
             for(i=0; i<numFsm; i++)
             {
             Fsm fsm = creaFsm (Integer.toString(i));
             listaFSM.add(fsm);
             }
             
             //Lettura stato iniziale
             StatoCorrente statoIniziale = leggiStatoIniziale();
             
             //Lettura relazioni
             relazioniTransizioni = leggiRelazioni();

             //Controllo dei vincoli di correttezza della simulazione
             if(!simulazioneCorretta() )
             {
                   //codice per comunicare l'errore all'utente
             }

                 //L'interfaccia tra io e core è data da questa riga, è l'unico punto di incontro
                 return (new Simulazione(listaFSM, relazioniTransizioni, statoIniziale));
            }
    
    
    
    
    /*
     *Metodo che controlla la correttezza delle informazione relative alla simulazione
     * che sono state introdotte/lette
     *
     */
    public boolean simulazioneCorretta ()
        {
            boolean correct = true;
            
            //////
            
            return correct;
        }
    
    
    
    
    
    /**
    * Metodo che crea la lista degli stati di una fsm (che però non esiste ancora)
    * La lettura del numero di stati può essere effettuata da tastiera o da file xml
    *
    * @param Serve per identificare la fsm nel file xml, oppure è il nome assegnatole da tastiera
    */
     public void leggiStati (String nomeFsm)
        {
            listaS = new Vector();
            /*for (int i=0; i<numStati; i++)
            {
            Stato s = new Stato (i);
            macch1.addStato(s);
            }
            */

        }

     
     
     
     
     /**
    * Metodo che crea la lista delle transizioni di una fsm (che però non esiste ancora)
    * La lettura delle transizioni può essere effettuata da tastiera o da file xml
    *
    * @param Serve per identificare la fsm nel file xml, oppure è il nome assegnatole da tastiera
    */
     public void leggiTransizioni (String nomeFsm)
        {
            listaT = new Vector();
            boolean ciSonoTrans= true;
            
            while (ciSonoTrans) 
            {
                //Stato sorg,dest;
                //Transizione t = new Transizione(sorg,dest);
                //macch1.listaTrans.add(t);
            }
           
        }

     
     
     
     /*
      * Metodo che crea un'istanza di Fsm a partire dalla variabili globali che sono
      * già state inizializzate
      * @return la nuova istanza di Fsm
      */

     public Fsm creaFsm (String nome){
        Fsm macchina= new Fsm ( nome , listaS, listaT);
        
        return macchina;
     }

     



        //ottiene dal parser lo stato iniziale e imposta lo stato corrente
        //lo stato deve € al vettore macch1.listaStati --->controllo!
        public StatoCorrente leggiStatoIniziale()
            {
            StatoCorrente s = new StatoCorrente ();
            
            //s.sCorrenteFsm1 = ;
            //s.sCorrenteFsm2 = ;
            
            return s;
            }
        
        
        
        /**
         * Metodo che ricava le relazioni tra le transizioni delle 2 FSM
         * @return il vettore contenente le relazioni tra TUTTE le transizioni [...]
         */
        public  Simulazione.Relazione[][] leggiRelazioni (){
            int n = listaFSM.elementAt(0).getTransizioni().size();//N° transizioni prima fsm
            int m = listaFSM.elementAt(1).getTransizioni().size();//..seconda
            Simulazione.Relazione _relazioniTransizioni[][] = new Simulazione.Relazione[n+1][m+1];

            Transizione t1,t2;
            //RelazioneTransizioni r = new RelazioneTransizioni (t1,t2);
            //listaRelazioni.add(r);
            
            return relazioniTransizioni;
        }
        

        
}
