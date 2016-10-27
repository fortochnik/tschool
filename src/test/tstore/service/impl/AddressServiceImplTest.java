package tstore.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import tstore.dao.AddressDao;
import tstore.model.AddressEntity;
import tstore.model.CountryEntity;
import tstore.service.impl.AddressServiceImpl;

import static org.mockito.Mockito.mock;

/**
 * Created by mpankin on 24.10.2016.
 */
public class AddressServiceImplTest {
    AddressServiceImplUnitTest addressServiceImplUnitTest;

    private class AddressServiceImplUnitTest extends AddressServiceImpl {
        public AddressServiceImplUnitTest() {
            super();
            addressDao = mock(AddressDao.class);
        }

        public AddressDao getAddressDao() {
            return addressDao;
        }
    }

    @Before
    public void setUp() {
        addressServiceImplUnitTest = new AddressServiceImplUnitTest();
    }

    @Test
    public void testAddAddress() throws Exception {
        AddressEntity addressEntity = new AddressEntity();
        addressServiceImplUnitTest.addAddress(addressEntity);
        Mockito.verify(addressServiceImplUnitTest.getAddressDao(), Mockito.atLeastOnce()).persist(addressEntity);
    }

    @Test
    public void testUpdateAddress() throws Exception {
        AddressEntity addressEntity = new AddressEntity();
        addressServiceImplUnitTest.updateAddress(addressEntity);
        Mockito.verify(addressServiceImplUnitTest.getAddressDao(), Mockito.atLeastOnce()).update(addressEntity);
    }

}
