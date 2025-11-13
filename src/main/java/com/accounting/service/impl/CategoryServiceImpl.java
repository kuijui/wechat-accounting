package com.accounting.service.impl;

import com.accounting.dto.CategoryResponse;
import com.accounting.entity.Category;
import com.accounting.mapper.CategoryMapper;
import com.accounting.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Category createCategory(Long userId, Category category) {
        category.setUserId(userId);
        category.setIsSystem(0);
        if (!StringUtils.hasText(category.getName())) {
            throw new RuntimeException("分类名称不能为空");
        }
        categoryMapper.insert(category);
        return categoryMapper.selectById(category.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Category updateCategory(Long userId, Long categoryId, Category category) {
        Category existing = categoryMapper.selectById(categoryId);
        if (existing == null || existing.getDeleted() != null && existing.getDeleted() == 1) {
            throw new RuntimeException("分类不存在");
        }
        if (existing.getIsSystem() != null && existing.getIsSystem() == 1) {
            throw new RuntimeException("系统分类不可修改");
        }
        if (!existing.getUserId().equals(userId)) {
            throw new RuntimeException("无权操作该分类");
        }
        existing.setName(StringUtils.hasText(category.getName()) ? category.getName() : existing.getName());
        existing.setIcon(StringUtils.hasText(category.getIcon()) ? category.getIcon() : existing.getIcon());
        existing.setColor(StringUtils.hasText(category.getColor()) ? category.getColor() : existing.getColor());
        existing.setType(category.getType() != null ? category.getType() : existing.getType());
        existing.setSortOrder(category.getSortOrder() != null ? category.getSortOrder() : existing.getSortOrder());
        categoryMapper.updateById(existing);
        return categoryMapper.selectById(categoryId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteCategory(Long userId, Long categoryId) {
        Category existing = categoryMapper.selectById(categoryId);
        if (existing == null || existing.getDeleted() != null && existing.getDeleted() == 1) {
            throw new RuntimeException("分类不存在");
        }
        if (existing.getIsSystem() != null && existing.getIsSystem() == 1) {
            throw new RuntimeException("系统分类不可删除");
        }
        if (!existing.getUserId().equals(userId)) {
            throw new RuntimeException("无权操作该分类");
        }
        return categoryMapper.deleteById(categoryId) > 0;
    }

    @Override
    public List<CategoryResponse> listCategories(Long userId, Integer type) {
        List<CategoryResponse> list = categoryMapper.selectCategoryList(userId, type);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list;
    }
}
