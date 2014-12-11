package espigah.gradle.haxe.projects.openfl;

import espigah.gradle.haxe.projects.IHaxeProject;
import espigah.gradle.haxe.LibObject;

class OpenFlProject implements  IHaxeProject
{
	OpenFlFile file	
	def mainFile;
	OpenFlProject(String mainFile)
	{		
		this.mainFile = mainFile;
		file = new OpenFlFile(mainFile)		
	}
	
	void parse(List<LibObject> libList)
	{		
		libList.eachWithIndex{ item, index ->
			//println "#_"+index +"#_"+ item.name + "#_"+item.version;
			file.searchLib(item)
		}		
		save ();
	}
	
	private save ()
	{
		println "SAVE"
		file.save();		
	}
}
