package kz.abudinislam.retrofitjas.utils

class Const {
    sealed class State {
        object ShowLoading : State()
        object HideLoading : State()
        object Finish : State()

    }
}