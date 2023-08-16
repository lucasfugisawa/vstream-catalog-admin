package br.com.fugisawa.vstream.catalog.admin.domain

abstract class AggregateRoot<ID: Identifier> protected constructor (id: ID)
    : Entity<ID>(id)