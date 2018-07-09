/*
 * Copyright 2018 Group 5.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.psu.ist411.presentation;

import edu.psu.ist411.data.User;
import edu.psu.ist411.domain.UserService;
import edu.psu.ist411.presentation.UserModels.UserView;
import edu.psu.ist411.presentation.UserModels.UserRequestBody;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

/**
 * Presentation-layer abstraction for users API.
 *
 * @author Tyler Suehr
 * @author David Wong
 * @author Steven Weber
 * @author Win Ton
 */
@Component
@Path("/users")
@Api(value = "/users", description = "Manage user details.")
public class UsersController {
    private final UserService userService;

    @Autowired
    public UsersController(final UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    private void prefillUsers() {
        final User userA = userService.createUser("a@a.com", "A", "A");
        final User userB = userService.createUser("b@b.com", "B", "B");
        final User userC = userService.createUser("c@c.com", "C", "C");
    }

    @ApiOperation(value = "Gets a page of users.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful operation.")
    })
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
    public Response getUsers(
        @ApiParam(value = "The page number.")
        @DefaultValue("0") @QueryParam("page") final int page,

        @ApiParam(value = "The number of results to return.")
        @DefaultValue("15") @QueryParam("results") final int results
    ) {
        final Page<UserView> modelPage = userService
            .getUsers(PageRequest.of(page, results))
            .map(UserView::new);
        return Response.status(200).entity(modelPage).build();
    }

    @ApiOperation(value = "Creates a user.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful operation.")
    })
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(
        @ApiParam(value = "The request body.")
        final UserRequestBody body
    ) {
        // Store and create the user.
        final User user = userService.createUser(
            body.getEmail(),
            body.getFirstName(),
            body.getLastName()
        );

        // Return the presentation-layer view as JSON.
        return Response.status(200).entity(new UserView(user)).build();
    }

    @ApiOperation(value = "Gets a user by ID.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful operation.")
    })
    @GET
    @Path("/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(
        @ApiParam(value = "The user ID.")
        @PathParam("userId") final long userId,

        @ApiParam(value = "The request body.")
        final UserRequestBody body
    ) {
        // Get the user.
        final User user = userService.getUser(userId);

        // Return the presentation-layer view as JSON.
        return Response.status(200).entity(new UserView(user)).build();
    }

    @ApiOperation(value = "Updates a user by ID.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful operation.")
    })
    @PUT
    @Path("/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(
        @ApiParam(value = "The user ID.")
        @PathParam("userId") final long userId,

        @ApiParam(value = "The request body.")
        final UserRequestBody body
    ) {
        // Update the user.
        final User user = userService.updateUser(
            userId,
            body.getEmail(),
            body.getFirstName(),
            body.getLastName()
        );

        // Return the presentation-layer view as JSON.
        return Response.status(200).entity(new UserView(user)).build();
    }

    @ApiOperation(value = "Deletes a user by ID.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful operation.")
    })
    @DELETE
    @Path("/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(
        @ApiParam(value = "The user ID.")
        @PathParam("userId") final long userId
    ) {
        // Delete the user.
        final User user = userService.deleteUser(userId);

        // Return the presentation-layer view as JSON.
        return Response.status(200).entity(new UserView(user)).build();
    }
}