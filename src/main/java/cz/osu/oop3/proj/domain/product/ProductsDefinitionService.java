/**
 * 
 */
package cz.osu.oop3.proj.domain.product;

import java.util.List;
import java.util.Objects;

import cz.osu.oop3.proj.domain.EntityNotFoundException;
import cz.osu.oop3.proj.domain.sorting.OrderBy;

/**
 * @author Tomáš Rohrbacher (rohrbacher[at]elvoris.cz)
 *
 */
public interface ProductsDefinitionService {

	List<ProductDefinitionDto> loadAllProducts();
	List<ProductDefinitionDto> loadAllProducts(OrderBy... order);
	
	List<ProductDefinitionDto> loadProductsByName(String searchByName, OrderBy... order);
	
	default List<ProductDefinitionDto> loadAllProducts(List<OrderBy> order)
	{
		if (order == null) {
			return loadAllProducts();
		} else {
			return loadAllProducts(
					order.stream()
					.filter(Objects::nonNull)
					.toArray(n -> new OrderBy[n]));
		}
	}
	
	ProductDefinitionDto getProduct(long id) throws EntityNotFoundException;

	ProductDefinitionDto storeNewProduct(ProductDefinitionDto obj);
	ProductDefinitionDto updateProduct(ProductDefinitionDto obj) throws EntityNotFoundException;
	void deleteProduct(long id) throws EntityNotFoundException;

//	static public class ProductOrdering
}
