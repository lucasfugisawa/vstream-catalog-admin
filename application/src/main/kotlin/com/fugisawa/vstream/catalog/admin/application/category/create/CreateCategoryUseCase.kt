package com.fugisawa.vstream.catalog.admin.application.category.create

import com.fugisawa.vstream.catalog.admin.application.utils.Either
import com.fugisawa.vstream.catalog.admin.application.UseCase
import com.fugisawa.vstream.catalog.admin.domain.validation.Notification

abstract class CreateCategoryUseCase: UseCase<CreateCategoryCommand, Either<Notification, CreateCategoryOutput>>()