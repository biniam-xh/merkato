package edu.mum.mercato.repository;

import edu.mum.mercato.domain.Advert;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdvertRepository extends CrudRepository<Advert, Long> {
    public Optional<Advert> findFirstBy();
}
