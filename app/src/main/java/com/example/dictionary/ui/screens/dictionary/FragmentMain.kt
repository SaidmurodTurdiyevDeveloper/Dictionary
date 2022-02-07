package com.example.dictionary.ui.screens.dictionary

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.data.model.Event
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.databinding.FragmentMainBinding
import com.example.dictionary.databinding.ScreenHeaderLayoutBinding
import com.example.dictionary.ui.adapter.AdapterDictionary
import com.example.dictionary.ui.dialogs.DialogDictionary
import com.example.dictionary.ui.dialogs.DialogText
import com.example.dictionary.ui.menu.MenuItemDictionary
import com.example.dictionary.ui.viewModel.impl.dictionary.ViewModelMain
import com.example.dictionary.utils.extention.loadOnlyOneTimeObserver
import com.example.dictionary.utils.extention.showToast
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FragmentMain constructor(var defViewModel: ViewModelMain? = null) : Fragment(R.layout.fragment_main),
    NavigationView.OnNavigationItemSelectedListener {

    /*
    *      Private empty objects
    * */
    private val binding: FragmentMainBinding by viewBinding()
    private var bindingHeader: ScreenHeaderLayoutBinding? = null

    /**
     * ViewModel
     * */
    private lateinit var viewModel: ViewModelMain
    @Inject
    lateinit var adapter: AdapterDictionary

    /*
    * Observers are created
    * */
    private val loadDictionaryListObservers = Observer<Event<List<DictionaryEntity>>> {
        adapter.submitList(it.peekContent())
        binding.defRecyclerView.spRefresh.isRefreshing = false
    }

    private val loadLearnCountObservers = Observer<Event<String>> { event ->
        loadOnlyOneTimeObserver(event) {
            binding.learnCount.text = this
        }
    }

    private val openDictionaryWordListObserver = Observer<Event<Long>> { event ->
        loadOnlyOneTimeObserver(event) {
            val action = FragmentMainDirections.openDictionaryWords(this)
            findNavController().navigate(action)
        }
    }

    private val openDictionaryItemObserver = Observer<Event<Long>> { event ->
        loadOnlyOneTimeObserver(event) {
            val action = FragmentMainDirections.openDictionaryItm(this)
            findNavController().navigate(action)
        }
    }

    private val dayNightObserver = Observer<Event<Boolean>> {
        val data = it.peekContent()
        if (data) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    private val updateObserver = Observer<Event<DictionaryEntity>> { event ->
        loadOnlyOneTimeObserver(event) { DialogDictionary(requireActivity(), "Edit").submit { item -> event.block?.invoke(item) }.show(this) }
    }
    private val deleteObserver = Observer<Event<DictionaryEntity>> { event ->
        loadOnlyOneTimeObserver(event) {
            DialogText(requireActivity(), "Delete").setListener { event.block?.invoke(this) }.show("This ${this.name} might be remove?")
        }
    }

    private val openHomeScreenObserver = Observer<Event<Unit>> {
        loadOnlyOneTimeObserver(it) {
            closeMenu()
        }
    }

    private val openArchiveScreenObserver = Observer<Event<Unit>> {
        loadOnlyOneTimeObserver(it) {
            findNavController().navigate(FragmentMainDirections.openArchive())
        }
    }

    private val addDictionaryObserver = Observer<Event<DictionaryEntity>> { event ->
        loadOnlyOneTimeObserver(event) { DialogDictionary(requireActivity(), "Add").submit { item -> event.block?.invoke(item) }.show() }
    }

    private val openSettingScreenObserver = Observer<Event<Unit>> {
        loadOnlyOneTimeObserver(it) { findNavController().navigate(FragmentMainDirections.openSetting()) }
    }

    private val openGameScreenObserver = Observer<Event<Unit>> {
        loadOnlyOneTimeObserver(it) { findNavController().navigate(FragmentMainDirections.openGame()) }
    }

    private val openChooseLanguageScreenObserver = Observer<Event<Unit>> {
        loadOnlyOneTimeObserver(it) { findNavController().navigate(FragmentMainDirections.openChooseCauntryTwo()) }
    }

    private val openAppInfoScreenObserver = Observer<Event<Unit>> {
        loadOnlyOneTimeObserver(it) { findNavController().navigate(FragmentMainDirections.openAppInfo()) }
    }

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    private val openAnotherActionBarObserver = Observer<Event<Unit>> { event ->
        loadOnlyOneTimeObserver(event) {
            binding.floatingBtnHome.hide()
            binding.defSelectionActionBar.selectingActionBar.isVisible = true
            binding.actionBarHome.isVisible = false
            onBackButton(true)
            binding.drawerLayoutHome.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            binding.defRecyclerView.spRefresh.setOnRefreshListener {
                binding.defRecyclerView.spRefresh.isRefreshing = false
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n", "NotifyDataSetChanged")
    private val closeAnotherActionBarObserver = Observer<Event<Unit>> { event ->
        loadOnlyOneTimeObserver(event) {
            binding.floatingBtnHome.show()
            binding.defSelectionActionBar.selectingActionBar.isVisible = false
            binding.actionBarHome.isVisible = true
            //onBackButton(false)
            unLockMenu()
            binding.defRecyclerView.spRefresh.isVisible = true
            binding.defRecyclerView.spRefresh.setOnRefreshListener {
                viewModel.loadData()
                viewModel.loadCountLearnedWord()
            }
        }
    }

    private val selectAllCheckboxObserver = Observer<Event<Boolean>> { event ->
        loadOnlyOneTimeObserver(event) {
            if (this)
                binding.defSelectionActionBar.actionBarSelectAll.setBackgroundResource(R.drawable.day_night_ic_checked)
            else
                binding.defSelectionActionBar.actionBarSelectAll.setBackgroundResource(R.drawable.day_night_ic_checkbox)

        }
    }

    private val showMessageObserver = Observer<Event<String>> { event ->
        loadOnlyOneTimeObserver(event) {
            DialogText(requireContext(), "Message").setListener {
                event.block?.invoke("delete")
            }.show(this)
        }
    }

    private val showToastObserver = Observer<Event<String>> { event ->
        loadOnlyOneTimeObserver(event) {
            requireContext().showToast(this)
        }
    }

    private val showErrorObserver = Observer<Event<String>> { event ->
        loadOnlyOneTimeObserver(event) {
            binding.defErrorLayout.errorLayout.isVisible = true
            binding.defErrorLayout.tvError.text = this
        }
    }

    private val showSnackBarObserver = Observer<Event<String>> { event ->
        loadOnlyOneTimeObserver(event) {
            Snackbar.make(binding.defRecyclerView.list, this, Snackbar.LENGTH_LONG).setAction("Undo") {
                event?.block?.invoke("Retire")
            }.show()
        }
    }

    /*
    * Override Funs
    * */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindingHeader = ScreenHeaderLayoutBinding.bind(binding.navViewHome.getHeaderView(0)!!)
        viewModel = defViewModel ?: ViewModelProvider(requireActivity())[ViewModelMain::class.java]
        registerObservers()
        loading()
        viewModel.loadData()
        viewModel.loadCountLearnedWord()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home_nav_draw_main_menu -> {
                viewModel.openHome()
            }
            R.id.change_language_nav_draw_main_menu -> {
                viewModel.openChangeLanguage()
            }
            R.id.game_nav_draw_main_menu -> {
                viewModel.openGame()
            }
            R.id.setting_nav_draw_main_menu -> {
                viewModel.openSetting()
            }
            R.id.arxiv_nav_draw_main_menu -> {
                viewModel.openArxive()
            }
            R.id.app_info_nav_draw_main_menu -> {
                viewModel.openAppInfo()
            }
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingHeader = null
    }

    /*
    * Private Funs
    * */
    private fun loading() {
        /*
        * Adapter is created
        * */
        adapter.submitListenerItemTouch {
            viewModel.clickItem(it)
        }
        adapter.submitLongPressListener { view, data, _ ->
            MenuItemDictionary(
                requireActivity(),
                view
            ).setListnerOpenThisDictionary {
                viewModel.openDictionaryItem(data.id)
            }.setlistenerDelete {
                viewModel.delete(data)
            }.setlistenerSelect {
                lifecycleScope.launch {
                    viewModel.openAnotherActionBar()
                    delay(300)
                    viewModel.clickItem(data)
                }
            }.setlistenerUpdate {
                viewModel.update(data)
            }.show()
        }
        // Adapter connected with recyclerview
        binding.defRecyclerView.list.layoutManager = LinearLayoutManager(activity)
        binding.defRecyclerView.list.adapter = adapter

        /*
        * Floating Button is created
        * */
        floatingButton()

        binding.floatingBtnHome.setOnClickListener {
            viewModel.add()
        }

        /*
        * MenuButton
         * */
        binding.menuBtnHome.setOnClickListener {
            binding.drawerLayoutHome.openDrawer(GravityCompat.START, true)
        }
        bindingHeader?.closeMenuButtonHome?.setOnClickListener {
            closeMenu()
        }
        bindingHeader?.dayNightButtonHome?.setOnClickListener {
            viewModel.dayNightClick()
        }
        /*
        * Set NaivagationView Listeners
        * */
        binding.navViewHome.setNavigationItemSelectedListener(this)

        /*
        * Set ActionBar Listeners
        * */
        binding.defSelectionActionBar.igbBack.setOnClickListener {
            requireActivity().onBackPressed()
            //viewModel.cancelSelected()
        }

        binding.defSelectionActionBar.actionBarDelete.setOnClickListener { viewModel.deleteAll() }

        binding.defSelectionActionBar.actionBarSelectAll.setOnClickListener {
            viewModel.checkAll()
        }
        binding.defRecyclerView.spRefresh.setOnRefreshListener {
            viewModel.loadData()
            viewModel.loadCountLearnedWord()
        }
    }

    private fun registerObservers() {
        val owner: LifecycleOwner = this
        viewModel.loadDictionaryListLiveData.observe(viewLifecycleOwner, loadDictionaryListObservers)
        viewModel.loadCountLearnWordsLiveData.observe(viewLifecycleOwner, loadLearnCountObservers)
        viewModel.clickItemLiveData.observe(owner, openDictionaryWordListObserver)
        viewModel.deleteLiveData.observe(owner, deleteObserver)
        viewModel.dayNighttClickLiveData.observe(viewLifecycleOwner, dayNightObserver)
        viewModel.updateLiveData.observe(owner, updateObserver)
        viewModel.openHomeLiveData.observe(owner, openHomeScreenObserver)
        viewModel.openArxiveLiveData.observe(owner, openArchiveScreenObserver)
        viewModel.addLiveData.observe(owner, addDictionaryObserver)
        viewModel.openSettingLiveData.observe(owner, openSettingScreenObserver)
        viewModel.openGameLiveData.observe(owner, openGameScreenObserver)
        viewModel.openChangeLanguageLiveData.observe(owner, openChooseLanguageScreenObserver)
        viewModel.openAppInfoLiveData.observe(owner, openAppInfoScreenObserver)
        viewModel.openAnotherActionBarLiveData.observe(owner, openAnotherActionBarObserver)
        viewModel.closeAnotherActionBarLiveData.observe(owner, closeAnotherActionBarObserver)
        viewModel.selectCheckBoxWhichSelectAll.observe(owner, selectAllCheckboxObserver)
        viewModel.openDictionaryItemLiveData.observe(viewLifecycleOwner, openDictionaryItemObserver)
        viewModel.showMessageLiveData.observe(viewLifecycleOwner, showMessageObserver)
        viewModel.showErrorLiveData.observe(viewLifecycleOwner, showErrorObserver)
        viewModel.showToastLiveData.observe(viewLifecycleOwner, showToastObserver)
        viewModel.showSnackbarLiveData.observe(viewLifecycleOwner, showSnackBarObserver)
        lifecycleScope.launchWhenCreated {
            viewModel.loadingScreenLivedata.collectLatest { cond ->
                binding.defLoading.loadingLayout.isVisible = cond
                if (cond) {
                    binding.defLoading.progress.show()
                } else {
                    binding.defLoading.progress.hide()
                }
            }
        }
    }

    private fun floatingButton() {
        binding.defRecyclerView.list.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && binding.floatingBtnHome.visibility == View.VISIBLE) {
                    binding.floatingBtnHome.shrink()
                } else {
                    binding.floatingBtnHome.extend()
                }
            }
        })
    }

    private fun closeMenu() {
        binding.drawerLayoutHome.closeDrawers()
    }

    private fun unLockMenu() {
        binding.drawerLayoutHome.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    private fun onBackButton(cond: Boolean) {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(cond) {
                override fun handleOnBackPressed() {
                    if (isEnabled) {
                        viewModel.cancelSelected()
                        remove()
                    }
                }
            }
            )
    }
}
