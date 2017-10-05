package com.sarah.persistence;

import com.sarah.entity.LocationEntity;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sarah on 10/1/2017.
 */
public class LocationDaoTest {
    private LocationDao locationDao = new LocationDao();

    @Test
    public void getAllLocations() throws Exception {

    }

    @Test
    public void insertLocation() throws Exception {
        LocationEntity location = new LocationEntity("place 1", "test id");

        int id = locationDao.insertLocation(location);

        Assert.assertEquals("IDs do not equal", 1, id);
    }

    @Test
    public void getLocationById() throws Exception {

    }

}