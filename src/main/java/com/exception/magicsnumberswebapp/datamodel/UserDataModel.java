package com.exception.magicsnumberswebapp.datamodel;  
  
import com.exception.magicsnumbersws.entities.User;
import java.util.Collections;
import java.util.List;  
import javax.faces.model.ListDataModel;  
import org.primefaces.model.SelectableDataModel;  
import org.springframework.beans.BeanUtils;
  
public class UserDataModel extends ListDataModel<User> implements SelectableDataModel<User> {    
  
    private List<User> users;    
    
    public UserDataModel() {  
    }  
  
    public UserDataModel(List<User> data) {  
        super(data);  
        this.users = data;
    }  

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    public int nextUserId(){
        if(users == null || users.isEmpty()){
            return 0;
        }
        User user = Collections.max(users);
        return user.getId() +1;
    }
    
    @Override  
    public User getRowData(String rowKey) {                   
        List<User> users = (List<User>) getWrappedData();   
        for(User user : users) {  
            if(user.getId().toString().equals(rowKey)){
                User userCopy = new User();
                BeanUtils.copyProperties(user,userCopy);
                return  userCopy;  
            }
        }            
        return null;  
    }  
  
    @Override  
    public Object getRowKey(User user) {  
        return user.getId();
    }  
}