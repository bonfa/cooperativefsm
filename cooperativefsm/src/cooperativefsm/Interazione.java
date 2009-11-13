/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cooperativefsm;

import java.util.*;

/**
 *
 * @author bonfa
 */
public class Interazione implements Messaggi{

    /**
     * POSSIBILI ERRORI:
     * 1)numero relazioni sincrone non settato in maniera corretta
     * 2)siamo nello stato finale
     *
     */



    /**
     * Presenta all'utente le  possibili relazioni da far scattare
     * e legge la scelta dell'utente
     * @param s
     */
    public boolean selezioneTransizioneDaFarScattare (Simulazione s)
    {
        //TODO
        Vector<TransizioniAbilitate> temp=null;
        String t;

        System.out.println(STATO_CORRENTE+s.getStatoCorrente().toString());
        ReturnCodeIterazione rci=s.eseguiIterazione();
        switch (rci)
        {
            case NUM_SYNC_REL_NOT_SETTED:
                System.out.println(ERRORE+ERR_TRANSIZIONI_SINCRONE);
                return true;
            case OUTGOING_TRANSITION_NOT_SETTED:
                //Non si dovrebbe mai verificare questo caso!
                System.out.println(ERR_TRANSIZIONI_USCENTI);
                return true;
            case NO_ERROR:
                temp=s.getTransizioniAbilitate();
                System.out.println("Continua\n");
                if (temp != null)
                    {
                    stampaTransizioniAbilitate(temp);
                    t=Servizio.leggiString(SCEGLI_TRANSIZIONE);
                    System.out.println(t);
                    return false;
                    }
                else
                    {
                    System.out.println(NO_TRANSIZIONI_ABILITATE+"\n");
                    System.out.print(STATO_FINALE+s.getStatoCorrente().toString());
                    System.out.println(SIMULAZIONE_TERMINATA);
                    return true;
                    }
        }
        //Non si dovrebbe mai arrivare a questo return
        return true;
    }

    /**
     * Stampa un elenco di coppie di transizioni
     *
     *
     */
    public void stampaTransizioniAbilitate(Vector<TransizioniAbilitate> t)
    {
        //TODO
        System.out.println(TRANSIZIONI_ABILITATE+"\n");
        for(int i=0;i<t.size();i++)
            System.out.println(t.elementAt(i).toString()+"\n");
            
    }


}
