package com.sarah.persistence;

import com.sarah.entity.TagEntity;
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
public class TagDaoTest {
    private final Logger log = Logger.getLogger(this.getClass());
    TagDao dao = new TagDao();

    TagEntity tag1 = new TagEntity("hiking");
    TagEntity tag2 = new TagEntity("camping");
    List<TagEntity> allTags = new ArrayList<TagEntity>();

    @Before
    public void setUp() throws Exception {
        allTags.add(tag1);
        allTags.add(tag2);
        Long id = dao.save(tag1);
        Long id2 = dao.save(tag2);
        tag1.setId(id);
        tag2.setId(id2);

    }

    @After
    public void tearDown() throws Exception {

        DatabaseCleaner cleaner = new DatabaseCleaner();
        cleaner.run();

    }

    @Test
    public void getAllTags() throws Exception {
        List<TagEntity> expectedTags = dao.findAll(TagEntity.class);

        Assert.assertEquals("Incorrect number of tags", expectedTags.size(), allTags.size());

        for (int i = 0; i < expectedTags.size(); i ++) {

            Assert.assertTrue("Tags not the same", expectedTags.get(i).equals(allTags.get(i)));
        }

    }

    @Test
    public void insertTag() throws Exception {
        TagEntity newTag = new TagEntity("skiing");

        Long newId = dao.save(newTag);

        allTags.add(newTag);
        newTag.setId(newId);

        TagEntity retrievedTag = dao.find(TagEntity.class, newId);

        Assert.assertTrue("Id no matches", retrievedTag.equals(newTag));

    }

    @Test
    public void getTagById() throws Exception {
        TagEntity tag = dao.find(TagEntity.class, tag1.getId());

        Assert.assertTrue("Tag by id not the same", tag.equals(tag1));

    }

    @Test
    public void updateTag() throws Exception {
        tag1.setName("biking");
        dao.update(tag1);

        TagEntity returnedTag = dao.find(TagEntity.class, tag1.getId());

        Assert.assertTrue("Tag not updated properly", returnedTag.equals(tag1));

    }

    @Test
    public void deleteTag() throws Exception {
        dao.delete(TagEntity.class, tag2.getId());
        allTags.remove(1);

        TagEntity expectedTag = dao.find(TagEntity.class, tag2.getId());

        Assert.assertNull(expectedTag);

        List<TagEntity> expectedAllTags = dao.findAll(TagEntity.class);

        Assert.assertEquals("Incorrect number of tags in database", expectedAllTags.size(), allTags.size());

    }



}