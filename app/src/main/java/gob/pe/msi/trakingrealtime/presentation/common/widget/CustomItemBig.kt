package gob.pe.msi.trakingrealtime.presentation.common.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import gob.pe.msi.trakingrealtime.R
import gob.pe.msi.trakingrealtime.databinding.ViewWidgetItemBigBinding
import gob.pe.msi.trakingrealtime.presentation.common.utils.OnSingleClickListener

class CustomItemBig (
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    private var binding: ViewWidgetItemBigBinding = ViewWidgetItemBigBinding.inflate(LayoutInflater.from(context))


    var txtHeader: String? = null
    var btnGo: TextView? = null
    //var imgItem: Int = 0
    var txtTitle: String? = null
    var txtSubTitle: String? = null
    var status: String? = null
    var itemIcon: Int = 0
    var listener: ItemListener? = null
    var enabledButton: Boolean = true
    var tag: String? = null
    var isDetail: Boolean = false

    init {
        //LayoutInflater.from(context).inflate(R.layout.view_widget_item_big, this, true)
        attrs?.let { setupAttrs(context, it) }

        binding.event = this
        addView(binding.root)
        setupUI()

    }



    private fun setupAttrs(context: Context, attrs: AttributeSet) {
        val type = context.obtainStyledAttributes(attrs, R.styleable.CustomItemBig, 0, 0)
        this.setOnClickListener {
        }

        try {
            tag = type.getString(R.styleable.CustomItemBig_tagItem)
            txtHeader = type.getString(R.styleable.CustomItemBig_header)
            btnGo?.text = type.getString(R.styleable.CustomItemBig_btnGo)
            itemIcon = type.getResourceId(R.styleable.CustomItemBig_imgItem, R.drawable.product_placeholder)
            txtTitle = type.getString(R.styleable.CustomItemBig_title)
            txtSubTitle = type.getString(R.styleable.CustomItemBig_subTitle)
            //status = type.getString(R.styleable.CustomItemBig_txtStatus)
            enabledButton = type.getBoolean(R.styleable.CustomItemBig_enableButton, true)
            isDetail = type.getBoolean(R.styleable.CustomItemBig_isDetail, false)

            binding.btnGo.setOnClickListener {
                tag?.let {
                    listener!!.testClick(it)
                }

            }
            /*binding.btnGo.setOnClickListener(object : OnSingleClickListener() {
                override fun onSingleClick(v: View) {
                    listener!!.testClick(tag)
                }
            })*/

        } finally {
            type.recycle()
        }
    }
    private fun setupUI() {
        setup()
    }
    // Provide methods to interact with the views
    fun setHeader(text: String) {
        binding.txtHeader.text = text
    }
    fun setBtnGo(text: String) {
        binding.btnGo.text = text
    }
    fun setTitle(text: String) {
        binding.txtTitle.text = text
    }
    fun setSubTitle(text: String) {
        binding.txtSubTitle.text = text
    }
    /*fun setStatus(text: String) {
        binding.txtStatus.text = text
    }*/

    fun setOnclickItem(listener: ItemListener) {
        this.listener = listener
    }

    fun setDrawableResource(drawable: Drawable?) {
        binding.imgItem.setImageDrawable(drawable)
    }

    /*fun setIcon(url: String) {
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.ic_visa_recurrency)
            .error(R.drawable.ic_visa_recurrency)
            .into(binding.imgItem);

    }*/
    fun enabledComponentes(value: Boolean) {
        binding.flItem.isEnabled = value
        enabledButton=value
        setAlpha()
    }

    fun setAlpha(){
        if (!enabledButton) {
            binding.txtHeader.alpha = 0.3f
            binding.btnGo.alpha= 0.3f
        } else {
            binding.txtHeader.alpha = 1f
            binding.btnGo.alpha= 1f

        }
    }

    fun setVisibleDetail(value: Boolean) {
        binding.clBody.visibility = if (value) View.VISIBLE else View.GONE
    }

    private fun setup() {
        btnGo = binding.btnGo
        binding.txtHeader.text = txtHeader
        binding.txtTitle.text = txtTitle
        binding.txtSubTitle.text = txtSubTitle
        //binding.txtStatus.text = status
        enabledComponentes(enabledButton)
        setVisibleDetail(isDetail)
        if (tag.isNullOrEmpty()) {
            tag = ""
        }
    }





    interface ItemListener {
        fun testClick(tag: String?)
    }
}