package net.asfun.spring.web.view;

import java.util.Map;

import javax.script.Bindings;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.asfun.template.Configuration;
import net.asfun.template.Template;

import org.springframework.beans.BeansException;
import org.springframework.web.servlet.view.AbstractTemplateView;

public class JangodView extends AbstractTemplateView{
	
	Configuration conf;
	Template template;
	
	public void setEncoding(String encoding) {
		conf.setEncoding(encoding);
	}
	
	protected String getEncoding() {
		return conf.getEncoding();
	}
	
	public void setTemplate(Template template) {
		this.template = template;
	}
	
	@Override
	protected void initApplicationContext() throws BeansException {
		super.initApplicationContext();
		if ( template == null ) {
			setTemplate(null);//TODO DDD
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedTemplateModel(Map model, HttpServletRequest req,
			HttpServletResponse resp) throws Exception {;
		Bindings datas = template.createBindings(Template.NORMBINDINGS);
		datas.putAll(model);
		System.out.println(getUrl());
		template.render(getUrl(), datas, resp.getWriter());
	}

}
