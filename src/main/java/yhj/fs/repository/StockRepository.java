package yhj.fs.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import yhj.fs.entity.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

	
	// 창고 미지정시 상품명으로 검색
	@Query(value = "SELECT s "
			+ "FROM Stock s "
			+ "JOIN s.product p "
			+ "WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :search, '%')) ")
	Page<Stock> searchByName (@Param(value = "search") String search, Pageable pageable);
	
	// 창고 지정시 상품명으로 검색
	@Query(value = "SELECT s "
			+ "FROM Stock s "
			+ "JOIN s.product p "
			+ "JOIN s.storage st "
			+ "WHERE (LOWER(p.productName) LIKE LOWER(CONCAT('%', :search, '%'))) "
			+ "AND (st.storageNo = :storageNo) ")
	Page<Stock> search (@Param(value = "search") String search,@Param(value = "storageNo") Integer storageNo,Pageable pageable);

	// 상품 번호와 창고 번호로 재고 검색
	@Query(value = "SELECT s "
			+ "FROM Stock s "
			+ "JOIN s.product p "
			+ "JOIN s.storage st "
			+ "WHERE (p.productNo = :productNo) AND "
			+ "(st.storageNo = :storageNo) ")
	Optional<Stock> checkStockExistence(@Param(value = "productNo") int productNo, @Param(value = "storageNo") int storageNo);
}
