package tstore.dao.impl;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import tstore.dao.CountryDao;
import tstore.model.CountryEntity;
import tstore.model.enums.BasketOrderState;

import javax.persistence.NoResultException;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by mipan on 02.10.2016.
 */
@Repository
public class CountryDaoImpl extends GenericDaoImpl<CountryEntity, Integer> implements CountryDao {
    final static Logger logger = Logger.getLogger(CountryDaoImpl.class);
    public CountryEntity findByName(String country) {
        CountryEntity entity = null;
        String hql = "from CountryEntity where country = :country";
        Query query = getCurrentSession().createQuery(hql).setParameter("country", country);
        try {
            List list = query.list();
            entity = (CountryEntity) query.list().get(0);
            if (list.size()>1){
                throw new NonUniqueResultException(list.size());
            }
        }
        catch(IndexOutOfBoundsException e) {
            logger.info("No result single result", e);
            return null;
        }
        catch (NonUniqueResultException e)
        {
            logger.error("Non unique  CountryEntity by country : " + country, e);
        }

        return entity;
    }
}
