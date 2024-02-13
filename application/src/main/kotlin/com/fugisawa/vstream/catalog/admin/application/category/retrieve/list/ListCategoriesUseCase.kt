package com.fugisawa.vstream.catalog.admin.application.category.retrieve.list

import com.fugisawa.vstream.catalog.admin.application.UseCase
import com.fugisawa.vstream.catalog.admin.domain.category.CategorySearchQuery
import com.fugisawa.vstream.catalog.admin.domain.pagination.Pagination

abstract class ListCategoriesUseCase: UseCase<CategorySearchQuery, Pagination<CategoryListOutput>>()