package model;

import java.util.Comparator;

public class ComparadorNombre implements Comparator<User> {
	
	@Override
	public int compare(User user1, User user2) {
		return user1.getName().compareToIgnoreCase(user2.getName());
	}

}
