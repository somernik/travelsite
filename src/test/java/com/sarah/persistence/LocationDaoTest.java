package com.sarah.persistence;

import com.sarah.entity.LocationEntity;
import com.sarah.utility.DatabaseCleaner;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by sarah on 10/1/2017.
 */
public class LocationDaoTest {
    private LocationDao locationDao = new LocationDao();

    private final Logger log = Logger.getLogger(this.getClass());
    private LocationEntity locationEntity = new LocationEntity("Madison WI", "ChIJ_xkgOm1TBogRmEFIurX8DE4");

    @Before
    public void setup() throws Exception {
        locationDao.save(locationEntity);
    }


    @After
    public void tearDown() throws Exception {

        DatabaseCleaner cleaner = new DatabaseCleaner();
        cleaner.run();

    }

    @Test
    public void getAllLocations() throws Exception {
        DatabaseCleaner cleaner = new DatabaseCleaner();
        cleaner.run();

        locationDao.save(locationEntity);

        List<LocationEntity> locations = locationDao.findAll(LocationEntity.class);
        Assert.assertEquals("Wrong number of entities", 1, locations.size());
    }

    @Test
    public void insertLocation() throws Exception {
        LocationEntity location = new LocationEntity(new Long(2),"place 1", "test id");

        Long id = locationDao.save(location);

        LocationEntity locationEntityTest = locationDao.find(LocationEntity.class, id);

        Assert.assertEquals("Google IDs do not equal", locationEntityTest.getGoogleId(), location.getGoogleId());
    }

    @Test
    public void updateLocationTest() throws Exception {
        locationEntity.setName("Not Madison");

        LocationEntity testLocation = locationDao.update(locationEntity);

        Assert.assertTrue("Locations are not equal!", testLocation.toString().equals(locationEntity.toString()));
    }

    @Test
    public void getLocationById() throws Exception {

        //LocationEntity testLocation = locationDao.find(LocationEntity.class, new Long(2));

        //Assert.assertEquals("Locations do not match", testLocation.getName(), locationEntity.getName());
    }

    @Test
    public void getLocationWithPrivilege() throws Exception {
        LocationEntity testLocation = new LocationEntity();
        Long id = new Long(0);
        List<LocationEntity> locations = locationDao.findAll(LocationEntity.class);
        for (LocationEntity location: locations) {
            id = location.getId();
        }

        testLocation = locationDao.getLocationByIdWithReview(id);

        Assert.assertEquals("Wrong # of reviews", 0, testLocation.getReviews().size());
    }

}