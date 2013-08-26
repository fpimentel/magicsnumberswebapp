package com.exception.magicsnumberswebapp.datamodel;  
  
import com.exception.magicsnumbersws.entities.User;
import java.util.List;  
import javax.faces.model.ListDataModel;  
import org.primefaces.model.SelectableDataModel;  
  
public class UserDataModel extends ListDataModel<User> implements SelectableDataModel<User> {    
  
    private List<User> users;
    private User selectedUser;
    
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

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }
      
    @Override  
    public User getRowData(String rowKey) {  
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  
          
        List<User> users = (List<User>) getWrappedData();  
          
        for(User user : users) {  
            if(user.getUserName().equals(rowKey))  
                return user;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(User user) {  
        return user.getUserName();  
    }  
}