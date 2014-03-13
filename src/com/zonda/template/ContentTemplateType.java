package com.zonda.template;

public enum ContentTemplateType {

	isGrid(0), isWebView(1), isList(2), isAboutUs(3);
	
	int id = -1;
	
	private ContentTemplateType(int id){
		
		this.id = id;
	}
}
