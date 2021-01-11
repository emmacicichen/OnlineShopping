
package onlineShop.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import onlineShop.entity.Authorities;
import onlineShop.entity.Customer;
import onlineShop.entity.User;

@Repository//本质上是component ，但是由于这里是数据库Dao的，他单独给你提供了一个database相关的annotation，
// 用这个比较make sense，显示他是represent it is related to database
public class CustomerDao {

    @Autowired //spring will created SessionFactory instance inject int here
    private SessionFactory sessionFactory;//SessionFactory is created from ApplicationConfig
    //这个session是Hibernate提供的一个数据库的session，对数据库进行访问 增删改查的方法

    public void addCustomer(Customer customer) {
        Authorities authorities = new Authorities();//这个POJO的instance直接在这new就可以，不用spring去new，因为用完了就可以销毁，等着GC
        authorities.setAuthorities("ROLE_USER");//spring创建的对象是要等spring这个life cycle结束了才会被GC
        authorities.setEmailId(customer.getUser().getEmailId());
        Session session = null;

        try {
            session = sessionFactory.openSession();
            session.beginTransaction();//transaction：把你要做的事情定义在，如果中间有异常， 就会回滚到没执行的地方。如果没有异常，就执行完了
            session.save(authorities);
            session.save(customer);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();//回滚到执行之前，数据库就不会出现dirty value
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Customer getCustomerByUserName(String userName) {
        User user = null;
        try (Session session = sessionFactory.openSession()) {

            Criteria criteria = session.createCriteria(User.class);//Criteria是个搜索条件，把SQL的条件转成obj，交给Hibernate 把我查询
            user = (User) criteria.add(Restrictions.eq("emailId", userName)).uniqueResult();
            //select * from users where emailID = userName
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user != null)
            return user.getCustomer();
        return null;
    }
}
