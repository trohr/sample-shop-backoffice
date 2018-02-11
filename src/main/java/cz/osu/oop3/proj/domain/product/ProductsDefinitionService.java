/**
 * 
 */
package cz.osu.oop3.proj.domain.product;

import java.util.List;

import cz.osu.oop3.proj.domain.EntityNotFoundException;

/**
 * @author Tomáš Rohrbacher (rohrbacher[at]elvoris.cz)
 *
 */
public interface ProductsDefinitionService {

	List<ProductDefinitionDto> loadAllProducts();
//	List<ProductDefinitionDto> loadAllProducts(ProductOrdering order);
	
	ProductDefinitionDto getProduct(long id) throws EntityNotFoundException;

	ProductDefinitionDto storeNewProduct(ProductDefinitionDto obj);
	ProductDefinitionDto updateProduct(ProductDefinitionDto obj) throws EntityNotFoundException;
	void deleteProduct(long id) throws EntityNotFoundException;

//	static public class ProductOrdering
}
