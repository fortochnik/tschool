package tstore.dao;

import tstore.model.CountryEntity;

/**
 * Created by mipan on 02.10.2016.
 */
public interface CountryDao extends GenericDao<CountryEntity, Integer>{
    CountryEntity findByName(String country);
}
