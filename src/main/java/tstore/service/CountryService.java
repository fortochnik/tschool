package tstore.service;

import tstore.dao.CountryDao;
import tstore.model.CountryEntity;

import java.util.List;

/**
 * Created by mipan on 18.10.2016.
 */
public interface CountryService {
    List<CountryEntity> getAll();
}
