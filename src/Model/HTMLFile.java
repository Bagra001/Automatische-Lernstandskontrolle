package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Element;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.resource.XMLResource;
import org.xml.sax.InputSource;

import com.lowagie.text.DocumentException;

public class HTMLFile {
	private File htmlFile;
	private Document doc;
	private org.jsoup.nodes.Document jsoupDoc;

	public HTMLFile(String path) {
		setHtmlFile(new File(path));
		openHTMLFile(path);
	}

	private void openHTMLFile(String path) {
		try {
			jsoupDoc = Jsoup.parse(htmlFile, "UTF-8", "");
			doc = XMLResource.load(new InputSource(path)).getDocument();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean setMatrikel(String matrikelnummer) {
		Element p = jsoupDoc.select("P").first();
		return true;
	}

	public void generatePDF(String pdf) {
		try {
			
			FileOutputStream os = new FileOutputStream(pdf);

			ITextRenderer renderer = new ITextRenderer();

			renderer.setDocument(doc, null);
			renderer.layout();
			renderer.createPDF(os);
			renderer.finishPDF();

			os.close();
			os = null;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

	public File getHtmlFile() {
		return htmlFile;
	}

	public void setHtmlFile(File htmlFile) {
		this.htmlFile = htmlFile;
	}
}
