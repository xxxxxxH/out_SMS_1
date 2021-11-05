package net.basicmodel.entity

import java.io.Serializable

/**
 * Copyright (C) 2021,2021/11/5, a Tencent company. All rights reserved.
 *
 * User : v_xhangxie
 *
 * Desc :
 */
data class RecordItemEntity(
    var createDate: String = "",
    var number: String = "",
    var smsFrom: String = "",
    var content: String = "",
    var msgId: String = "",
    var relativeTime: String = "",
    var isChecked: Boolean
) : Serializable
