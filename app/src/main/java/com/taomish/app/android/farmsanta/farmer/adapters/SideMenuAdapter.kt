package com.taomish.app.android.farmsanta.farmer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.IntDef
import androidx.recyclerview.widget.RecyclerView
import com.taomish.app.android.farmsanta.farmer.R
import com.taomish.app.android.farmsanta.farmer.databinding.ItemDividerBinding
import com.taomish.app.android.farmsanta.farmer.databinding.SideMenuItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

const val VIEW_TYPE_ITEM = 1
const val VIEW_TYPE_DIVIDER = 2
class SideMenuAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var menuItems: List<SideMenuItem>? = null
    var onItemClick = {_: SideMenuItem? ->}
    private var previousPosition = NavigationMenu.NONE
    var selectedPosition = NavigationMenu.HOME
    set(value) {
        previousPosition = selectedPosition
        notifyItemChanged(selectedPosition)
        field = value
        notifyItemChanged(selectedPosition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) ItemViewHolder(
            SideMenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ) else {
            DividerItem(
                ItemDividerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) holder.bindData(menuItems?.get(position))
    }

    override fun getItemCount(): Int = menuItems?.size ?: 0

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount.minus(3)) VIEW_TYPE_DIVIDER else VIEW_TYPE_ITEM
    }

    inner class ItemViewHolder(val binding: SideMenuItemBinding): RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                previousPosition = selectedPosition
                notifyItemChanged(selectedPosition)
                selectedPosition = bindingAdapterPosition
                notifyItemChanged(selectedPosition)
                CoroutineScope(Dispatchers.Main).launch {
                    delay(300)
                    onItemClick.invoke(menuItems?.get(selectedPosition))
                }
            }
        }

        fun bindData(sideMenuItem: SideMenuItem?) {
            sideMenuItem?.let {
                binding.tvName.text = it.title
                binding.tvName.isPressed = selectedPosition == bindingAdapterPosition
                if (selectedPosition == bindingAdapterPosition
                    && selectedPosition == NavigationMenu.HOME) {
                    binding.ivIcon.setImageResource(R.drawable.ic_home_new)
                } else {
                    binding.ivIcon.setImageResource(it.drawableRes)
                }
            }
            binding.root.isSelected = selectedPosition == bindingAdapterPosition
        }

    }

    inner class DividerItem(val binding: ItemDividerBinding): RecyclerView.ViewHolder(binding.root)

    fun getPreviousPosition(): Int {
        return previousPosition
    }
}

data class SideMenuItem(
    @DrawableRes val drawableRes: Int,
    val title: String,
    @NavigationMenu val action: Int? = null
)

annotation class NavigationMenu {
    companion object {
        const val NONE = -1
        const val HOME = 0
        const val MY_QUERIES = 1
        const val FARM_TALK = 2
        const val POP = 3
        const val PROFILE = 4
        const val ADVISORY = 5
        const val CROP_CALENDAR = 6
        const val MARKET_ANALYSIS = 7
        const val FERTILIZER_CALCULATOR = 8
        const val INVITE = 10
        const val ABOUT = 11
        const val LOGOUT = 12
        const val LANGUAGE = 13
        const val NUTRI_SOURCE = 14
    }
}