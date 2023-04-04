package ru.my_career.config

import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

typealias MongoDB = CoroutineDatabase

object Database {
    private const val mongoConnectionString = "mongodb://root:root@localhost:27017"
    private const val dbName = "test"
    private val mongoClient = KMongo.createClient(mongoConnectionString).coroutine

    val db: MongoDB = mongoClient.getDatabase(dbName)
}
