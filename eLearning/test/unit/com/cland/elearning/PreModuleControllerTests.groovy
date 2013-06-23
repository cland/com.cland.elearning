package com.cland.elearning



import org.junit.*
import grails.test.mixin.*

@TestFor(PreModuleController)
@Mock(PreModule)
class PreModuleControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/preModule/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.preModuleInstanceList.size() == 0
        assert model.preModuleInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.preModuleInstance != null
    }

    void testSave() {
        controller.save()

        assert model.preModuleInstance != null
        assert view == '/preModule/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/preModule/show/1'
        assert controller.flash.message != null
        assert PreModule.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/preModule/list'

        populateValidParams(params)
        def preModule = new PreModule(params)

        assert preModule.save() != null

        params.id = preModule.id

        def model = controller.show()

        assert model.preModuleInstance == preModule
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/preModule/list'

        populateValidParams(params)
        def preModule = new PreModule(params)

        assert preModule.save() != null

        params.id = preModule.id

        def model = controller.edit()

        assert model.preModuleInstance == preModule
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/preModule/list'

        response.reset()

        populateValidParams(params)
        def preModule = new PreModule(params)

        assert preModule.save() != null

        // test invalid parameters in update
        params.id = preModule.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/preModule/edit"
        assert model.preModuleInstance != null

        preModule.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/preModule/show/$preModule.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        preModule.clearErrors()

        populateValidParams(params)
        params.id = preModule.id
        params.version = -1
        controller.update()

        assert view == "/preModule/edit"
        assert model.preModuleInstance != null
        assert model.preModuleInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/preModule/list'

        response.reset()

        populateValidParams(params)
        def preModule = new PreModule(params)

        assert preModule.save() != null
        assert PreModule.count() == 1

        params.id = preModule.id

        controller.delete()

        assert PreModule.count() == 0
        assert PreModule.get(preModule.id) == null
        assert response.redirectedUrl == '/preModule/list'
    }
}
