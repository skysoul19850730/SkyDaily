package com.skysoul.skydaily.module_accounts.activities.edit

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.WindowManager
import android.widget.Toast
import com.skysoul.skydaily.module_accounts.R
import com.skysoul.skydaily.module_accounts.model.beans.ExtraColumn
import kotlinx.android.synthetic.main.ac_dialog_extra.*

/**
 * Created by Administrator on 2018/4/17.
 */
class Edit_Extra_Dialog(context: Context, var listerner: OnEditDialogClickListerner) : AlertDialog(context) {

    interface OnEditDialogClickListerner {
        fun onClickOk(extra: ExtraColumn)
        fun onClickCancel()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.apply {
            setGravity(Gravity.CENTER)
            decorView.setPadding(0, 0, 0, 0)
            decorView.setPadding(0, 0, 0, 0)
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT
            attributes.height = WindowManager.LayoutParams.WRAP_CONTENT
            clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }

        setContentView(R.layout.ac_dialog_extra)
        btn_cancel.setOnClickListener{
            listerner.onClickCancel()
        }
        btn_save.setOnClickListener{
            var extraColumn = getExtraColumn()
            if (extraColumn == null) {
                Toast.makeText(context, "输入不完整", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            listerner.onClickOk(extraColumn)
        }
    }

    private fun getExtraColumn(): ExtraColumn? {
        if (TextUtils.isEmpty(et_title.text.toString()) || TextUtils.isEmpty(et_value.text.toString())) {
            return null
        }
        var extraColumn = mExtraColumn
        if(extraColumn==null){
            extraColumn = ExtraColumn()
        }
        extraColumn.key = et_title.text.toString()
        extraColumn.value = et_value.text.toString()
        return extraColumn
    }

    fun showWithExtraColumn(extra: ExtraColumn?) {
        super.show()
        et_title.setText(extra?.key ?: "")
        et_value.setText(extra?.value ?: "")
        mExtraColumn = extra
    }

    private var mExtraColumn: ExtraColumn? = null
}