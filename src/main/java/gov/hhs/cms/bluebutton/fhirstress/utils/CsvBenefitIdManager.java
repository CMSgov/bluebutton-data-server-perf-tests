package gov.hhs.cms.bluebutton.fhirstress.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.google.common.base.Strings;

public class CsvBenefitIdManager implements BenefitIdManager {
	CSVParser parser;
	List<CSVRecord> list;
	Iterator<CSVRecord> i;
	File f;

	public CsvBenefitIdManager() {
		this("");
	}

	public CsvBenefitIdManager(String prefix) {
		f = Paths.get("/usr/local/bluebutton-jmeter-service/" + (Strings.isNullOrEmpty(prefix) ? "" : prefix + "-")
				+ "bene-ids.csv").toFile();
		try {
			parser = CSVParser.parse(f, Charset.defaultCharset(), CSVFormat.DEFAULT);
			list = parser.getRecords();
		} catch (IOException e) {
			e.printStackTrace();
		}
		init();
	}

	public CsvBenefitIdManager(File f) {
		this.f = f;
		init();
	}

	private void init() {
		try {
			i = list.iterator();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String nextId() { 
		CSVRecord r;
		try {
			r = i.next();
		} catch (NoSuchElementException e) {
			// If we get an exception here it's likely that we hit the end of file
			// So I want to restart from the beginning of the file.
			init();
			r = i.next();
		}

		return r.get(0);
	}

}
