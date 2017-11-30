package com.sarah.persistence;

import com.sarah.entity.LocationEntity;
import com.sarah.entity.TagEntity;
import com.sarah.entity.TaglocationEntity;
import com.sarah.utility.DatabaseCleaner;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarah on 11/12/2017.
 */
public class TagLocationDaoTest {
    private final Logger log = Logger.getLogger(this.getClass());
    TagLocationDao dao = new TagLocationDao();

    List<TaglocationEntity> allTagLocations = new ArrayList<TaglocationEntity>();
    LocationEntity location = new LocationEntity("Madison", "123");
    TagEntity tag = new TagEntity("hiking");
    TagEntity tag2 = new TagEntity("biking");

    TaglocationEntity tagLocation1 = new TaglocationEntity(1, 2, location, tag);
    TaglocationEntity tagLocation2 = new TaglocationEntity(3, 1, location, tag2);

    @Before
    public void setUp() throws Exception {
        allTagLocations.add(tagLocation1);
        allTagLocations.add(tagLocation2);

        LocationDao ldao = new LocationDao();
        TagDao tdao = new TagDao();

        ldao.save(location);
        tdao.save(tag);
        tdao.save(tag2);

        Long id = dao.save(tagLocation1);
        Long id2 = dao.save(tagLocation2);
        tagLocation1.setId(id);
        tagLocation2.setId(id2);
    }

    @After
    public void tearDown() throws Exception {

        DatabaseCleaner cleaner = new DatabaseCleaner();
        cleaner.run();
    }

    @Test
    public void getAllTagLocationTest() throws Exception {
        List<TaglocationEntity> expectedTagLocations = dao.findAll(TaglocationEntity.class);

        Assert.assertEquals("Incorrect number of tags", expectedTagLocations.size(), allTagLocations.size());

        for (int i = 0; i < expectedTagLocations.size(); i ++) {

            Assert.assertTrue("TagLocation not the same", expectedTagLocations.get(i).equals(allTagLocations.get(i)));
        }

    }


    @Test
    public void insertTagLocationTest() throws Exception {

        TagEntity tag = new TagEntity("boating");
        dao.save(tag);

        TaglocationEntity tagLocation2 = new TaglocationEntity(1, 2, location, tag);

        Long newId = dao.save(tagLocation2);

        allTagLocations.add(tagLocation2);
        tagLocation2.setId(newId);

        TaglocationEntity retrievedTag = dao.find(TaglocationEntity.class, newId);

        Assert.assertTrue("Id no matches", retrievedTag.equals(tagLocation2));

    }

    @Test
    public void getTagLocationById() throws Exception {
        TaglocationEntity tagLocation = dao.find(TaglocationEntity.class, tagLocation1.getId());

        Assert.assertTrue("Taglocation by id not the same", tagLocation.equals(tagLocation1));

    }

    @Test
    public void updateTag() throws Exception {
        tagLocation1.setRank(2);
        dao.update(tagLocation1);

        TaglocationEntity returnedTagLocation = dao.find(TaglocationEntity.class, tagLocation1.getId());

        Assert.assertTrue("TagLocation not updated properly", returnedTagLocation.equals(tagLocation1));

    }

    @Test
    public void deleteTag() throws Exception {
        dao.delete(TaglocationEntity.class, tagLocation2.getId());
        allTagLocations.remove(1);

        TaglocationEntity expectedTagLocation = dao.find(TaglocationEntity.class, tagLocation2.getId());

        Assert.assertNull(expectedTagLocation);

        List<TaglocationEntity> expectedTagLocations = dao.findAll(TaglocationEntity.class);

        Assert.assertEquals("Incorrect number of taglocations in database", expectedTagLocations.size(), allTagLocations.size());

    }

    @Test
    public void getRankByLocationAndTagTest() throws Exception {

        tagLocation1.setRank(3);
        dao.update(tagLocation1);

        TaglocationEntity returnedEntity = dao.getByLocationAndTag(location, tag);
        log.info(returnedEntity);

        Assert.assertTrue("Tag Locations not equal", tagLocation1.equals(returnedEntity));
    }

    @Test
    public void findByAndInitializeTagTest() throws Exception {
        List<TaglocationEntity> tagLocations = dao.findByAndInitializeTag("tag", tag);
        Assert.assertEquals("Wrong number of taglocations", tagLocations.size(), 1);
    }
}