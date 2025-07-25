package yhj.fs.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import yhj.fs.dto.StorageDTO;
import yhj.fs.entity.Storage;
import yhj.fs.repository.StorageRepository;

@Service
public class StorageService {
	
	private final StorageRepository STORAGE_REPOSITORY;
	private final ModelMapper modelMapper;
	
	public StorageService(StorageRepository SR, ModelMapper MM) {
		this.STORAGE_REPOSITORY = SR;
		this.modelMapper = MM;
	}
	

	// 전체 목록 출력
	public List<StorageDTO> findAllStorage () {
		List<Storage> storage = STORAGE_REPOSITORY.findAll(Sort.by("storageNo").descending());
		
		return storage.stream()
				.map(i -> modelMapper.map(i, StorageDTO.class))
				.collect(Collectors.toList());
	}
	
	
	// 검색어로 검색
	public Page<StorageDTO> searchStorageByName (int page, String search) {
		
		Pageable pageable = PageRequest.of(page - 1, 10, Sort.by("storageNo").descending());
		
		return STORAGE_REPOSITORY.searchByName(search, pageable).map(i -> modelMapper.map(i, StorageDTO.class));
	}
		
	
	@Transactional
	public void addStorage (StorageDTO storage) {
		
		STORAGE_REPOSITORY.save(modelMapper.map(storage, Storage.class));
	}
	
	// 특정 창고 찾기
	public Optional<StorageDTO> findByStorageName(String storageName) {
		
		return STORAGE_REPOSITORY.findByName(storageName).map(i -> modelMapper.map(i, StorageDTO.class));
	}
	
	public Optional<StorageDTO> findByStorageNo (int storageNo) {
		
		return STORAGE_REPOSITORY.findById(storageNo).map(i -> modelMapper.map(i, StorageDTO.class));
	}
	
	@Transactional
	public void deleteStorage (int storageNo) {
		
		STORAGE_REPOSITORY.deleteById(storageNo);
	}
}
