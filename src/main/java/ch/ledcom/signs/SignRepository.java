package ch.ledcom.signs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

public interface SignRepository extends ElasticsearchCrudRepository<Sign, String> {

    Sign findByName(String name);

    Page<Sign> findByDescription(String description, Pageable pageable);

    @Query("{ \"match\" : {\"_all\" : \"?0\"}}")
    Page<Sign> findByAllFields(String query, Pageable pageable);
}
