/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cooperativefsm;

/**
 *
 * @author Cominardi Luca
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
    private Vector<Stato> statoCur = new Vector<Stato>();
    private Document doc = null;

    /**
     *
     * @param URI
     * @param è il percorso del file xml da caricre
     * @return è il costruttore della classe InputXML
     */
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
            System.out.println("File xml non formattato correttamente");
            error.printStackTrace();
        }
        //controllo che siano state create almeno 2 fsm prima di settare lo stato corrente della simulazione
        if(statoCur.size()>1)
            statoIniziale.setStati(statoCur.get(0), statoCur.get(1));
        /*
        System.out.println("Le fsm sono: " + listaFsm.size());
        System.out.println("Stati fsm1: " + listaFsm.get(0).getNumStati());
        System.out.println("Stati fsm2: " + listaFsm.get(1).getNumStati());
        System.out.println("Stati fsm1: " + listaFsm.get(0).getNumTr());
        System.out.println("Stati fsm2: " + listaFsm.get(1).getNumTr());
        */
    }

    /**
     *
     * @return una nuova istanza di simulazione
     * Metodo eriditato dalla classe Input
     */
    public @Override Simulazione leggiSimulazione()
    {
        return new Simulazione(listaFsm, relazioniTransizioni, statoIniziale);
    }

    /**
     *
     * @param list
     * @return lo stato iniziale della simulazione
     * Metodo eriditato dalla classe Input
     */
    public @Override StatoCorrente leggiStatoIniziale(Vector<Fsm> list)
    {
        return statoIniziale;
    }

    /**
     *
     * @param node
     * @param level
     * Serve per far passare tutti i nodi del file xml e invocare il metodo appropriato in base al tipo di oggetto
     */
    private void insSIM(Node node, int level)
    {
        //Estraggo tutti i nodi figli del nodo passato come parametro
        NodeList nl = node.getChildNodes();

        //for che fa passare tutti i nodi figli del nodo passato come parametro
        for(int i=0, cnt=nl.getLength(); i<cnt; i++)
	{
            //prendo il nome del nodo (identificatore tag)
            String test=nl.item(i).getNodeName();
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

    /**
     *
     * @param node
     * @return una istanza di Fsm
     * Serve per creare e inizializzare una Fsm
     */
    private Fsm insFSM(Node node)
    {
           boolean stati=true;
           boolean name=true;
           boolean trans=true;
           String id = "";
           Vector<Stato> listaS = new Vector<Stato>();
           Vector<Transizione> listaT = new Vector<Transizione>();

           //Estraggo tutti i nodi figli del nodo passato come parametro
           NodeList nl = node.getChildNodes();

           //Prima controllo il name e il numero di stati
           //Prima devo creare gli stati per poter creare poi le transizioni che si riferiscono agli stati
           for(int i=0, cnt=nl.getLength(); i<cnt; i++)
           {
               //prendo il nome del nodo (identificatore tag)
                String test=nl.item(i).getNodeName();

                if(test.equalsIgnoreCase("name"))
                {
                    //Se è presente un tag "name" imposto name=false
                    name=false;
                    //Prendo il valore del tag name
                    id = nl.item(i).getFirstChild().getNodeValue();
                    //Controllo che non ci sia già un'altra fsm con lo stesso id
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
                    //Se è presente un tag "states" imposto stati=false
                    stati=false;
                    try
                    {
                        //Faccio un parse da String a Int per il numero di stati
                        int states = Integer.parseInt(nl.item(i).getFirstChild().getNodeValue());
                        //Aggiungo un numero di stati pari a quanto definito in states
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
           //Ora posso creare le transizioni
           for(int i=0, cnt=nl.getLength(); i<cnt; i++)
           {
               //prendo il nome del nodo (identificatore tag)
                String test=nl.item(i).getNodeName();

                if(test.equalsIgnoreCase("transition"))
                {
                    //Se è presente un tag "transition" imposto trans=false
                    trans=false;
                    //Leggo e aggiungo una transizione alla lista delle transizioni
                    listaT.add(insTRANS(nl.item(i), listaS, listaT));
                }
                else if(test.equalsIgnoreCase("current"))
                {
                    try
                    {
                        //Faccio un parse da String a Int per il numero dello stato corrente
                        int stato = Integer.parseInt(nl.item(i).getFirstChild().getNodeValue());
                        //Controllo che il numero dello stato corrente non sia maggiore del numero massimo degli stati
                        if(stato>listaS.size())
                            System.out.println("Il numero dello stato corrente non può superare il numero totale degli stati");
                        else
                        {
                           //Estraggo dalla lista degli stati l'oggetto Stato identificato dall'id
                           Stato scurrent=getStatoById(stato, listaS);
                           if(scurrent==null)
                           {
                               System.out.println("Lo stato corrente è nullo");
                           }
                           else
                           {
                               //Aggiungo lo stato corrente di questa fsm al vector degli stati correnti
                               statoCur.add(scurrent);
                           }
                        }
                    }
                    catch(NumberFormatException ne)
                    {
                        System.out.println("Lo stato dev'essere identificato da un numero intero!!!");
                    }
                }
           }
           //Messaggi di errore se non sono stati dichiarati alcuni tag
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
           //Creo la fsm
           Fsm f = new Fsm(id, listaS, listaT);

           return f;
    }

    /**
     *
     * @param node
     * @param lS
     * @param lT
     * @return una transizione
     * Serve per creare e inizializzare una transizione di una fsm
     */
    private Transizione insTRANS(Node node, Vector<Stato> lS, Vector<Transizione> lT)
    {
        //Estraggo tutti i nodi figli del nodo passato come parametro
        NodeList nl = node.getChildNodes();

        String nome = "";
        Stato s1 = null;
        Stato s2 = null;
        int length = 0;

        //Faccio passare tutti i tag figli della transizione
        for(int i=0, cnt=nl.getLength(); i<cnt; i++)
        {
            //prendo il nome del nodo (identificatore tag)
            String test=nl.item(i).getNodeName();

            if(test.equalsIgnoreCase("id"))
            {
                //Prendo il valore del tag id
                nome = nl.item(i).getFirstChild().getNodeValue();
                //Controllo che non ci siano due transizioni con lo stesso id
                for(int j=0; j<lT.size(); j++)
                {
                    if(nome.equals(lT.get(j).getNome()))
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
                    //Faccio un parse da String a int per il numero dello stato
                    int stato = Integer.parseInt(nl.item(i).getFirstChild().getNodeValue());
                    //Controllo che il numero dello stato non sia maggiore del numero degli stati della fsm
                    if(stato>lS.size())
                        System.out.println("Il numero dello stato non può superare il numero totale degli stati");
                    else
                    {
                        //Estraggo dalla lista degli stati l'oggetto Stato identificato dall'id
                        s1=getStatoById(stato, lS);
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
                    //Faccio un parse da String a int per il numero dello stato
                    int stato = Integer.parseInt(nl.item(i).getFirstChild().getNodeValue());
                    //Controllo che il numero dello stato non sia maggiore del numero degli stati della fsm
                    if(stato>lS.size())
                        System.out.println("Il numero dello stato non può superare il numero totale degli stati");
                    else
                    {
                        //Estraggo dalla lista degli stati l'oggetto Stato identificato dall'id
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
        //Creo una istanza di Transizione
        Transizione T = null;
        //Prima di inizializzare T controllo che gli stati s1 e s2 non siano nulli
        if(s1!=null && s2!=null)
        {
            T = new Transizione(lT.size(),s1,s2);
            //Setto il nome simbolico della transizione
            T.setNome(nome);
            //Aggiungo allo stato sorgente della transizione la transizione stessa come transizione uscente
            s1.addTransUscente(T);
        }
        return T;
    }

    /**
     *
     * @param id
     * @param lS
     * @return lo stato identificato dall'id all'interno della lista di stati passata per parametro
     */
    private Stato getStatoById(int id, Vector<Stato> lS)
    {
        Stato k = null;
        //Faccio passare tutti gli stati e controllo se è presente l'id passato come parametro
        for(int i=0; i<lS.size(); i++)
        {
            Stato p = lS.get(i);
            if(id==lS.get(i).getId())
                k=p;
        }
        return k;
    }

    /**
     * @param node
     * Serve per creare e inizializzare una relazione tra transizioni
     */
    private void insREL(Node node)
    {
        int x = listaFsm.get(0).getNumTr();
        int y = listaFsm.get(1).getNumTr();
        Simulazione.Relazione relazioniTransizioni[][] = (new Simulazione.Relazione [x+1][y+1]);

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

    private void insTRANSVAL(Node node)
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

    
    /**
     *
     * @param file
     * @return un oggetto di tipo Document, è un albero contenente tutti i nodi xml
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     */
    public Document parserXML(File file) throws SAXException, IOException, ParserConfigurationException
    {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
    }

}
