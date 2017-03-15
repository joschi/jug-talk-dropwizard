package com.example.demo.resource;

import com.codahale.metrics.annotation.Timed;
import com.example.demo.InMemoryStore;
import com.example.demo.model.ImmutableKitten;
import com.example.demo.model.Kitten;
import com.example.demo.view.KittenView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Locale;
import java.util.Optional;

@Path("/kittens")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api("Nice kittens")
public class KittenResource {
    private final InMemoryStore store;

    public KittenResource(final InMemoryStore store) {
        this.store = store;
    }

    @GET
    @Timed
    @ApiOperation("Show all kittens")
    public Collection<Kitten> getAllKittens() {
        return store.getAll();
    }

    @POST
    @Timed
    @ApiOperation("Add cute kitten")
    public Response addKitten(@Valid final Kitten kitten,
                              @Context final UriInfo uriInfo) throws URISyntaxException {
        store.put(kittenWithImage(kitten, uriInfo.getBaseUriBuilder()));

        return Response.created(new URI("/" + kitten.getName())).build();
    }

    /*
     * Ensure that the given Kitten has a valid image.
     */
    private Kitten kittenWithImage(Kitten kitten, UriBuilder uriBuilder) {
        if (kitten.getImagePath() == null) {
            final String imgPath = uriBuilder
                    .path("images")
                    .path(kitten.getName().toLowerCase(Locale.ENGLISH) + ".jpg")
                    .build()
                    .toString();
            return ImmutableKitten.copyOf(kitten).withImagePath(imgPath);
        } else {
            return kitten;
        }
    }

    @GET
    @Path("{name}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
    @ApiOperation("Get kitten")
    public Optional<Kitten> getKitten(@ApiParam("Name of the Kitten to retrieve")
                                      @PathParam("name") String name) {
        return store.get(name);
    }

    @GET
    @Path("{name}/view")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_HTML})
    @ApiOperation("Show kitten")
    public KittenView getKittenView(@ApiParam("Name of the Kitten to retrieve")
                                    @PathParam("name") String name) {
        final Kitten kitten = store.get(name)
                .orElseThrow(() -> new NotFoundException("Kitten \"" + name + "\" not found"));

        return new KittenView(kitten);
    }
}
