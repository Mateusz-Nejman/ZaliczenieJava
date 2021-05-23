package com.nejman.userLibrary.core;

import java.util.ArrayList;

public class Utils
{
	public static User findUserById(int userId, ArrayList<User> users)
	{
		for(int a = 0; a < users.size(); a++)
		{
			if(users.get(a).getId() == userId)
			{
				return users.get(a);
			}
		}
		
		return null;
	}
	
	public static Group findGroupById(int groupId, ArrayList<Group> groups)
	{
		for(int a = 0; a < groups.size(); a++)
		{
			if(groups.get(a).getId() == groupId)
			{
				return groups.get(a);
			}
		}
		
		return null;
	}
	
	public static ArrayList<Group> findGroupByUserId(int userId, ArrayList<Group> groups)
	{
		ArrayList<Group> returnArray = new ArrayList<Group>();
		
		for(int a = 0; a < groups.size(); a++)
		{
			Group group = groups.get(a);
			
			if(group.getUserIds().contains(userId))
			{
				returnArray.add(group);
			}
		}
		
		return returnArray;
	}
}
