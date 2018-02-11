/**
 * 
 */
package cz.osu.oop3.proj.persistence;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 * @author Tomáš Rohrbacher (rohrbacher[at]elvoris.cz)
 *
 */
// TODO priste pouzit Kotlin a jeho data class
// TODO jak jednoduše dostat JPA anotace PRYČ z business modelu a napojit JPA entity a persistenci jako plug-in?
// 		a) překlápět z jednoho objektu do druhého? jak jednoduše a efektivně?
//
@Entity(name="ProductDefinition")
public class ProductDefinitionJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
    @Version
    private Integer version;
    
	@Column(name="name", length=128, nullable=false)
	private String name;

	@Column(name="caption", length=256, nullable=true)
	private String caption;
	
	@Column(name="price", scale=2, precision=12, nullable=false)
	private BigDecimal price;
	
	@Column(name="imageUrl", length=256, nullable=true)
	private String imageUrl;
	
	@Column(name="specification", length=2048, nullable=true)
	private String specification;

	@Column(name="tags", length=256, nullable=true)
	private String tags;
	
	public ProductDefinitionJpa() {
		super();
	}
	
	public ProductDefinitionJpa(String name, String caption, BigDecimal price, 
			String specification, String tags, String imageUrl)
	{
		super();
		this.name = name;
		this.caption = caption;
		this.specification = specification;
		this.tags = tags;
		this.imageUrl = imageUrl;
	}

	public Long getId() {
		return id;
	}
	public Integer getVersion() {
		return version;
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
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
}
