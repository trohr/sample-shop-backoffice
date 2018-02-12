/**
 * 
 */
package cz.osu.oop3.proj.persistence;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Spring-data repository interface.
 * <p>
 * Supports CRUD and sorting operations.
 * 
 * @author Tomáš Rohrbacher (rohrbacher[at]elvoris.cz)
 */
public interface ProductDefinitionRepository extends PagingAndSortingRepository<ProductDefinitionJpa, Long>
{

}
