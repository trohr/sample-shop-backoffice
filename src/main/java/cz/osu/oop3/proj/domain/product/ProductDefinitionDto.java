/**
 * 
 */
package cz.osu.oop3.proj.domain.product;

import java.math.BigDecimal;

/**
 * @author Tomáš Rohrbacher (rohrbacher[at]elvoris.cz)
 *
 */
// TODO priste pouzit Kotlin a jeho data class
public class ProductDefinitionDto {

	private Long id;

	private String name;
	private String caption;
	private BigDecimal price;
	private String specification;
	private String imageUrl;
	private String tags;
	
	public ProductDefinitionDto() {
		super();
	}
	
	public ProductDefinitionDto(
			String name, String caption, BigDecimal price,
			String specification, String imageUrl, String tags) {
		super();
		this.name = name;
		this.caption = caption;
		this.specification = specification;
		this.imageUrl = imageUrl;
		this.tags = tags;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getSpecification() {
		return specification;
	}
	public void setSpecification(String specification) {
		this.specification = specification;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	
}
