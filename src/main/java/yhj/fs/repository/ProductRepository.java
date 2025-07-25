package yhj.fs.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import yhj.fs.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	
	// 카테고리 미지정시 검색기능
	@Query(value = "SELECT p "
			+ "FROM Product p "
			+ "WHERE (LOWER(p.productName) LIKE LOWER(CONCAT('%', :search, '%'))) "
			+ "OR (CONCAT(p.productNo, '') LIKE CONCAT('%', :search, '%'))" )
	Page<Product> searchByName (@Param(value = "search") String search, Pageable pageable);

	
	// 카테고리 지정시 검색기능
	@Query(value = "SELECT p "
			+ "FROM Product p "
			+ "WHERE ((LOWER(p.productName) LIKE LOWER(CONCAT('%', :search, '%'))) "
			+ "OR (CONCAT(p.productNo, '') LIKE CONCAT('%', :search, '%'))) "
			+ "AND p.category.categoryNo = :category")
	Page<Product> search(@Param(value = "search") String search,@Param(value = "category") Integer category, Pageable pageable);

}
