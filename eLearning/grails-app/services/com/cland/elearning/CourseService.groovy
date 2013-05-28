package com.cland.elearning
/**
 * 
 * @author Jay
 * handles anything related to couses
 */
class CourseService {

    def serviceMethod() {

    }
	def findCoursesFor(personId, courseId, role){
		Course.findAll ("from Registration as r, Course as c where r.persod.id=? and r.course.id=?",[personId],[courseId])
	}
} //end calss
