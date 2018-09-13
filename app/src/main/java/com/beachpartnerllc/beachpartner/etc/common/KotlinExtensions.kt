package com.beachpartnerllc.beachpartner.etc.common

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Samuel Robert <sam@spotsoon.com>
 * @created on 16 May 2018 at 6:05 PM
 */


/********         Arch Components         ***************/
inline fun <reified T : ViewModel> Fragment.getViewModel(factory: ViewModelProvider.Factory, ofActivity: Boolean = true): T {
	return if (ofActivity) ViewModelProviders.of(activity!!, factory)[T::class.java]
	else ViewModelProviders.of(this, factory)[T::class.java]
}

inline fun <reified T : ViewModel> AppCompatActivity.getViewModel(factory: ViewModelProvider.Factory): T {
	return ViewModelProviders.of(this, factory)[T::class.java]
}


/********          View Components          **************/
inline fun <reified T : RecyclerView.ViewHolder> ViewGroup.create(createHolder: (View) -> T, @LayoutRes res: Int): T {
	val inflater = LayoutInflater.from(context)
	val view = inflater.inflate(res, this, false)
	return createHolder(view)
}

inline fun <reified T : RecyclerView.ViewHolder, R : ViewDataBinding> ViewGroup.bind(construct: (R) -> T, @LayoutRes resId: Int): T {
	val inflater = LayoutInflater.from(context)
	val binding: R = DataBindingUtil.inflate(inflater, resId, this, false)
	return construct(binding)
}

inline fun <reified R : ViewDataBinding> ViewGroup.bind(@LayoutRes resId: Int): R {
	val inflater = LayoutInflater.from(context)
	return DataBindingUtil.inflate(inflater, resId, this, false)
}

inline fun <reified R : ViewDataBinding> LayoutInflater.bind(@LayoutRes layoutResId: Int, parent: ViewGroup?): R {
	return DataBindingUtil.inflate(this, layoutResId, parent, false)
}

inline fun <reified T : AppCompatActivity> Activity.startActivity(
		data: Bundle = Bundle(),
		isForResult: Boolean = false,
		requestCode: Int = 0
) {
	val intent = Intent(this, T::class.java)
	intent.putExtras(data)
	if (isForResult) startActivityForResult(intent, requestCode)
	else startActivity(intent)
}

/*******              String                 ***************/

fun String?.isEmail() = this != null && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String?.isMobile() = this != null && Patterns.PHONE.matcher(this).matches()

fun String?.isPassword() = this != null && length > 7

fun String?.isName() = this != null && this.matches("[a-zA-Z]+".toRegex())

