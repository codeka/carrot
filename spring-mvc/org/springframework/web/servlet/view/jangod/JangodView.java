package org.springframework.web.servlet.view.jangod;

import java.util.Map;

import javax.script.Bindings;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.asfun.template.Configuration;
import net.asfun.template.Template;

import org.springframework.web.servlet.view.AbstractTemplateView;

public class JangodView extends AbstractTemplateView{
	
	private Template template;
	private Configuration config;
	
	public JangodView() {
		config = Configuration.getDefaultConfig();
		template = new Template(config);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedTemplateModel(Map model, HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		Bindings bings = template.createBindings(Template.NORMBINDINGS);
		bings.putAll(model);
		template.render(getUrl(), bings, resp.getWriter());
	}

	public void setRoot(String root) {
		config.setTemplateRoot(root);
		template = new Template(config);
	}

}
