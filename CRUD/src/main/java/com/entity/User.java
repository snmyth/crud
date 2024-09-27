package com.entity;

public class User {
	private int id;
	private String name;
	private String grade;
	private boolean isTeacher;
	private int level;
	
	
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User( String name, String grade, boolean isTeacher, int level) {
		super();
	
		this.name = name;
		this.grade = grade;
		this.isTeacher = isTeacher;
		this.level = level;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGrade() {
		return grade;
	}
	public void setClass(String grade) {
		this.grade = grade;
	}
	public boolean isTeacher() {
		return isTeacher;
	}
	public void setTeacher(boolean isTeacher) {
		this.isTeacher = isTeacher;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	
	

}
