package com.kpn.killbill.writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.kpn.killbill.model.Event;

public class PMDMWritter {

	private static final String SAMPLE_CSV_FILE = "C:\\work\\killbill\\data\\output\\pmdm.csv";
	
	private static final String GL_CSV_FILE = "C:\\work\\killbill\\data\\output\\general_ledger.csv";

	public static void writePMDMCSV(List<Event> events) throws IOException {
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(SAMPLE_CSV_FILE));

				CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("CustomerId", "TransType",
						"ChargeAmountIncl", "ServiceProv", "Email", "IBAN"));) {

			for (Event event : events) {
				csvPrinter.printRecord(event.getCustomerId(), event.getTransType(), event.getChargeAmountIncl(),
						event.getServiceProv(), event.getEmail(), event.getIban());
			}

			csvPrinter.flush();
		}

	}

	public static void writeGL(Float netflix, Float O365) throws IOException {
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(GL_CSV_FILE));

				CSVPrinter csvPrinter = new CSVPrinter(writer,
						CSVFormat.DEFAULT.withHeader("ServiceProv", "Revenue"));) {

			csvPrinter.printRecord("NETFLIX", netflix);
			csvPrinter.printRecord("O365", O365);
			csvPrinter.flush();
		}

	}
}
