package com.fugisawa.vstream.catalog.admin.application.category.retrieve.list

import com.fugisawa.vstream.catalog.admin.application.UseCase
import com.fugisawa.vstream.catalog.admin.domain.category.CategorySearchCriteria
import com.fugisawa.vstream.catalog.admin.domain.pagination.Page

abstract class ListCategoriesUseCase: UseCase<CategorySearchCriteria, Page<CategoryListOutput>>()