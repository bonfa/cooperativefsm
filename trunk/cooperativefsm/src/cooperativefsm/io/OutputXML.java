/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cooperativefsm.io;

import cooperativefsm.logic.*;
import cooperativefsm.logic.Simulazione.Relazione;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Questa classe salva su un file di testo una simulazione. La formattazione
 * del file è di tipo xml, questo file può essere letto per riprendere lo stato
 * della simulazione in un momento successivo
 * @author luca
 */
public class OutputXML {

private static Simulazione s;

/**
 * Questo metodo salva su un file di testo una simulazione. La formattazione
 * del file è di tipo xml, questo file può essere letto per riprendere lo stato
 * della simulazione in un momento successivo 
 *
 * @param _s è la simulazione da salvare
 * @param file è il percorso dove salvare il file
 */
public static void salvaSimulazione(Simulazione _s, String file)
    {
        try {
                s=_s;
                scriviSimulazione(file);
        } catch (IOException ex) {
            Logger.getLogger(Simulazione.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void scriviSimulazione(String file) throws IOException
    {
            Vector<Fsm> listaFsm = s.getListaFsm();
            StatoCorrente statoCorrente = s.getStatoCorrente();

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
                    if(s.relazioniTransizioni[x][y]!=Relazione.ASINCRONA)
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
                        if(s.relazioniTransizioni[x][y]==Relazione.M_EX)
                            out.println("\t\t<type>mutex</type>");
                        else if(s.relazioniTransizioni[x][y]==Relazione.SINCRONA)
                            out.println("\t\t<type>sync</type>");
                        out.println("\t</relation>");
                    }
                }
            }
            out.println("</simulation>");
            out.close();
    }

    private static String getTrNameById(int id, int index)
    {
        Vector<Fsm> listaFsm = s.getListaFsm();
        
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
}