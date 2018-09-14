package it.uniroma1.lcl.babelarity;

import java.util.ArrayList;
import java.util.List;
/**
 * Classe per la gestione del synset.
 */
public class BabelSynset implements Synset
{
	public enum PartOfSpeech
	{
		NOUN,ADV,ADJ,VERB
	}
	private String id;
	private PartOfSpeech pos;
	private List<String> lemmas=new ArrayList<String>();
	private List<String> glosses=new ArrayList<String>();
	public BabelSynset(String id,List<String> lemmas)
	{
		this.id=id;
		switch(id.charAt(11))
		{
		case 'n':
			pos=PartOfSpeech.NOUN;
			break;
		case 'v':
			pos=PartOfSpeech.VERB;
			break;
		case 'a':
			pos=PartOfSpeech.ADJ;
			break;
		case 'r':
			pos=PartOfSpeech.ADV;
			break;
		}
		this.lemmas=lemmas;
	}
	public String getID()
	{
		return id;
	}
	public PartOfSpeech getPOS()
	{
		return pos;
	}
	
	public List<String> getLemmas()
	{
		return lemmas;
	}
	public List<String> getGlosses()
	{
		return glosses;
	}
}
