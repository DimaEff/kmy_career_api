package ru.my_career.common.db

import kotlinx.serialization.Contextual
import org.litote.kmongo.Id

typealias MongoId<T> = @Contextual Id<T>