package com.shinykeys.translator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.cloud.translate.Detection;
import com.google.cloud.translate.Language;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.LanguageListOption;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.common.collect.ImmutableList;

public class CodeTranslator {

	private static String TRANSLATED_FILE = "";
	private static String DIFF_FILE = "";
	private static StringBuilder helpString = new StringBuilder();
	//@Parameter(names={"--input", "-if"}, required = true)
    int inputFilename;
	//@Parameter(names = {"-infile", "-if"}, converter = FileConverter.class, required = true)
	File inFile;
    //@Parameter(names={"--outfile", "-of"}, converter = FileConverter.class)
    int outFile;
    //@Parameter(names = "--help", help = true)
    private boolean help;
	
	public static void main(String ... argv) {
		
		
//		CodeTranslator codeTranslator = new CodeTranslator();
//		JCommander.newBuilder()
//        .addObject(codeTranslator)
//        .build()
//        .parse(argv);
//		codeTranslator.run();
		
		helpString.append("This application had a simple purpose of reading Atari assembly language programs,");
		helpString.append("parsing the comments, and translating them.");
		helpString.append("CT");
		//processCommandLine(argv);
		BufferedReader reader = null;
		BufferedWriter transWriter = null;
		BufferedWriter diffWriter = null;
		TRANSLATED_FILE = "trans_" + argv[0].toString() + ".txt";
		DIFF_FILE = "diff_ " + argv[0].toString() + ".txt";
		int lineNumber = 0;
		try {
			reader = new BufferedReader(new FileReader(
					argv[0].toString()));
			transWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(TRANSLATED_FILE)));
			diffWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(DIFF_FILE)));
			String line = reader.readLine();
			StringBuilder currentDiffString = new StringBuilder();
			StringBuilder currentTransString = new StringBuilder();
			Translate translated = createTranslateService();
			while (line != null) {
				lineNumber ++;
				//split it

				String split[] = line.split("[;]+");
				if (split[1].length() > 0) {
					//translate it
					
				    Translation translation = translated.translate(split[1].toString());
					currentDiffString.append(lineNumber + "," + split[1] + "," + translation);
					currentTransString.append(split[0] + translation); 
				}
				// read next line
				line = reader.readLine();
			}
			//write to files
			//transWriter.write(currentTransString.toString());
			//diffWriter.write(currentDiffString.toString());
			reader.close();
			transWriter.close();
			diffWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void run() {
		// TODO Auto-generated method stub
		
	}

	private static Translate createTranslateService() {
		return TranslateOptions.newBuilder().build().getService();
	}
	
	private static void processCommandLine(String[] processArgs) {
		//Let's just make sure we have the correct arguments
		//Case 2: program name, file
		//Case 3: program name, file, English to input language
		//Case 4: program name, file, source language, target language
		
	}
	
	public class FileConverter implements IStringConverter<File> {
		  public File convert(String value) {
		    return new File(value);
		  }
		}
	
}
