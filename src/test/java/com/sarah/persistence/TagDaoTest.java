package com.sarah.persistence;

import com.sarah.entity.TagEntity;
import com.sarah.utility.DatabaseCleaner;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Test
    public void getReviews() throws Exception {
        List<String> ids = new ArrayList<>(Arrays.asList());
        for (String id : ids) {
            URI baseURI = UriBuilder.fromUri("https://maps.googleapis.com/maps/api/place/details/json?placeid=" + id + "&key=AIzaSyA_wVJfh8Ov9cLUZDxSNhOpzw3OEx6y3HE").build();

            Client client = ClientBuilder.newClient();

            WebTarget target = client.target(baseURI);

            String response = target.request().accept(MediaType.APPLICATION_JSON).get(String.class);
            log.info(response);
            // place_id
            // rating (for whole location)
            // reviews.author_name, reviews[i].rating, reviews[i].text

            try {
                JSONObject jsonObj = new JSONObject(response);
                JSONObject result = jsonObj.getJSONObject("result");

                String placeId = result.getString("place_id");
                JSONArray reviews = result.getJSONArray("reviews");

                for (int i = 0; i < reviews.length(); i++) {
                    String body = reviews.getJSONObject(i).getString("text");

                    String cleanBody = StringUtils.remove(body, "'");
                    String rating = reviews.getJSONObject(i).getString("rating");
                    String author = StringUtils.remove(reviews.getJSONObject(i).getString("author_name"), "'");

                    String sql = "INSERT INTO googleReviews (placeId, body, rating, author) VALUES ('" + placeId + "','" + cleanBody + "','" + rating + "', '" + author + "')";
                    log.info(sql);

                    BufferedWriter writer = new BufferedWriter(new FileWriter("googleReviews.sql", true));
                    writer.append(sql);
                    writer.newLine();

                    writer.close();
                }

            } catch (JSONException e) {
                log.error("JSON Exception: ", e);

            } catch (HibernateException he) {
                log.error("Error adding reviews: ", he);

            } catch (FileNotFoundException he) {
                log.error("File not found: ", he);

            } catch (IOException he) {
                log.error("Error writing sql to page: ", he);

            }
        }

    }

}