/**
 * 
 */
package cz.osu.oop3.proj.domain.product;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import cz.osu.oop3.proj.domain.EntityNotFoundException;
import cz.osu.oop3.proj.domain.sorting.OrderBy;
import cz.osu.oop3.proj.persistence.ProductDefinitionJpa;
import cz.osu.oop3.proj.persistence.ProductDefinitionRepository;

/**
 * @author Tomáš Rohrbacher (rohrbacher[at]elvoris.cz)
 *
 */
@Service
public class ProductsDefinitionServiceImpl implements ProductsDefinitionService {

	// Spring-data JPA repository
	private ProductDefinitionRepository repository;
	
	@Autowired
	public void setProductBackofficeRepository(ProductDefinitionRepository repository) {
		this.repository = repository;
	}



	/**
	 * {@inheritDoc}
	 * @see cz.osu.oop3.proj.domain.product.ProductsDefinitionService#loadAllProducts()
	 */
	@Override
	public List<ProductDefinitionDto> loadAllProducts()
	{
		final Iterable<ProductDefinitionJpa> allStoredTexts = repository.findAll();
		final List<ProductDefinitionDto> dtoList = StreamSupport.stream(allStoredTexts.spliterator(), false)
			.map(ProductsDefinitionServiceImpl::assembleDtoFromJpa)
			.collect(Collectors.toList());
		return dtoList;
	}

	@Override
	public List<ProductDefinitionDto> loadAllProducts(OrderBy... order)
	{
		final Optional<Sort> sort = orderArrayToSpringData(order);
		
		final Iterable<ProductDefinitionJpa> allStoredTexts;
		if (sort.isPresent()) {
			allStoredTexts = repository.findAll(sort.get());
		} else {
			allStoredTexts = repository.findAll();
		}
		final List<ProductDefinitionDto> dtoList = StreamSupport.stream(allStoredTexts.spliterator(), false)
			.map(ProductsDefinitionServiceImpl::assembleDtoFromJpa)
			.collect(Collectors.toList());
		return dtoList;
	}

	@Override
	public ProductDefinitionDto getProduct(long id) throws EntityNotFoundException
	{
		ProductDefinitionJpa entity = repository.findOne(id);
		if (entity == null) {
			throw new EntityNotFoundException("Entity Product not found for id: "+id);
		}
		return assembleDtoFromJpa(entity);
	}

	/**
	 * {@inheritDoc}
	 * @see cz.osu.oop3.proj.domain.product.ProductsDefinitionService#storeNewProduct(cz.osu.oop3.proj.domain.product.ProductDefinitionDto)
	 */
	@Override
	public ProductDefinitionDto storeNewProduct(ProductDefinitionDto obj)
	{
		ProductDefinitionJpa entity = assembleJpaFromDto(null, obj);
		entity = repository.save(entity);
		return assembleDtoFromJpa(entity);
	}

	/**
	 * {@inheritDoc}
	 * @return 
	 * @throws EntityNotFoundException 
	 * @see cz.osu.oop3.proj.domain.product.ProductsDefinitionService#updateProduct(cz.osu.oop3.proj.domain.product.ProductDefinitionDto)
	 */
	@Override
	public ProductDefinitionDto updateProduct(ProductDefinitionDto obj)
			throws EntityNotFoundException
	{
		ProductDefinitionJpa entity = repository.findOne(obj.getId());
		if (entity == null) {
			throw new EntityNotFoundException("Entity Product not found for id: "+obj.getId());
		}
		entity = assembleJpaFromDto(entity, obj);
		entity = repository.save(entity);
		return assembleDtoFromJpa(entity);
	}

	/**
	 * {@inheritDoc}
	 * @see cz.osu.oop3.proj.domain.product.ProductsDefinitionService#deleteProduct(long)
	 */
	@Override
	public void deleteProduct(long id) {
		// XXX jak se chova Spring-Data Repository, kdyz ji podsunu id neexistujiciho?
		repository.delete(id);
	}

	static ProductDefinitionDto assembleDtoFromJpa (ProductDefinitionJpa jpa)
	{
		final ProductDefinitionDto r = new ProductDefinitionDto();
		r.setId(jpa.getId());
		r.setName(jpa.getName());
		r.setCaption(jpa.getCaption());
		r.setPrice(jpa.getPrice());
		r.setSpecification(jpa.getSpecification());
		r.setImageUrl(jpa.getTags());
		r.setTags(jpa.getImageUrl());
		return r;
	}
	static ProductDefinitionJpa assembleJpaFromDto (ProductDefinitionJpa jpa, ProductDefinitionDto dto)
	{
		if (jpa == null) {
			jpa = new ProductDefinitionJpa();
		}
		jpa.setName(dto.getName());
		jpa.setCaption(dto.getCaption());
		jpa.setPrice(dto.getPrice());
		jpa.setSpecification(dto.getSpecification());
		jpa.setTags(dto.getImageUrl());
		jpa.setImageUrl(dto.getTags());
		return jpa;
	}

	static Optional<Sort> orderArrayToSpringData (OrderBy... order) {
		if (order == null || order.length == 0) {
			return Optional.empty();
		}
		final List<Order> springOrders = Arrays.stream(order)
				.map(ProductsDefinitionServiceImpl::orderToSpringData)
				.collect(Collectors.toList());
		return Optional.of(new Sort(springOrders));
	}

	static Order orderToSpringData (OrderBy order) {
		final Sort.Direction direction = order.getDirection() == OrderBy.Direction.ASC
				? Sort.Direction.ASC : Sort.Direction.DESC;
		return new Order(direction, order.getProperty());
	}

}
