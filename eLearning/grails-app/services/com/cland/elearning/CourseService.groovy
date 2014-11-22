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
	def findCourseForEvent(Event eventInstance){
		def courselist = Course.createCriteria().list{
			events{
				idEq(eventInstance?.id)
			}
		}
		if(courselist.size() > 0) return courselist.first() else return null
	}
} //end calss
