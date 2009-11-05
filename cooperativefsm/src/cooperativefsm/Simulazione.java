package cooperativefsm;

/**
 *
 * @author Alessandro Ferrari, Carlo Svanera, Luca Cominardi
 */
import java.util.Vector;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    private final static String NO_TR_DISP = "Non ci sono transizioni abilitate a scattare. Fine della simulazione";
    private final static String UNICA_TR = "Questa è l'unica transizione/coppia di transizioni abilitata a scattare";
    private final static String PIU_TR_DISP = "Ci sono più transizioni/coppie di transizioni abilitate a scattare";
    private final static String SCELTA_TR = "Scegli la transizione/coppia di transizioni che deve scattare";
    private final static String PROSEGUI = "Vuoi proseguire con la simulazione?";

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
        transizioniAbilitate = new Vector<TransizioniAbilitate>();
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

    /**
     * Imposta il vettore contenente le transizioni abilitate a seconda della stato della simulazione
     */
    private void setTransizioniAbilitate()
    {
        //TODO: vedi macchina a stati finiti uml
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
    public boolean eseguiIterazione ()
    {
        //TODO
        setTransizioniAbilitate();
        
        if(transizioniAbilitate.isEmpty())
        {
            System.out.println(NO_TR_DISP);
            return false;
        }
        
        TransizioniAbilitate t = new TransizioniAbilitate();
        
        if(transizioniAbilitate.size() == 1)
        {
            System.out.println(UNICA_TR);
            t = transizioniAbilitate.get(0);
            System.out.println(t.toString());
        }
        
        if(transizioniAbilitate.size() > 1)
        {
            System.out.println(PIU_TR_DISP);
            int numTrAbil  = transizioniAbilitate.size();
            String [] scelte = new String [numTrAbil];

            for (int i=0; i<numTrAbil; i++)
                scelte [i] = transizioniAbilitate.get(i).toString();    //TODO

            MyMenu sceltaTr = new MyMenu(SCELTA_TR, scelte);
            int scelta = sceltaTr.scegli();

            t = transizioniAbilitate.get(scelta - 1);
        }
        
        statoCorrente = scatta(t);

        return Servizio.yesOrNo(PROSEGUI);
        }
/**
 *
 * @return
 */
    public void salvaSimulazione(String file)
    {
        try {
                scriviSimulazione(file);
        } catch (IOException ex) {
            Logger.getLogger(Simulazione.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void scriviSimulazione(String file) throws IOException
    {
            FileWriter outFile = new FileWriter(file);
            PrintWriter out = new PrintWriter(outFile);
            out.println("<?xml version=\"1.0\"?>");
            out.println("<simulation>");
            for(int i=0; i<listaFsm.size(); i++)
            {
                out.println("\t<fsm>");
                Fsm fsm=listaFsm.get(i);
                out.println("\t\t<name>"+fsm.getId()+"</name>");
                out.println("\t\t<states>"+fsm.getNumStati()+"</states>");                
                Vector<Transizione> tr = fsm.getTransizioni();
                for(int j=0; j<tr.size(); j++)
                {
                    Transizione t=tr.get(j);
                    out.println("\t\t<transition>");
                    out.println("\t\t\t<id>"+t.getNome()+"</id>");
                    out.println("\t\t\t<s1>"+t.getStato1().getId()+"</s1>");
                    out.println("\t\t\t<s2>"+t.getStato2().getId()+"</s2>");
                    out.println("\t\t</transition>");
                }
                out.println("\t\t<current>"+statoCorrente.getStato(i).getId()+"</current>");
                out.print("\t</fsm>\n");
            }
            //out.println("\n");
            for(int x=0; x<listaFsm.get(0).getNumTr(); x++)
            {
                for(int y=0; y<listaFsm.get(1).getNumTr(); y++)
                {
                    if(relazioniTransizioni[x][y]!=Relazione.ASINCRONA)
                    {
                        out.println("\t<relation>");
                        out.println("\t\t<transval>");
                        out.println("\t\t\t<fsmval>"+listaFsm.get(0).getId()+"</fsmval>");
                        out.println("\t\t\t<idval>"+getTrNameById(x, 0)+"</idval>");
                        out.println("\t\t</transval>");
                        out.println("\t\t<transval>");
                        out.println("\t\t\t<fsmval>"+listaFsm.get(1).getId()+"</fsmval>");
                        out.println("\t\t\t<idval>"+getTrNameById(y, 1)+"</idval>");
                        out.println("\t\t</transval>");
                        if(relazioniTransizioni[x][y]==Relazione.M_EX)
                            out.println("\t\t<type>mutex</type>");
                        else if(relazioniTransizioni[x][y]==Relazione.SINCRONA)
                            out.println("\t\t<type>sync</type>");
                        out.println("\t</relation>");
                    }    
                }
            }
            out.println("</simulation>");
            out.close();
    }

    private String getTrNameById(int id, int index)
    {
        Fsm fsm = listaFsm.get(index);
        Vector<Transizione> tr=fsm.getTransizioni();
        String name="";
        for(int i=0; i<tr.size(); i++)
        {
            int p=tr.get(i).getId();
            if(p==id)
                name=tr.get(i).getNome();
        }
        return name;
    }

    public String ToString()
    {
        String s = "\nDATI DELLA SIMULAZIONE\n--------------------------------------------\n";

        for(int i=0; i<listaFsm.size(); i++)
            s += listaFsm.get(i).ToString();
        
        s += "\nRelazioni tra transizioni:\n" +
                "Fsm1\tFsm2\tTipo\n";

        for(int i=0; i<relazioniTransizioni.length; i++)
            for(int j=0; j<relazioniTransizioni[0].length; j++)
                s += "id: " + i + "\tid: " + j + "\t" + relazioniTransizioni[i][j]+"\n";
        return s;
    }

}
