package com.aadhk.product.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;

public class CsvUtil {

	public static void writeCsvFile(String sdCsvPath, String[] header, List<String[]> data) throws SQLException, IOException {
		FileOutputStream fos = new FileOutputStream(sdCsvPath);
		fos.write(0xef);
		fos.write(0xbb);
		fos.write(0xbf);
		CSVWriter csvWrite = new CSVWriter(new OutputStreamWriter(fos));
		//CSVWriter csvWrite = new CSVWriter(new FileWriter(sdCsvPath));
		// write header
		csvWrite.writeNext(header);
		csvWrite.writeAll(data);
		csvWrite.close();
	}

}
