package com.example.project2.dao;

import com.example.project2.domain.entity.User;
import com.example.project2.exception.UserSaveFailedException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDao {
    @Autowired
    SessionFactory sessionFactory;
    public List<User> getAllUsers(){
        Session session;
        List<User> userList = null;
        try{
            session = sessionFactory.getCurrentSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            cq.select(root);
            userList = session.createQuery(cq).getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }
        return (userList.isEmpty()) ? null : userList;
    }

    public User getUserById(int id){
        Session session;
        Optional<User> user = null;
        try{
            session = sessionFactory.getCurrentSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            Predicate predicate = cb.equal(root.get("id"), id);
            cq.select(root).where(predicate);
            user = session.createQuery(cq).uniqueResultOptional();
        }catch (Exception e){
            e.printStackTrace();
        }
        return(user.isPresent()) ? user.get() : null;
    }
    public void addUser(User user){
        Session session;
        try{
            session = sessionFactory.getCurrentSession();
            session.saveOrUpdate(user);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void somethingWentWrong() throws UserSaveFailedException{
        throw new UserSaveFailedException("Something went wrong, rolling back");
    }
    public User getUserByUsernameAndPassword(String username, String password) {
        Session session;
        Optional<User> user = null;
        try {
            session = sessionFactory.getCurrentSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            Predicate usernamePredicate = cb.equal(root.get("username"), username);
            Predicate passwordPredicate = cb.equal(root.get("password"), password);
            cq.select(root).where(cb.and(usernamePredicate, passwordPredicate));
            user = session.createQuery(cq).uniqueResultOptional();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (user.isPresent()) ? user.get() : null;
    }
}
