package espigah.gradle.haxe;

import org.gradle.api.Project;
import org.gradle.api.Plugin;

import espigah.gradle.haxe.HaxelibDependenciesExtension;
import espigah.gradle.haxe.LibObject;

import espigah.gradle.haxe.internal.Constants;

class HaxelibDependenciesPlugin implements Plugin<Project> {
	
	def haxelib = [];
	 void apply(Project project) {
		applyExtension(project)
		applyTasks(project)
    }
	
	void applyExtension(Project project) {
		//final HaxeExtension extension = project.getExtensions().create("haxe", HaxelibDependenciesExtension.class, project, instantiator);      
		haxelib = project.extensions.create(Constants.MAIN_EXTENSION, HaxelibDependenciesExtension, project)		
	}
	
	void applyTasks(Project project) {
		  project.task(Constants.MAIN_TASK) << {			
			haxelib.haxeProject.parse(haxelib.libList);
        }
	}
	
}