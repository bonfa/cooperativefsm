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
     private Vector<Fsm> listaFsm;
     private StatoCorrente statoIniziale;
     private Document doc = null;

    public @Override Simulazione leggiSimulazione()
    {  
        return new Simulazione(listaFsm, relazioniTransizioni, statoIniziale);
    }

    public @Override StatoCorrente leggiStatoIniziale(Vector<Fsm> list) {return new StatoCorrente();}

    public InputXML(String URI)
	{
		try
		{
			doc = parserXML(new File(URI));

			insSIM(doc, 0);
		}
		catch(Exception error)
		{
			error.printStackTrace();
		}
	}

	public void insSIM(Node node, int level)
	{
		NodeList nl = node.getChildNodes();

		for(int i=0, cnt=nl.getLength(); i<cnt; i++)
		{
                    String test=nl.item(i).getNodeName();
                    //System.out.println(test);
                    //System.out.println(nl.item(i).getNodeName());
                    //System.out.println(nl.item(i).getNodeType());
                    if(test.equalsIgnoreCase("fsm"))
                    {
                        insFSM(nl.item(i));
                    }
                    else if(test.equalsIgnoreCase("relation"))
                    {
                        insREL(nl.item(i));
                    }

                    insSIM(nl.item(i), level+1);
		}
	}

        public void insFSM(Node node)
        {
               NodeList nl = node.getChildNodes();
               String id = "";
               int states = 0;

               for(int i=0, cnt=nl.getLength(); i<cnt; i++)
               {
                    String test=nl.item(i).getNodeName();

                    //System.out.println(nl.item(i).getNodeName());
                    //System.out.println(nl.item(i).getNodeValue()+"\n");
                    if(test.equalsIgnoreCase("name"))
                    {
                        id = nl.item(i).getFirstChild().getNodeValue();
                    }
                    else if(test.equalsIgnoreCase("states"))
                    {
                        states = Integer.parseInt(nl.item(i).getFirstChild().getNodeValue());
                        for(i=0; i<states; i++)
                        {
                            Stato s = new Stato(i);
                            //listaS.add(s);
                        }
                    }
                    else if(test.equalsIgnoreCase("transition"))
                    {
                        insTRANS(nl.item(i));
                    }
               }
        }

        public void insTRANS(Node node)
        {
            NodeList nl = node.getChildNodes();

            for(int i=0, cnt=nl.getLength(); i<cnt; i++)
            {
                String test=nl.item(i).getNodeName();

                if(test.equalsIgnoreCase("id"))
                {
                    System.out.println("id: " + nl.item(i).getFirstChild().getNodeValue());
                }
                else if(test.equalsIgnoreCase("s1"))
                {
                    System.out.println("s1: " + nl.item(i).getFirstChild().getNodeValue());
                }
                else if(test.equalsIgnoreCase("s2"))
                {
                    System.out.println("s2: " + nl.item(i).getFirstChild().getNodeValue());
                }
                else if(test.equalsIgnoreCase("length"))
                {
                    System.out.println("length: " + nl.item(i).getFirstChild().getNodeValue());
                }
            }
        }

        public void insREL(Node node)
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
                    System.out.println("fsmval: " + nl.item(i).getFirstChild().getNodeValue());
                }
                else if(test.equalsIgnoreCase("idval"))
                {
                    System.out.println("idval: " + nl.item(i).getFirstChild().getNodeValue());
                }
            }
        }

	public Document parserXML(File file) throws SAXException, IOException, ParserConfigurationException
	{
		return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
	}
   
}
