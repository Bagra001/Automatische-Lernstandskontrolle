package Model;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HTMLFile {
	private File htmlFile;
	private Document doc;
	
	public HTMLFile(String path) {
		setHtmlFile(new File(path));
	}
	
	private void openHTMLFile() {
		try {
			doc = Jsoup.parse(htmlFile, "UTF-8", "");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean setMatrikel(String matrikelnummer) {
		Element p = doc.select("P").first();
		return true;
	}

	public File getHtmlFile() {
		return htmlFile;
	}

	public void setHtmlFile(File htmlFile) {
		this.htmlFile = htmlFile;
	}
}
