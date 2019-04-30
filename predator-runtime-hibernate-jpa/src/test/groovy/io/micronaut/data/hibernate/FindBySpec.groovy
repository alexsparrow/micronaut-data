package io.micronaut.data.hibernate

import io.micronaut.context.annotation.Property
import io.micronaut.test.annotation.MicronautTest
import org.hibernate.SessionFactory
import spock.lang.Specification

import javax.inject.Inject
import javax.sql.DataSource

@MicronautTest
@Property(name = "datasources.default.name", value = "mydb")
@Property(name = 'jpa.default.properties.hibernate.hbm2ddl.auto', value = 'create-drop')
class FindBySpec extends Specification {

    @Inject
    DataSource dataSource

    @Inject
    SessionFactory sessionFactory

    @Inject
    PersonRepository personRepository

    void "test setup"() {
        expect:
        dataSource != null
        sessionFactory != null
    }

    void "test find by name"() {
        when:
        Person p = personRepository.findByName("Fred")

        then:
        p == null

        when:
        sessionFactory.currentSession.persist(new Person(name: "Fred"))
        p = personRepository.findByName("Fred")

        then:
        p != null
        p.name == "Fred"
    }
}
