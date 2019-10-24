package edu.mum.mercato.repository;

import edu.mum.mercato.domain.AddImage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddImageRepository extends CrudRepository<AddImage,Long> {
}
