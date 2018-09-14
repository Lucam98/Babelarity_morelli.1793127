	package it.uniroma1.lcl.babelarity;
	/**
	 * Classe che gestisce le parole da confontare.
	 */
	public class Word implements LinguisticObject
	{
		/**
		 * Stringa della parola.
		 */
		private String name;
		/**
		 * Costruttore.
		 * @param name da settare.
		 */
		public Word(String name) 
		{
			this.name=name;
		}
		/**
		 * Funzione che da una stringa ritorna una word.
		 * @param s Stringa con la quale creare la word.
		 * @return Word creata.
		 */
	    public static Word fromString(String s) 
	    {
	        return new Word(s);
	    }
	    /**
		 * Getter nome.
		 * @return nome.
		 */
	    public String getName() 
	    {
	    	return name;
	    }
	}
