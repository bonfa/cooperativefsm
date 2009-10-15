package cooperativefsm;

/**
 *
 * @author Alessandro Ferrari, Carlo Svanera, Luca Cominardi
 */
import java.util.Vector;

public abstract class  Input
{

    /**
     * Metodo astratto che inizializza una vera e propria sessione di simulazione
     * La chiamata è comune sia per istanze di IputTast sia di InputXML,
     * mentre il metodo è sovrascritto nelle sottoclassi
     */

    public abstract Simulazione leggiSimulazione();
    
    
       /**
      * Metodo che imposta lo stato iniziale e imposta lo stato corrente
      * 
      * @return Lo stato iniziale della simulazione
      */
        
     public abstract StatoCorrente leggiStatoIniziale(Vector<Fsm> list);
     
     
     
    
        
}
