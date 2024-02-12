package com.fugisawa.vstream.catalog.admin.application.category.update

import com.fugisawa.vstream.catalog.admin.application.UseCase
import com.fugisawa.vstream.catalog.admin.application.utils.Either
import com.fugisawa.vstream.catalog.admin.domain.validation.Notification

abstract class UpdateCategoryUseCase : UseCase<UpdateCategoryCommand, Either<Notification, UpdateCategoryOutput>>()