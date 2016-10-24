package tstore.service;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import tstore.dao.CountryDao;
import tstore.model.CountryEntity;
import tstore.service.impl.CountryServiceImpl;

import static org.mockito.Mockito.mock;

/**
 * Created by mipan on 23.10.2016.
 */
@Ignore
public class CountryServiceImplTest
{

    CountryServiseImplUnitTest countryServiseImplUnitTest;

    private class CountryServiseImplUnitTest extends CountryServiceImpl{

        CountryDao countryDao;
        public CountryServiseImplUnitTest() {
            super();
            countryDao = mock(CountryDao.class);
        }
        public CountryDao getCountryDao() {
            return countryDao;
        }
    }

    @Before
    public void setUp() {
        this.countryServiseImplUnitTest = new CountryServiseImplUnitTest();
        CountryServiseImplUnitTest countryServiseImplUnitTest = new CountryServiseImplUnitTest();
    }

    public void testGetAll() throws Exception {

        countryServiseImplUnitTest.getAll();
        Mockito.verify(countryServiseImplUnitTest.getCountryDao(), Mockito.atLeastOnce()).findAll(CountryEntity.class);
    }

    public void testGetByName() throws Exception {
        countryServiseImplUnitTest.getByName("Россия");
        Mockito.verify(countryServiseImplUnitTest.getCountryDao()).findByName("Россия");
    }
}