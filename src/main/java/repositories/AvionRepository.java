package repositories;

import beans.Avion;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.inject.Model;

import java.util.List;

@Model
public class AvionRepository implements PanacheRepositoryBase<Avion, Long> {

    public List<Avion> findByOperator(String operatorParameter) {
        return find("operator", operatorParameter).list(); //Rechercher les avions par leurs compagnies !
    }

    public void createPlane() {
        persist(new Avion()); //Créer un avion
    }
}
