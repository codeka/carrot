package org.springframework.web.servlet.view.jangod;

import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.util.WebUtils;

public class JangodViewResolver extends AbstractTemplateViewResolver {

	@Override
	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		JangodView view = (JangodView) super.buildView(viewName);
		String webRoot = WebUtils.getRealPath(getServletContext(), "/");
		view.setRoot(webRoot + getPrefix());
		view.setUrl(viewName + getSuffix());
		return view;
	}
}
