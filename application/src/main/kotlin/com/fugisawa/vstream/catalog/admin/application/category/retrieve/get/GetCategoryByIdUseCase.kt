package com.fugisawa.vstream.catalog.admin.application.category.retrieve.get

import com.fugisawa.vstream.catalog.admin.application.UseCase
import com.fugisawa.vstream.catalog.admin.domain.category.CategoryID

abstract class GetCategoryByIdUseCase : UseCase<CategoryID, CategoryOutput>()