package espigah.gradle.haxe.projects.openfl;
import espigah.gradle.haxe.LibObject;
import groovy.xml.StreamingMarkupBuilder;
import groovy.xml.*
import groovy.util.slurpersupport.*

import espigah.gradle.haxe.internal.Constants
import espigah.gradle.haxe.internal.Command

import espigah.gradle.haxe.internal.CommandFeedback;

class OpenFlFile {

	File srcFile
	def xmlFile
	
	OpenFlFile(String mainFile)
	{		
		srcFile = new File(mainFile)	
		xmlFile =  new XmlSlurper().parse(srcFile)
	}
	
	def searchLib(LibObject lib)
	{
		if(xmlFile.haxelib != null)
		{		
			def tagSize = xmlFile.haxelib.size();		
			def createNewLibEnable = true;
			for (int i = 0; i < tagSize; i++)
			{				
				def isValidLib = validationLib(xmlFile.haxelib[i],lib);
				//println "\t\t @isValidLib "+ isValidLib + "Index"+ i;
				if(isValidLib == true)
				{
					createNewLibEnable = false;
					new Command().config(xmlFile.haxelib[i])
					break;
				}
				else
				{					
					createNewLibEnable = true 
				}				
			}
			if(createNewLibEnable == true)
			{
				//println "\t\t\t @createLib "+ lib.name;
				
				createLib(lib, new Command().config(lib));
			}
		}
		else
		{
			createLib(lib, new Command().config(lib))
		}		
	}
	
	
	
	private boolean validationLib(haxelibNode,LibObject lib)
	{		
		//println "@validationLib "+  haxelibNode.@name
		if(haxelibNode.@name == lib.name)
		{		
			if(lib.version != "")
			{
				if(haxelibNode.@version != lib.version)
				{
					setVersionLib(haxelibNode,lib);
				}				
			}			
			return true;
		}
		return false;
	}
	
	private void setVersionLib(haxelibNode,LibObject lib)
	{
		if(haxelibNode.@version == null)
		{
			println "\t\t @setLibVersion == null";		
		}		
		haxelibNode.@version = lib.version;
		
	}
	
	private void createLib(LibObject lib, CommandFeedback commandFeedback = null)
	{
		if(commandFeedback.errorLevel == -1 || commandFeedback.errorLevel == 2)
		{return null};
		
		if(lib.version == null || lib.version == "")
		{
			xmlFile.appendNode{
				haxelib(name:lib.name)
			}
		}
		else
		{
			xmlFile.appendNode{
				haxelib(name:lib.name, version:lib.version)
			}
		}				
		// xmlFile.appendChild( Constants.OPENFL_NODE_LIB,  [name:lib.name, version:lib.version])
	}
	
	public save ()
	{		
		def inFile = new File( "file.xml" )
		def markupBuilder = new StreamingMarkupBuilder()
		markupBuilder.encoding = 'UTF-8'
		String xmlStr = XmlUtil.serialize( markupBuilder.bind { mkp.yield xmlFile } )
		inFile.write(xmlStr, "UTF-8")           
	}
}

//---------------------------------------SOFRIMENTO
	//xmlFile = new XmlParser().parse(srcFile)		nao funciona!!
//def stringsFile = new File(mainFile)
//stringsFile.write(file.xmlFile)

//def xml = markupBuilder.bind 
//{
//mkp.xmlDeclaration()					
//mkp.yieldUnescaped xmlFile

//}
//println    xml;
//println    XmlUtil.serialize(xml);

//inFile.withWriter () { outWriter ->  
//XmlUtil.serialize( markupBuilder.bind{
//mkp.xmlDeclaration()
//mkp.yield xmlFile }, outWriter )  
//println anode;
//def node = new NodeChild(xmlFile.project, null, null)
//println xml;
//inFile.write(anode, "UTF-8")


//def writer = new FileWriter('/tmp/file.xml')

////Option 1: Write XML all on one line
//def builder = new StreamingMarkupBuilder()
//writer << builder.bind {
//mkp.yield file.xmlFile
//}
//def xml = markupBuilder.bind 
//{
//mkp.xmlDeclaration()
////////yieldUnescaped 
//mkp.yield xmlFile
//}.toString()
//
//println XmlUtil.serialize(xml)
//def xmlOutput = new StringWriter()
//16.XmlUtil.serialize xmlString, xmlOutput
//
//def inFile = file( 'file.xml' )
//inFile.withWriter ('UTF-8') {
//XmlUtil.serialize( new StreamingMarkupBuilder().bind{ mkp.yield xmlFile } )
//}
