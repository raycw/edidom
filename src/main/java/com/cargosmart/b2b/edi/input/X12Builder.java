package com.cargosmart.b2b.edi.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.cargosmart.b2b.edi.common.Document;

public class X12Builder {

	public Document buildDocument(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		char[] buffer = new char[1024];
		int nRead;
		StringBuilder content = new StringBuilder();
		while ((nRead = reader.read(buffer, 0, 1024)) != -1) {
			content.append(buffer, 0, nRead);
		}
		return buildDocument(content.toString());
	}

	public Document buildDocument(String content) {
		// TODO Auto-generated method stub
		return null;
	}
}
