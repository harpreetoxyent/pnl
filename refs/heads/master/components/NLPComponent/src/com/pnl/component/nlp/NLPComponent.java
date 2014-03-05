package com.pnl.component.nlp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Hashtable;

import org.dom4j.Document;

import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.component.baseComponent.exception.ComponentException;
import com.oxymedical.component.baseComponent.maintenance.annotations.MaintenancePublisher;
import com.oxymedical.core.commonData.IHICData;
import com.oxymedical.core.maintenanceData.IMaintenanceData;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.Span;
import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;

public class NLPComponent implements INLPComponent , IComponent{

	public static void main(String[] args) throws IOException 
	{
		InputStream sentenceModelFile;
		InputStream tokenModelFile;
		InputStream nameModelFile;
		InputStream locationModelFile;
		File posModelFile = null;
		
		SentenceModel sentModel = null;
		TokenizerModel tokenModel = null;
		TokenNameFinderModel nameFinderModel = null;
		POSModel posModel = null;
		TokenNameFinderModel locationFinderModel = null;
		
		sentenceModelFile = new FileInputStream("/PNL/Technology/NLP_Models/en-sent.bin");
		tokenModelFile = new FileInputStream("/PNL/Technology/NLP_Models/en-token.bin");
		nameModelFile = new FileInputStream("/PNL/Technology/NLP_Models/en-ner-person.bin");
		locationModelFile = new FileInputStream("/PNL/Technology/NLP_Models/en-ner-location.bin");
		posModelFile = new File("/PNL/Technology/NLP_Models/en-pos-maxent.bin");
		
		String paragraph  = "This isn't the greatest example sentence in the world because I've seen better. Ankit Kumar Singh sweet and Mike Smith Kumar lives in California";
		String line;
		try 
		{
			
			sentModel = new SentenceModel(sentenceModelFile);  
			tokenModel = new TokenizerModel(tokenModelFile);
			nameFinderModel = new TokenNameFinderModel(nameModelFile);
			locationFinderModel = new TokenNameFinderModel(locationModelFile);
			posModel = new POSModelLoader().load(posModelFile);
		} 
		
		catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		}
		catch (IOException e) 
		{
	          e.printStackTrace();
	    }
		 
        finally 
        {
          if (sentenceModelFile != null)
          {
            try 
            {
            	sentenceModelFile.close();
            }
            catch (IOException e) 
            {
            }
          }
          if (tokenModelFile != null)
          {
 	           try 
 	           {
 	        	   tokenModelFile.close();
 	           }
 	           catch (IOException e) 
 	           {
 	           }
          }
          if (nameModelFile != null)
          {
 	           try 
 	           {
 	        	  nameModelFile.close();
 	           }
 	           catch (IOException e) 
 	           {
 	           }
          }
         
        }
	       
		
		//Sentence Model
		SentenceDetectorME sdetector = new SentenceDetectorME(sentModel);
		String sentences[] = sdetector.sentDetect(paragraph);
		System.out.println("Output of sentence model");	
		
		System.out.println(sentences[0]);
		System.out.println(sentences[1]);	
		
		System.out.println("*****************");	
		
		// Tokenizer Model
		Tokenizer tokenizer = new TokenizerME(tokenModel);
		String tokens[] = tokenizer.tokenize(paragraph);	 
		System.out.println("Output of token model");	

		for (String a : tokens)
		{
			System.out.println(a);
		}	
		System.out.println("*****************");
		
		 
		// Name Finder Model	
		 NameFinderME nameFinder  = new NameFinderME(nameFinderModel);
		 Span nameSpans[] = nameFinder.find(tokens);
		 System.out.println("Output of name finder model");	 
			for(Span s: nameSpans)
			{
				System.out.println(s.toString());
			}	
			System.out.println("*****************");
			
			if (tokenModelFile != null)
	         {
		           try 
		           {
		        	   tokenModelFile.close();
		           }
		           catch (IOException e) 
		           {
		           }
	         }
			
			// Location Finder Model	
			 NameFinderME locationFinder  = new NameFinderME(locationFinderModel);
			 Span locationSpans[] = locationFinder.find(tokens);
			 System.out.println("Output of location finder model");	 
				for(Span s: locationSpans)
				{
					System.out.println(s.toString());
				}	
				System.out.println("*****************");
				
				if (tokenModelFile != null)
		         {
			           try 
			           {
			        	   tokenModelFile.close();
			           }
			           catch (IOException e) 
			           {
			           }
		         }
		// Part-Of-Speech Model	
		
		PerformanceMonitor perfMon = new PerformanceMonitor(System.err, "sent");
		POSTaggerME tagger = new POSTaggerME(posModel);
		ObjectStream<String> lineStream = new PlainTextByLineStream(new StringReader(paragraph));
		perfMon.start();
		System.out.println("Output of pos model");	
		while ((line = lineStream.read()) != null) 
		{
			 
			String whitespaceTokenizerLine[] = WhitespaceTokenizer.INSTANCE.tokenize(line);
			String[] tags = tagger.tag(whitespaceTokenizerLine);
	 
			POSSample sample = new POSSample(whitespaceTokenizerLine, tags);
			System.out.println(sample.toString());
	 
			perfMon.incrementCounter();
		}
		perfMon.stopAndPrintFinalResult();
		System.out.println("*****************");
				
	}

	@Override
	public void start(Hashtable<String, Document> configData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() throws ComponentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() throws ComponentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() throws ComponentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IHICData getHicData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setHicData(IHICData hicData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@MaintenancePublisher
	public void maintenance(IMaintenanceData maintenanceData) {
		// TODO Auto-generated method stub
		
	}


}
