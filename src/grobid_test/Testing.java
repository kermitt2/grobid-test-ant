package grobid_test;

import org.grobid.core.utilities.GrobidPropertyKeys;
import org.grobid.core.data.BiblioItem;
import org.grobid.core.data.BibDataSet;
import org.grobid.core.data.PatentItem;
import org.grobid.core.engines.Engine;
import org.grobid.core.factory.GrobidFactory;
import org.grobid.core.mock.MockContext;
import org.grobid.core.utilities.GrobidProperties;

import java.util.Properties;
import java.util.ArrayList;
import java.util.List; 
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.FileInputStream;

/**
 *  This is an example how to use Grobid Java API in a third party ant project. 
 * 
 *  @author Patrice Lopez
 */
public class Testing {

	/**
     * Command line execution.
     * 
     * @param args Command line arguments.
     */
    public static void main( String[] args ) {	
    	try {
			// context variable are read from the project property file grobid-example.properties
			Properties prop = new Properties();
			prop.load(new FileInputStream("grobid-test-ant.properties"));
			String pGrobidHome = prop.getProperty("grobid_test_ant.pGrobidHome");
			String pGrobidProperties = prop.getProperty("grobid_test_ant.pGrobidProperties");
			
			MockContext.setInitialContext(pGrobidHome, pGrobidProperties);
		    GrobidProperties.getInstance();

			System.out.println(">>>>>>>> GROBID_HOME="+GrobidProperties.get_GROBID_HOME_PATH());			
	
			Engine engine = GrobidFactory.getInstance().createEngine();

			// test header extraction from article
			String pdfPath = "resources/Wang-paperAVE2008.pdf";
			BiblioItem resHeader = new BiblioItem();
			System.out.println(engine.processHeader(pdfPath, false, resHeader));
			
			// test for a patent text
			File txtPath = new File("resources/test1.txt");
			String inputStr = FileUtils.readFileToString(txtPath, "UTF-8");
			List<BibDataSet> articles = new ArrayList<BibDataSet>();
			List<PatentItem> patents = new ArrayList<PatentItem>();
			System.out.println(engine.processAllCitationsInPatent(inputStr, articles, patents, false));
		}
		catch (Exception e) {
	    	// If an exception is generated, print a stack trace
		    e.printStackTrace();
		}
		finally {
			try {
				MockContext.destroyInitialContext();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
    }

}

