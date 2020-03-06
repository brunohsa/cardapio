package br.com.unip.cardapio.repository.persistence

import org.apache.tomcat.util.file.ConfigFileLoader.getSource
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.util.ReflectionUtils
import java.lang.reflect.Field


class CascadeCallback(val source: Any, val mongoOperations: MongoOperations) : ReflectionUtils.FieldCallback {

    @Throws(IllegalArgumentException::class, IllegalAccessException::class)
    override fun doWith(field: Field) {
        ReflectionUtils.makeAccessible(field)

        if (field.isAnnotationPresent(DBRef::class.java) && field.isAnnotationPresent(CascadeSave::class.java)) {

            val fieldValue = field.get(getSource())
            if (fieldValue != null) {
                val callback = FieldCallback()
                ReflectionUtils.doWithFields(fieldValue.javaClass, callback)

                mongoOperations.save(fieldValue)
            }
        }
    }
}