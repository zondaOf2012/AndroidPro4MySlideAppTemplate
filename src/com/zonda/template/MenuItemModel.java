package com.zonda.template;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;

import android.os.Bundle;

public class MenuItemModel implements Serializable{

	private static final long serialVersionUID = 6078543180744650323L;

	public int id;
	
	public String title;
	
	public ContentTemplateType type;
	
	public String web_uri;
	
	public String grid_item_prefix;
	
	public String grid_item_postfix;
	
	public int grid_count;
	
	public int grid_first_index;
	
	public String grid_detail_prefix;
	
	public String grid_detail_postfix;

	public HashMap<String, String> params;
	
	public MenuItemModel(){
		
		params = new HashMap<String, String>();
	}
	
	public Bundle getParams(){
		
		Bundle bundle = new Bundle();
		
		for (Entry<String, String> entry : params.entrySet()) {
			
			bundle.putString(entry.getKey(), entry.getValue());
		}
		return bundle;
	}// end of getParams()
}
