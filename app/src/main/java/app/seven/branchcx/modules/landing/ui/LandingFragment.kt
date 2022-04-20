package app.seven.branchcx.modules.landing.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import app.seven.branchcx.R
import app.seven.branchcx.modules.landing.data.model.LandingUIEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LandingFragment : Fragment() {
    private val viewModel: LandingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.landing_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUiEventListeners()
    }

    private fun setupUiEventListeners() {
        viewModel.uiEvents.observe(viewLifecycleOwner) { item ->
            when(item){
                is LandingUIEvent.UserNotAuthenticated -> {
                    activity?.let { activity ->
                        val navHostFragment = activity.supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                        navHostFragment.navController.navigate(R.id.action_landingFragment_to_loginFragment)
                    }
                }
                is LandingUIEvent.UserAuthenticated -> {
                    activity?.let { activity ->
                        val navHostFragment = activity.supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
                        navHostFragment.navController.navigate(R.id.action_landingFragment_to_thread_nav_graph)
                    }
                }
            }
        }
    }

}