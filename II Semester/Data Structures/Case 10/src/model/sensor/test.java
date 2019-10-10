 package model.sensor;

import java.lang.reflect.Type;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;

public class test {
	//https://stackoverflow.com/questions/6187724/force-gson-to-use-specific-constructor
	public static void main(String[] args)
	  {
	    UserAction action = new UserAction(UUID.randomUUID());
	    action.setUserId("user1");

	    String json = new Gson().toJson(action);
	    System.out.println(json);

	    GsonBuilder gsonBuilder = new GsonBuilder();
	    gsonBuilder.registerTypeAdapter(UserAction.class, new UserActionInstanceCreator());
	    Gson gson = gsonBuilder.create();
	    UserAction actionCopy = gson.fromJson(json, UserAction.class);
	    System.out.println(gson.toJson(actionCopy));
	  }
	}

	class UserActionInstanceCreator implements InstanceCreator<UserAction>
	{
	  @Override
	  public UserAction createInstance(Type type)
	  {
	    return new UserAction(null);
	  }
	}

	class UserAction
	{
	  private final UUID id;
	  private String userId;

	  public UserAction()
	  {
	    throw new RuntimeException("this constructor is not used");
	  }

	  public UserAction(UUID uuid)
	  {
	    this.id = uuid;
	  }

	  void setUserId(String userId)
	  {
	    this.userId = userId;
	  }
}
