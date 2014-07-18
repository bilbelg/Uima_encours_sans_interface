import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.jcas.JCas;
import org.junit.Test;
import org.uimafit.factory.AnalysisEngineFactory;
import org.uimafit.factory.JCasFactory;


public class OrgAnnotatorTest {

	OrgAnnotator organisation =new OrgAnnotator();
	
	@Test
	public void test() throws UIMAException, IOException {
		//JCas jCas = JCasFactory.createJCas();

		//jCas.setDocumentText("hlihsofibgbpdnfp");
		AnalysisEngine engine =
				
				AnalysisEngineFactory.createAnalysisEngineFromPath("OrganisationAE.xml");

		JCas jCas =
				AnalysisEngineFactory.process(engine, "Senior Vice President at Monaco Telecom Monaco Telecommunications Current Previous 1. Monaco Telecom,2. Anevia,");
	}

}
