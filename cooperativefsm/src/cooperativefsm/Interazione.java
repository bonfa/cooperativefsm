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
     * Presenta all'utente le  possibili relazioni da far scattare
     * e legge la scelta dell'utente
     * @param s
     */
    public boolean selezioneTransizioneDaFarScattare (Simulazione s)
    {
        this.stampaStatoCorrente(s,STATO_CORRENTE);
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
                return this.esecuzioneSenzaErrori(s);
        }
        //Non si dovrebbe mai arrivare a questo return
        return true;
    }

    /**
     * Stampa un elenco di coppie di transizioni
     * @param t
     */
    private void stampaTransizioniAbilitate(Vector<TransizioniAbilitate> t)
    {
        System.out.println(TRANSIZIONI_ABILITATE+"\n");
        System.out.println(MENU_TRANSIZIONI_ABILITATE);
        for(int i=0;i<t.size();i++)
            this.stampaCoppiaDiTransizioni(t.elementAt(i),i);
           
    }

    /**
     * Visualizza lo stato corrente della simulazione
     * @param s,mex
     */
    private void stampaStatoCorrente(Simulazione s, String mex)
    {
        System.out.println(mex);
        System.out.print(MENU_STATO_CORRENTE+"\n");
        System.out.println("   "+s.getStatoCorrente().getStatoCorrenteFSM1().getId()+"\t\t"+s.getStatoCorrente().getStatoCorrenteFSM2().getId());
    }

    /**
     * Gestisce l'interazione con l'utente
     * @param s,mex
     */
    private boolean esecuzioneSenzaErrori(Simulazione s)
    {

        //TODO
        Vector<TransizioniAbilitate> temp=null;
        
        temp=s.getTransizioniAbilitate();
        System.out.println("\n");
        if (temp.size() == 0)
            {
            System.out.println(NO_TRANSIZIONI_ABILITATE+"\n");
            this.stampaStatoCorrente(s,STATO_FINALE);
            System.out.println(SIMULAZIONE_TERMINATA);
            return true;
            }
        else
            {
            this.stampaTransizioniAbilitate(temp);
            int transizioneAbilitata=Servizio.leggiInt(SCEGLI_TRANSIZIONE,0,temp.size()-1);
            System.out.println(MENU_TRANSIZIONI_ABILITATE);
            this.stampaCoppiaDiTransizioni(temp.elementAt(transizioneAbilitata), transizioneAbilitata);
            boolean risp=Servizio.yesOrNo(ABILITAZIONE);
            if (risp)
                    System.out.println("Scatta transizione!\n");
            else
                    System.out.println("Non scatta transizione!\n");
                //s.scatta(temp.elementAt(transizioneAbilitata));
            return false;
            }
               
    }

    /**
     * Stampa una coppia di transizioni
     * @param t
     */
    private void stampaCoppiaDiTransizioni(TransizioniAbilitate t,int id)
    {
            System.out.print(id+"\t     ");

            if (t.getTransizioneFSM1()== null)
                System.out.print(NIENTE+" ");
            else
                System.out.print(t.getTransizioneFSM1().getStato1().getId()+"\t\t"+t.getTransizioneFSM1().getStato2().getId()+"\t\t ");


            if (t.getTransizioneFSM2()== null)
                System.out.print(NIENTE);
            else
                System.out.print(t.getTransizioneFSM2().getStato1().getId()+"\t\t"+t.getTransizioneFSM2().getStato2().getId());
            System.out.println("");

    }

}
