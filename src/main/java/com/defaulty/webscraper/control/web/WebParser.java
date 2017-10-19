package com.defaulty.webscraper.control.web;

import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTMLEditorKit;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;

public class WebParser {

    private String uri;

    public WebParser(String uri) {
        this.uri = uri;
    }

    /**
     * Using {@code HTMLEditorKit} for read connection stream
     * and cut text part from received html document.
     * @return - cut text.
     */
    public String extractTextFromHTML() {
        try {
            final EditorKit kit = new HTMLEditorKit();
            final Document doc = kit.createDefaultDocument();

            doc.putProperty("IgnoreCharsetDirective", Boolean.TRUE);

            final Reader rd = getReaderNew(uri);
            kit.read(rd, doc, 0);

            return doc.getText(0, doc.getLength());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Getting stream object for url.
     * @param uri - loaded link.
     * @return - stream object.
     * @throws IOException
     */
    private Reader getReader(String uri) throws IOException {
        URLConnection conn = new URL(uri).openConnection();
        return new InputStreamReader(conn.getInputStream());
    }

    /**
     * Getting stream object for url. Use custom connection properties
     * that necessary for some websites.
     * @param uri - loaded link.
     * @return - stream object.
     * @throws IOException
     */
    private Reader getReaderNew(String uri) throws IOException {
        URLConnection uc;
        URL url = new URL(uri);
        uc = url.openConnection();
        uc.connect();
        uc = url.openConnection();
        uc.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
        uc.getInputStream();
        URLConnection conn = new URL(uri).openConnection();
        return new InputStreamReader(conn.getInputStream());
    }


}