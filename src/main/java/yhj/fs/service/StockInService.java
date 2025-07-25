package yhj.fs.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import yhj.fs.dto.StockInDTO;
import yhj.fs.entity.StockIn;
import yhj.fs.repository.ProductRepository;
import yhj.fs.repository.StockInRepository;
import yhj.fs.repository.StorageRepository;

@Service
public class StockInService {

	private final StockInRepository STOCK_IN_REPOSITORY;
	private final StorageRepository STORAGE_REPOSITORY;
	private final ProductRepository PRODUCT_REPOSITORY;
	private final ModelMapper modelMapper;
	
	public StockInService(StockInRepository SIR, StorageRepository SR,
			ProductRepository PR, ModelMapper MM) {
		this.STOCK_IN_REPOSITORY = SIR;
		this.STORAGE_REPOSITORY = SR;
		this.PRODUCT_REPOSITORY = PR;
		this.modelMapper = MM;
	}
	
	// 창고 미지정시
	public Page<StockInDTO> searchByName (int page, String search) {
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("stockInNo").descending());
		
		return STOCK_IN_REPOSITORY.searchByName(search, pageable).map(i -> modelMapper.map(i, StockInDTO.class));
	}
	
	// 창고 지정시
	public Page<StockInDTO> search (int page, String search, int storageNo) {
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("stockInNo").descending());
		
		return STOCK_IN_REPOSITORY.search(search, storageNo, pageable).map(i -> modelMapper.map(i, StockInDTO.class));
	}
	
	@Transactional
	public void addStockIn (StockInDTO stockIn) {
		
		stockIn.setProduct(PRODUCT_REPOSITORY.findById(stockIn.getProductNo()).get());
		stockIn.setStorage(STORAGE_REPOSITORY.findById(stockIn.getStorageNo()).get());
		
		STOCK_IN_REPOSITORY.save(modelMapper.map(stockIn, StockIn.class));
	}
	
	// detail 페이지 (번호로 찾아서 하나 표현)
	public Optional<StockInDTO> findbyStockInNo (int stockInNo) {
		
		return STOCK_IN_REPOSITORY.findById(stockInNo).map(i -> modelMapper.map(i, StockInDTO.class));
	}
}
