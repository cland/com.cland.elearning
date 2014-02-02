package com.cland.elearning



import org.junit.*
import grails.test.mixin.*

@TestFor(AttachmentHolderController)
@Mock(AttachmentHolder)
class AttachmentHolderControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/attachmentHolder/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.attachmentHolderInstanceList.size() == 0
        assert model.attachmentHolderInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.attachmentHolderInstance != null
    }

    void testSave() {
        controller.save()

        assert model.attachmentHolderInstance != null
        assert view == '/attachmentHolder/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/attachmentHolder/show/1'
        assert controller.flash.message != null
        assert AttachmentHolder.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/attachmentHolder/list'

        populateValidParams(params)
        def attachmentHolder = new AttachmentHolder(params)

        assert attachmentHolder.save() != null

        params.id = attachmentHolder.id

        def model = controller.show()

        assert model.attachmentHolderInstance == attachmentHolder
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/attachmentHolder/list'

        populateValidParams(params)
        def attachmentHolder = new AttachmentHolder(params)

        assert attachmentHolder.save() != null

        params.id = attachmentHolder.id

        def model = controller.edit()

        assert model.attachmentHolderInstance == attachmentHolder
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/attachmentHolder/list'

        response.reset()

        populateValidParams(params)
        def attachmentHolder = new AttachmentHolder(params)

        assert attachmentHolder.save() != null

        // test invalid parameters in update
        params.id = attachmentHolder.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/attachmentHolder/edit"
        assert model.attachmentHolderInstance != null

        attachmentHolder.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/attachmentHolder/show/$attachmentHolder.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        attachmentHolder.clearErrors()

        populateValidParams(params)
        params.id = attachmentHolder.id
        params.version = -1
        controller.update()

        assert view == "/attachmentHolder/edit"
        assert model.attachmentHolderInstance != null
        assert model.attachmentHolderInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/attachmentHolder/list'

        response.reset()

        populateValidParams(params)
        def attachmentHolder = new AttachmentHolder(params)

        assert attachmentHolder.save() != null
        assert AttachmentHolder.count() == 1

        params.id = attachmentHolder.id

        controller.delete()

        assert AttachmentHolder.count() == 0
        assert AttachmentHolder.get(attachmentHolder.id) == null
        assert response.redirectedUrl == '/attachmentHolder/list'
    }
}
