package com.defaulty.webscraper.control.web;

import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTMLEditorKit;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;

public class GetHTMLText {

    private String uri;

    public GetHTMLText(String uri) {
        this.uri = uri;
    }

    public String extractTextFromHTML() {
        try {
            final EditorKit kit = new HTMLEditorKit();
            final Document doc = kit.createDefaultDocument();

            doc.putProperty("IgnoreCharsetDirective", Boolean.TRUE);

            final Reader rd = getReader(uri);
            kit.read(rd, doc, 0);

            return doc.getText(0, doc.getLength());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private Reader getReader(String uri) throws IOException {
        URLConnection conn = new URL(uri).openConnection();
        return new InputStreamReader(conn.getInputStream());
    }
}