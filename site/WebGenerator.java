import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import net.asfun.jangod.template.TemplateEngine;


public class WebGenerator {
	
	
	public static void main(String...strings) {
		try {
			generate("aboutme.html");
			generate("articles.html");
			generate("changes.html");
			generate("codes.html");
			generate("customize.html");
			generate("developguide.html");
			generate("downloads.html");
			generate("faq.html");
			generate("features.html");
			generate("glossary.html");
			generate("index.html");
			generate("license.html");
			generate("links.html");
			generate("quickstart.html");
			generate("roadmap.html");
			generate("sponsor.html");
			generate("thanks.html");
			generate("usage.html");
			generate("userguide.html");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	
	
	static TemplateEngine engine = new TemplateEngine();
	static String path;
	
	static {
		try {
			File f = new File("site/templates");
			if ( f.exists() ) {
				path = f.getCanonicalPath();
			} else {
				throw new IOException("file not found");
			}
		} catch (IOException e) {
			path = WebGenerator.class.getClassLoader().getResource("").getPath();
			path += "../site/templates";
		}
		System.out.println("Template path >>>" + path);
		engine.getConfiguration().setWorkspace(path);
	}
	
	static void generate(String file) throws Exception {
		try {
			FileWriter out = new FileWriter(path + "/../war/" + file);
			engine.process(file, null, out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("Generate web failed >>> " + file);
		}
	}
	
}
