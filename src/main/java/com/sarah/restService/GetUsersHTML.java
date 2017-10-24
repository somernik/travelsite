package com.sarah.restService;

/**
 * Created by sarah on 10/22/2017.
 */
import com.sarah.entity.User;
import com.sarah.persistence.UserDao;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/HTML")
public class GetUsersHTML {
    private UserDao userDao = new UserDao();
    // The Java method will process HTTP GET requests
    @GET
    @Produces({"text/html", "text/plain"})
    public Response showOptionsHTML() {
        String output = "Use /HTML/users to display all users. Use /HTML/users/<id> to display a specific user";
        return Response.status(200).entity(output).build();
    }

    // The Java method will process HTTP GET requests
    @GET
    @Produces({"text/html", "text/plain"})
    @Path("/users")
    public Response getAllUsers() {
        List<User> users = userDao.getAllUsersWithPrivileges();

        String output = "";
        for (User user: users) {
            output += user.toStringJSON();
        }
        return Response.status(200).entity(output).build();
    }

    @GET
    @Produces({"text/html", "text/plain"})
    @Path("users/{id}")
    public Response getSingleUser(@PathParam("id") String id) {
        if (!isNumeric(id)) {
            return Response.status(400).entity("Ids should be numeric").build();
        }

        User user = userDao.getUserById(Integer.parseInt(id));
        String output;

        if (user != null) {
            output = user.toString();
            return Response.status(200).entity(output).build();

        } else {
            output = "User does not exist";
            return Response.status(404).entity(output).build();
        }
    }


    public static boolean isNumeric(String str)
    {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
}