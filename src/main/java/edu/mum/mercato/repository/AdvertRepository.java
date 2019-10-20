package edu.mum.mercato.repository;

import edu.mum.mercato.domain.Advert;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertRepository extends CrudRepository<Advert, Long> {
}
