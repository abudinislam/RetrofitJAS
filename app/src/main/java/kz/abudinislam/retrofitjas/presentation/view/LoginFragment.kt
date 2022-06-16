package kz.abudinislam.retrofitjas.presentation.view

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kz.abudinislam.retrofitjas.R
import kz.abudinislam.retrofitjas.databinding.FragmentLoginBinding
import kz.abudinislam.retrofitjas.domain.model.LoginApprove
import kz.abudinislam.retrofitjas.presentation.viewmodel.LoginViewModel
import kz.abudinislam.retrofitjas.utils.LoadingState
import kz.abudinislam.retrofitjas.utils.LoadingState.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Response.error
import kotlin.coroutines.CoroutineContext

class LoginFragment : Fragment(), CoroutineScope {


    private lateinit var binding: FragmentLoginBinding

    private val viewModel by viewModel<LoginViewModel>()

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    private lateinit var prefSettings: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        prefSettings =
            context?.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE) as SharedPreferences
        if (prefSettings.getString(SESSION_ID_KEY, null) != null) {
            findNavController().navigate(R.id.action_loginFragment_to_navigation_movies)
        }
        editor = prefSettings.edit()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        addTextChangeListeners()
        onLoginClick()
    }

//    private fun initViewModel() {
//        viewModel =
//            ViewModelProvider(
//                this,
//                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
//            )[LoginViewModel::class.java]
//    }

    private fun onLoginClick() {
        binding.btnLogin.setOnClickListener {
            hideKeyboard(requireActivity())

            viewModel.resetErrorInputPassword()
            viewModel.resetErrorInputName()


            //if (!binding.etUsername.text.isNullOrBlank() && !binding.etPassword.text.isNullOrBlank()) {

            val userName  = binding.etUsername.text.toString().trim()
            val password  = binding.etPassword.text.toString().trim()

                val data = LoginApprove(
                    username = userName,
                    password = password,
                    request_token = ""
                )
                viewModel.login(data, userName, password)
                observeLoadingState()
            }
//        else {
//                Toast.makeText(requireContext(), "Введите данные", Toast.LENGTH_SHORT).show()
//            }
        }



    private fun observeViewModel() {
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                "Неверные данные"
            } else {
                null
            }
            binding.etPassword.error = message
        }
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                "Неверные данные"
            } else {
                null
            }
            binding.etUsername.error = message
        }

    }

    private fun addTextChangeListeners() {
        binding.etUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputPassword()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }


    private fun observeLoadingState() {
        viewModel.loadingState.observe(viewLifecycleOwner) {
            when (it) {
                LoginViewModel.LoadingState.HideLoading -> binding.pbLoading.visibility =
                    View.VISIBLE
                LoginViewModel.LoadingState.Finish -> binding.pbLoading.visibility = View.GONE
                LoginViewModel.LoadingState.ShowLoading -> {
                    viewModel.sessionId.observe(viewLifecycleOwner) {
                        sessionId = it
                        putDataIntoPref(sessionId)
                        try {
                            findNavController().navigate(R.id.action_loginFragment_to_navigation_movies)
                        } catch (e: Exception) {
                        }
                    }
                }
                else -> Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun putDataIntoPref(string: String) {
        editor.putString(SESSION_ID_KEY, string)
        editor.commit()
        binding.etUsername.text = null
        binding.etPassword.text = null
    }

    //скрыть клавиатуру
    private fun hideKeyboard(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    companion object {

        private var sessionId: String = ""
        const val APP_SETTINGS = "Settings"
        const val SESSION_ID_KEY = "SESSION_ID"
    }
}

