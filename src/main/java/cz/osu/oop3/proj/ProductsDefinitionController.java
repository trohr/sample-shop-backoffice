package cz.osu.oop3.proj;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.osu.oop3.proj.domain.EntityNotFoundException;
import cz.osu.oop3.proj.domain.product.ProductDefinitionDto;
import cz.osu.oop3.proj.domain.product.ProductsDefinitionService;


@Controller
@RequestMapping(ProductsDefinitionController.PATH_PRODUCT_DEFINITIONS)
public class ProductsDefinitionController {

	static final String PATH_PRODUCT_DEFINITIONS = "/products";
	static final String EDIT_PATH(long id) {
		return String.format("%s/%d/%s/",
				PATH_PRODUCT_DEFINITIONS,
				id,
				"edit"
		);
	}


	// Spring-data JPA repository
	@Autowired ProductsDefinitionService service;
	
	
	public ProductsDefinitionController() {
		System.out.println("Controller start");
	}

	
	
	// 
	// LIST
	// 
	
	/** 1) List all page */
	@GetMapping
	public String getAllProductDefinitions (Model model)
	{
		final List<ProductDefinitionDto> products = service.loadAllProducts();
		model.addAttribute("products", products);
		return "product-definitions-list";
	}


	
	// 
	// CREATE
	// 

	/**
	 * 2) GET page with form to create a new product
	 */
	@GetMapping("/create")
	public String getCreateNewProductPage (Model model)
	{
		return "product-definition-detail";
	}

	/** 3) POST for new product */
	@PostMapping("/create")
	public String addProductDefinition (
			ProductDefinitionDto content, Model model)
	{
		Assert.notNull(content, "Content cannot be null");
		
		// Store in a DB
		final ProductDefinitionDto createdProduct = service.storeNewProduct(content);
		
//		// Return the product model to the view
//		model.addAttribute("newProduct", createdProduct);

		return "redirect:"+EDIT_PATH(createdProduct.getId());
//		return "redirect:"+PATH_PRODUCT_DEFINITIONS;
	}



	// 
	// EDIT
	// 

	/**
	 * 4) GET page with form to edit an existing product
	 */
	@GetMapping("/{productID}/edit")
	public String getEditProductPage (
			@PathVariable(name="productID", required=true) long productID,
			Model model
			)
			throws EntityNotFoundException // TODO handle the exception using spring-web handler
	{
		// Retrieve
		final ProductDefinitionDto product = service.getProduct(productID);
		// Return information to the view
		model.addAttribute("product", product);
		return "product-definition-detail";
	}

	/** 5) POST for updating of the product */
	@PostMapping("/{productID}/edit")
	public String updateProductDefinition (
			@PathVariable(name="productID", required=true) long productID,
			ProductDefinitionDto content, Model model
			)
			throws EntityNotFoundException // TODO handle the exception using spring-web handler
	{
		Assert.notNull(content, "Content cannot be null");
		
		// Store in a DB
		content.setId(productID);
		final ProductDefinitionDto updatedProduct = service.updateProduct(content);

//		// Return the product model to the view
//		model.addAttribute("updatedProduct", updatedProduct);

		return "redirect:"+EDIT_PATH(updatedProduct.getId());
//		return "redirect:"+PATH_PRODUCT_DEFINITIONS;
	}



	// 
	// DELETE
	// 
	/** 6) POST for deleting of the product (in REST should be DELETE) */
	@PostMapping("/{productID}/delete")
	public String deleteProductDefinition (
			@PathVariable(name="productID", required=true) long productID,
			Model model
			)
			throws EntityNotFoundException // TODO handle the exception using spring-web handler
	{
		// Retrieve
		final ProductDefinitionDto product = service.getProduct(productID);
		// Delete
		service.deleteProduct(product.getId());
		// Return information to the view
		model.addAttribute("deletedProduct", product);
		
		return "redirect:"+PATH_PRODUCT_DEFINITIONS;
	}

}
