package it.uniroma1.lcl.babelarity;
/**
 * Classe per la gestione del documento.
 */
	public class Document implements LinguisticObject
	{
		/**
		 * Attributi documento.
		 */
		private String id,title,content="";
		/**
		 * Getter id.
		 * @return id.
		 */
	    public String getId()
	    {
	    	return id;
	    }
	    /**
		 * Getter titolo.
		 * @return titolo.
		 */
	    public String getTitle() 
	    {
	    	return title;
	    }
	    /**
		 * Getter contenuto.
		 * @return contenuto.
		 */
	    public String getContent()
	    {
	    	return content;
	    }
	    /**
		 * Funzione per aggiungere del contenuto.
		 * @param content Stringa del contenuto da aggiungere.
		 */
	    public void addContent(String content)
	    {
	    	this.content+=content;
	    }
	    /**
		 * Setter id.
		 * @param id da settare.
		 */
	    public void setId(String id)
	    {
	    	this.id=id;
	    }
	    /**
		 * Setter titolo.
		 * @param title titolo da settare.
		 */
	    public void setTitle(String title)
	    {
	    	this.title=title;
	    }
	}
