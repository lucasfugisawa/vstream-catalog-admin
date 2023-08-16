package br.com.fugisawa.vstream.catalog.admin.domain.validation

abstract class Validator protected constructor(val handler: ValidationHandler) {
    abstract fun validate()
}