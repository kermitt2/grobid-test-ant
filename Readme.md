This project illustrates how to embed Grobid ([grobid](https://raw.github.com/kermitt2/grobid)) in your Java application using ant.

You need to install first and build Grobid, see this link: [Build the project](http://grobid.readthedocs.org/en/latest/Install-Grobid/)

Via ant (or maven), this will create a jar file under: grobid/grobid-core/target/grobid-core-`<current version>`.jar

Copy the Grobid jar library under grobid-test-ant/lib :

> copy target/grobid-core-`<current version>`.jar `path_to_grobid_example`/grobid-example/lib

The paths to __grobid-home__ and to the property __grobid.properties__ file must be changed in the project property file:  `grobid-example/grobid-test-ant.properties` according to your installation, for instance: 

		grobid_example.pGrobidHome=/Users/lopez/grobid/grobid-home
		grobid_example.pGrobidProperties=/Users/lopez/grobid/grobid-home/config/grobid.properties

You can then build and test the example project with ant:

> ant jar
> ant test


### API call

When using Grobid, you have to initiate a context with the path to the Grobid resources, the following class give an example of usage:

        import org.grobid.core.*;
        import org.grobid.core.data.*;
        import org.grobid.core.factory.*;
        import org.grobid.core.mock.*;
        import org.grobid.core.utilities.*;
        import org.grobid.core.engines.Engine;
        ...
        String pdfPath = "mypdffile.pdf";
        ...
	try {
			String pGrobidHome = "/Users/lopez/grobid/grobid-home";
			String pGrobidProperties = "/Users/lopez/grobid/grobid-home/config/grobid.properties";

			MockContext.setInitialContext(pGrobidHome, pGrobidProperties);		
			GrobidProperties.getInstance();

			System.out.println(">>>>>>>> GROBID_HOME="+GrobidProperties.get_GROBID_HOME_PATH());

			Engine engine = GrobidFactory.getInstance().createEngine();

			// Biblio object for the result
			BiblioItem resHeader = new BiblioItem();
			String tei = engine.processHeader(pdfPath, false, resHeader);
		} 
		catch (Exception e) {
			// If an exception is generated, print a stack trace
			e.printStackTrace();
		} 
		finally {
			try {
				MockContext.destroyInitialContext();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}

The context paths (pGrobidHome and pGrobidProperties) can be set by a property file, or for a web application by a web.xml file (see for instance grobid-service in the [grobid](https://github.com/kermitt2/grobid) project).



