package tstore.dao.impl;

import org.hibernate.NonUniqueResultException;
import org.hibernate.query.Query;
import tstore.dao.CountryDao;
import tstore.model.CountryEntity;
import tstore.model.enums.BasketOrderState;

import javax.persistence.NoResultException;

/**
 * Created by mipan on 02.10.2016.
 */
public class CountryDaoImpl extends GenericDaoImpl<CountryEntity, Integer> implements CountryDao {
    public CountryEntity findByName(String country) {
        CountryEntity countryEntity = null;
        String hql = "from CountryEntity where country = :country";
        Query query = getCurrentSession().createQuery(hql).setParameter("country", country);
        try {
            countryEntity = (CountryEntity) query.getSingleResult();
        }
        catch(NoResultException e) {
            return null;
        }
        catch (NonUniqueResultException e)
        {
//            todo Change exception (?)
//            todo add logging
        }

        return countryEntity;
    }
}
