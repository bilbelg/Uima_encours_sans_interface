
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceAccessException;
import org.jdom.Element;






public class OrgAnnotator extends JCasAnnotator_ImplBase {
	
private StringMapResource mMap;

static org.jdom.Document document;
static Element racine;
	
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		
		//tokenisation de profil
		String text=aJCas.getDocumentText();
		int pos = 0;
	    int pos2=0;
	    int pos3=0;
	    String token="";
			try {
				mMap=(StringMapResource)getContext().getResourceObject("organization");
			} catch (ResourceAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		
		
		
		StringTokenizer tokenizer =new StringTokenizer(text," \t\n\r.<.>/?\";:[{]}\\|=+()!", true);
		String tokPres="";
	    String PresdePres="";
	    while (tokenizer.hasMoreTokens()) {
	     
	   
	    String doubleMot="";
	    String triMot="";
	      
	      if (!(token.equals(" ")||token.equals(","))){ 
	     PresdePres=tokPres;
	     tokPres=token;
	    
	     
	      }
	      
	      token = tokenizer.nextToken();
	      
	      if (!(token.equals(" ")||token.equals(","))){
	 
	         
	          if((token.matches("^[A-Z].*"))){
	         
	         if((tokPres.matches("^[A-Z].*"))){
	         doubleMot=tokPres+" "+token;
	         
	         
	         	if((PresdePres.matches("^[A-Z].*"))){
	         	triMot=PresdePres+" "+doubleMot;
	         	
	         	}
	         }
	          
	      }
	          }
	   
	      String expandedForm = mMap.get(token);
	      String expandedForm2=mMap.get(doubleMot);
	      String expandedForm3=mMap.get(triMot);
	      
	      if ((expandedForm != null)) {
	        // create annotation
	    	  
	     
	        Org annot = new Org(aJCas, pos, pos + token.length(), token);
	        
	        annot.addToIndexes();
	        
	      }
	      
	      if ((expandedForm2 != null)) {
	          // create annotation
	      
	       Org annot2 = new Org(aJCas, pos2, pos2 + doubleMot.length(), doubleMot);
	          annot2.addToIndexes();
	        }
	      
	      if ((expandedForm3 != null)) {
	          // create annotation
	      
	       Org annot3 = new Org(aJCas, pos2, pos2 + triMot.length(), triMot);
	          annot3.addToIndexes();
	        }
	      
	      // incrememnt pos and go to next token
	      pos += token.length();
	      pos2 += doubleMot.length();
	      pos3 +=triMot.length();
	      
	    }
	  }
	/*
		public static void afficheAll(){
		
	    HashMap<String,Integer> organization=new HashMap<String, Integer>();
		List listeOrg=racine.getChildren(); 
		//crée un Iterator pour parcourir la liste
		
		System.out.println("\t ya rabiiiiiiiiii \t");
		Iterator i=listeOrg.iterator();
		while(i.hasNext()){
			
			Element courant =(Element) i.next();
			if(courant.getAttributeValue("expandedForm") !=null){
			//System.out.println(courant.getAttributeValue("expandedForm"));
			organization.put(courant.getAttributeValue("expandedForm"),1);
			}
		}
		
		for (String organisation : organization.keySet()){
			System.out.println("organisation "+organisation);
		}
	}*/
	}

