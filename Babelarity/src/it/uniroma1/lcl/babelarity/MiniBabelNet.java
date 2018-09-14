package it.uniroma1.lcl.babelarity;

import java.io.BufferedReader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import it.uniroma1.lcl.babelarity.BabelLexicalAdvancedSimilarity;
/**
 * Classe che gestisce tutte le informazioni sui synsets.
 * Inoltre espone il metodo computeSimilarity che confonta i vari oggetti linguistici.
 */
public class MiniBabelNet implements Iterable<Synset>
{
	/**
	 * Lista dei synsets.
	 */
	private ArrayList<Synset> synsets=new ArrayList<Synset>();
	/**
	 * Struttura dati per le relazioni.
	 */
	private HashMap<String,ArrayList<List<String>>> relations=new HashMap<String,ArrayList<List<String>>>();
	/**
	 * Mappa delle lemmalizazioni.
	 */
	private HashMap<String,List<String>> lemmalization=new HashMap<String,List<String>>();
	/**
	 * Unica istanza di MiniBabelNet.
	 */
	static private MiniBabelNet mbn=new MiniBabelNet();
	/**
	 * Strategia lessicale usata.
	 */
	private BabelLexicalSimilarity strategyL=new BabelLexicalAdvancedSimilarity();
	/**
	 * Strategia semantica usata.
	 */
	private BabelSemanticSimilarity strategyS=new BabelSemanticAdvancedSimilarity();
	/**
	 * Strategia dei documenti usata.
	 */
	private BabelDocumentSimilarity strategyD=new BabelDocumentSimpleSimilarity();
	/**
	 * Funzione in cui si ottiene l'unica e sola istanza dell'oggetto MiniBabelNet.
     * @return Istanza MiniBabelNet.
	 */
	static MiniBabelNet getInstance() 
	{ 
		if(mbn.synsets.isEmpty()) 
		{
			mbn.parseDictionary(Paths.get("resources/dictionary.txt"));
			mbn.parseGlosses(Paths.get("resources/glosses.txt"));
		}
		if(mbn.lemmalization.isEmpty())
			mbn.parseLemmatization(Paths.get("resources/lemmatization-en.txt"));
		if(mbn.relations.isEmpty())
			mbn.parseRelations(Paths.get("resources/relations.txt"));
        return mbn;
    }
	/**
	 * Costruttore privato per singleton.
	 */
	private MiniBabelNet()
	{}
	/**
	 * Funzione per parsare il dizionario.
	 * @param path del dizionario.
	 */
    private void parseDictionary(Path path)
    {
    	try(BufferedReader br=Files.newBufferedReader(path))
    	{
    	    while(br.ready())
    	    {
    	    	 	String s=br.readLine();
    	            if(s==null)
    	              break;
    	            String[] riga=s.split("\t");
    	            List<String> lemmas=Arrays.asList(Arrays.copyOfRange(riga,1,riga.length));
    	            synsets.add(new BabelSynset(riga[0],lemmas));
    	    }
    	}
    	catch(Exception IOException)
    	{
    		System.out.println("ERRORE LETTURA");
    	}
    }
    /**
	 * Funzione per parsare le definizioni.
	 * @param path delle definizioni.
	 */
    private void parseGlosses(Path path)
    {
    	try(BufferedReader br=Files.newBufferedReader(path))
    	{
    	    while(br.ready())
    	    {
    	    	 	String s=br.readLine();
    	            if(s==null)
    	              break;
    	            String[] riga=s.split("\t");
    	            for(Synset sin:synsets)
    	            {
    	            	if(((BabelSynset)sin).getID().equals(riga[0])) 
    	            	{
    	            		((BabelSynset)sin).getGlosses().addAll(Arrays.asList(Arrays.copyOfRange(riga,1,riga.length)));
    	            		break;
    	            	}
    	            }
    	    }
    	}
    	catch(Exception IOException)
    	{
    		System.out.println("ERRORE LETTURA");
    	}
    }
    /**
	 * Funzione per parsare le lemmalizazioni.
	 * @param path delle lemmalizzazioni.
	 */
    private void parseLemmatization(Path path)
    {
    	try(BufferedReader br=Files.newBufferedReader(path))
    	{
    	    while(br.ready())
    	    {
    	    	 	String s=br.readLine();
    	            if(s==null)
    	              break;
    	            String[] riga=s.split("\t");
    	            if(lemmalization.containsKey(riga[0]))
    	            	lemmalization.get(riga[0]).add(riga[1]);
    	            else {
    	            	ArrayList<String> l=new ArrayList<String>();
    	            	l.add(riga[1]);
    	            	lemmalization.put(riga[0],l);
    	            }
    	    }
    	}
    	catch(Exception IOException)
    	{
    		System.out.println("ERRORE LETTURA");
    	}
    }
    /**
	 * Funzione per parsare le relazioni.
	 * @param path delle relazioni.
	 */
    private void parseRelations(Path path)
    {
    	try(BufferedReader br=Files.newBufferedReader(path))
    	{
    	    while(br.ready())
    	    {;
    	    	 	String s=br.readLine();
    	            if(s==null)
    	              break;   	         
    	            String[] riga=s.split("\t");
    	            String key=riga[0];   

    	            if (relations.keySet().contains(key))
    	            {
    	            	System.out.print("b");
    	            	relations.get(key).add(Arrays.asList(riga).subList(1,4));
    	            }
    	            	else 
    	            {    	   	 
    	            	System.out.print("a");
    	            	ArrayList<List<String>> o=new ArrayList<List<String>>();
    	            	relations.put(key,o);
    	            	relations.get(key).add(Arrays.asList(riga).subList(1,4));
    	            }
    	     }
    	}
    	catch(Exception IOException)
    	{
    		System.out.println("ERRORE LETTURA");
    	}
    }
    /**
	 * Funzione che data una word restituisce la lista dei suoi synsets.
	 * @param word Parola da analizzare.
     * @return Lista dei synsets della word.
	 */
    public List<Synset> getSynsets(String word)
    {
    	ArrayList<Synset> out=new ArrayList<Synset>();
    	for(Synset b:synsets) 
    	{
    		if(((BabelSynset)b).getLemmas().contains(word))
    			out.add(b);
    	}
    	return out;
    }
    /**
	 * Funzione che dato l'id restituisce il synset.
	 * @param id del synset da ricercare.
     * @return Synset con l'id in input.
	 */
    public Synset getSynset(String id) 
    {
    	for(Synset s:synsets)
    		if(((BabelSynset)s).getID().equals(id))
    			return s;
    	return null;
    }
    /**
	 * Funzione che data la word restituisce la lista dei suoi lemmi.
	 * @param word Parola da analizzare.
     * @return Lista dei lemmi di quella parola.
	 */
    public List<String> getLemmas(String word)
    {
    	return lemmalization.get(word);
    }
    
    /**
     * Restituisce le informazioni inerenti al Synset fornito in input sotto forma di stringa.
     * Il formato della stringa è il seguente:
     * ID\tPOS\tLEMMI\tGLOSSE\tRELAZIONI
     * Le componenti LEMMI, GLOSSE e RELAZIONI possono contenere più elementi, questi sono separati dal carattere ";"
     * Le relazioni devono essere condificate nel seguente formato:
     * TARGETSYNSET_RELNAME   es. bn:00081546n_has-kind
     * 
     * es: bn:00047028n    NOUN    word;intelligence;news;tidings    Information about recent and important events    bn:0000001n_has-kind;bn:0000001n_is-a
     * 
     * @param s Synset su cui fare il sommario.
     * @return Stringa sommario del synset in input
     */
    public String getSynsetSummary(Synset s) 
    {
    	String str=(((BabelSynset)s).getID()+"\t"+((BabelSynset)s).getPOS()+"\t");
    	for(String l:((BabelSynset)s).getLemmas())
    		str+=l+=";";
    	str+="\t";
    	for(String g:((BabelSynset)s).getGlosses())
    		str+=g+=";";
    	str+="\t";
    	for(String r:relations.keySet())
    		if(r.equals(((BabelSynset)s).getID())) 
    		{
    			for(List<String> o:relations.get(r)) 
    			{
    			str+=(o.get(0)+"_"+o.get(1));
    			str+=";";
    			}
    		}
    	return str;
    	
    }
    /**
	 * Setter della strategia lessicale.
	 * @param lS strategia da settare.
	 */
    public void setLexicalSimilarity(BabelLexicalSimilarity lS)
    {strategyL=lS;}
    /**
   	 * Setter della strategia semantica.
   	 * @param sS strategia da settare.
   	 */
    public void setSemanticSimilarity(BabelSemanticSimilarity sS)
    {strategyS=sS;}
    /**
   	 * Setter della strategia dei documenti.
   	 * @param dS strategia da settare.
   	 */
    public void setDocumentSimilarity(BabelDocumentSimilarity dS)
    {strategyD=dS;}
    /**
   	 * Funzione che confronta due LinguisticOject a seconda di che tipo siano.
   	 * @param o1 LinguisticObject1 da confrontare.
   	 * @param o2 LinguisticObject2 da confrontare.
     * @return double rappresentante il grado di similarita'
   	 */
    public double computeSimilarity(LinguisticObject o1, LinguisticObject o2) 
    {
    	if(o1.getClass().equals(Word.class))
    		return strategyL.computeSimilarity((Word)o1,(Word)o2);
    	if(o1.getClass().equals(BabelSynset.class)) 
    		return strategyS.computeSimilarity((Synset)o1,(Synset)o2);
    	if(o1.getClass().equals(Document.class))
    		return strategyD.computeSimilarity((Document)o1,(Document)o2); 	
    	else
    		return 0.0;
    }
    /**
   	 * Getter delle relazioni.
   	 * @return relations.
   	 */
    public HashMap<String,ArrayList<List<String>>> getRelations()
    {return relations;}
    /**
   	 * Funzione iterator ereditata dall'interfaccia iterable.
   	 * @return iterator della lista synsets.
   	 */
    public Iterator<Synset> iterator() 
    {
        return synsets.iterator();
    }
}
