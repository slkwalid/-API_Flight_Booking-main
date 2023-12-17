package resources;

import beans.Vol;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import repositories.AvionRepository;
import repositories.VolRepository;
import java.util.List;


@Path("/flights")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VolResource extends GenericResource {

    @Inject
    private VolRepository repository; 
    @Inject
    private AvionRepository avionRepository;

    @Inject
    Validator validator;

    // Method to get every flight depending on the destination 
    @GET
    public Response getFlights(@QueryParam("destination") String destination) {
        List<Vol> list;
        if (StringUtils.isBlank(destination)) {
            list = repository.listAll();
        } else {
            list = repository.findByDestination(destination);
        }
        return getOr404(list);
    }

    // Get plane by id
    @GET
    @Path("/{id}")
    public Response getFlight(@PathParam("id")Long id) {
        var vol = repository.findByIdOptional(id).orElse(null); //Permet de prendre en compte le fait que la réponse est vide !
        return getOr404(vol);
    }

    // Create a flight
    @POST
    @Transactional
    public Response createFlight(Vol vol) {
        var violations = validator.validate(vol);
        if(!violations.isEmpty()){ //Si l'avion n'est pas valide !
            return Response.status(400).entity(new ErrorWrapper(violations)).build(); //Erreur au niveau du client !
        }

        var idAvion = avionRepository.findById(vol.getPlane_id().getId());
        if(!idAvion.isPersistent()){ //Si l'id de l'avion n'est pas trouvé !
            return Response.status(400).entity(new ErrorWrapper(String.valueOf(idAvion))).build(); //Erreur au niveau du client !
        }else {
            vol.setPlane_id(idAvion);
        }

        try {
            repository.persistAndFlush(vol);
            return Response.status(201).build();
        } catch (PersistenceException e) {
            return Response.serverError().entity(new ErrorWrapper(e.getMessage())).build();
        }
    }

    // Update flight by its id
    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateFlight(@PathParam("id") Long id, Vol updatedVol) {
        var ExistingFlight = repository.findByIdOptional(id).orElse(null);
        if (ExistingFlight == null) {
            return Response.status(404).entity(new ErrorWrapper("Le vol n'existe pas !")).build();
        }

        var idAvion = avionRepository.findByIdOptional(updatedVol.getPlane_id().getId()).orElse(null);
        if (idAvion == null) {
            return Response.status(404).entity(new ErrorWrapper("Le vol n'existe pas !")).build();
        }

        var violations = validator.validate(updatedVol);
        if (!violations.isEmpty()) {
            return Response.status(400).entity(new ErrorWrapper(violations)).build();
        }

        ExistingFlight.setNumber(updatedVol.getNumber());
        ExistingFlight.setOrigin(updatedVol.getOrigin());
        ExistingFlight.setDestination(updatedVol.getDestination());
        ExistingFlight.setDeparture_date(updatedVol.getDeparture_date());
        ExistingFlight.setDeparture_time(updatedVol.getDeparture_time());
        ExistingFlight.setArrival_date(updatedVol.getArrival_date());
        ExistingFlight.setArrival_time(updatedVol.getArrival_time());
        ExistingFlight.setPlane_id(updatedVol.getPlane_id());

        try {
            repository.persistAndFlush(ExistingFlight);
            return Response.status(200).entity(ExistingFlight).build();
        } catch (PersistenceException e) {
            return Response.serverError().entity(new ErrorWrapper(e.getMessage())).build();
        }
    }

    // delete flight by id
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteFlight(@PathParam("id") Long id) {
        var vol = repository.findByIdOptional(id).orElse(null);

        if (vol == null) {
            return Response.status(404).entity(new ErrorWrapper("Le vol n'existe pas !")).build();
        }

        try {
            repository.delete(vol);
            return Response.status(204).build();
        } catch (PersistenceException e) {
            return Response.serverError().entity(new ErrorWrapper(e.getMessage())).build();
        }
    }
}