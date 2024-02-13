package com.fugisawa.vstream.catalog.admin.infrastructure.category

import com.fugisawa.vstream.catalog.admin.domain.category.Category
import com.fugisawa.vstream.catalog.admin.domain.category.CategoryGateway
import com.fugisawa.vstream.catalog.admin.domain.category.CategoryID
import com.fugisawa.vstream.catalog.admin.domain.category.CategorySearchCriteria
import com.fugisawa.vstream.catalog.admin.domain.pagination.Page
import com.fugisawa.vstream.catalog.admin.infrastructure.category.persistence.CategoryRepository
import com.fugisawa.vstream.catalog.admin.infrastructure.category.persistence.toAggregate
import com.fugisawa.vstream.catalog.admin.infrastructure.category.persistence.toJpaEntity
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class CategoryMySQLGateway(
    val categoryRepository: CategoryRepository,
) : CategoryGateway {

    override fun create(category: Category): Category = category
        .toJpaEntity()
        .let(categoryRepository::save)
        .toAggregate()

    override fun deleteById(id: CategoryID): Unit = TODO("Not yet implemented")

    override fun findById(id: CategoryID): Category? = TODO("Not yet implemented")

    override fun update(category: Category): Category = TODO("Not yet implemented")

    override fun findAll(criteria: CategorySearchCriteria): Page<Category> = TODO("Not yet implemented")
}