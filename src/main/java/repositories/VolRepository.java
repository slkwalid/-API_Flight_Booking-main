package repositories;

import beans.Vol;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.inject.Model;

import java.util.List;

@Model
public class VolRepository implements PanacheRepositoryBase<Vol, Long> {

    public List<Vol> findByDestination(String destinationParameter) {
        return find("destination", destinationParameter).list();
    }

    public void createFlight(Vol flight) {
        persist(flight);
    }
}
