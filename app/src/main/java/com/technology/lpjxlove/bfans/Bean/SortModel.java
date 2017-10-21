package com.technology.lpjxlove.bfans.Bean;

/**
 * 城市模型
 * @author Chen
 */
public class SortModel {
	
	/**城市名称**/
	private String name;   //显示的数据
	/**拼音的首字母**/
	private String sortLetters;  //显示数据拼音的首字母
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}

}
