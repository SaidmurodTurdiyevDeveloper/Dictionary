package com.example.dictionary.ui.screens.dictionary

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.data.model.Event
import com.example.dictionary.data.source.local.room.entity.DictionaryEntity
import com.example.dictionary.databinding.FragmentMainBinding
import com.example.dictionary.databinding.HeaderLayoutMenuBinding
import com.example.dictionary.ui.adapter.AdapterDictionaryWithSelect
import com.example.dictionary.ui.dialogs.DialogDictionary
import com.example.dictionary.ui.dialogs.DialogText
import com.example.dictionary.ui.menu.MenuItemDictionary
import com.example.dictionary.ui.viewModel.dictionary.ViewModelMain
import com.example.dictionary.utils.extention.loadOnlyOneTimeObserver
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FragmentMain : Fragment(R.layout.fragment_main),
    NavigationView.OnNavigationItemSelectedListener {

    /*
    *      Private empty objects
    * */
    private val binding: FragmentMainBinding by viewBinding()
    private var bindingHeader: HeaderLayoutMenuBinding? = null

    /*
    *       Latinent vars
    * */
    private lateinit var adapterDictionaryWithSelect: AdapterDictionaryWithSelect

    /*
    * ViewModel is created
    * */
    private val viewModel: ViewModelMain by viewModels()


    /*
    * Observers are created
    * */
    private val _loadObservers = Observer<Event<List<DictionaryEntity>>> {
        openLoading()
        adapterDictionaryWithSelect.submitList(it.peekContent())
        closeLoading()
    }

    private val _loadLearnCountObservers = Observer<Event<Long>> {
        openLoading()
        val data = it
        if (data != null) {
            if (data.peekContent() > 999L) {
                val d: Float = data.peekContent().toFloat()
                binding.learnCount.text = ((d / 1000F).toString() + "K")
            } else {
                binding.learnCount.text = data.peekContent().toString()
            }
        }
        closeLoading()
    }

    private val _clickItemObserver = Observer<Event<Long>> {
        openLoading()
        val data = it.getContentIfNotHandled()
        if (data != null) {
            val action = FragmentMainDirections.actionFragmentMainToFragmentWordsLearn()
            action.arguments.putLong("id", data)
            findNavController().navigate(action)
        }
        closeLoading()
    }

    private val _openItemObserver = Observer<Event<Long>> {
        openLoading()
        val data = it.getContentIfNotHandled()
        if (data != null) {
            val action = FragmentMainDirections.actionFragmentMainToFragmentDictionaryItem()
            action.arguments.putLong("id", data)
            findNavController().navigate(action)
        }
        closeLoading()
    }

    private val _deleteObserver = Observer<Event<DictionaryEntity>> {
        openLoading()
        val data = it.getContentIfNotHandled()
        if (data != null) {
            val dialog = DialogText(requireActivity(), "Delete")
            dialog.submit { viewModel.delete(data) }
            dialog.show("This ${data.name} is delete")
        }
        closeLoading()
    }

    private val _darkNightObserver = Observer<Event<Boolean>> {
        val data = it.peekContent()
        if (data) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    private val _editObserver = Observer<Event<DictionaryEntity>> { event ->
        loadOnlyOneTimeObserver(event) {
            openLoading()
            val dilaog = DialogDictionary(requireActivity(), "Edit")
            dilaog.submit { item ->
                event.block?.invoke(item)
            }
            dilaog.show(this)
        }
        closeLoading()
    }

    private val _openHomeObserver = Observer<Event<Unit>> {
        openLoading()
        val data = it.getContentIfNotHandled()
        if (data != null)
            closeMenu()
        closeLoading()
    }

    private val _openArxiveObserver = Observer<Event<Unit>> {
        openLoading()
        val data = it.getContentIfNotHandled()
        if (data != null)
            findNavController().navigate(
                FragmentMainDirections.actionFragmentMainToFragmentArxiv()
            )
        closeLoading()
    }

    private val _addDataObserver = Observer<Event<DictionaryEntity>> { event ->
        loadOnlyOneTimeObserver(event) {
            openLoading()
            val dilaog = DialogDictionary(requireActivity(), "Add")
            dilaog.submit { item ->
                event.block?.invoke(item)
            }
            dilaog.show()
        }
        closeLoading()
    }

    private val _openSettingObserver = Observer<Event<Unit>> {
        openLoading()
        val data = it.getContentIfNotHandled()
        if (data != null)
            findNavController().navigate(
                FragmentMainDirections.actionFragmentMainToFragmentSetting()
            )
        closeLoading()
    }

    private val _openGameObserver = Observer<Event<Unit>> {
        openLoading()
        val data = it.getContentIfNotHandled()
        if (data != null)
            findNavController().navigate(
                FragmentMainDirections.actionFragmentMainToFragmentGame()
            )
        closeLoading()
    }

    private val _openChangeLanguageObserver = Observer<Event<Unit>> {
        openLoading()
        val data = it.getContentIfNotHandled()
        if (data != null)
            findNavController().navigate(FragmentMainDirections.actionFragmentMainToFragmentChooseLanguages2())
        closeLoading()
    }

    private val _openInfoObserver = Observer<Event<Unit>> {
        openLoading()
        val data = it.getContentIfNotHandled()
        if (data != null)
            findNavController().navigate(FragmentMainDirections.actionFragmentMainToFragmentAppInfo())
        closeLoading()
    }

    /*
    * Observers are created which items are selected time
    * */
    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    private val _openSelectedActionBarObserver = Observer<Event<Unit>> {
        val data = it.getContentIfNotHandled()
        if (data != null) {
            binding.floatingBtnHome.hide()
            binding.defSelectingActionBar.actionBar.visibility = View.VISIBLE
            binding.actionBarHome.visibility = View.INVISIBLE
            onBackButton(true)
            binding.drawerLayoutHome.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    private val _closeActionBarObserver = Observer<Event<Unit>> {
        binding.floatingBtnHome.show()
        adapterDictionaryWithSelect.selectedDismiss()
        binding.defSelectingActionBar.actionBar.visibility = View.GONE
        binding.actionBarHome.visibility = View.VISIBLE
        onBackButton(false)
        unLockMenu()
    }

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    private val _deleteAllObserver = Observer<Event<Unit>> {
        binding.floatingBtnHome.show()
        viewModel.deleteAll()
        binding.defSelectingActionBar.actionBar.visibility = View.GONE
        binding.actionBarHome.visibility = View.VISIBLE
        adapterDictionaryWithSelect.selectedDismiss()
        onBackButton(false)
        unLockMenu()
    }

    private val _checkAllObserver = Observer<Event<Boolean>> {
        val cond = it.getContentIfNotHandled()
        if (cond != null) {
            if (cond)
                binding.defSelectingActionBar.actionBarSelectAll.setBackgroundResource(R.drawable.day_night_ic_checked)
            else
                binding.defSelectingActionBar.actionBarSelectAll.setBackgroundResource(R.drawable.day_night_ic_checkbox)
        }
    }

    /*
    * Override Funs
    * */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindingHeader =
            HeaderLayoutMenuBinding.bind(binding.navViewHome.getHeaderView(0)!!)
        registerObservers()
        loading()
        viewModel.loadData()
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
                viewModel.openInfo()
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
        adapterDictionaryWithSelect = AdapterDictionaryWithSelect(requireActivity())
        adapterDictionaryWithSelect.submitListenerItemOpen { id ->
            viewModel.clickItem(id)
        }
        adapterDictionaryWithSelect.submitListenerItemSelect { position ->
            viewModel.select(position)
        }
        adapterDictionaryWithSelect.submitLongPressListener { view, data, position ->
            val itemMenu = MenuItemDictionary(
                requireActivity(),
                view,
                {
                    adapterDictionaryWithSelect.selectedDismiss()
                    viewModel.openItem(data.id)
                },
                {
                    viewModel.onceCheck(position)
                }, {
                    viewModel.update(data)
                    adapterDictionaryWithSelect.selectedDismiss()
                }, {
                    viewModel.remove(data)
                    adapterDictionaryWithSelect.selectedDismiss()
                }
            )
            itemMenu.show()
        }
        // Adapter connected with recyclerview
        binding.listHome.layoutManager = LinearLayoutManager(activity)
        binding.listHome.adapter = adapterDictionaryWithSelect

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
        bindingHeader?.darckNightButtonHome?.setOnClickListener {
            viewModel.darkLightClick()
        }
        /*
        * Set NaivagationView Listeners
        * */
        binding.navViewHome.setNavigationItemSelectedListener(this)

        /*
        * Set ActionBar Listeners
        * */
        binding.defSelectingActionBar.igbBack.setOnClickListener { viewModel.cancelSelected() }

        binding.defSelectingActionBar.actionBarDelete.setOnClickListener { viewModel.deleteAll() }

        binding.defSelectingActionBar.actionBarSelectAll.setOnClickListener { viewModel.checkAll(true) }

    }

    private fun registerObservers() {
        val owner: LifecycleOwner = this
        viewModel.loadLiveData.observe(viewLifecycleOwner, _loadObservers)
        viewModel.learnCountLiveData.observe(viewLifecycleOwner, _loadLearnCountObservers)
        viewModel.clickItemLiveData.observe(owner, _clickItemObserver)
        viewModel.openItemLiveData.observe(owner,_openItemObserver)
        viewModel.deleteLiveData.observe(owner, _deleteObserver)
        viewModel.darkLightClickLiveData.observe(viewLifecycleOwner, _darkNightObserver)
        viewModel.editLiveData.observe(owner, _editObserver)
        viewModel.openHomeLiveData.observe(owner, _openHomeObserver)
        viewModel.openArxiveLiveData.observe(owner, _openArxiveObserver)
        viewModel.addLiveData.observe(owner, _addDataObserver)
        viewModel.openSettingLiveData.observe(owner, _openSettingObserver)
        viewModel.openGameLiveData.observe(owner, _openGameObserver)
        viewModel.openChangeLanguageLiveData.observe(owner, _openChangeLanguageObserver)
        viewModel.openInfoLiveData.observe(owner, _openInfoObserver)
        viewModel.openSelectedActionBarLiveData.observe(owner, _openSelectedActionBarObserver)
        viewModel.closeActionBarLiveData.observe(owner, _closeActionBarObserver)
        viewModel.deleteAllActionBarLiveData.observe(owner, _deleteAllObserver)
        viewModel.selectAllBarLiveData.observe(owner, _checkAllObserver)
    }

    private fun floatingButton() {
        binding.listHome.addOnScrollListener(object :
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

    private fun openLoading() {
        if (binding.layoutLoadingMain.visibility == View.GONE) {
            binding.progress.show()
            binding.layoutLoadingMain.visibility = View.VISIBLE
        }
    }

    private fun closeLoading() {
        if (binding.layoutLoadingMain.visibility == View.VISIBLE) {
            binding.progress.hide()
            binding.layoutLoadingMain.visibility = View.GONE
        }
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
                        isEnabled = false
                    } else {
                        requireActivity().onBackPressed()
                    }
                }
            }
            )
    }
}
