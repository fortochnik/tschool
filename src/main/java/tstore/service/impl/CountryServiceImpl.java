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
    CountryDao countryDao = new CountryDaoImpl();
    public List<CountryEntity> getAll() {
        return countryDao.findAll(CountryEntity.class);
    }
}
