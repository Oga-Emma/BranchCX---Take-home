package app.seven.branchcx.modules.landing.data.model

sealed class LandingUIEvent {
    object UserAuthenticated: LandingUIEvent()
    object UserNotAuthenticated: LandingUIEvent()
}