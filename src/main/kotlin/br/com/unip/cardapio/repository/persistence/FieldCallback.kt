package br.com.unip.cardapio.repository.persistence

import org.springframework.data.annotation.Id
import org.springframework.util.ReflectionUtils
import java.lang.reflect.Field

class FieldCallback : ReflectionUtils.FieldCallback {

    var isIdFound: Boolean = false

    override fun doWith(field: Field) {
        ReflectionUtils.makeAccessible(field)

        if (field.isAnnotationPresent(Id::class.java)) {
            isIdFound = true
        }
    }
}