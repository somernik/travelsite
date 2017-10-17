package com.sarah.persistence;

import com.sarah.entity.PrivilegeEntity;
import com.sarah.entity.User;
import com.sarah.entity.UserPrivilegeEntity;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Access users in the user table.
 *
 * @author somernik
 */
public class UserDao {

    private final Logger log = Logger.getLogger(this.getClass());

    /** Return a list of all users
     *
     * @return All users
     */
    public List<User> getAllUsersWithPrivileges() {
        List<User> users = new ArrayList<User>();
        Session session = null;
        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            users = session.createCriteria(User.class).list();
            for (User user : users) {
                Hibernate.initialize(user.getUserPrivileges());
            }
        } catch (HibernateException he) {
            log.error("Error getting all users", he);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return users;
    }

    /** save new user
     * @param user user to insert
     * @return id of the inserted user
     */
    public int insert(User user) {
        int id = 0;
        Transaction transaction = null;
        Session session = null;

        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            id = (Integer) session.save(user);
            transaction.commit();
        } catch (HibernateException he) {
            log.error("Starting roll back: " + user, he);
            if (transaction != null) {
                try {

                    transaction.rollback();
                } catch (HibernateException he2) {
                    log.error("Error rolling back user insert: " + user, he2);
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return id;
    }

    /** Get a single user for the given id
     *
     * @param id user's id
     * @return User
     */
    public User getUserById(int id) {

        User user = null;
        Session session = null;

        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            user = (User) session.get(User.class, id);
            Hibernate.initialize(user.getLocations());
            Hibernate.initialize(user.getUserPrivileges());
        } catch (HibernateException he) {
            log.error("Error getting user with id: " + id, he);

        } finally {
            if (session != null) {
                session.close();
            }
        }

        return user;
    }

    /** Get a single user for the given id
     *
     * @param username user's username
     * @return User
     */
    public User getUserByUsername(String username) {

        User user = new User();
        Session session = null;

        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            Criteria c2 = session.createCriteria(User.class);
            c2.add(Restrictions.ilike("userName", username, MatchMode.END));
            c2.setMaxResults(1);
            user = (User) c2.uniqueResult();
            Hibernate.initialize(user.getUserPrivileges());

        } catch (HibernateException he) {
            log.error("Error getting user with username: " + username, he);

        } finally {
            if (session != null) {
                session.close();
            }
        }

        return user;
    }

    /** Update  user
     * @param user user to update
     */
    public void update(User user) {
        Transaction transaction = null;
        Session session = null;

        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(user);
            transaction.commit();
        } catch(HibernateException he) {
            log.error("Hibernate Exception: ", he);
            if (transaction != null) {

                try {
                    transaction.rollback();
                } catch (HibernateException he2) {
                    log.error("Error rolling back save of user: " + user, he2);
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /** Delete  user
     * @param user user to update
     */
    public void delete(User user) {
        Transaction transaction = null;
        Session session = null;

        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
        } catch(HibernateException he) {
            log.error("Hibernate Exception: ", he);
            if (transaction != null) {

                try {
                    transaction.rollback();
                } catch (HibernateException he2) {
                    log.error("Error rolling back delete of user: " + user, he2);
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void addAdmin(User user) {

        PrivilegeEntity privilege = new PrivilegeEntity(1, "Administrator");

        UserPrivilegeEntity userPrivilege = new UserPrivilegeEntity(user.getUserName(), user, privilege);

        user.getUserPrivileges().add(userPrivilege);

        this.update(user);
    }

    public void removeAdmin(User user) {

        Session session = null;
        Transaction transaction = null;
        try {
            session = SessionFactoryProvider.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            // delete admin privelege for current user
            String sql = "DELETE FROM userprivilege WHERE user_name='" + user.getUserName() + "' AND Privilege_Id=1";
            SQLQuery query = session.createSQLQuery(sql);
            query.executeUpdate();
            transaction.commit();

        } catch(HibernateException he) {
            log.error("Hibernate Exception: ", he);

        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

}