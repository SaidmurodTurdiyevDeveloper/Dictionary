package com.example.dictionary.ui.screens.words

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentWordsPlayBinding
import com.example.dictionary.ui.dialogs.DialogText
import com.example.dictionary.ui.viewModel.impl.word.ViewModelWordsPlayImplament
import com.example.dictionary.utils.extention.loadOnlyOneTimeObserver
import com.example.dictionary.utils.extention.showToast
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentWordsPlay constructor(var defViewModel: ViewModelWordsPlayImplament? = null) : Fragment(R.layout.fragment_words_play) {
    private lateinit var viewModel: ViewModelWordsPlayImplament
    private val binding: FragmentWordsPlayBinding by viewBinding()
    private var dictionaryId = -1L
    private var defList = ArrayList<Char>()
    private var isFirst = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = defViewModel ?: ViewModelProvider(requireActivity())[ViewModelWordsPlayImplament::class.java]
        getDictionaryId()
        loadListener()
        loadObserver()
        viewModel.loadWordsList(dictionaryId)
    }

    private fun loadListener() {
        binding.defActionBar.igbBack.setOnClickListener {
            viewModel.close()
        }

        binding.fbDone.setOnClickListener {
            val text = if (isFirst) binding.defFirstKeyboard.tvKeyboard.text.toString() else binding.defSecondKeyboard.tvKeyboard.text.toString()
            viewModel.done(text)
        }
        binding.fbNext.setOnClickListener {
            viewModel.next()
        }
        binding.fbPrevious.setOnClickListener {
            viewModel.previous()
        }
        binding.chBFirstWord.setOnCheckedChangeListener { _, isChecked ->
            viewModel.check(isChecked)
        }
        binding.chBSecondWord.setOnCheckedChangeListener { _, isChecked ->
            viewModel.check(!isChecked)
        }
        //FirstKeyboard
        binding.defFirstKeyboard.btFirst.customSetOnClickListener()
        binding.defFirstKeyboard.btSecond.customSetOnClickListener()
        binding.defFirstKeyboard.btThird.customSetOnClickListener()
        binding.defFirstKeyboard.btFour.customSetOnClickListener()
        binding.defFirstKeyboard.btFive.customSetOnClickListener()
        binding.defFirstKeyboard.btSix.customSetOnClickListener()
        binding.defFirstKeyboard.btSeven.customSetOnClickListener()
        binding.defFirstKeyboard.btEigth.customSetOnClickListener()
        binding.defFirstKeyboard.btNine.customSetOnClickListener()
        binding.defFirstKeyboard.btTen.customSetOnClickListener()
        binding.defFirstKeyboard.btEleven.customSetOnClickListener()
        binding.defFirstKeyboard.btTwelve.customSetOnClickListener()
        binding.defFirstKeyboard.btThirteen.customSetOnClickListener()
        binding.defFirstKeyboard.btForteen.customSetOnClickListener()
        binding.defFirstKeyboard.btFiveteen.customSetOnClickListener()
        binding.defFirstKeyboard.btSixteen.customSetOnClickListener()
        binding.defFirstKeyboard.btSeventeen.customSetOnClickListener()
        binding.defFirstKeyboard.btEighteen.customSetOnClickListener()
        binding.defFirstKeyboard.btNineteen.customSetOnClickListener()
        binding.defFirstKeyboard.btTwenty.customSetOnClickListener()
        binding.defFirstKeyboard.btTwentyOne.customSetOnClickListener()
        binding.defFirstKeyboard.btTwentyTwo.customSetOnClickListener()
        binding.defFirstKeyboard.btTwentyThree.customSetOnClickListener()
        binding.defFirstKeyboard.btTwentyFour.customSetOnClickListener()
        binding.defFirstKeyboard.btTwentyFive.customSetOnClickListener()
        binding.defFirstKeyboard.btTwentySix.customSetOnClickListener()
        binding.defFirstKeyboard.btTwentySeven.customSetOnClickListener()
        binding.defFirstKeyboard.btTwentyEight.customSetOnClickListener()
        binding.defFirstKeyboard.btTwentyNine.customSetOnClickListener()
        binding.defFirstKeyboard.btThirty.customSetOnClickListener()
        //FirstKeyboard
        binding.defSecondKeyboard.btFirst.customSetOnClickListener()
        binding.defSecondKeyboard.btSecond.customSetOnClickListener()
        binding.defSecondKeyboard.btThird.customSetOnClickListener()
        binding.defSecondKeyboard.btFour.customSetOnClickListener()
        binding.defSecondKeyboard.btFive.customSetOnClickListener()
        binding.defSecondKeyboard.btSix.customSetOnClickListener()
        binding.defSecondKeyboard.btSeven.customSetOnClickListener()
        binding.defSecondKeyboard.btEigth.customSetOnClickListener()
        binding.defSecondKeyboard.btNine.customSetOnClickListener()
        binding.defSecondKeyboard.btTen.customSetOnClickListener()
        binding.defSecondKeyboard.btEleven.customSetOnClickListener()
        binding.defSecondKeyboard.btTwelve.customSetOnClickListener()
        binding.defSecondKeyboard.btThirteen.customSetOnClickListener()
        binding.defSecondKeyboard.btForteen.customSetOnClickListener()
        binding.defSecondKeyboard.btFiveteen.customSetOnClickListener()
        binding.defSecondKeyboard.btSixteen.customSetOnClickListener()
        binding.defSecondKeyboard.btSeventeen.customSetOnClickListener()
        binding.defSecondKeyboard.btEighteen.customSetOnClickListener()
        binding.defSecondKeyboard.btNineteen.customSetOnClickListener()
        binding.defSecondKeyboard.btTwenty.customSetOnClickListener()
        binding.defSecondKeyboard.btTwentyOne.customSetOnClickListener()
        binding.defSecondKeyboard.btTwentyTwo.customSetOnClickListener()
        binding.defSecondKeyboard.btTwentyThree.customSetOnClickListener()
        binding.defSecondKeyboard.btTwentyFour.customSetOnClickListener()
        binding.defSecondKeyboard.btTwentyFive.customSetOnClickListener()
        binding.defSecondKeyboard.btTwentySix.customSetOnClickListener()
        binding.defSecondKeyboard.btTwentySeven.customSetOnClickListener()
        binding.defSecondKeyboard.btTwentyEight.customSetOnClickListener()
        binding.defSecondKeyboard.btTwentyNine.customSetOnClickListener()
        binding.defSecondKeyboard.btThirty.customSetOnClickListener()
    }

    private fun loadObserver() {
        viewModel.toastLiveData.observe(viewLifecycleOwner) { event ->
            loadOnlyOneTimeObserver(event) {
                requireActivity().showToast(this)
            }
        }

        viewModel.messageLiveData.observe(viewLifecycleOwner) { event ->
            loadOnlyOneTimeObserver(event) {
                DialogText(requireContext(), "Message").show(this)
            }
        }

        viewModel.snackBarLiveData.observe(viewLifecycleOwner) { event ->
            loadOnlyOneTimeObserver(event) {
                Snackbar.make(requireContext(), binding.defWordsPlayLayout, this, Snackbar.LENGTH_LONG).setAction(event.text) {
                    event.block?.invoke("Reload")
                }.show()
            }
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) { event ->
            loadOnlyOneTimeObserver(event) {
                binding.tvFirstWord.text = this
                binding.tvSecondWord.text = this
            }
        }

        viewModel.closeWindowLiveData.observe(viewLifecycleOwner) { event ->
            loadOnlyOneTimeObserver(event) {
                findNavController().navigateUp()
            }
        }

        viewModel.titleLiveData.observe(viewLifecycleOwner) {
            binding.defActionBar.tvTitle.text = it
        }

        viewModel.isChangedDataLiveData.observe(viewLifecycleOwner) {
            isFirst = it
            binding.chBFirstWord.isChecked = it
            binding.chBSecondWord.isChecked = !it
            lifecycleScope.launch {
                try {
                    if (it) {
                        defList.clear()
                        defList.addAll(viewModel.firstWordList.shuffled())
                        val list = mutableSetOf<Char>()
                        defList.forEach { char ->
                            list.add(char)
                        }
                        delay(200)
                        putKeyboardFirst(list)
                        putKeyboardSecond(emptySet())
                        binding.tvSecondWord.text = viewModel.currentData.wordSecond
                        binding.tvFirstWord.text = ""

                    } else {
                        defList.clear()
                        defList.addAll(viewModel.secondWordList.shuffled())
                        val list = mutableSetOf<Char>()
                        defList.forEach { char ->
                            list.add(char)
                        }
                        delay(200)
                        putKeyboardSecond(list)
                        putKeyboardFirst(emptySet())
                        binding.tvFirstWord.text = viewModel.currentData.wordFirst
                        binding.tvSecondWord.text = ""
                    }
                } catch (e: Exception) {
                    requireContext().showToast("Something wrong")
                }
            }
        }

        viewModel.currentTextLiveData.observe(viewLifecycleOwner) {
            loadOnlyOneTimeObserver(it) {
                if (isFirst)
                    binding.defFirstKeyboard.tvKeyboard.text = this
                else
                    binding.defSecondKeyboard.tvKeyboard.text = this
            }
        }
    }

    private fun getDictionaryId() {
        val bundle = requireArguments()
        dictionaryId = bundle.getLong("Id")
    }

    private fun putKeyboardFirst(list: Set<Char>) {
        binding.defFirstKeyboard.llFirstLine.isVisible = list.isNotEmpty()
        binding.defFirstKeyboard.llSecondLine.isVisible = list.size > 10
        binding.defFirstKeyboard.llThiredLine.isVisible = list.size > 20
        if (list.isNotEmpty()) {
            binding.defFirstKeyboard.btFirst.setKeyboardText(true, 0, list)
            binding.defFirstKeyboard.btSecond.setKeyboardText(list.size > 1, 1, list)
            binding.defFirstKeyboard.btThird.setKeyboardText(list.size > 2, 2, list)
            binding.defFirstKeyboard.btFour.setKeyboardText(list.size > 3, 3, list)
            binding.defFirstKeyboard.btFive.setKeyboardText(list.size > 4, 4, list)
            binding.defFirstKeyboard.btSix.setKeyboardText(list.size > 5, 5, list)
            binding.defFirstKeyboard.btSeven.setKeyboardText(list.size > 6, 6, list)
            binding.defFirstKeyboard.btEigth.setKeyboardText(list.size > 7, 7, list)
            binding.defFirstKeyboard.btNine.setKeyboardText(list.size > 8, 8, list)
            binding.defFirstKeyboard.btTen.setKeyboardText(list.size > 9, 9, list)
            binding.defFirstKeyboard.btEleven.setKeyboardText(list.size > 10, 10, list)
            binding.defFirstKeyboard.btTwelve.setKeyboardText(list.size > 11, 11, list)
            binding.defFirstKeyboard.btThirteen.setKeyboardText(list.size > 12, 12, list)
            binding.defFirstKeyboard.btForteen.setKeyboardText(list.size > 13, 13, list)
            binding.defFirstKeyboard.btFiveteen.setKeyboardText(list.size > 14, 14, list)
            binding.defFirstKeyboard.btSixteen.setKeyboardText(list.size > 15, 15, list)
            binding.defFirstKeyboard.btSeventeen.setKeyboardText(list.size > 16, 16, list)
            binding.defFirstKeyboard.btEighteen.setKeyboardText(list.size > 17, 17, list)
            binding.defFirstKeyboard.btNineteen.setKeyboardText(list.size > 18, 18, list)
            binding.defFirstKeyboard.btTwenty.setKeyboardText(list.size > 19, 19, list)
            binding.defFirstKeyboard.btTwentyOne.setKeyboardText(list.size > 20, 20, list)
            binding.defFirstKeyboard.btTwentyTwo.setKeyboardText(list.size > 21, 21, list)
            binding.defFirstKeyboard.btTwentyThree.setKeyboardText(list.size > 22, 22, list)
            binding.defFirstKeyboard.btTwentyFour.setKeyboardText(list.size > 23, 23, list)
            binding.defFirstKeyboard.btTwentyFive.setKeyboardText(list.size > 24, 24, list)
            binding.defFirstKeyboard.btTwentySix.setKeyboardText(list.size > 25, 25, list)
            binding.defFirstKeyboard.btTwentySeven.setKeyboardText(list.size > 26, 26, list)
            binding.defFirstKeyboard.btTwentyEight.setKeyboardText(list.size > 27, 27, list)
            binding.defFirstKeyboard.btTwentyNine.setKeyboardText(list.size > 28, 28, list)
            binding.defFirstKeyboard.btThirty.setKeyboardText(list.size > 29, 29, list)
        }
    }

    private fun putKeyboardSecond(list: Set<Char>) {
        binding.defSecondKeyboard.llFirstLine.isVisible = list.isNotEmpty()
        binding.defSecondKeyboard.llSecondLine.isVisible = list.size > 10
        binding.defSecondKeyboard.llThiredLine.isVisible = list.size > 20
        if (list.isNotEmpty()) {
            binding.defSecondKeyboard.btFirst.setKeyboardText(true, 0, list)
            binding.defSecondKeyboard.btSecond.setKeyboardText(list.size > 1, 1, list)
            binding.defSecondKeyboard.btThird.setKeyboardText(list.size > 2, 2, list)
            binding.defSecondKeyboard.btFour.setKeyboardText(list.size > 3, 3, list)
            binding.defSecondKeyboard.btFive.setKeyboardText(list.size > 4, 4, list)
            binding.defSecondKeyboard.btSix.setKeyboardText(list.size > 5, 5, list)
            binding.defSecondKeyboard.btSeven.setKeyboardText(list.size > 6, 6, list)
            binding.defSecondKeyboard.btEigth.setKeyboardText(list.size > 7, 7, list)
            binding.defSecondKeyboard.btNine.setKeyboardText(list.size > 8, 8, list)
            binding.defSecondKeyboard.btTen.setKeyboardText(list.size > 9, 9, list)
            binding.defSecondKeyboard.btEleven.setKeyboardText(list.size > 10, 10, list)
            binding.defSecondKeyboard.btTwelve.setKeyboardText(list.size > 11, 11, list)
            binding.defSecondKeyboard.btThirteen.setKeyboardText(list.size > 12, 12, list)
            binding.defSecondKeyboard.btForteen.setKeyboardText(list.size > 13, 13, list)
            binding.defSecondKeyboard.btFiveteen.setKeyboardText(list.size > 14, 14, list)
            binding.defSecondKeyboard.btSixteen.setKeyboardText(list.size > 15, 15, list)
            binding.defSecondKeyboard.btSeventeen.setKeyboardText(list.size > 16, 16, list)
            binding.defSecondKeyboard.btEighteen.setKeyboardText(list.size > 17, 17, list)
            binding.defSecondKeyboard.btNineteen.setKeyboardText(list.size > 18, 18, list)
            binding.defSecondKeyboard.btTwenty.setKeyboardText(list.size > 19, 19, list)
            binding.defSecondKeyboard.btTwentyOne.setKeyboardText(list.size > 20, 20, list)
            binding.defSecondKeyboard.btTwentyTwo.setKeyboardText(list.size > 21, 21, list)
            binding.defSecondKeyboard.btTwentyThree.setKeyboardText(list.size > 22, 22, list)
            binding.defSecondKeyboard.btTwentyFour.setKeyboardText(list.size > 23, 23, list)
            binding.defSecondKeyboard.btTwentyFive.setKeyboardText(list.size > 24, 24, list)
            binding.defSecondKeyboard.btTwentySix.setKeyboardText(list.size > 25, 25, list)
            binding.defSecondKeyboard.btTwentySeven.setKeyboardText(list.size > 26, 26, list)
            binding.defSecondKeyboard.btTwentyEight.setKeyboardText(list.size > 27, 27, list)
            binding.defSecondKeyboard.btTwentyNine.setKeyboardText(list.size > 28, 28, list)
            binding.defSecondKeyboard.btThirty.setKeyboardText(list.size > 29, 29, list)
        }
    }

    private fun Button.setKeyboardText(cond: Boolean, index: Int, list: Set<Char>) {
        isVisible = cond
        if (cond) {
            text = try {
                list.elementAt(index).toString()
            } catch (e: java.lang.Exception) {
                "#"
            }
        }
    }

    private fun Button.customSetOnClickListener() {
        setOnClickListener {
            val x = text.toString()
            if (text.isNotEmpty()) {
                defList.removeFirstChar(x.first())
                isVisible = defList.contains(x.first())
                viewModel.clickKeyboard(x)
            }
        }
    }

    private fun ArrayList<Char>.removeFirstChar(char: Char) {
        var index = -1
        var i = 0
        while (i < defList.size) {
            if (defList[i] == char) {
                index = i
                i = defList.size
            }
            i++
        }
        if (index != -1)
            removeAt(index)
    }
}








