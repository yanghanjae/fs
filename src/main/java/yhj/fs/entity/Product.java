package yhj.fs.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "product")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_no", nullable = false)
	private int productNo;
	
	@Column(name = "product_name", nullable = false, length = 30)
	private String productName;
	
	@Column(name = "product_img_url", nullable = false, length = 500)
	private String productImgUrl;
	
	@Column(name = "product_unit", nullable = false, length = 5)
	private String productUnit;
	
	@Column(name = "product_price", nullable = false)
	private int productPrice;
	
	@ManyToOne
	@JoinColumn(name = "category_no", nullable = false)
	private Category category;
}