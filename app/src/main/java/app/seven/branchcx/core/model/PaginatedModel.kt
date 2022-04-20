package app.seven.branchcx.core.model

class PaginatedModel<T>(
    val items: List<T>,
    val currentPage: Int,
    val perPage: Int,
    val totalPage: Int
)