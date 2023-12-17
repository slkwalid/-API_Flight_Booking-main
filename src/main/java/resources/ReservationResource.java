package resources;

import beans.Reservation;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import repositories.ReservationRepository;

import java.util.List;

@Path("/reservations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservationResource extends GenericResource {

    @Inject
    private ReservationRepository repository;

    @Inject
    Validator validator;

    // Méthode pour afficher toutes les réservations !
    @GET
    public Response getReservations(@QueryParam("flightId") Integer flightId) {
        List<Reservation> list;
        if (flightId == null) {
            list = repository.listAll();
        } else {
            list = repository.findByFlightId(flightId);
        }
        return getOr404(list);
    }

    // Méthode pour retrouver une réservation par son id !
    @GET
    @Path("/{id}")
    public Response getReservation(@PathParam("id") Long id) {
        var reservation = repository.findByIdOptional(id).orElse(null);
        return getOr404(reservation);
    }

    // Méthode pour créer une réservation !
    @POST
    @Transactional
    public Response createReservation(Reservation reservation) {
        var violations = validator.validate(reservation);
        if (!violations.isEmpty()) {
            return Response.status(400).entity(new ErrorWrapper(violations)).build();
        }
        try {
            repository.persistAndFlush(reservation);
            return Response.status(201).build();
        } catch (PersistenceException e) {
            return Response.serverError().entity(new ErrorWrapper(e.getMessage())).build();
        }
    }

    // Méthode pour modifier une réservation par son id !
    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateReservation(@PathParam("id") Long id, Reservation updatedReservation) {
        var existingReservation = repository.findByIdOptional(id).orElse(null);

        if (existingReservation == null) {
            return Response.status(404).entity(new ErrorWrapper("La réservation n'existe pas !")).build();
        }

        var violations = validator.validate(updatedReservation);
        if (!violations.isEmpty()) {
            return Response.status(400).entity(new ErrorWrapper(violations)).build();
        }

        existingReservation.setFlight_id(updatedReservation.getFlight_id());
        existingReservation.setPassenger_id(updatedReservation.getPassenger_id());

        try {
            repository.persistAndFlush(existingReservation);
            return Response.status(200).entity(existingReservation).build();
        } catch (PersistenceException e) {
            return Response.serverError().entity(new ErrorWrapper(e.getMessage())).build();
        }
    }

    // Méthode pour supprimer une réservation par son id !
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteReservation(@PathParam("id") Long id) {
        var reservation = repository.findByIdOptional(id).orElse(null);

        if (reservation == null) {
            return Response.status(404).entity(new ErrorWrapper("The reservation does not exist")).build();
        }

        try {
            repository.delete(reservation);
            return Response.status(204).build();
        } catch (PersistenceException e) {
            return Response.serverError().entity(new ErrorWrapper(e.getMessage())).build();
        }
    }
}