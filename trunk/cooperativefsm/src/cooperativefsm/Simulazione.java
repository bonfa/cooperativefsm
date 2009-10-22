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

    private void setTransizioniAbilitate(){
        //TODO: vedi macchina a stati finiti uml
    }

    private StatoCorrente scatta(TransizioniAbilitate t){
        StatoCorrente prossimoStato = new StatoCorrente();

        //TODO

        return prossimoStato;
    }

    /**
     *This method perform the simultion step.
     * 
     * @return
     */
    public boolean eseguiIterazione ()
    {
        //TODO

        return true;
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
