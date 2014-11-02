package com.cland.elearning



import org.junit.*
import grails.test.mixin.*

@TestFor(RegistrationFormController)
@Mock(RegistrationForm)
class RegistrationFormControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/registrationForm/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.registrationFormInstanceList.size() == 0
        assert model.registrationFormInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.registrationFormInstance != null
    }

    void testSave() {
        controller.save()

        assert model.registrationFormInstance != null
        assert view == '/registrationForm/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/registrationForm/show/1'
        assert controller.flash.message != null
        assert RegistrationForm.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/registrationForm/list'

        populateValidParams(params)
        def registrationForm = new RegistrationForm(params)

        assert registrationForm.save() != null

        params.id = registrationForm.id

        def model = controller.show()

        assert model.registrationFormInstance == registrationForm
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/registrationForm/list'

        populateValidParams(params)
        def registrationForm = new RegistrationForm(params)

        assert registrationForm.save() != null

        params.id = registrationForm.id

        def model = controller.edit()

        assert model.registrationFormInstance == registrationForm
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/registrationForm/list'

        response.reset()

        populateValidParams(params)
        def registrationForm = new RegistrationForm(params)

        assert registrationForm.save() != null

        // test invalid parameters in update
        params.id = registrationForm.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/registrationForm/edit"
        assert model.registrationFormInstance != null

        registrationForm.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/registrationForm/show/$registrationForm.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        registrationForm.clearErrors()

        populateValidParams(params)
        params.id = registrationForm.id
        params.version = -1
        controller.update()

        assert view == "/registrationForm/edit"
        assert model.registrationFormInstance != null
        assert model.registrationFormInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/registrationForm/list'

        response.reset()

        populateValidParams(params)
        def registrationForm = new RegistrationForm(params)

        assert registrationForm.save() != null
        assert RegistrationForm.count() == 1

        params.id = registrationForm.id

        controller.delete()

        assert RegistrationForm.count() == 0
        assert RegistrationForm.get(registrationForm.id) == null
        assert response.redirectedUrl == '/registrationForm/list'
    }
}
