package app.seven.branchcx.modules.messaging.ui.thread_list

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import app.seven.branchcx.databinding.MessagingThreadListFragmentBinding
import app.seven.branchcx.modules.messaging.ui.thread_list.component.ThreadListAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MessagingThreadListFragment : Fragment() {
    private var _binding: MessagingThreadListFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var threadAdapter: ThreadListAdapter
    private val viewModel: ThreadListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = MessagingThreadListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupUiEventListeners()

        viewModel.fetchData(force = true)
    }

    private fun setupView() {
        binding.swipeToRefreshLayout.setOnRefreshListener {
            viewModel.fetchData(force = true)
            Handler(Looper.getMainLooper()).postDelayed(Runnable { // Stop animation (This will be after 3 seconds)
                binding.swipeToRefreshLayout.isRefreshing = false
            }, 4000)
        }

        threadAdapter = ThreadListAdapter()
        threadAdapter.setOnItemClickListener {
            val action = MessagingThreadListFragmentDirections.actionThreadListFragmentToThreadDetailsFragment(it)
            view?.findNavController()?.navigate(action)
        }
        binding.rvThreadList.apply {
            adapter = threadAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun setupUiEventListeners() {
        viewModel.uiEvents.observe(viewLifecycleOwner) {
            when (it) {
                is ThreadListUiEvents.DataFetchingFailed -> {
                    binding.pbLoading.visibility = View.GONE
                    showError(it.message)
                }
                is ThreadListUiEvents.DataFetchingSuccess -> {
                    binding.pbLoading.visibility = View.GONE
                    binding.rvThreadList.visibility = View.VISIBLE
                    threadAdapter.differ.submitList(it.data)
                }
                is ThreadListUiEvents.Error -> {
                    binding.pbLoading.visibility = View.GONE
                    showError(it.message)
                }
                is ThreadListUiEvents.Loading -> {
                    binding.rvThreadList.visibility = View.GONE
                    binding.pbLoading.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }


}