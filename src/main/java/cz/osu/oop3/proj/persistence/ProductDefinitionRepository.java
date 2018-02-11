/**
 * 
 */
package cz.osu.oop3.proj.persistence;

import org.springframework.data.repository.CrudRepository;

/**
 * Spring-data repository interface
 * 
 * @author Tomáš Rohrbacher (rohrbacher[at]elvoris.cz)
 */
public interface ProductDefinitionRepository extends CrudRepository<ProductDefinitionJpa, Long>
{

}
