/*******************************************************************************
 * Copyright 2013 Raymond Chin
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.github.edi.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import com.github.edi.common.Document;

public abstract class EdiBuilder {

	public EdiBuilder() {
		super();
	}

	public abstract Document buildDocument(String content);

	/**
	 * It will build a EDI document from file.
	 * 
	 * @param file File to read from
	 * @return Document 
	 * @throws IOException when an I/O error prevents a document from being fully parsed
	 */
	public Document buildDocument(File file) throws IOException {
	    Reader reader = new FileReader(file);
	    Document doc = buildDocument(reader);
	    reader.close();
		return doc;
	}
	
    /**
     * It will build a EDI document from reader.
     * 
     * @param reader document to read from
     * @return Document 
     * @throws IOException when an I/O error prevents a document from being fully parsed
     */
	public Document buildDocument(Reader reader) throws IOException {
        BufferedReader buffReader = new BufferedReader(reader);
        char[] buffer = new char[1024];
        int nRead;
        StringBuilder content = new StringBuilder();
        while ((nRead = buffReader.read(buffer, 0, 1024)) != -1) {
            content.append(buffer, 0, nRead);
        }
        buffReader.close();
        return buildDocument(content.toString());
	}

}
