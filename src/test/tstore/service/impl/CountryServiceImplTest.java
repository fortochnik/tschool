package tstore.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import tstore.dao.CountryDao;
import tstore.model.CountryEntity;
import tstore.service.impl.CountryServiceImpl;

import static org.mockito.Mockito.mock;

/**
 * Created by mipan on 23.10.2016.
 */
//@Ignore
public class CountryServiceImplTest
{

    CountryServiceImplUnitTest countryServiceImplUnitTest;

    private class CountryServiceImplUnitTest extends CountryServiceImpl{

//        CountryDao countryDao;
        public CountryServiceImplUnitTest() {
            super();
            countryDao = mock(CountryDao.class);
        }
        public CountryDao getCountryDao() {
            return countryDao;
        }
    }

    @Before
    public void setUp() {
        this.countryServiceImplUnitTest = new CountryServiceImplUnitTest();
//        CountryServiceImplUnitTest countryServiceImplUnitTest = new CountryServiceImplUnitTest();
    }
@Test
    public void testGetAll() throws Exception {

        countryServiceImplUnitTest.getAll();
        Mockito.verify(countryServiceImplUnitTest.getCountryDao(), Mockito.atLeastOnce()).findAll(CountryEntity.class);
    }
@Test
    public void testGetByName() throws Exception {
        countryServiceImplUnitTest.getByName("Россия");
        Mockito.verify(countryServiceImplUnitTest.getCountryDao()).findByName("Россия");
    }
}