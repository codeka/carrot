package net.asfun.jangod.template;

public class SimpleConfig implements TemplateConfig {

	String encoding;
	String root;
	
	@Override
	public String getTemplateEncoding() {
		return encoding;
	}

	@Override
	public String getTemplateRoot() {
		return root;
	}
	
	public void setTemplateEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	public void setTemplateRoot(String root) {
		this.root = root;
	}

}
