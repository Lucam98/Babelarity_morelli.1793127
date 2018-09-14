package it.uniroma1.lcl.babelarity;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Classe per la gestione dei documenti e del corpus.
 */
public class CorpusManager implements Iterable<Document>
{ 
	/**
	 * Lista dei documenti salvati.
	 */
	private ArrayList<Document> documents=new ArrayList<Document>();
	/**
	 * Lista per il corpus.
	 */
	private ArrayList<HashSet<String>> corpus=new ArrayList<HashSet<String>>();
	/**
	 * Unica istanza di CorpusManager.
	 */
	static private CorpusManager gd=new CorpusManager();
	/**
	 * Costruttore privato per singleton.
	 */
	private CorpusManager()
	{
	}
	/**
	 * Funzione in cui si ottiene l'unica e sola istanza dell'oggetto CorpusManager.
     * @return Istanza Corpus Manager.
	 */
	static public CorpusManager getInstance()
	{	
		if(gd.corpus.isEmpty()) 
		{
			for(Integer i=100300;i<100500;i++)
				gd.parseCorpus(Paths.get("resources/corpus/"+i.toString()+".txt"));
			for(Integer i=127110;i<127210;i++)
				gd.parseCorpus(Paths.get("resources/corpus/"+i.toString()+".txt"));
			for(Integer i=127700;i<127800;i++)
				gd.parseCorpus(Paths.get("resources/corpus/"+i.toString()+".txt"));
		}
		return gd;
	}
	/**
	 * Funzione per parsare i documenti.
	 * @param path del documento da parsare.
     * @return Documento parsato.
	 */
	 public Document parseDocument(Path path) 
	    {
	    	Document document=new Document();
	    	try(BufferedReader br=Files.newBufferedReader(path))
	    	{
	    		String s=br.readLine();
	    		String[] riga= s.split("\t");
	    		document.setTitle(riga[0]);
	    		document.setId(riga[1]);
	    	    while(br.ready())
	    	    {
	    	    	 	s=br.readLine();
	    	            if(s==null)
	    	              break;
	    	            document.addContent(s);
	    	    }
	    	}
	    	catch(Exception IOException)
	    	{
	    		System.out.println("ERRORE LETTURA");
	    	}
	    return document;
	    }
	 /**
	 * Funzione per parsare il corpus.
	 * @param path documento corpus da parsare.
	 */
	 public void parseCorpus(Path path) 
	    {
	    	try(BufferedReader br=Files.newBufferedReader(path))
	    	{
	    	    while(br.ready())
	    	    {
	    	    	String s=br.readLine();
	    	            if(s==null)
	    	              break;
	    	            HashSet<String> i=new HashSet<String>();
	    	            i.addAll(Arrays.asList((s.replace(",","").replace("«","").replace("[","").replace("]","").replace("»","").replace("~","").replace("-"," ").replace("|","").replace("?","").replace("]","").replace("—","").replace("/","").replace("+","").replace("–"," ").replace(";","").replace(".","").replace("!","").replace("=","").replace("€","").replace("“","").replace(")","").replace(":","").replace("(","").replace("’","").replace("*","").replace("0","").replace("1","").replace("2","").replace("3","").replace("4","").replace("5","").replace("6","").replace("7","").replace("8","").replace("9","").replace("\"","").replace("-","").replace("%","").replace("$","").replace(">","").replace("<","").replace("#","").replace("&","").replace("'","").replace("‘","")).toLowerCase().split(" ")));	    	    	 
	    	            corpus.add(i);
	    	    }
	    	}
	    	catch(Exception IOException)
	    	{
	    		System.out.println("ERRORE LETTURA");
	    	}
	    }
	 /**
	 * Funzione per caricare un documento.
	 * @param id del documento da caricare.
     * @return Documento caricato.
	 */
    public Document loadDocument(String id) 
    {
    	for(Document d:documents)
    		if(d.getId().equals(id)) return d;
    	System.out.println("documento non trovato");
    	return null;
    }
    /**
	 * Funzione per salvare un documento.
	 * @param d Documento da salvare.
	 */
    public void saveDocument(Document d) 
    {
    	documents.add(d);
    }
    /**
	 * getter corpus.
     * @return corpus.
	 */
    public ArrayList<HashSet<String>> getCorpus() 
    {
    	return corpus;
    }
    /**
	 * Funzione iterator ereditata dall'interfaccia iterable.
     * @return iterator della lista documents.
	 */
    public Iterator<Document> iterator() 
    {
        return documents.iterator();
    }
}
