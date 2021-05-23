package com.nejman.userLibrary.core;

public class User implements Comparable<User> 
{
	private int id;
	private String name;

	public User(int id)
	{
		this(id, "");
	}
	
	public User(int id, String name)
	{
		this.id = id;
		this.name = name;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	@Override
	public String toString()
	{
		return "id: " + this.id + "; name = '" + this.name + "'";
	}
	
	@Override
	public int compareTo(User user)
	{
		return this.getName().compareTo(user.getName());
	}
}
