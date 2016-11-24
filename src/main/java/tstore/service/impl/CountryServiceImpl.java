package tstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tstore.dao.CountryDao;
import tstore.dao.impl.CountryDaoImpl;
import tstore.model.CountryEntity;
import tstore.service.CountryService;

import java.util.List;

/**
 * Created by mipan on 18.10.2016.
 */
@Service
@Transactional
public class CountryServiceImpl implements CountryService {
    @Autowired
    protected CountryDao countryDao;

    /**
     * Get all {@link CountryEntity}
     * @return all {@link CountryEntity}
     */
    public List<CountryEntity> getAll() {
        List<CountryEntity> countryEntities = countryDao.findAll(CountryEntity.class);
        return countryEntities;
    }

    /**
     * Get {@link CountryEntity} by name
     * @param country country name
     * @return {@link CountryEntity} with target country name
     */
    public CountryEntity getByName(String country) {
        CountryEntity countryEntity = countryDao.findByName(country);
        return countryEntity;
    }

}
