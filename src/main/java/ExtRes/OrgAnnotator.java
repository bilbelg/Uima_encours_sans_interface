package ExtRes;

import java.io.File;
import java.io.FileOutputStream;
import java.util.StringTokenizer;





import org.apache.uima.analysis_engine.AnalysisEngine;
//import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;






import static org.apache.uima.fit.factory.ExternalResourceFactory.bindResource;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.resource.ExternalResourceDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.util.InvalidXMLException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ExternalResource;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.ExternalResourceFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;

import ExtRes.organisation;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/**
 * Annotates UIMA acronyms and provides their expanded forms. When combined in an aggregate TAE with
 * the UimaMeetingAnnotator, demonstrates the use of the ResourceManager to share data between
 * annotators.
 * 
 * 
 */
@TypeCapability(outputs= {"ExtRes.organisation", "ExtRes.organisation:expandedForm"})


public class OrgAnnotator extends JCasAnnotator_ImplBase {
	
	static final String RESOURCE_Org_TABLE = "Org";
	@ExternalResource(key=RESOURCE_Org_TABLE)
	private StringMapResource mMap;

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		// TODO Auto-generated method stub
		    // go through document word-by-word
		    String text = aJCas.getDocumentText();
		    int pos = 0;
		    StringTokenizer tokenizer = new StringTokenizer(text, " \t\n\r.<.>/?\";:[{]}\\|=+()!", true);
		    while (tokenizer.hasMoreTokens()) {
		      String token = tokenizer.nextToken();
		      // look up token in map to see if it is an acronym
		      String expandedForm = mMap.get(token);
		      if (expandedForm != null) {
		        // create annotation
		    	organisation annot = new organisation(aJCas, pos, pos + token.length());
		        annot.setExpandedForm(expandedForm);
		        annot.addToIndexes();
		      }
		      // incrememnt pos and go to next token
		      pos += token.length();
		    }
		  }

		  public static AnalysisEngineDescription createDescription() throws InvalidXMLException, ResourceInitializationException {
				TypeSystemDescription tsd = TypeSystemDescriptionFactory.createTypeSystemDescription("OrgType.xml");
				AnalysisEngineDescription aed = AnalysisEngineFactory.createPrimitiveDescription(OrgAnnotator.class, tsd);
				ExternalResourceDescription erd = ExternalResourceFactory.createExternalResourceDescription("List_Org", StringMapResource_impl.class, "src/main/resources/dico.txt");
				bindResource(aed, RESOURCE_Org_TABLE, erd);
				return aed;
		  }
		  
		  public static void main(String[] args) throws Exception {
				File outputDirectory = new File("src/main/resources/UIMA_XML/");
				outputDirectory.mkdirs();
				AnalysisEngineDescription aed = createDescription();
				aed.toXML(new FileOutputStream(new File(outputDirectory, "UimaAcronymAnnotator.xml")));
				
				
			
				
				//=  AnalysisEngineFactory.createAnalysisEngine("List_Org");

				//analysisEngine.process(jCas);
				
			}

	
	}

