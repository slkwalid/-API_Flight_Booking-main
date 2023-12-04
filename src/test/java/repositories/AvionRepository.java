package repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.inject.Model;

import beans.Avion;
import java.util.List;

@Model
public class AvionRepository implements PanacheRepositoryBase<Avion, Long> {

    public List<Avion> findByOperator(String operatorParameter) {
        return find("operator", operatorParameter).list(); //Rechercher tous les avions !
    }

    public void machin() {
        findById(1L); //Rechercher un avion !
        persist(new Avion()); //Créer un avion !
    }
}
