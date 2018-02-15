/**
 * 
 */
package cz.osu.oop3.proj.persistence;

import java.util.List;

import org.springframework.data.domain.Sort;
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
	List<ProductDefinitionJpa> findProductByName(String name);
	List<ProductDefinitionJpa> findProductByName(String name, Sort sort);
}
