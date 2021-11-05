package net.basicmodel.entity

import java.io.Serializable

/**
 * Copyright (C) 2021,2021/11/5, a Tencent company. All rights reserved.
 *
 * User : v_xhangxie
 *
 * Desc :
 */
data class MsgRecordEntity(
    val content: ArrayList<RecordItemEntity>? = null
) : Serializable
