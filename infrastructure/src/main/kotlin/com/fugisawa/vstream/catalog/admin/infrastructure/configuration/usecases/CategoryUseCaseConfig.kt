package com.fugisawa.vstream.catalog.admin.infrastructure.configuration.usecases

import com.fugisawa.vstream.catalog.admin.application.category.create.CreateCategoryUseCase
import com.fugisawa.vstream.catalog.admin.application.category.create.DefaultCreateCategoryUseCase
import com.fugisawa.vstream.catalog.admin.application.category.delete.DefaultDeleteCategoryUseCase
import com.fugisawa.vstream.catalog.admin.application.category.delete.DeleteCategoryUseCase
import com.fugisawa.vstream.catalog.admin.application.category.retrieve.get.DefaultGetCategoryByIdUseCase
import com.fugisawa.vstream.catalog.admin.application.category.retrieve.get.GetCategoryByIdUseCase
import com.fugisawa.vstream.catalog.admin.application.category.retrieve.list.DefaultListCategoriesUseCase
import com.fugisawa.vstream.catalog.admin.application.category.retrieve.list.ListCategoriesUseCase
import com.fugisawa.vstream.catalog.admin.application.category.update.DefaultUpdateCategoryUseCase
import com.fugisawa.vstream.catalog.admin.application.category.update.UpdateCategoryUseCase
import com.fugisawa.vstream.catalog.admin.domain.category.CategoryGateway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CategoryUseCaseConfig(
    private val categoryGateway: CategoryGateway,
) {
    @Bean
    fun createCategoryUseCase(): CreateCategoryUseCase = DefaultCreateCategoryUseCase(categoryGateway)

    @Bean
    fun deleteCategoryUseCase(): DeleteCategoryUseCase = DefaultDeleteCategoryUseCase(categoryGateway)

    @Bean
    fun updateCategoryUseCase(): UpdateCategoryUseCase = DefaultUpdateCategoryUseCase(categoryGateway)

    @Bean
    fun getCategoryByIdUseCase(): GetCategoryByIdUseCase = DefaultGetCategoryByIdUseCase(categoryGateway)

    @Bean
    fun listCategoriesUseCase(): ListCategoriesUseCase = DefaultListCategoriesUseCase(categoryGateway)
}