package yhj.fs.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import yhj.fs.entity.Storage;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Integer> {

	
	// 검색어로 검색
	@Query(value = "SELECT s "
			+ "FROM Storage s "
			+ "WHERE (LOWER(s.storageName) LIKE LOWER(CONCAT('%', :search, '%'))) "
			+ "OR (CONCAT(s.storageNo, '') LIKE CONCAT('%', :search, '%'))" )
	Page<Storage> searchByName(@Param(value = "search") String search, Pageable pageable);

	
	@Query(value = "SELECT s "
			+ "FROM Storage s "
			+ "WHERE LOWER(s.storageName) LIKE LOWER(CONCAT('%', :storageName, '%')) ")	
	Optional<Storage> findByName(@Param(value = "storageName") String storageName);

}
