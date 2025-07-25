package yhj.fs.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import yhj.fs.dto.CategoryDTO;
import yhj.fs.entity.Category;
import yhj.fs.repository.CategoryRepository;

@Service
public class CategoryService {
	
	private final CategoryRepository CATEGORY_REPOSITORY;
	private final ModelMapper MODEL_MAPPER;
	
	public CategoryService(CategoryRepository CR, ModelMapper MM) {
		this.CATEGORY_REPOSITORY = CR;
		this.MODEL_MAPPER = MM;
	}
	
	// 전체 목록 출력
	public List<CategoryDTO> findAllCategory () {
		List<Category> category = CATEGORY_REPOSITORY.findAll(Sort.by("categoryName").descending());
		
		return category.stream()
				.map(i -> MODEL_MAPPER.map(i, CategoryDTO.class))
				.collect(Collectors.toList());
	}

}
