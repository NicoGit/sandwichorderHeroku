import models.Sandwich;
import models.User;
import play.Play;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;
import play.vfs.VirtualFile;

@OnApplicationStart
public class Bootstrap extends Job {
	  public void doJob(){
	       
	        if(Sandwich.count() == 0){
	            VirtualFile appRoot = VirtualFile.open(Play.applicationPath);
	            Play.javaPath.add(0,appRoot.child("test"));
	            Fixtures.loadModels("data.yml");
	            Fixtures.loadModels("data2.yml");

	           
	            //nhumeau.save();
	        }
	  
	  User nhumeau = new User(
	            "nhumeau",
	            "Nicolas",
	            "Humeau",
	            "nicolas.humeau@mines-nantes.fr",
	            true);
	    }
	  

}
