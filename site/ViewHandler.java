import java.io.IOException;
import java.util.Map;
import net.asfun.jangod.template.TemplateEngine;

public class ViewHandler {

	final static TemplateEngine engine = new TemplateEngine();
	static {
		engine.getConfiguration().setWorkspace("D:/templates/");
	}
	
	public static String render(String templateFile, Map<String,Object> datas)
			throws IOException {
		return engine.process(templateFile, datas);
	}
}
