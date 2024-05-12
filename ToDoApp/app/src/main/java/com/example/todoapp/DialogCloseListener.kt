/*
 * Created by Seyed on 8/3/21, 2:05 PM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 8/3/21, 1:55 PM
 */

package com.example.todoapp

import android.content.DialogInterface

interface DialogCloseListener {
    fun handleDialogClose(dialog: DialogInterface)
}
