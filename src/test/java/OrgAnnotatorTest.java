import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.jcas.JCas;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.junit.Test;
import org.uimafit.factory.AnalysisEngineFactory;
import org.uimafit.factory.JCasFactory;


public class OrgAnnotatorTest {

	OrgAnnotator organisation =new OrgAnnotator();
	static org.jdom.Document document;
	static Element racine;
	
	@Test
	public void test() throws UIMAException, IOException {
		
		AnalysisEngine engine =
				
				AnalysisEngineFactory.createAnalysisEngineFromPath("OrganisationAE.xml");

		JCas jCas =
				AnalysisEngineFactory.process(engine, "Senior Vice President at Monaco Telecom Monaco Telecommunications Current Previous 1. Monaco Telecom,2. Anevia,");
		
		
		
		//crée une instance de SAXBuilder
				SAXBuilder sxb = new SAXBuilder();
				
				try {
					//crée un nouveau document  JDOM avec en argument le fichier XML
					//parsing
					document=sxb.build(new File("src/main/resources/UIMA_XML/doc0.xmi"));
				} 
				catch (Exception e) {}
				
				//On initialise un nouvel élément racine avec l'élement racine du document 
				
				racine=document.getRootElement();
				//organisation.afficheAll();
				
				 HashMap<String,Integer> organization=new HashMap<String, Integer>();
					List listeOrg=racine.getChildren(); 
					//crée un Iterator pour parcourir la liste
					
					
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
				
	}

}
