package onlineShop.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import onlineShop.entity.Product;

@Repository// through sprint, I need spring to create
public class ProductDao {//Dao : related to the business logic

    @Autowired //inject the SessionFactory obj into ProductDao
    private SessionFactory sessionFactory;

    public void addProduct(Product product) {
        Session session = null;
        try {
            session = sessionFactory.openSession();//session obj is from sessionFactory
            session.beginTransaction();//对数据库的操作是原子操作
            session.save(product);
            session.getTransaction().commit();
        } catch (Exception e) {//如果出问题，rollback，避免数据一部分存进去，另一部分没存进去，不会浪费资源
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void deleteProduct(int productId) {//input：product的primary key
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Product product = session.get(Product.class, productId);//get的product的obj，
            session.delete(product);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    public void updateProduct(Product product) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.saveOrUpdate(product);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    public Product getProductById(int productId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Product.class, productId);//通过get得到一个obj
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<Product>();
        try (Session session = sessionFactory.openSession()) {
            products = session.createCriteria(Product.class).list();//对一个表进行搜索（由于没有critieria），把所有的Product相关的都返回，返回一个list
        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }
}
