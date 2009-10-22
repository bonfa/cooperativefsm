package cooperativefsm;

/**
 *
 * @author Alessandro Ferrari, Carlo Svanera, Luca Cominardi
 */
import java.util.Vector;

public abstract class  Input
{

    public Simulazione.Relazione relazioniTransizioni[][]; //Relazione è un tipo enum che definisce i tipi di relazione
    public Vector<Fsm> listaFsm = new Vector<Fsm>();
    public StatoCorrente statoIniziale = new StatoCorrente();

    /**
     * Metodo astratto che inizializza una vera e propria sessione di simulazione
     * La chiamata è comune sia per istanze di IputTast sia di InputXML,
     * mentre il metodo è sovrascritto nelle sottoclassi
     * @return
     */

    public abstract Simulazione leggiSimulazione();
    
    
       /**
      * Metodo che imposta lo stato iniziale e imposta lo stato corrente
      * 
        * @param list 
        * @return Lo stato iniziale della simulazione
      */
        
     public abstract StatoCorrente leggiStatoIniziale(Vector<Fsm> list);
     
     
     
    
        
}
