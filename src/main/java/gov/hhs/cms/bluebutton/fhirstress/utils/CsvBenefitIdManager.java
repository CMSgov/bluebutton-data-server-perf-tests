package gov.hhs.cms.bluebutton.fhirstress.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CsvBenefitIdManager implements BenefitIdManager {
	CSVParser parser;
	Iterator<String> i;
	File f;
	private static ArrayList<String> idList;

	static {
		try {
			File f = Paths.get("/usr/local/bluebutton-jmeter-service/bene-ids.csv").toFile();
			CSVParser parser = CSVParser.parse(f, Charset.defaultCharset(), CSVFormat.DEFAULT);
			idList = new ArrayList<String>();
			for (CSVRecord record : parser.getRecords()) {
				idList.add(record.get(0));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public CsvBenefitIdManager() {
		this("");
	}

	public CsvBenefitIdManager(String prefix) {
		init();
	}

	public CsvBenefitIdManager(File f) {
		this.f = f;
		try {
			parser = CSVParser.parse(f, Charset.defaultCharset(), CSVFormat.DEFAULT);
			idList = new ArrayList<String>();
			for (CSVRecord record : parser.getRecords()) {
				idList.add(record.get(0));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		init();
	}

	private void init() {
		try {
			i = idList.iterator();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String nextId() { 
		String r;
		try {
			r = i.next();
		} catch (NoSuchElementException e) {
			// If we get an exception here it's likely that we hit the end of file
			// So I want to restart from the beginning of the file.
			init();
			r = i.next();
		}
		return r;
	}

}
