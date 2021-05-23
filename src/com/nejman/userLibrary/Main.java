package com.nejman.userLibrary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;

import com.nejman.userLibrary.core.Group;
import com.nejman.userLibrary.core.User;
import com.nejman.userLibrary.core.Utils;

public class Main 
{
	private static int nextUserId = 1;
	private static int nextGroupId = 1;
	private static PrintStream console = System.out;
	private static BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args) throws IOException, NumberFormatException
	{
		ArrayList<User> users = new ArrayList<User>();
		ArrayList<Group> groups = new ArrayList<Group>();
		
		console.println("User groups by Mateusz Nejman");
		
		nextUserId = FileProcessing.loadUsersData(users);
		console.println("Users data loaded");
		
		nextGroupId = FileProcessing.loadGroupsData(groups);
		console.println("Groups data loaded");
		
		while(true)
		{
			showMainMenu();
			String input = consoleReader.readLine();
			
			if(input.equals("1"))
			{
				addUser(users);
			}
			else if(input.equals("2"))
			{
				editUser(users);
			}
			else if(input.equals("3"))
			{
				removeUser(users, groups);
			}
			else if(input.equals("4"))
			{
				listUsers(users);
			}
			else if(input.equals("5"))
			{
				listUsersSorted(users);
			}
			else if(input.equals("6"))
			{
				addGroup(groups);
			}
			else if(input.equals("7"))
			{
				editGroup(groups);
			}
			else if(input.equals("8"))
			{
				removeGroup(groups);
			}
			else if(input.equals("9"))
			{
				listGroups(groups);
			}
			else if(input.equals("10"))
			{
				listGroupsSorted(groups);
			}
			else if(input.equals("11"))
			{
				listUsersInGroup(groups, users);
			}
			else if(input.equals("12"))
			{
				listUsersInGroupSorted(groups, users);
			}
			else if(input.equals("13"))
			{
				addUserToGroup(users, groups);
			}
			else if(input.equals("14"))
			{
				removeUserFromGroup(users, groups);
			}
			else if(input.equals("15"))
			{
				console.println("Bye.");
				break;
			}
		}
	}
	
	private static void showMainMenu()
	{
		console.println("[1] Add user");
		console.println("[2] Edit user");
		console.println("[3] Remove user");
		console.println("[4] List users");
		console.println("[5] List users sorted");
		console.println("[6] Add group");
		console.println("[7] Edit group");
		console.println("[8] Remove group");
		console.println("[9] List groups");
		console.println("[10] List groups sorted");
		console.println("[11] List users in group");
		console.println("[12] List users in group sorted");
		console.println("[13] Add user to group");
		console.println("[14] Remove user from group");
		console.println("[15] Exit");
	}
	
	private static void addUser(ArrayList<User> users) throws IOException, NumberFormatException
	{
		console.println();
		console.println("Type user name: ");
		
		String input = consoleReader.readLine();
		
		User user = new User(nextUserId, input);
		users.add(user);
		nextUserId++;
		
		FileProcessing.saveUsersData(users, "users.txt");
		
		console.println();
	}
	
	private static void editUser(ArrayList<User> users) throws IOException, NumberFormatException
	{
		listUsers(users);
		
		console.println("Type user id:");
		String input = consoleReader.readLine();
		
		User user = Utils.findUserById(Integer.parseInt(input), users);
		
		if(user == null)
		{
			console.println("User doesn't exist!");
			return;
		}
		
		console.println("Type user name:");
		input = consoleReader.readLine();
		console.println();
		
		user.setName(input);
		
		FileProcessing.saveUsersData(users, "users.txt");
	}
	
	private static void removeUser(ArrayList<User> users, ArrayList<Group> groups) throws IOException, NumberFormatException
	{
		listUsers(users);
		
		console.println("Type user id:");
		String input = consoleReader.readLine();
		int userId = Integer.parseInt(input);
		
		User user = Utils.findUserById(userId, users);
		
		if(user == null)
		{
			console.println("User doesn't exists");
			return;
		}
		
		users.remove(user);
		
		ArrayList<Group> groupsWithUser = Utils.findGroupByUserId(userId, groups);
		
		for(int a = 0; a < groupsWithUser.size(); a++)
		{
			groupsWithUser.get(a).removeUser(userId);
		}
		console.println();
		
		FileProcessing.saveUsersData(users, "users.txt");
		FileProcessing.saveGroupsData(groups, "groups.txt");
	}
	
	private static void listUsers(ArrayList<User> users)
	{
		console.println();
		
		for(int a = 0; a < users.size(); a++)
		{
			console.println(users.get(a).toString());
		}
		
		console.println();
	}
	
	private static void listUsersSorted(ArrayList<User> users)
	{
		ArrayList<User> sortedUsers = (ArrayList<User>)users.clone();
		Collections.sort(sortedUsers);
		
		console.println();
		
		for(int a = 0; a < sortedUsers.size(); a++)
		{
			console.println(sortedUsers.get(a).toString());
		}
		
		console.println();
	}
	
	private static void addGroup(ArrayList<Group> groups) throws IOException
	{
		console.println();
		console.println("Type group name: ");
		
		String input = consoleReader.readLine();
		
		Group group = new Group(nextGroupId, input);
		groups.add(group);
		nextGroupId++;
		
		FileProcessing.saveGroupsData(groups, "groups.txt");
		
		console.println();
	}
	
	private static void editGroup(ArrayList<Group> groups) throws IOException, NumberFormatException
	{
		listGroups(groups);
		
		console.println("Type group id:");
		String input = consoleReader.readLine();
		
		Group group = Utils.findGroupById(Integer.parseInt(input), groups);
		
		if(group == null)
		{
			console.println("Group doesn't exist!");
			return;
		}
		
		console.println("Type group name:");
		input = consoleReader.readLine();
		console.println();
		
		group.setName(input);
		FileProcessing.saveGroupsData(groups, "groups.txt");
	}
	
	private static void removeGroup(ArrayList<Group> groups) throws IOException, NumberFormatException
	{
		listGroups(groups);
		
		console.println("Type group id:");
		String input = consoleReader.readLine();
		
		Group group = Utils.findGroupById(Integer.parseInt(input), groups);
		
		if(group == null)
		{
			console.println("Group doesn't exist!");
			return;
		}
		
		console.println();
		
		groups.remove(group);
		FileProcessing.saveGroupsData(groups, "groups.txt");
	}
	
	private static void listGroups(ArrayList<Group> groups)
	{
		console.println();
		
		for(int a = 0; a < groups.size(); a++)
		{
			console.println(groups.get(a).toString());
		}
		
		console.println();
	}
	
	private static void listGroupsSorted(ArrayList<Group> groups)
	{
		ArrayList<Group> sortedGroups = (ArrayList<Group>)groups.clone();
		Collections.sort(sortedGroups);
		
		console.println();
		
		for(int a = 0; a < sortedGroups.size(); a++)
		{
			console.println(sortedGroups.get(a).toString());
		}
		
		console.println();
	}
	
	private static void listUsersInGroup(ArrayList<Group> groups, ArrayList<User> users) throws IOException, NumberFormatException
	{
		listGroups(groups);
		
		console.println("Type group id:");
		String input = consoleReader.readLine();
		
		Group group = Utils.findGroupById(Integer.parseInt(input), groups);
		
		if(group == null)
		{
			console.println("Group doesn't exist!");
			return;
		}
		
		group.listUsers(users);
	}
	
	private static void listUsersInGroupSorted(ArrayList<Group> groups, ArrayList<User> users) throws IOException
	{
		listGroups(groups);
		
		console.println("Type group id:");
		String input = consoleReader.readLine();
		
		Group group = Utils.findGroupById(Integer.parseInt(input), groups);
		
		if(group == null)
		{
			console.println("Group doesn't exist!");
			return;
		}
		
		group.listUsersSorted(users);
	}
	
	private static void addUserToGroup(ArrayList<User> users, ArrayList<Group> groups) throws IOException, NumberFormatException
	{
		listUsers(users);
		
		console.println("Type user id:");
		String input = consoleReader.readLine();
		
		int id = Integer.parseInt(input);
		
		User user = Utils.findUserById(id, users);
		
		if(user == null)
		{
			console.println("User doesn't exist!");
			return;
		}
		
		listGroups(groups);
		
		console.println("Type group id:");
		input = consoleReader.readLine();
		
		id = Integer.parseInt(input);
		
		Group group = Utils.findGroupById(id, groups);
		
		if(group == null)
		{
			console.println("Group doesn't exist!");
			return;
		}
		
		console.println();
		
		group.addUser(user.getId());
		FileProcessing.saveGroupsData(groups, "groups.txt");
	}
	
	private static void removeUserFromGroup(ArrayList<User> users, ArrayList<Group> groups) throws IOException, NumberFormatException
	{
		listUsers(users);
		
		console.println("Type user id:");
		String input = consoleReader.readLine();
		
		int id = Integer.parseInt(input);
		
		User user = Utils.findUserById(id, users);
		
		if(user == null)
		{
			console.println("User doesn't exist!");
			return;
		}
		
		console.println();
		
		listGroups(groups);
		
		console.println("Type group id:");
		input = consoleReader.readLine();
		
		id = Integer.parseInt(input);
		
		Group group = Utils.findGroupById(id, groups);
		
		if(group == null)
		{
			console.println("Group doesn't exist!");
			return;
		}
		
		console.println();
		
		group.removeUser(user.getId());
		FileProcessing.saveGroupsData(groups, "groups.txt");
	}
}
