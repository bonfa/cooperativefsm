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

    //Variabili globali utili per la costruzione della simulazione
    private Simulazione.Relazione relazioniTransizioni[][]; //Relazione è un tipo enum che definisce i tipi di relazione
    private Vector<Fsm> listaFsm = new Vector<Fsm>();
    private StatoCorrente statoIniziale = new StatoCorrente();
    private Vector<Stato> statoCur = new Vector<Stato>();
    private Document doc = null;
    //Variabile di appoggio per verificare se sono già state inserite delle relazioni
    private boolean rel=true;

    /**
     *
     * @param URI
     * è il percorso del file xml da caricare
     * @throws SAXException
     * @throws IOException 
     * @throws ParserConfigurationException
     */
    public InputXML(String URI) throws SAXException, IOException, ParserConfigurationException
    {
        //provo a leggere il file e fare il parser xml
	doc = parserXML(new File(URI));
        insSIM(doc, 0);
        //controllo che siano state create almeno 2 fsm prima di settare lo stato corrente della simulazione
        //if(statoCur.size()>1)
        statoIniziale.setStati(statoCur.get(0), statoCur.get(1));
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
     * @return lo stato iniziale della simulazione.
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
        }
        for(int i=0, cnt=nl.getLength(); i<cnt; i++)
        {
            String test=nl.item(i).getNodeName();
            //Se è definita una relazione la leggo e la aggiungo alla lista delle relazioni
            if(test.equalsIgnoreCase("relation"))
            {
                insREL(nl.item(i));
            }
        }
        //Chiamata ricorsiva per far passare tutti i nodi del file
        for(int i=0; i<nl.getLength(); i++)
        {
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
           //booleani per verificare se sono stati inseriti gli stati, il nome, le transizioni e lo stato corrente
           boolean stati=true;
           boolean name=true;
           boolean trans=true;
           boolean stCor=true;

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
                        if(id.equalsIgnoreCase(listaFsm.get(j).getId()))
                        {
                            System.out.println("-- Due fsm non possono avere lo stesso id!!! -- ");
                            listaFsm.get(-1);//per uscire dal programma
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
                        System.out.println("-- Lo stato dev'essere identificato da un numero intero!!! --");
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
                    //System.out.println(insTRANS(nl.item(i), listaS, listaT).ToString());
                }
                else if(test.equalsIgnoreCase("current"))
                {
                    stCor=false;
                    try
                    {
                        //Faccio un parse da String a Int per il numero dello stato corrente
                        int stato = Integer.parseInt(nl.item(i).getFirstChild().getNodeValue());
                        //Controllo che il numero dello stato corrente non sia maggiore del numero massimo degli stati
                        if(stato>listaS.size())
                            System.out.println("-- Il numero dello stato corrente non può superare il numero totale degli stati!!! --");
                        else
                        {
                           //Estraggo dalla lista degli stati l'oggetto Stato identificato dall'id
                           Stato scurrent=getStatoById(stato, listaS);
                           //Aggiungo lo stato corrente di questa fsm al vector degli stati correnti
                           statoCur.add(scurrent);
                        }
                    }
                    catch(NumberFormatException ne)
                    {
                        System.out.println("-- Lo stato dev'essere identificato da un numero intero!!! --");
                    }
                }
           }
           //Messaggi di errore se non sono stati dichiarati alcuni tag
           if(name)
           {
               System.out.println("-- Non è stato definito l'id per una fsm!!! --");
           }
           if(stati)
           {
               System.out.println("-- Non è stato definito il numero di stati per una fsm!!! --");
           }
           if(trans)
           {
               System.out.println("-- Non è stata definita alcuna transizione per una fsm!!! --");
           }
           if(stCor)
           {
               System.out.println("-- Definire lo stato corrente per tutte le fsm!!! --");
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
                        System.out.println("-- Due transizioni appartenenti alla stessa fsm non possono avere lo stesso id!!! --");
                        lT.get(-1);//per uscire dal programma
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
                        System.out.println("-- Il numero dello stato usato in una relazione non può superare il numero totale degli stati di quella fsm!!! --");
                    else
                    {
                        //Estraggo dalla lista degli stati l'oggetto Stato identificato dall'id
                        s1=getStatoById(stato, lS);
                    }
                }
                catch(NumberFormatException ne)
                {
                        System.out.println("-- Lo stato dev'essere identificato da un numero intero!!! --");
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
                        System.out.println("-- Il numero dello stato usato in una relazione non può superare il numero totale degli stati di quella fsm!!! --");
                    else
                    {
                        //Estraggo dalla lista degli stati l'oggetto Stato identificato dall'id
                        s2=getStatoById(stato, lS);
                    }
                }
                catch(NumberFormatException ne)
                {
                        System.out.println("-- Lo stato dev'essere identificato da un numero intero!!! --");
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
    private void insREL(Node node) throws ArrayIndexOutOfBoundsException
    {
        //se non è ancora stata inizializzata relazioniTransizioni la inizializzo
        if(rel)
        {
            inizRel();
        }
        //Estraggo tutti i nodi figli del nodo passato come parametro
        NodeList nl = node.getChildNodes();
        //Vector contenente array, ogni array ha 2 elementi: il primo è l'id della fsm, il secondo l'id della transizione
        Vector<Integer[]> ind = new Vector<Integer[]>();
        //Vector d'appoggio usato per riordinare il vector ind usando come criterio l'id delle fsm contenute nel primo elemento dell'array
        Vector<Integer[]> app = new Vector<Integer[]>();
        String type="";

        //Faccio passare tutti gli elementi del nodo e prendo solo quelli che hanno il nome transval
        for(int i=0, cnt=nl.getLength(); i<cnt; i++)
        {
            String test=nl.item(i).getNodeName();

            if(test.equalsIgnoreCase("transval"))
            {
                try
                {
                    //Inserisco una transizione all'interno del vector ind
                    ind.add(insTRANSVAL(nl.item(i)));
                }
                catch(IndexOutOfBoundsException e)
                {
                    System.out.println("-- L'id della fsm o della transizione all'interno della definizione di una relazione non è valido!!! --");
                }
                catch(NullPointerException w)
                {
                    System.out.println("-- L'id della fsm o della transizione all'interno della definizione di una relazione non è valido!!! --");
                }
            }
        }

        //Faccio passare tutti gli elementi del nodo cercando quello con nome type
        for(int i=0, cnt=nl.getLength(); i<cnt; i++)
        {
            String test=nl.item(i).getNodeName();

            if(test.equalsIgnoreCase("type"))
            {
                type=nl.item(i).getFirstChild().getNodeValue();
                //System.out.println("type: " +type);
            }
        }

        //Faccio passare tutto il vector ind e lo riordino in base all'id delle fsm
        for(int i=0; i<ind.size(); i++)
        {
            //System.out.println("Ind fsm: " + ind.get(i)[0] + "\tInd tr: " + ind.get(i)[1]);
            for(int j=0; j<ind.size(); j++)
            {
                if(ind.get(i)[0]==ind.get(j)[0] && i!=j)
                {
                    System.out.println("-- Non è possibile inserire una relazione tra due transizioni appartenenti alla stessa macchina!!! --");
                    ind.get(-1);//per uscire dal programma
                }
                int p=ind.get(j)[0];
                if(i==p)
                    app.add(ind.get(j));
            }
        }

        if(type.equalsIgnoreCase("sync"))
        {
            relazioniTransizioni[app.get(0)[1]][app.get(1)[1]]=Simulazione.Relazione.SINCRONA;
        }
        else if(type.equalsIgnoreCase("mutex"))
        {
            relazioniTransizioni[app.get(0)[1]][app.get(1)[1]]=Simulazione.Relazione.M_EX;
        }
        
//        int x = listaFsm.get(0).getNumTr();
//        int y = listaFsm.get(1).getNumTr();
//        for(int i=0; i<x; i++)
//            for(int j=0; j<y; j++)
//                System.out.println("x: " + i + "\ty: " + j + "\t" + relazioniTransizioni[i][j]);
    }

    /**
     *
     * @param node
     * @return ritorna un array contenente nel primo elemento l'id della fsm, nel secondo l'id della transizione
     */
    private Integer[] insTRANSVAL(Node node)
    {
        Integer[] a = new Integer[2];
        NodeList nl = node.getChildNodes();
        String idfsm = "";
        String idtr = "";

        //faccio passare tutti gli elemtni del nodo cercando quello con nome fsmval
        for(int i=0, cnt=nl.getLength(); i<cnt; i++)
        {
            String test=nl.item(i).getNodeName();

            if(test.equalsIgnoreCase("fsmval"))
            {
                 idfsm = nl.item(i).getFirstChild().getNodeValue();
            }
        }
        //faccio passare tutti gli elemtni del nodo cercando quello con nome idval
        for(int i=0, cnt=nl.getLength(); i<cnt; i++)
        {
            String test=nl.item(i).getNodeName();

            if(test.equalsIgnoreCase("idval"))
            {
                 idtr = nl.item(i).getFirstChild().getNodeValue();
            }
        }
        //ad a[0] assegno l'id della fsm che ha nome idfsm
        a[0] = getFsmIndexById(idfsm);
        //ad a[1] assengo l'id della transizione che ha nome idtr e fa parte della fsm identificata da a[0]
        a[1] = getTrIdByName(idtr, a[0]);

        return a;
    }

    private void inizRel()
    {
        //inizializzo il doppio array relazioniTransizioni[][] con le dimensionsioni x e y
        int x = listaFsm.get(0).getNumTr();
        int y = listaFsm.get(1).getNumTr();
        relazioniTransizioni = new Simulazione.Relazione [x][y];
        //riempo il doppio array con tutte relazioni asincrone
        for(int i=0; i<x; i++)
            for(int j=0; j<y; j++)
                relazioniTransizioni[i][j]=Simulazione.Relazione.ASINCRONA;
        //imposto il booleano rel a false, il doppio array è stato inizializzato e non c'è più bisogno di invocare questo metodo
        rel=false;
    }
    /**
     *
     * @param name
     * @param indexFsm
     * @return l'indice della transizione identificata da name all'interno della fsm identificata da indexFsm
     */
    private int getTrIdByName(String name, int indexFsm) throws IndexOutOfBoundsException, NullPointerException
    {
        Vector<Transizione> lT= listaFsm.get(indexFsm).getTransizioni();
        int k=-1;
        //faccio passare tutte le transizioni cercando quella con nome uguale a name
        for(int i=0; i<lT.size(); i++)
        {
            if(lT.get(i).getNome().equalsIgnoreCase(name))
                k=i;
        }
        //Se non è stata trovata la transizione ritorno -1 altrimenti ritorno l'indice della transizione all'interno del vector
        return k;
    }
    
    /**
     * 
     * @param id
     * @return l'indice della fsm identificata da id all'interno di listaFsm
     */
    private int getFsmIndexById(String id) throws IndexOutOfBoundsException, NullPointerException
    {
        int k = -1;
        //faccio passare tutte le fsm cercando quella con id uguale a all'id della fsm
        for(int i=0; i<listaFsm.size(); i++)
        {
            if(listaFsm.get(i).getId().equalsIgnoreCase(id))
                k=i;
        }
        //Se non è stata trovata la fsm ritorno -1 altrimenti ritorno l'indice della fsm all'interno di listaFsm
        return k;
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
