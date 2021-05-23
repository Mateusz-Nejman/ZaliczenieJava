package com.nejman.userLibrary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import com.nejman.userLibrary.core.Group;
import com.nejman.userLibrary.core.User;
import com.nejman.userLibrary.core.Utils;

public class FileProcessing
{
	public static int loadUsersData(ArrayList<User> users)
	{
		int nextUserId = 1;
		File file = new File("users.txt");
		
		if(file.exists())
		{
			try
			{
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(
								new FileInputStream(file.getAbsolutePath())));
				
				String line;
				
				while((line = reader.readLine()) != null)
				{
					String[] elements = line.split(";");
					if(elements.length == 2)
					{
						int id = Integer.parseInt(elements[0]);
						String name = elements[1];
						
						if(Utils.findUserById(id, users) == null)
						{
							User newUser = new User(id, name);
							
							users.add(newUser);
							
							if(id >= nextUserId)
							{
								nextUserId = id + 1;
							}
						}
					}
				}
				
				reader.close();
			}
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
		}
		
		return nextUserId;
	}
	
	public static void saveUsersData(ArrayList<User> users, String fileName)
	{
		String buffer = "";
		
		for(int a = 0; a < users.size(); a++)
		{
			User user = users.get(a);
			String item = Integer.toString(user.getId()) + ";" + user.getName();
			
			buffer += item;
			
			if(a < users.size() - 1)
			{
				buffer += "\n";
			}
		}
		
		try
		{
			File file = new File(fileName);
			Files.writeString(file.toPath(), buffer);
		}
		catch (IOException e)
		{
			//Ignore
		}
	}
	
	public static void saveGroupsData(ArrayList<Group> groups, String fileName)
	{
		String buffer = "";
		
		for(int a = 0; a < groups.size(); a++)
		{
			Group group = groups.get(a);
			String item = Integer.toString(group.getId()) + ";" + group.getName();
			
			ArrayList<Integer> userIds = group.getUserIds();
			
			for(int b = 0; b < userIds.size(); b++)
			{
				item += ";" + userIds.get(b);
			}
			
			buffer += item;
			
			if(a < groups.size() - 1)
			{
				buffer += "\n";
			}
		}
		
		try
		{
			File file = new File(fileName);
			Files.writeString(file.toPath(), buffer);
		}
		catch (IOException e)
		{
			//Ignore
		}
	}
	
	public static int loadGroupsData(ArrayList<Group> groups)
	{
		int nextGroupId = 1;
		File file = new File("groups.txt");
		
		if(file.exists())
		{
			try
			{
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(
								new FileInputStream(file.getAbsolutePath())));
				
				String line;
				
				while((line = reader.readLine()) != null)
				{
					String[] elements = line.split(";");
					
					if(elements.length >= 2) //id;name;userId1;userId2...
					{
						int id = Integer.parseInt(elements[0]);
						String name = elements[1];
						
						if(!groups.contains(id))
						{
							Group group = new Group(id, name);
							
							for(int a = 2; a < elements.length; a++)
							{
								int userId = Integer.parseInt(elements[a]);
								
								group.addUser(userId);
							}
							
							groups.add(group);
							
							if(id >= nextGroupId)
							{
								nextGroupId = id + 1;
							}
						}
					}
				}
				
				reader.close();
			}
			catch (Exception e1)
			{
				//Ignore
			}
		}
		
		return nextGroupId;
	}
}
