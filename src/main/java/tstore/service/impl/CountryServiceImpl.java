package tstore.service.impl;

import tstore.dao.CountryDao;
import tstore.dao.impl.CountryDaoImpl;
import tstore.model.CountryEntity;
import tstore.service.CountryService;

import java.util.List;

/**
 * Created by mipan on 18.10.2016.
 */

public class CountryServiceImpl implements CountryService {
    protected CountryDao countryDao = new CountryDaoImpl();

    /**
     * Get all {@link CountryEntity}
     * @return all {@link CountryEntity}
     */
    public List<CountryEntity> getAll() {
        countryDao.beginTransaction();
        List<CountryEntity> countryEntities = countryDao.findAll(CountryEntity.class);
        countryDao.closeTransaction();
        return countryEntities;
    }

    /**
     * Get {@link CountryEntity} by name
     * @param country country name
     * @return {@link CountryEntity} with target country name
     */
    public CountryEntity getByName(String country) {
        countryDao.beginTransaction();
        CountryEntity countryEntity = countryDao.findByName(country);
        countryDao.closeTransaction();
        return countryEntity;
    }

}
