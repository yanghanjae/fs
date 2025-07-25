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
@Table(name = "stock")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "stock_no", nullable = false)
	private int stockNo;
	
	@ManyToOne
	@JoinColumn(name = "product_no", nullable = false)
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "storage_no", nullable = false)
	private Storage storage;
	
	@Column(name = "stock_quantity", nullable = false)
	private int stockQuantity;
}
