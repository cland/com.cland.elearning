class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(view:"/index")
		//"/"(controller:"course",action:"list")
		"500"(view:'/error')
		"404"(view:'/error')
		
		name module: "/module/show/$id/$headline" {
			controller = "module"
			action = "show"
		}
		name submodule: "/subModule/show/$id/$headline" {
			controller = "subModule"
			action = "show"
		}
		name course: "/course/show/$id/$headline" {
			controller = "course"
			action = "show"
		}
	}
}
