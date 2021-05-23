package com.nejman.userLibrary.core;

import java.util.ArrayList;
import java.util.Collections;

public class Group implements Comparable<Group> 
{
	private int id;
	private String name;
	private ArrayList<Integer> users;
	
	public Group(int id)
	{
		this(id,"");
	}
	
	public Group(int id, String name)
	{
		this.id = id;
		this.name = name;
		this.users = new ArrayList<Integer>();
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
	
	public void addUser(int userId)
	{
		if(!this.users.contains(userId))
		{
			this.users.add(userId);
		}
	}
	
	public ArrayList<Integer> getUserIds()
	{
		return this.users;
	}
	
	public void removeUser(int userId)
	{
		if(this.users.contains(userId))
		{
			int index = this.users.indexOf(userId);
			this.users.remove(index);
		}
	}
	
	public void listUsers(ArrayList<User> usersData)
	{
		System.out.println();
		for(int a = 0; a < this.users.size(); a++)
		{
			int id = this.users.get(a);
			User user = Utils.findUserById(id, usersData);
			
			if(user == null)
			{
				System.out.println("Invalid user with id: " + id);
			}
			else
			{
				System.out.println(user.toString());
			}
		}
		System.out.println();
	}
	
	public void listUsersSorted(ArrayList<User> usersData)
	{
		System.out.println();
		ArrayList<User> sortedList = new ArrayList<User>();
		
		for(int a = 0; a < this.users.size(); a++)
		{
			User user = Utils.findUserById(this.users.get(a), usersData);
			
			if(user != null)
			{
				sortedList.add(user);
			}
		}
		
		Collections.sort(sortedList);
		
		for(int a = 0; a < sortedList.size(); a++)
		{
			System.out.println(sortedList.get(a).toString());
		}
		
		System.out.println();
	}
	
	@Override
	public String toString()
	{
		return "id: " + this.id + "; name: " + this.name + "; users in group: " + this.users.size();
	}
	
	@Override
	public int compareTo(Group group)
	{
		return this.getName().compareTo(group.getName());
	}
}
