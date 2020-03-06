package br.com.unip.cardapio.repository.persistence

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent
import org.springframework.util.ReflectionUtils

class CascadeSaveMongoEventListener : AbstractMongoEventListener<Any>() {

    @Autowired
    private val mongoOperations: MongoOperations? = null

    override fun onBeforeConvert(event: BeforeConvertEvent<Any>) {
        val source = event.source
        ReflectionUtils.doWithFields(source.javaClass, CascadeCallback(source, mongoOperations!!))
    }
}