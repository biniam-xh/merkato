package edu.mum.mercato.serviceImpl;

import edu.mum.mercato.repository.CategoryRepository;
import edu.mum.mercato.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
}
