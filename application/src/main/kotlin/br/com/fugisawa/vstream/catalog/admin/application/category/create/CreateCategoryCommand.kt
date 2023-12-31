package br.com.fugisawa.vstream.catalog.admin.application.category.create

data class CreateCategoryCommand(
    val name: String,
    val description: String,
    val active: Boolean,
) {
    companion object {
        fun with(
            name: String,
            description: String,
            active: Boolean,
        ) = CreateCategoryCommand(name, description, active)
    }
}