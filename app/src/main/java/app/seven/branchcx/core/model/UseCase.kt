package app.seven.branchcx.core.model

abstract  class UseCase<T, Z> {
    abstract fun call(request: T): Z
}

abstract  class SuspendUseCase<T, Z> {
    abstract suspend  fun call(request: T): Z
}

abstract  class NoParamUseCase<Z> {
    abstract fun call(): Z
}