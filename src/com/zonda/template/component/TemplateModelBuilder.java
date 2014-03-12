package com.zonda.template.component;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.zonda.template.ContentTemplateType;
import com.zonda.template.MenuItemModel;

public class TemplateModelBuilder {

	
	public void build(){
		
		List<MenuItemModel> datas = new ArrayList<MenuItemModel>(); 
		
		MenuItemModel itemModel = null;
		
		for (int i = 0; i < 5; i++) {
			
			itemModel = new MenuItemModel();
			
			itemModel.id = i;
			
			itemModel.title = "侧边栏Title名";
			
			itemModel.type = ContentTemplateType.isGrid;
			
			itemModel.params.put("参数Key", "参数Value");
			itemModel.params.put("参数Key1", "参数Value");
			
			datas.add(itemModel);
		}
		
		String json = new GsonBuilder().serializeNulls().create().toJson(datas);
		
		Log.i("TAG", "TemplateModelBuilder" + json);
	}
	
	private static volatile TemplateModelBuilder mInstance;
	
	private TemplateModelBuilder(){
		
	}
	
	public static TemplateModelBuilder getInstance(Context appContext){
		
		if(mInstance == null){
			
			synchronized (TemplateModelBuilder.class) {
			
				if(mInstance == null){
					
					mInstance = new TemplateModelBuilder();
				}
			}
		}
		
		return mInstance;
	}
}
