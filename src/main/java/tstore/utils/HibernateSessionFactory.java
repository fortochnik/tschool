package tstore.utils;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
//import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Util class for management  session Factory
 * Created by mipan on 24.09.2016.
 */
public class HibernateSessionFactory {

//    private static SessionFactory sessionFactory = buildSessionFactory();

    private HibernateSessionFactory() {
    }

    /*protected static SessionFactory buildSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources( registry )
                    .buildMetadata()
                    .buildSessionFactory();
        }
        catch (Exception e) {
            StandardServiceRegistryBuilder.destroy( registry );

            throw new ExceptionInInitializerError("Initial SessionFactory failed" + e);
        }
        return sessionFactory;
    }
*/

    /**
     * get Session Factory
     * @return instance of the Session Factory
     */
    /*public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }*/

//    public static void shutdown() {
//        getSessionFactory().close();
//    }
}
