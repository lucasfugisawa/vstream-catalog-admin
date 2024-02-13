package com.fugisawa.vstream.catalog.admin.infrastructure.category.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository: JpaRepository<CategoryJpaEntity, String>