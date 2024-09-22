package com.fugisawa.vstream.catalog.admin.infrastructure.category

import com.fugisawa.vstream.catalog.admin.domain.category.Category
import com.fugisawa.vstream.catalog.admin.domain.category.CategoryGateway
import com.fugisawa.vstream.catalog.admin.domain.category.CategoryID
import com.fugisawa.vstream.catalog.admin.domain.category.CategorySearchQuery
import com.fugisawa.vstream.catalog.admin.domain.pagination.Pagination
import com.fugisawa.vstream.catalog.admin.infrastructure.category.persistence.CategoryJpaEntity
import com.fugisawa.vstream.catalog.admin.infrastructure.category.persistence.CategoryRepository
import com.fugisawa.vstream.catalog.admin.infrastructure.category.persistence.toAggregate
import com.fugisawa.vstream.catalog.admin.infrastructure.category.persistence.toJpaEntity
import com.fugisawa.vstream.catalog.admin.infrastructure.utils.like
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull


@Service
class CategoryMySQLGateway(
    val categoryRepository: CategoryRepository,
) : CategoryGateway {

    override fun create(category: Category): Category =
        category.toJpaEntity().let(categoryRepository::save).toAggregate()

    override fun deleteById(id: CategoryID) {
        if (categoryRepository.existsById(id.value)) categoryRepository.deleteById(id.value)
    }

    override fun findById(id: CategoryID): Category? = categoryRepository.findById(id.value).getOrNull()?.toAggregate()

    override fun update(category: Category): Category =
        category.toJpaEntity().let(categoryRepository::save).toAggregate()

    override fun existsByIds(ids: Iterable<CategoryID>): List<CategoryID> =
        ids.map { it.value }.let(categoryRepository::existsByIds).map(::CategoryID)

    override fun findAll(query: CategorySearchQuery): Pagination<Category> {
        val page = PageRequest.of(
            query.page,
            query.pageSize,
            Sort.by(Sort.Direction.fromString(query.direction.name), query.sort),
        )

        val specifications = query.terms.let(this::assembleSpecification)

        val pageResult = categoryRepository.findAll(Specification.where(specifications), page)

        return Pagination<Category>(current = pageResult.number,
            size = pageResult.size,
            total = pageResult.totalElements,
            items = pageResult.mapNotNull { it?.toAggregate() }
        )
    }

    private fun assembleSpecification(str: String): Specification<CategoryJpaEntity> {
        val nameLike: Specification<CategoryJpaEntity> = like("name", str)
        val descriptionLike: Specification<CategoryJpaEntity> = like("description", str)
        return nameLike.or(descriptionLike)
    }


}

