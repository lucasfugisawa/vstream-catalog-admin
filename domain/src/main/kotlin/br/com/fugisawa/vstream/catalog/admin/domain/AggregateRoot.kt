package br.com.fugisawa.vstream.catalog.admin.domain

open class AggregateRoot<ID: Identifier> protected constructor (val id: ID)