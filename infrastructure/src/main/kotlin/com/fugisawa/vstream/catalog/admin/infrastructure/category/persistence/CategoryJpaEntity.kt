package com.fugisawa.vstream.catalog.admin.infrastructure.category.persistence

import com.fugisawa.vstream.catalog.admin.domain.category.Category
import com.fugisawa.vstream.catalog.admin.domain.category.CategoryID
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

@Entity(name = "Category")
@Table(name = "categories")
data class CategoryJpaEntity(
    @Id
    @Column(name = "id", nullable = false)
    var id: String,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "description", length = 4000)
    var description: String? = null,

    @Column(name = "active", nullable = false)
    var active: Boolean = false,

    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME(6)")
    var createdAt: Instant,

    @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME(6)")
    var updatedAt: Instant,

    @Column(name = "deleted_at", columnDefinition = "DATETIME(6)")
    var deletedAt: Instant? = null,
)

fun Category.toJpaEntity(): CategoryJpaEntity = CategoryJpaEntity(
    id = this.id.value,
    name = this.name,
    description = this.description,
    active = this.active,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    deletedAt = this.deletedAt,
)

fun CategoryJpaEntity.toAggregate(): Category = Category(
    id = CategoryID(this.id),
    name = this.name,
    description = this.description,
    active = this.active,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    deletedAt = this.deletedAt
)
