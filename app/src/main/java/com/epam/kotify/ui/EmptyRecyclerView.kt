package com.epam.kotify.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class EmptyRecyclerView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : RecyclerView(context, attributeSet) {

    var emptyView: View? = null

    private val dataObserver = object : AdapterDataObserver() {
        override fun onChanged() {
            super.onChanged()
            adapter?.let { adapter ->
                emptyView?.let { view ->
                    if (adapter.itemCount == 0) {
                        this@EmptyRecyclerView.visibility = GONE
                        view.visibility = View.VISIBLE
                    } else {
                        this@EmptyRecyclerView.visibility = View.VISIBLE
                        view.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(dataObserver)
        dataObserver.onChanged()
    }

}