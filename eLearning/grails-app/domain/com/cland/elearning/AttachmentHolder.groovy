package com.cland.elearning

class AttachmentHolder {
	static attachmentable = true
	static belongsTo = [person:Person]
    static constraints = {
    }
	def beforeInsert = {
	// your code goes here
	}
	def beforeUpdate = {
	// your code goes here
	}

	def onLoad = {
	// your code goes here
	}
	/**
	 * To ensure that all attachments are removed when the "onwer" domain is deleted.
	 */
	transient def beforeDelete = {
		withNewSession{
		  removeAttachments()
		}
	  }
	String toString(){
	// TODO: make me interesting
	}
} //end of class
