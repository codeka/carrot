package org.springframework.web.servlet.view.jangod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.theme.AbstractThemeResolver;

public class ApplicationThemeResolver extends AbstractThemeResolver{
	
	private String theme = "default";

	@Override
	public String resolveThemeName(HttpServletRequest req) {
		return theme;
	}

	@Override
	public void setThemeName(HttpServletRequest req, HttpServletResponse resp, String theme) {
		this.theme = theme;
	}

}
