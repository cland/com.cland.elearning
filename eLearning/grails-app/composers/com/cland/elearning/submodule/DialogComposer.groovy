package com.cland.elearning.submodule

import org.zkoss.zk.ui.Component
import org.zkoss.zul.*
import org.zkoss.zk.ui.event.*

import com.cland.elearning.*

class DialogComposer {
    Window self
	
	def springSecurityService
	Person curPerson
	
    def afterCompose = {Component comp ->
        if(!springSecurityService.isLoggedIn()){
			println("Not logged in...")
			 return;
        }else{
		println("Welcome... logged in...")
        }
		curPerson = (Person) springSecurityService.currentUser
		//examView.setVisible(false)
		redraw()
    } //end after Compose
	
	
	private redraw(int activePage = 0) {

			
	}//end function

		void onClick_closeBtn(Event e){
		println("Close dialog - Dialog!")
		//def params=self.params
		self.detach()
	}
    
} //end classs