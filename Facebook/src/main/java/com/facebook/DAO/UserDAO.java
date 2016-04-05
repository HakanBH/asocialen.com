package com.facebook.DAO;

import java.io.File; 

import org.hibernate.Query;
import org.hibernate.Session;
import org.jasypt.util.password.BasicPasswordEncryptor;

import com.facebook.POJO.Picture;
import com.facebook.POJO.User;
import com.facebook.POJO.UserInfo;

public class UserDAO implements IUserDAO {
	private static final String EMAIL_CHECK_QUERY = "from User U where U.email = :email";

	public void insertUser(User user) throws Exception {
		Session session = SessionDispatcher.getSession();
		try {
			session.beginTransaction();

			//insert extra info column
			UserInfo details = new UserInfo();
			user.setUserInfo(details);
			details.setUser(user);
			
			//encrypt password
			BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
			String encryptedPassword = passwordEncryptor.encryptPassword(user.getPassword());
			user.setPassword(encryptedPassword);
			
			//set default picture
			Picture pic = (Picture) session.get(Picture.class, 1);
			user.setProfilePicture(pic);
			session.persist(user);
	
			new File(User.STORAGE_PATH + File.separator + user.getEmail()).mkdirs();
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new Exception(e);
		} finally {
			session.close();
		}
	}

	public boolean deleteUser(int id) throws Exception {
		Session session = SessionDispatcher.getSession();
		try {
			session.beginTransaction();
			User u = (User) session.get(User.class, id);

			File userDir = new File(User.STORAGE_PATH + File.separator + u.getEmail() + File.separator);
			System.out.println(userDir);

			String[] entries = userDir.list();
			for (String s : entries) {
				System.out.println(s);
				File currentFile = new File(userDir.getPath(), s);
				currentFile.delete();
			}
			userDir.delete();

			session.delete(u);

			session.getTransaction().commit();
			return true;
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw new Exception(e);
		} finally {
			session.close();
		}
	}

	/**
	 * @param email
	 *            - a string which is the email to be searched for.
	 * @param pass
	 *            - string - password to be searched for.
	 * @return Instance of object if there is a user with password and email
	 *         equal to the given parameters. Returns null otherwise.
	 * @throws Exception
	 */

	@Override
	public User login(String email, String pass) throws Exception {
		Session session = SessionDispatcher.getSession();
		try {
			Query query = session.createQuery(EMAIL_CHECK_QUERY);
			query.setString("email", email);
			User user = (User) query.uniqueResult();
			
			BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
			
			if(passwordEncryptor.checkPassword(pass, user.getPassword())){
				return user;
			} else{
				return null;
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			session.close();
		}
	}

	@Override
	public boolean isEmailTaken(String email) throws Exception {
		Session session = SessionDispatcher.getSession();
		try {
			Query query = session.createQuery(EMAIL_CHECK_QUERY);
			query.setString("email", email);

			return(query.uniqueResult() != null); 
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			session.close();
		}
	}

	@Override
	public void addProfileImage(int userId, Picture imagePath) {
		Session session = SessionDispatcher.getSession();
		try {
			session.beginTransaction();
			User user = (User) session.load(User.class, userId);
			user.setProfilePicture(imagePath);
			session.update(user);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	@Override
	public void addFriend(User u, User friend) {
		Session session = SessionDispatcher.getSession();
		try {
			session.beginTransaction();

			if (!u.getFriends().contains(friend)) {
				u.addFriend(friend);
				session.merge(u);
			} else {
				return;
			}

			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	@Override
	public void removeFriend(User user, User friend) {
		Session session = SessionDispatcher.getSession();
		try {
			session.beginTransaction();

			user.removeFriend(friend);

			session.merge(user);

			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	@Override
	public void updateUserInfo(User user, UserInfo info) {
		Session session = null;
		try {
			session = SessionDispatcher.getSession();

			int userId = user.getId();
			User userToUpdate = (User) session.get(User.class, userId);
			UserInfo infoToUpdate = (UserInfo) session.get(UserInfo.class, userId);

			userToUpdate.copy(user);
			infoToUpdate.copy(info);
			
			session.update(userToUpdate);
			session.update(infoToUpdate);
			
			session.beginTransaction();
			session.getTransaction().commit();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public User getUserById(int id) {
		Session session = null;
		User user = null;
		try {
			session = SessionDispatcher.getSession();
			session.beginTransaction();

			user = (User) session.get(User.class, id);

			session.getTransaction().commit();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return user;
	}
}