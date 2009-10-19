/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cooperativefsm;

/**
 *
 * @author Renato
 */

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.util.Vector;

public class InputXML extends Input {

     private Simulazione.Relazione relazioniTransizioni[][]; //Relazione è un tipo enum che definisce i tipi di relazione
     private Vector<Fsm> listaFsm = new Vector<Fsm>();
     private StatoCorrente statoIniziale = new StatoCorrente();
     private Document doc = null;

    public @Override Simulazione leggiSimulazione()
    {  
        return new Simulazione(listaFsm, relazioniTransizioni, statoIniziale);
    }

    public @Override StatoCorrente leggiStatoIniziale(Vector<Fsm> list) {return new StatoCorrente();}

    //costruttore di InputXML
    public InputXML(String URI)
    {
        //provo a leggere il file e fare il parser xml
	try
	{
            doc = parserXML(new File(URI));
            insSIM(doc, 0);
        }
        catch(Exception error)
        {
            error.printStackTrace();
        }
        /*
        System.out.println("Le fsm sono: " + listaFsm.size());
        System.out.println("Stati fsm1: " + listaFsm.get(0).getNumStati());
        System.out.println("Stati fsm2: " + listaFsm.get(1).getNumStati());
        System.out.println("Stati fsm1: " + listaFsm.get(0).getNumTr());
        System.out.println("Stati fsm2: " + listaFsm.get(1).getNumTr());
        */
    }

    //creo una nuova instanza di Simulazione facendo passare tutti i nodi presenti nel file
    private void insSIM(Node node, int level)
    {
        NodeList nl = node.getChildNodes();

        for(int i=0, cnt=nl.getLength(); i<cnt; i++)
	{
            String test=nl.item(i).getNodeName();
            //System.out.println(test);
            //System.out.println(nl.item(i).getNodeName());
            //System.out.println(nl.item(i).getNodeType());
            //Se è definita una fsm la leggo e la aggiungo alla lista delle fsm
            if(test.equalsIgnoreCase("fsm"))
            {
                listaFsm.add(insFSM(nl.item(i)));
            }
            //Se è definita una relazione la leggo e la aggiungo alla lista delle relazioni
            else if(test.equalsIgnoreCase("relation"))
            {
                insREL(nl.item(i));
            }
            //Chiamata ricorsiva per far passare tutti i nodi del file
            insSIM(nl.item(i), level+1);
        }
    }

    private Fsm insFSM(Node node)
    {
           boolean stati=true;
           boolean name=true;
           boolean trans=true;
           String id = "";
           Vector<Stato> listaS = new Vector<Stato>();
           Vector<Transizione> listaT = new Vector<Transizione>();

           NodeList nl = node.getChildNodes();

           //prima controllo il name e il numero di stati
           for(int i=0, cnt=nl.getLength(); i<cnt; i++)
           {
                String test=nl.item(i).getNodeName();

                if(test.equalsIgnoreCase("name"))
                {
                    name=false;
                    id = nl.item(i).getFirstChild().getNodeValue();
                    for(int j=0; j<listaFsm.size(); j++)
                    {
                        if(id.equals(listaFsm.get(j).getId()))
                        {
                            System.out.println("Due fsm definite con lo stesso id!!!");
                            //TODO Uscire dal programma
                        }
                    }
                }
                else if(test.equalsIgnoreCase("states"))
                {
                    stati=false;
                    try
                    {
                        int states = Integer.parseInt(nl.item(i).getFirstChild().getNodeValue());
                        for(i=0; i<states; i++)
                        {
                            Stato s = new Stato(i+1);
                            listaS.add(s);
                        }
                    }
                    catch(NumberFormatException ne)
                    {
                        System.out.println("Lo stato dev'essere identificato da un numero intero!!!");
                    } 
                }
           }//end for
           //poi controllo le transizioni
           for(int i=0, cnt=nl.getLength(); i<cnt; i++)
           {
                String test=nl.item(i).getNodeName();

                if(test.equalsIgnoreCase("transition"))
                {
                    trans=false;
                    listaT.add(insTRANS(nl.item(i), listaS, listaT));
                }
                else if(test.equalsIgnoreCase("current"))
                {
                    //TODO finire l'impostazione dello StatoCorrente
                    try
                    {
                        int stato = Integer.parseInt(nl.item(i).getFirstChild().getNodeValue());
                        if(stato>listaS.size())
                            System.out.println("Il numero dello stato corrente non può superare il numero totale degli stati");
                        else
                        {
                           //Stato s1=getStatoById(stato, listaS);
                           //System.out.println(s1.ToString());
                        }
                    }
                    catch(NumberFormatException ne)
                    {
                        System.out.println("Lo stato dev'essere identificato da un numero intero!!!");
                    }
                }
           }
           if(name)
           {
               System.out.println("Non è stato definito l'id per una fsm");
           }
           if(stati)
           {
               System.out.println("Non è stato definito il numero di stati per una fsm");
           }
           if(trans)
           {
               System.out.println("Non è stata definita alcuna transizione per una fsm");
           }
           Fsm f = new Fsm(id, listaS, listaT);

           return f;
    }

    private Transizione insTRANS(Node node, Vector<Stato> lS, Vector<Transizione> lT)
    {
        NodeList nl = node.getChildNodes();
        String id = "";
        Stato s1 = null;
        Stato s2 = null;
        int length = 0;

        for(int i=0, cnt=nl.getLength(); i<cnt; i++)
        {
            String test=nl.item(i).getNodeName();

            if(test.equalsIgnoreCase("id"))
            {
                id = nl.item(i).getFirstChild().getNodeValue();
                for(int j=0; j<lT.size(); j++)
                {
                    if(id.equals(lT.get(j).getId()))
                    {
                        System.out.println("Due transizioni definite con lo stesso id!!!");
                        //TODO Uscire dal programma
                    }
                }
            }
            else if(test.equalsIgnoreCase("s1"))
            {
                try
                {
                    int stato = Integer.parseInt(nl.item(i).getFirstChild().getNodeValue());
                    if(stato>lS.size())
                        System.out.println("Il numero dello stato non può superare il numero totale degli stati");
                    else
                    {
                        s1=getStatoById(stato, lS);
                        //System.out.println(s1.ToString());
                    }
                }
                catch(NumberFormatException ne)
                {
                        System.out.println("Lo stato dev'essere identificato da un numero intero!!!");
                }
            }
            else if(test.equalsIgnoreCase("s2"))
            {
                try
                {
                    int stato = Integer.parseInt(nl.item(i).getFirstChild().getNodeValue());
                    if(stato>lS.size())
                        System.out.println("Il numero dello stato non può superare il numero totale degli stati");
                    else
                    {
                        s2=getStatoById(stato, lS);
                    }
                }
                catch(NumberFormatException ne)
                {
                        System.out.println("Lo stato dev'essere identificato da un numero intero!!!");
                }
            }
            /*
            else if(test.equalsIgnoreCase("length"))
            {
                System.out.println("length: " + nl.item(i).getFirstChild().getNodeValue());
            }*/
        }
        Transizione T = new Transizione(id,s1,s2);
        s1.addTransUscente(T);
        return T;
    }

    private Stato getStatoById(int id, Vector<Stato> lS)
    {
        Stato k = null;
        for(int i=0; i<lS.size(); i++)
        {
            Stato p = lS.get(i);
            if(id==lS.get(i).getId())
                k=p;
        }
        return k;
    }

    private void insREL(Node node)
    {
        NodeList nl = node.getChildNodes();

        for(int i=0, cnt=nl.getLength(); i<cnt; i++)
        {
            String test=nl.item(i).getNodeName();

            if(test.equalsIgnoreCase("transval"))
            {
                insTRANSVAL(nl.item(i));
            }
        }
    }

    public void insTRANSVAL(Node node)
    {
        NodeList nl = node.getChildNodes();

        for(int i=0, cnt=nl.getLength(); i<cnt; i++)
        {
            String test=nl.item(i).getNodeName();

            if(test.equalsIgnoreCase("fsmval"))
            {
                //System.out.println("fsmval: " + nl.item(i).getFirstChild().getNodeValue());
            }
            else if(test.equalsIgnoreCase("idval"))
            {
                //System.out.println("idval: " + nl.item(i).getFirstChild().getNodeValue());
            }
        }
    }

    

    public Document parserXML(File file) throws SAXException, IOException, ParserConfigurationException
    {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
    }

}
