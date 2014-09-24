package com.cland.elearning



import org.junit.*
import grails.test.mixin.*

@TestFor(CertificateController)
@Mock(Certificate)
class CertificateControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/certificate/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.certificateInstanceList.size() == 0
        assert model.certificateInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.certificateInstance != null
    }

    void testSave() {
        controller.save()

        assert model.certificateInstance != null
        assert view == '/certificate/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/certificate/show/1'
        assert controller.flash.message != null
        assert Certificate.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/certificate/list'

        populateValidParams(params)
        def certificate = new Certificate(params)

        assert certificate.save() != null

        params.id = certificate.id

        def model = controller.show()

        assert model.certificateInstance == certificate
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/certificate/list'

        populateValidParams(params)
        def certificate = new Certificate(params)

        assert certificate.save() != null

        params.id = certificate.id

        def model = controller.edit()

        assert model.certificateInstance == certificate
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/certificate/list'

        response.reset()

        populateValidParams(params)
        def certificate = new Certificate(params)

        assert certificate.save() != null

        // test invalid parameters in update
        params.id = certificate.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/certificate/edit"
        assert model.certificateInstance != null

        certificate.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/certificate/show/$certificate.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        certificate.clearErrors()

        populateValidParams(params)
        params.id = certificate.id
        params.version = -1
        controller.update()

        assert view == "/certificate/edit"
        assert model.certificateInstance != null
        assert model.certificateInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/certificate/list'

        response.reset()

        populateValidParams(params)
        def certificate = new Certificate(params)

        assert certificate.save() != null
        assert Certificate.count() == 1

        params.id = certificate.id

        controller.delete()

        assert Certificate.count() == 0
        assert Certificate.get(certificate.id) == null
        assert response.redirectedUrl == '/certificate/list'
    }
}
