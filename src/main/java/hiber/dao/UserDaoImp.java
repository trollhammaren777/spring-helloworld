package hiber.dao;

import hiber.model.Car;
import hiber.model.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public List<User> getUserByCarModelAndSeries(Car car) {
      Session session = sessionFactory.getCurrentSession();
      int series = car.getSeries();
      String model = car.getModel();
      TypedQuery<User> query = session.createQuery("SELECT u FROM User u WHERE u.car = :car " +
              "AND u.car.model LIKE :model AND cast(u.car.series as string) LIKE cast(:series as string)");
      query.setParameter("car", car);
      query.setParameter("model", model);
      query.setParameter("series", series);
      return query.getResultList();
   }
}
