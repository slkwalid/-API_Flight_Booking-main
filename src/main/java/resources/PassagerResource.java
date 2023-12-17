package resources;

import beans.Passager;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import repositories.PassagerRepository;

import java.util.List;

@Path("/passengers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PassagerResource extends GenericResource {

    @Inject
    private PassagerRepository repository;

    @Inject
    Validator validator;

    // Méthode pour récupérer tous les passagers ou tous les passagers en fonction de leur nom de famille !
    @GET
    public Response getPassengers(@QueryParam("Surname") String surnameParameter) {
        List<Passager> list;
        if (StringUtils.isBlank(surnameParameter)) {
            list = repository.listAll();
        } else {
            list = repository.findBySurname(surnameParameter);
        }
        return getOr404(list);
    }

    // Méthode pour afficher un passager par son id !
    @GET
    @Path("/{id}")
    public Response getPassenger(@PathParam("id") Long id) {
        var passager = repository.findByIdOptional(id).orElse(null);
        return getOr404(passager);
    }

    // Méthode pour créer un passager !
    @POST
    @Transactional
    public Response createPassenger(Passager passager) {
        var violations = validator.validate(passager);
        if (!violations.isEmpty()) {
            return Response.status(400).entity(new ErrorWrapper(violations)).build();
        }
        try {
            repository.persistAndFlush(passager);
            return Response.status(201).build();
        } catch (PersistenceException e) {
            return Response.serverError().entity(new ErrorWrapper(e.getMessage())).build();
        }
    }

    // Méthode pour modifier un passager par son id !
    @PUT
    @Path("/{id}")
    @Transactional
    public Response updatePassenger(@PathParam("id") Long id, Passager updatedPassager) {
        var existingPassager = repository.findByIdOptional(id).orElse(null);

        if (existingPassager == null) {
            return Response.status(404).entity(new ErrorWrapper("Le passager n'existe pas !")).build();
        }

        var violations = validator.validate(updatedPassager);
        if (!violations.isEmpty()) {
            return Response.status(400).entity(new ErrorWrapper(violations)).build();
        }

        existingPassager.setSurname(updatedPassager.getSurname());
        existingPassager.setFirstname(updatedPassager.getFirstname());
        existingPassager.setEmail_address(updatedPassager.getEmail_address());

        try {
            repository.persistAndFlush(existingPassager);
            return Response.status(200).entity(existingPassager).build();
        } catch (PersistenceException e) {
            return Response.serverError().entity(new ErrorWrapper(e.getMessage())).build();
        }
    }

    // Méthode pour supprimer un passager par son id !
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletePassenger(@PathParam("id") Long id) {
        var passager = repository.findByIdOptional(id).orElse(null);

        if (passager == null) {
            return Response.status(404).entity(new ErrorWrapper("Le passager n'existe pas !")).build();
        }

        try {
            repository.delete(passager);
            return Response.status(204).build();
        } catch (PersistenceException e) {
            return Response.serverError().entity(new ErrorWrapper(e.getMessage())).build();
        }
    }
}
