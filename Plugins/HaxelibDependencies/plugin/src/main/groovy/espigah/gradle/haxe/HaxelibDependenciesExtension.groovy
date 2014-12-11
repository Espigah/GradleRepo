package espigah.gradle.haxe;
import org.gradle.api.Project;
import espigah.gradle.haxe.LibObject;
import espigah.gradle.haxe.projects.openfl.OpenFlProject;
import espigah.gradle.haxe.projects.IHaxeProject;
class HaxelibDependenciesExtension {

	Project project;
	IHaxeProject haxeProject;
	HaxelibDependenciesExtension(Project project)
	{
		this.project = project;
	}

	List<LibObject> libList = [];
	
	def addLib(String libName, String version="")	{	
		libList.add(new LibObject(libName,version))
		//println "$version,$libName"		
	}
	
	def testLib(Closure closure)	{	
		
		closure()	
	}
	
	def openflMode(String mainFile) 
	{
		haxeProject = new OpenFlProject(mainFile);
	}
	def haxeMode(String mainFile) {}
	def flambeMode(String mainFile) {}
}

	 // @ClosureParams(value=String.class, options="byte")
	// def addLib(Closure closure)	{
		// version;
		// def name;
		// println "$version,$name"
		// println closure;
	// }
// project.haxelib.extensions.addLib = project.container(ElementA) {  
    // def elementAExtension =  project.extension.collectionA.extensions.create("$it", ElementA, "$it".toString())  
    // project.extension.collectionA."$it".extensions.'collectionB' =  project.container(ElementB)  
    // elementAExtension  
// }

class AsgardRegion {
  String name

  }