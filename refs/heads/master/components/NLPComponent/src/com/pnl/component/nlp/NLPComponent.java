package com.pnl.component.nlp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Hashtable;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;

import com.oxymedical.component.baseComponent.IComponent;
import com.oxymedical.component.baseComponent.annotations.EventSubscriber;
import com.oxymedical.component.baseComponent.exception.ComponentException;
import com.oxymedical.component.baseComponent.maintenance.annotations.MaintenancePublisher;
import com.oxymedical.core.commonData.IData;
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

public class NLPComponent implements INLPComponent, IComponent {
	public static final String PREFIX = "posmodel";
    public static final String SUFFIX = ".tmp";
    static public IHICData hicData;
    
    
	public static  void main(String[] args) throws IOException 
	{
	
	}
	
	@EventSubscriber(topic = "executeNLP")
	public IHICData execute(IHICData hicDataObject) throws IOException 
	{
		NLPComponent.hicData = hicDataObject;
		InputStream sentenceModelFile = null;
		InputStream tokenModelFile = null;
		InputStream nameModelFile = null;
		InputStream locationModelFile = null;
		InputStream posModelFile = null;
		SentenceModel sentModel = null;
		TokenizerModel tokenModel = null;
		TokenNameFinderModel nameFinderModel = null;
		POSModel posModel = null;
		TokenNameFinderModel locationFinderModel = null;
		String line = null;
		String paragraph = null;
		File tempFile = File.createTempFile(PREFIX, SUFFIX);
		tempFile.deleteOnExit();
		try (FileOutputStream out = new FileOutputStream(tempFile))
		{
			IData data = hicData.getData();
			String queryToParse = (String) data.getFormPattern().getFormValues().get("searchTextBox");
			paragraph = queryToParse;
			System.out.println("------------Inside execute of NLP Component---+ data="+queryToParse);

			// sentenceModelFile = new
			// FileInputStream("/PNL/Technology/NLP_Models/en-sent.bin");
			sentenceModelFile = NLPComponent.class
					.getResourceAsStream("/com/pnl/component/nlp/model/en-sent.bin");
			tokenModelFile = NLPComponent.class
					.getResourceAsStream("/com/pnl/component/nlp/model/en-token.bin");
			nameModelFile = NLPComponent.class
					.getResourceAsStream("/com/pnl/component/nlp/model/en-ner-person.bin");
			locationModelFile = NLPComponent.class
					.getResourceAsStream("/com/pnl/component/nlp/model/en-ner-location.bin");
			posModelFile = NLPComponent.class.
					getResourceAsStream("/com/pnl/component/nlp/model/en-pos-maxent.bin");
			sentModel = new SentenceModel(sentenceModelFile);
			tokenModel = new TokenizerModel(tokenModelFile);
			nameFinderModel = new TokenNameFinderModel(nameModelFile);
			locationFinderModel = new TokenNameFinderModel(locationModelFile);
			IOUtils.copy(posModelFile, out);
			posModel = new POSModelLoader().load(tempFile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		catch (IOException e) {
			e.printStackTrace();
		}
		finally 
		{
			if (sentenceModelFile != null) {
				try {
					sentenceModelFile.close();
				} catch (IOException e) {
				}
			}
			if (tokenModelFile != null) {
				try {
					tokenModelFile.close();
				} catch (IOException e) {
				}
			}
			if (nameModelFile != null) {
				try {
					nameModelFile.close();
				} catch (IOException e) {
				}
			}

		}

		// Sentence Model
		SentenceDetectorME sdetector = new SentenceDetectorME(sentModel);
		String sentences[] = sdetector.sentDetect(paragraph);
		System.out.println("Output of sentence model");
		for(int i=0;i<sentences.length;i++)
		{
			System.out.println(sentences[i]);
			System.out.println("*****************");
		}
		// Tokenizer Model
		Tokenizer tokenizer = new TokenizerME(tokenModel);
		String tokens[] = tokenizer.tokenize(paragraph);
		System.out.println("Output of token model");

		for (String tokenData : tokens) {
			System.out.println(tokenData);
		}
		System.out.println("*****************");

		// Name Finder Model
		NameFinderME nameFinder = new NameFinderME(nameFinderModel);
		Span nameSpans[] = nameFinder.find(tokens);
		System.out.println("Output of name finder model");
		for (Span s : nameSpans) {
			System.out.println(s.toString());
		}
		System.out.println("*****************");

		if (tokenModelFile != null) {
			try {
				tokenModelFile.close();
			} catch (IOException e) {
			}
		}

		// Location Finder Model
		NameFinderME locationFinder = new NameFinderME(locationFinderModel);
		Span locationSpans[] = locationFinder.find(tokens);
		System.out.println("Output of location finder model");
		for (Span s : locationSpans) {
			System.out.println(s.toString());
		}
		System.out.println("*****************");

		if (tokenModelFile != null) {
			try {
				tokenModelFile.close();
			} catch (IOException e) {
			}
		}
		// Part-Of-Speech Model

		PerformanceMonitor perfMon = new PerformanceMonitor(System.err, "sent");
		POSTaggerME tagger = new POSTaggerME(posModel);
		ObjectStream<String> lineStream = new PlainTextByLineStream(
				new StringReader(paragraph));
		perfMon.start();
		System.out.println("Output of pos model");
		while ((line = lineStream.read()) != null) {

			String whitespaceTokenizerLine[] = WhitespaceTokenizer.INSTANCE
					.tokenize(line);
			String[] tags = tagger.tag(whitespaceTokenizerLine);

			POSSample sample = new POSSample(whitespaceTokenizerLine, tags);
			System.out.println(sample.toString());
			perfMon.incrementCounter();
		}
		perfMon.stopAndPrintFinalResult();
		System.out.println("*********TOKENS********" + tokens);
		for(int i = 0; i < tokens.length; i++) { 
		hicData.getData().getFormPattern().getFormValues().put("NLPComponent",tokens[i]);
		}
		return hicData;
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
		return NLPComponent.hicData;
	}

	@Override
	public void setHicData(IHICData hicData) 
	{
		NLPComponent.hicData = hicData;
		
	}

	@Override
	@MaintenancePublisher
	public void maintenance(IMaintenanceData maintenanceData) {
		// TODO Auto-generated method stub

	}

}
